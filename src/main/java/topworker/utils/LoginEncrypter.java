package topworker.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by echomil on 17.03.16.
 */
public class LoginEncrypter {

    private Cipher cipher;
    private Key aesKey;

    public LoginEncrypter() {
        try {
            String key = "Bar69069Bar06900";
            aesKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String str) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(str.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : encrypted) {
            sb.append((char) b);
        }
        return sb.toString();
    }

    public String decrypt(String str) throws Exception {
        byte[] bb = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            bb[i] = (byte) str.charAt(i);
        }
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        return new String(cipher.doFinal(bb));
    }
}