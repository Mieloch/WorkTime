package topworker.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Created by echomil on 17.03.16.
 */
public class DesEncrypter {
    private Cipher eCipher;

    private Cipher dCipher;

    public DesEncrypter() {
        try {
            SecretKey key = KeyGenerator.getInstance("DES").generateKey();
            eCipher = Cipher.getInstance("DES");
            dCipher = Cipher.getInstance("DES");
            eCipher.init(Cipher.ENCRYPT_MODE, key);
            dCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String str) throws Exception {
        byte[] utf8 = str.getBytes("UTF8");
        byte[] enc = eCipher.doFinal(utf8);
        return new sun.misc.BASE64Encoder().encode(enc);
    }

    public String decrypt(String str) throws Exception {
        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
        byte[] utf8 = dCipher.doFinal(dec);
        return new String(utf8, "UTF8");
    }
}