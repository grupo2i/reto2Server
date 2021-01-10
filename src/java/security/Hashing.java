package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Ander
 */
public class Hashing {

    public static String encode(String texto) {
        MessageDigest messageDigest;
        try {
            // Obtén una instancia de MessageDigest que usa SHA
            messageDigest = MessageDigest.getInstance("SHA");
            // Convierte el texto en un array de bytes 
            byte[] textos = texto.getBytes();
            // Actualiza el MessageDigest con el array de bytes
            messageDigest.update(texto.getBytes());
            textos = messageDigest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < textos.length; i++) {
                hexString.append(Integer.toHexString(0xFF & textos[i]));
            }
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
