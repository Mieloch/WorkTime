package topworker.service.impl;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import topworker.service.EmailService;
import topworker.utils.LoginEncrypter;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

/**
 * Created by echomil on 16.03.16.
 */

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private LoginEncrypter loginEncrypter;

    @Override
    public void sendRegistrationMsg(String email, String login) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(HttpAuthenticationFeature.basic("api", "key-1cba5a5bb5da111835115b123a8b0e3d")));
        WebTarget webTarget = client.target("https://api.mailgun.net/v3/echomil.pl/messages");
        Form form = new Form();
        form.param("from", "WorkTime <worktime@echomil.pl>");
        form.param("to", email);
        form.param("subject", "Confirm E-mail address");
        String encryptredLogin = null;
        try {
            encryptredLogin = loginEncrypter.encrypt(login);
        } catch (Exception e) {
            e.printStackTrace();
        }
        form.param("text", "Click to activate account \nhttp://worktime-echomil.rhcloud.com/api/user/active?login=" + encryptredLogin);

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
        String response = invocationBuilder.post(Entity.form(form), String.class);
        System.out.println(response); // TODO logger
    }
}
