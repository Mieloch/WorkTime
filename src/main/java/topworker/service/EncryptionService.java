package topworker.service;

/**
 * Created by echomil on 22.03.16.
 */
public interface EncryptionService {
    String encrypt(String str);

    String decrypt(String str);

    String digest(String str);

    boolean match(String str, String digest);
}
