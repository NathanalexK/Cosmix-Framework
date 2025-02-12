package mg.itu.prom16.security;

public class UrlRole {
    private String[] urlPatterns = new String[0];
    private boolean requireAuth = false;
    private String[] roles = new String[0];

    private HttpSecurity httpSecurity;

    public UrlRole(HttpSecurity httpSecurity) {
        this.httpSecurity = httpSecurity;
    }

    public HttpSecurity permitRoles(String... roles) {
        this.setRoles(roles);
        this.getHttpSecurity().addUrlRole(this);
        return this.getHttpSecurity();
    }

    public HttpSecurity permitAll() {
        this.setRoles(new String[]{"*"});
        this.getHttpSecurity().addUrlRole(this);
        return this.getHttpSecurity();
    }


    public HttpSecurity authenticated(){
        this.setRoles(new String[] {"*"});
        this.setRequireAuth(true);
        this.getHttpSecurity().addUrlRole(this);
        return httpSecurity;
    }
//    public HttpSecurity forbidRoles(String... roles)



    public String[] getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String[] urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public HttpSecurity getHttpSecurity() {
        return httpSecurity;
    }

    public void setHttpSecurity(HttpSecurity httpSecurity) {
        this.httpSecurity = httpSecurity;
    }

    public boolean isRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(boolean requireAuth) {
        this.requireAuth = requireAuth;
    }

    //    public
}
