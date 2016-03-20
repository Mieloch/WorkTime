package topworker.controller;

import java.util.Date;

/**
 * Created by echomil on 19.03.16.
 */
public class Message {
    private Date start;
    private Date end;
    private String login;


    public Message() {

    }

    public Message(Date start, Date end) {
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

