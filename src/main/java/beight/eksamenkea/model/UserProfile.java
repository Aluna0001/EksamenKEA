package beight.eksamenkea.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

public class UserProfile {

    private final int id;
    private final String username;
    private boolean darkmode;

    public static RowMapper<UserProfile> ROW_MAPPER = (ResultSet rs, int rowNum) ->
            new UserProfile(rs.getInt("user_profile_id"),
                    rs.getString("username"),
                    rs.getBoolean("darkmode"));

    public UserProfile(int id, String username, boolean darkmode) {
        this.id = id;
        this.username = username;
        this.darkmode = darkmode;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean darkModeIsOn() {
        return darkmode;
    }

    public void toggleDarkMode(boolean on) {
        this.darkmode = on;
    }
}
