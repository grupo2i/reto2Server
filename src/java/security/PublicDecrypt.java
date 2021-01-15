package security;

import exception.UnexpectedErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ResourceBundle;
import javax.crypto.Cipher;

/**
 * Contains the methods meant to decode a message encoded with RSA.
 * 
 * @author ander, Aitor Fidalgo
 */
public class PublicDecrypt {
    
    /**
     * Relative path of the private key used to decode.
     */
    private static final String PRIVATE_KEY_PATH = ResourceBundle
            .getBundle("properties.properties").getString("privateKeyPath");
    
    /**
     * Decodes a given message using RSA/ECB/PKCS1Padding.
     * 
     * The given message is encoded with a public key (RSA/ECB/PKCS1Padding) and
     * then encoded into an hexadecimal String.
     * 
     * @param encodedMessageStr The given message to be decoded.
     * @return Decoded byte array.
     * @throws UnexpectedErrorException If anything goes wrong.
     */
    public static byte[] decode(String encodedMessageStr) throws UnexpectedErrorException {
        byte[] decodedMessage = null;
        try {
            //Decoding hexadecimal message.
            byte[] encodedMessage = decodeHexadecimal(encodedMessageStr);
            //Getting private key.
            byte fileKey[] = getPrivateKey();
            
            //Setting the properties for the decoding...
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(fileKey);
            PrivateKey privateKey = keyFactory.generatePrivate(pKCS8EncodedKeySpec);

            //Decoding with private key...
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decodedMessage = cipher.doFinal(encodedMessage);
        } catch (Exception ex) {
            throw new UnexpectedErrorException(ex);
        }
        return decodedMessage;
    }
    
    /**
     * Reads the private key file and returns it as a byte array.
     * 
     * @return Private key content in byte array.
     * @throws IOException If and I/O error occurs.
     */
    public static byte [] getPrivateKey() throws IOException{ 
        byte[] publicKeyBytes;
        try (InputStream inputStream = PublicDecrypt.class.getClassLoader()
                .getResourceAsStream(PRIVATE_KEY_PATH)) {
            publicKeyBytes = new byte[inputStream.available()];
            inputStream.read(publicKeyBytes);
        }
        return publicKeyBytes;
    }

    /**
     * Decodes a hexadecimal string into a byte array.
     * 
     * @param encodedMessageStr Encoded String.
     * @return Decoded byte array.
     */
    private static byte[] decodeHexadecimal(String encodedMessageStr) {
        byte[] decodedMessage = new byte[encodedMessageStr.length() / 2];
        for (int i = 0; i < decodedMessage.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(encodedMessageStr.substring(index, index + 2), 16);
            decodedMessage[i] = (byte) j;
        }
        return decodedMessage;
    }
            
}
