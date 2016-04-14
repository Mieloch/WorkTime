package topworker.controller;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import topworker.TopWorkerApplication;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by echomil on 14.04.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TopWorkerApplication.class)
@WebIntegrationTest
public class WorkPeriodsControllerTest {

    @Value("${local.server.port}")
    private int port;
    private final String api = "api/workperiod";
    private final String address = "http://localhost:" + port + api;

    @Test
    public void cantPostPeriodIfStartAfterStop() {
        GregorianCalendar calendar = new GregorianCalendar();
        Date start = calendar.getTime();
        calendar.add(GregorianCalendar.HOUR, 1);
        Date end = calendar.getTime();
        Message message = new Message(end, start);
        message.setLogin("demo");
        RestTemplate restTemplate = new TestRestTemplate();
        MultiValueMap m = createHeaders("demo", "demo");
        HttpEntity<Message> ss = new HttpEntity<>(message, m);
        restTemplate.exchange(address, HttpMethod.POST, ss, HttpStatus.class);
    }

    private HttpHeaders createHeaders(final String username, final String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

}