package mg.itu.prom16.configuration;

import jakarta.servlet.http.HttpServletRequest;

public class DefaultRoleConfiguration implements RoleConfiguration{
    @Override
    public boolean isAuthenticated(HttpServletRequest request) {
        return true;
    }

    @Override
    public String getRole(HttpServletRequest request) {
        return "";
    }
}
