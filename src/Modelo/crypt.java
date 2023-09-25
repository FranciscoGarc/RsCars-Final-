package Modelo;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Fran
 */
public class crypt {

    
    private static final String AES = "AES";
    private static final String SECRET_KEY = "miClaveSecreta123"; // Clave secreta para la encriptación

    // Función para encriptar la contraseña usando AES
    public static String encryptPassword(String password) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            key = java.util.Arrays.copyOf(key, 16); // Seleccionar solo los primeros 128 bits

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Función para desencriptar la contraseña usando AES
    public static String decryptPassword(String encryptedPassword) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] key = sha.digest(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            key = java.util.Arrays.copyOf(key, 16); // Seleccionar solo los primeros 128 bits

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES);
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
