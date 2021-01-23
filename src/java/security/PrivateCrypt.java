package security;

import exception.UnexpectedErrorException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Contains the methods meant to encode with AES.
 * 
 * @author Ander Vicente, Aitor Fidalgo
 */
public class PrivateCrypt {
    
    private static final Logger LOGGER = Logger.getLogger(PrivateCrypt.class.getName());
    /**
     * Salt used to encode.
     */
    private static final byte[] SALT = "esta es la salt!".getBytes(); 

    /**
     * Encodes the specified message with AES/CBC/PKCS5Padding.
     * 
     * Uses the specified secret word to encode the message and returns it. Also
     * creates a file in the specified path with with the encoded message.
     * 
     * @param secretWord The specified secret word.
     * @param message The specified message.
     * @param path Path of the file where the encoded message will be stored.
     * @return Encoded message.
     * @throws exception.UnexpectedErrorException If anything goes wrong.
     */
    public static String encode(String secretWord, String message, String path) throws UnexpectedErrorException {
        String ret = null;
        KeySpec keySpec;
        SecretKeyFactory secretKeyFactory;
        
        try {
            LOGGER.log(Level.INFO, "Starting method encode on {0}", PrivateCrypt.class.getName());
            //Creating SecretKey using the secret word and salt.
            keySpec = new PBEKeySpec(secretWord.toCharArray(), SALT, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            //Creating Cipher with the AES algorithm.
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            //Getting encoded message.
            byte[] encodedMessage = cipher.doFinal(message.getBytes());
            byte[] iv = cipher.getIV(); // vector de inicializacion por modo CBC

            //Saving the encoded message and iv in a file.
            byte[] combined = concatArrays(iv, encodedMessage);
            fileWriter(path, combined);

            ret = new String(encodedMessage);
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex);
        }
        return ret;
    }

    /**
     * Concatenates two given arrays ans returns the result.
     * 
     * @param array1 A given array to be concatenated.
     * @param array2 A given array to be concatenated.
     * @return The result of the concatenation.
     */
    private static byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    /**
     * Creates a file with the specified path and content.
     * 
     * @param path The specified path.
     * @param text The specified content.
     */
    private static void fileWriter(String path, byte[] text) throws FileNotFoundException, IOException {
        LOGGER.log(Level.INFO, "Starting method fileWriter on {0}", PrivateCrypt.class.getName());
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(text);
    }
}
