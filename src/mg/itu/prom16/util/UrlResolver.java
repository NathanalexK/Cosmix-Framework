package mg.itu.prom16.util;

import mg.itu.prom16.FrontController;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlResolver {
    public static String getRelativeUrl(String fullURL) throws URISyntaxException {
        URI uri = new URI(fullURL);
        String temp = uri.getPath();
        String relativeUrl = temp.replace( FrontController.getApplicationName(), "");
//        System.out.printf("Application Name:" + FrontController.getApplicationName());
//        System.out.println("relative Url: " + relativeUrl);
        return relativeUrl;
    }
}
