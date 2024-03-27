package mx.edu.utez.adoptameappserver.config;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class Hs256KeyGenerator {
    public static void main(String[] args) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HMACSHA256");
            SecretKey secretKey = keyGenerator.generateKey();

            String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            byte[] keyBytes = Base64.getDecoder().decode(base64Key);

            String hexKey = bytesToHex(keyBytes);

            System.out.println("HS256 Key (Base64):");
            System.out.println(base64Key);

            System.out.println("HS256 Key (Hex):");
            System.out.println(hexKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}