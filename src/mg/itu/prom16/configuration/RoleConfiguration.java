package mg.itu.prom16.configuration;

import jakarta.servlet.http.HttpServletRequest;

public interface RoleConfiguration {
    boolean isAuthenticated(HttpServletRequest request);
    String getRole(HttpServletRequest request);
}

