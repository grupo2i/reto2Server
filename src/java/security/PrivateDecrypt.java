package security;

import exception.UnexpectedErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Contains the methods meant to decode the email credential with AES.
 * 
 * @author Ander Vicente, Aitor Fidalgo
 */
public class PrivateDecrypt {
    
    private static final Logger LOGGER = Logger.getLogger(PrivateDecrypt.class.getName());
    /**
     * Relative path of the email credentials file.
     */
    private static final String EMAIL_CREDENTIALS_PATH = ResourceBundle
            .getBundle("properties.properties").getString("emailCredentialsPath");
    /**
     * Secret word used to decode.
     */
    private static final String SECRET_WORD = ResourceBundle
            .getBundle("properties.properties").getString("secretWord");
    /**
     * Salt used to decode.
     */
    private static final byte[] SALT = "esta es la salt!".getBytes(); 
    
    
    /**
     * Decodes the email credentials file with AES/CBC/PKCS5Padding.
     * 
     * @return Decoded email credentials fromatted the following way: user?password.
     * @throws exception.UnexpectedErrorException If anything goes wrong.
     */
    public static String decodeEmailCredentials() throws UnexpectedErrorException {
        String ret = null;
        KeySpec keySpec;
        SecretKeyFactory secretKeyFactory;
        
        try {
            LOGGER.log(Level.INFO, "Starting method decodeEmailCredentials on {0}", PrivateDecrypt.class.getName());
            //Getting email credentials file content.
            byte[] fileContent = getEmailCredentials();
        
            //Creating SecretKey using SECRET_WORD and SALT...
            keySpec = new PBEKeySpec(SECRET_WORD.toCharArray(), SALT, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            //Creating Cipher for AES algorithm...
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16)); // La IV est� aqu�
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            //Decoding file content.
            byte[] decodedMessage = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));
            ret = new String(decodedMessage);
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex);
        }
        return ret;
    }
    
    /**
     * Reads the private key file and returns it as a byte array.
     * 
     * @return Private key content in byte array.
     * @throws IOException If and I/O error occurs.
     */
    public static byte [] getEmailCredentials() throws IOException {
        LOGGER.log(Level.INFO, "Starting method getEmailCredentials on {0}", PrivateDecrypt.class.getName());
        byte[] publicKeyBytes;
        InputStream inputStream = PublicDecrypt.class.getClassLoader()
                .getResourceAsStream(EMAIL_CREDENTIALS_PATH);
        publicKeyBytes = new byte[inputStream.available()];
        inputStream.read(publicKeyBytes);
        return publicKeyBytes;
    }
}
