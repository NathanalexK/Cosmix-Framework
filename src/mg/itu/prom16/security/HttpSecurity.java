package mg.itu.prom16.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpSecurity {
    private List<UrlRole> urlRoles = new ArrayList<>();


    public UrlRole urlMatchers(String... urlPatterns) {
        UrlRole urlRole = new UrlRole(this);
        urlRole.setUrlPatterns(urlPatterns);
        return urlRole;
    }

    public UrlRole anyUrl() {
        UrlRole urlRole = new UrlRole(this);
        urlRole.setUrlPatterns(new String[]{"/*"});
        return urlRole;
    }

    public void addUrlRole(UrlRole urlRole) {
        this.urlRoles.add(urlRole);
    }

    public List<UrlRole> getUrlRoles() {
        return urlRoles;
    }

    //    void addUrlRoles(String[] urlPatterns, String[] rolePatterns) {
//        this.urlRoles.put(urlPatterns, rolePatterns);
//    }


}
