package topworker.controller;

import org.apache.commons.codec.binary.Base64;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import topworker.TopWorkerApplication;
import topworker.controller.transport.PostPeriodMessage;
import topworker.dal.WorkDayDao;
import topworker.dal.WorkPeriodDao;
import topworker.dal.entity.WorkPeriod;

import java.net.HttpRetryException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertThat;

/**
 * Created by echomil on 14.04.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TopWorkerApplication.class)
@WebIntegrationTest(randomPort = true)
@ActiveProfiles(profiles = "dev")
public class WorkPeriodsControllerTest {

    @Autowired
    private WorkDayDao workDayDao;

    @Autowired
    private WorkPeriodDao workPeriodDao;

    @Value("${local.server.port}")
    private int port;
    private final String api = "/api/workperiod";
    private String address;
    private SimpleDateFormat dateFormat;
    private String username = "demo";
    private String password = "demo";

    public WorkPeriodsControllerTest() {
    }

    @Before
    public void init() {
        workPeriodDao.removeAll();
        workDayDao.removeAll();
        address = "http://localhost:" + port + api;
        dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
    }

    @Test
    public void cantPostPeriodIfStartAfterStop() {
        Date start = parseDate("01-01-2016 11:05:00");
        Date end = parseDate("01-01-2016 11:00:00");
        PostPeriodMessage message = new PostPeriodMessage(start, end, username);
        ResponseEntity<WorkPeriod> responseEntity = postPeriod(message, username, password);
        assertThat(responseEntity.getStatusCode(), Matchers.equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void cantPostPeriodWithoutBaseAuth() {
        Date start = parseDate("01-01-2016 11:00:00");
        Date end = parseDate("01-01-2016 11:05:00");
        PostPeriodMessage message = new PostPeriodMessage(start, end, username);
        try {
            ResponseEntity<WorkPeriod> responseEntity = postPeriod(message, "notUserName", "notPassword");
        } catch (ResourceAccessException e) {
            HttpRetryException cause = (HttpRetryException) e.getCause();
            assertThat(cause.responseCode(), Matchers.equalTo(HttpStatus.UNAUTHORIZED.value()));
        }
    }

    @Test
    public void canPostPeriod() {
        Date start = parseDate("01-01-2016 11:00:00");
        Date end = parseDate("01-01-2016 11:05:00");
        PostPeriodMessage message = new PostPeriodMessage(start, end, username);
        ResponseEntity<WorkPeriod> responseEntity = postPeriod(message, username, password);
        assertThat(responseEntity.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
        WorkPeriod currentPeriod = responseEntity.getBody();
        WorkPeriod periodFromDb = workPeriodDao.get(currentPeriod.getId());
        assertThat("start time should be equal", currentPeriod.getStart(), Matchers.equalTo(periodFromDb.getStart()));
        assertThat("stop time should be equal", currentPeriod.getStop(), Matchers.equalTo(periodFromDb.getStop()));
    }

    @Test
    public void nextPeriodMakesCurrentPeriodLonger() {
        Date firstPeriodStart = parseDate("01-01-2016 11:00:00");
        Date firstPeriodEnd = parseDate("01-01-2016 11:05:00");
        PostPeriodMessage firstMessage = new PostPeriodMessage(firstPeriodStart, firstPeriodEnd, username);
        Date secondPeriodStart = parseDate("01-01-2016 11:05:00");
        Date secondPeriodEnd = parseDate("01-01-2016 11:10:00");
        PostPeriodMessage secondMessage = new PostPeriodMessage(secondPeriodStart, secondPeriodEnd, username);
        postPeriod(firstMessage, username, password);
        ResponseEntity<WorkPeriod> responseEntity = postPeriod(secondMessage, username, password);
        WorkPeriod currentPeriod = responseEntity.getBody();
        WorkPeriod periodFromDb = workPeriodDao.get(currentPeriod.getId());
        assertThat("Status code is not OK", responseEntity.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
        assertThat("Response period stop time should be equal to second message stop time", currentPeriod.getStop(), Matchers.equalTo(secondPeriodEnd));
        assertThat("Response period start time should be equal to second message start time", currentPeriod.getStart(), Matchers.equalTo(firstPeriodStart));

        assertThat(currentPeriod.getStart().getTime(), Matchers.equalTo(periodFromDb.getStart().getTime()));
        assertThat(currentPeriod.getStop().getTime(), Matchers.equalTo(periodFromDb.getStop().getTime()));


    }


    private Date parseDate(String text) {
        try {
            return dateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResponseEntity<WorkPeriod> postPeriod(PostPeriodMessage message, String username, String password) {
        RestTemplate restTemplate = new TestRestTemplate();
        MultiValueMap headersMap = createBasicAuthHeaders(username, password);
        headersMap.set("Content-Type", "application/json");
        HttpEntity<PostPeriodMessage> requestEntity = new HttpEntity<>(message, headersMap);
        ResponseEntity<WorkPeriod> responseEntity = restTemplate.exchange(address, HttpMethod.POST, requestEntity, WorkPeriod.class);
        return responseEntity;
    }


    private HttpHeaders createBasicAuthHeaders(final String username, final String password) {
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