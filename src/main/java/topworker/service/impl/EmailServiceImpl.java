package topworker.service.impl;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import topworker.dal.entity.User;
import topworker.service.EmailService;
import topworker.service.UserService;
import topworker.service.WorkPeriodService;
import topworker.utils.TimeUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by echomil on 16.03.16.
 */

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EncryptionServiceImpl encryptionService;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkPeriodService workPeriodService;

    @Resource(name = "apiProperties")
    private Properties apiProperties;

    private String MAILGUN_API_URL;
    private String ACTIVATE_URL;

    @PostConstruct
    private void init() {
        MAILGUN_API_URL = apiProperties.getProperty("mailgun_api_url");
        ACTIVATE_URL = apiProperties.getProperty("activate_url");
    }

    @Scheduled(cron = "0 7 1 * * ?")
    private void sendMonthlySummary() {
        List<User> users = userService.getAll();
        Date today = new Date();
        for (User user : users) {
            int workMinutes = workPeriodService.getMonthlySumInMinutes(today, user.getLogin());
            String time = TimeUtils.formatTime(workMinutes);
            sendMail(user.getEmail(), "Monthly Summary", time);
        }
    }

    @Override
    public void sendRegistrationMsg(String email, String login) {
        String encryptredLogin = null;
        try {
            encryptredLogin = encryptionService.encrypt(login);
            encryptredLogin = URLEncoder.encode(encryptredLogin, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text = "Click to activate account \n" + ACTIVATE_URL + encryptredLogin;
        sendMail(email, "Address confirmation", text);
    }

    private void sendMail(String to, String subject, String text) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(HttpAuthenticationFeature.basic("api", "key-1cba5a5bb5da111835115b123a8b0e3d")));
        WebTarget webTarget = client.target(MAILGUN_API_URL);
        Form form = new Form();
        form.param("from", "WorkTime <worktime@echomil.pl>");
        form.param("to", to);
        form.param("subject", subject);
        form.param("text", text);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        String response = invocationBuilder.post(Entity.form(form), String.class);
        System.out.println(response); // TODO logger
    }
}
