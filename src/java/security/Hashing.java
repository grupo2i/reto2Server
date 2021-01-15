package security;

import exception.UnexpectedErrorException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Contains the methods meant to encode a message using SHA.
 * 
 * @author ander, Aitor Fidalgo
 */
public class Hashing {

    /**
     * Encodes a byte array using SHA and returns the hexadecimal representation
     * of the encoded message.
     * 
     * @param message Message to be encoded.
     * @return Hexadecimal String representation of the encoded message.
     * @throws exception.UnexpectedErrorException If anything goes wrong.
     */
    public static String encode(byte[] message) throws UnexpectedErrorException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(message);
            //Getting encoded message.
            byte resumen[] = messageDigest.digest();
            //Calculating the hexadecimal value of the encodedMessage.
            String hexMessage = "";
            for (int i = 0; i < resumen.length; i++) {
                String h = Integer.toHexString(resumen[i] & 0xFF);
                if (h.length() == 1) {
                    hexMessage += "0";
                }
                hexMessage += h;
            }
            
            return hexMessage.toUpperCase();
        } catch (NoSuchAlgorithmException ex) {
            throw new UnexpectedErrorException(ex);
        }
    }

}
