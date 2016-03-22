package topworker.service.impl;


import org.jasypt.digest.StandardStringDigester;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * Created by echomil on 22.03.16.
 */
public class EncryptionServiceTest {
    private EncryptionServiceImpl encryptionService;

    @Before
    public void init() {
        encryptionService = new EncryptionServiceImpl();
        encryptionService.setPassword("123456");
    }

    @Test
    public void testEncryption() {
        String str = "Hello World";
        String result = encryptionService.encrypt(str);
        Assert.notNull(result);
    }

    @Test
    public void testEncryptionThenDecryption() {
        String str = "Hello World";
        String crypted = encryptionService.encrypt(str);
        System.out.println(crypted);
        String decrypted = encryptionService.decrypt(crypted);
        org.junit.Assert.assertEquals(str, decrypted);
    }

    @Test
    public void testAlwaysDecryptTheSame() {
        String str = "Hello World";
        encryptionService.setPassword("123456");
        String crypted = "hOE6SsRbrAVKzqPntpiY9yrWZ8J6sfxX";
        String decrypted = encryptionService.decrypt(crypted);
        org.junit.Assert.assertEquals(str, decrypted);
    }

    @Test
    public void testDigestWorks() {
        String str = "test";
        StandardStringDigester digester = new StandardStringDigester();
        digester.initialize();
        String result = digester.digest(str);
        Assert.notNull(result);
    }

    @Test
    public void testDigestMatch() {
        String str = "test";
        String toMatch = "2vjHzgq+bVGqJ8ADukfUbOADQRj6wKm6";
        StandardStringDigester digester = new StandardStringDigester();
        digester.initialize();
        boolean result = digester.matches(str, toMatch);
        Assert.isTrue(result);
    }


}