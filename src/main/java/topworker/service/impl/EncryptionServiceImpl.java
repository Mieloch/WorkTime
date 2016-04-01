package topworker.service.impl;


import org.jasypt.digest.StandardStringDigester;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import topworker.dal.SecurityDao;
import topworker.service.EncryptionService;

import javax.annotation.PostConstruct;

/**
 * Created by echomil on 17.03.16.
 */
@Service
//@DependsOn(value = {"securityDao"})
public class EncryptionServiceImpl implements EncryptionService {

    @Autowired
    private SecurityDao securityDao;

    private BasicTextEncryptor encryptor;
    private StandardStringDigester digester;
    private static final String TEXT_ENCRYPTION_KEY = "TEXT_ENCRYPTION_KEY";

    public EncryptionServiceImpl() {
        digester = new StandardStringDigester();
        digester.initialize();
        encryptor = new BasicTextEncryptor();
    }

    @PostConstruct
    public void init() {
        encryptor.setPassword(securityDao.getRecord(TEXT_ENCRYPTION_KEY));
    }

    @Override
    public String encrypt(String str) {
        return encryptor.encrypt(str);
    }

    @Override
    public String decrypt(String str) {
        return encryptor.decrypt(str);
    }

    @Override
    public String digest(String str) {
        return digester.digest(str);
    }

    @Override
    public boolean match(String str, String digest) {
        return digester.matches(str, digest);
    }

    protected void setPassword(String password) {
        encryptor.setPassword(password);
    }
}