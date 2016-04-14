package topworker.controller.transport;

import java.util.Date;

/**
 * Created by echomil on 19.03.16.
 */
public class PostPeriodMessage {
    private Date start;
    private Date end;
    private String login;


    public PostPeriodMessage() {

    }

    public PostPeriodMessage(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}

