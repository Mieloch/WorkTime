package topworker.utils;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;

/**
 * Created by echomil on 22.03.16.
 */
public class CustomEncoder extends PlaintextPasswordEncoder {


    public CustomEncoder() {
        super();
    }

    @Override
    public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
        StandardStringDigester digester = new StandardStringDigester();
        digester.initialize();
        try {
            if (digester.matches(rawPass, encPass)) {
                return true;
            }
        } catch (EncryptionOperationNotPossibleException e) {
            return false;
        }

        return false;
    }
}