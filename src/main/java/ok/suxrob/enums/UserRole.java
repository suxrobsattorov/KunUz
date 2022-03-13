package ok.suxrob.enums;

public enum UserRole {
    ADMIN("ADMIN"), USER("USER"),
    MODERATOR("MODERATOR"), PUBLISHER("PUBLISHER");
    String s;

    UserRole(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }

    public static UserRole userRole(String status) {
        if (status.equalsIgnoreCase("ADMIN")) {
            return ADMIN;
        } else if (status.equalsIgnoreCase("USER")) {
            return USER;
        } else if (status.equalsIgnoreCase("MODERATOR")) {
            return MODERATOR;
        } else if (status.equalsIgnoreCase("PUBLISHER")) {
            return PUBLISHER;
        } else {
            return null;
        }
    }
}
