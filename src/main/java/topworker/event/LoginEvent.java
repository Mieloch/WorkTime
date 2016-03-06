package topworker.event;

/**
 * Created by echomil on 06.03.16.
 */
public class LoginEvent {
    private final String login;

    private final String password;

    public LoginEvent(String login, String password) {

        this.login = login;
        this.password = password;
    }

    public String getLogin() {

        return login;
    }

    public String getPassword() {

        return password;
    }
}
