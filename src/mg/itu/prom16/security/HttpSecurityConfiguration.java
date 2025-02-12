package mg.itu.prom16.security;

public class HttpSecurityConfiguration {

    /*
    public void configure(HttpSecurity httpSecurity) {
        httpSe
    }

     */

    public void configure(HttpSecurity httpSecurity) {
        httpSecurity
                .urlMatchers("/admin/*").permitRoles("admin")
                .urlMatchers("/auth/*").permitAll()
                .urlMatchers("/user/*").authenticated();
    }
}
