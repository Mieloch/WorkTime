package topworker.model.bo;

/**
 * Created by echomil on 04.03.16.
 */
public enum UserRole {
    USER("USER"), ADMIN("ADMIN"), UNKNOWN("UNKNOWN");

    private String type;

    private UserRole(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
