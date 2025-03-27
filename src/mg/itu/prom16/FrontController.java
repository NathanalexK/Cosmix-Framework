package mg.itu.prom16;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.prom16.annotations.Url;
import mg.itu.prom16.configuration.DefaultRoleConfiguration;
import mg.itu.prom16.configuration.RoleConfiguration;
import mg.itu.prom16.exception.FormException;
import mg.itu.prom16.exception.UnallowedRoleException;
import mg.itu.prom16.http.HttpException;
import mg.itu.prom16.page.ContentType;
import mg.itu.prom16.page.PageError;
import mg.itu.prom16.security.HttpSecurity;
import mg.itu.prom16.security.HttpSecurityConfiguration;
import mg.itu.prom16.util.Mapping;
import mg.itu.prom16.util.ModelView;
import mg.itu.prom16.util.MyJSON;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static mg.itu.prom16.util.Reflect.*;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 15     // 15 MB
)
//@WebServlet(urlPatterns = "/app/*")
public class FrontController extends HttpServlet {
    protected static Map<String, Mapping> urlMapping = null;
    protected static boolean firstInit = true;
    protected static String appName = "";
    protected RoleConfiguration roleConfiguration = new DefaultRoleConfiguration();
    protected static HttpSecurity httpSecurity = new HttpSecurity();

    @Override
    public void init() throws ServletException{
//        super.init();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Processing GET method");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doFirstInit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String packageName = this.getInitParameter("controller-package");
        String roleConfigName = this.getInitParameter("role-config-class");
        String securityConfigName = this.getInitParameter("security-config-class");

        if(securityConfigName != null) {
            Class<HttpSecurityConfiguration> httpSecurityConfigurationClass = (Class<HttpSecurityConfiguration>) Class.forName(securityConfigName);
            HttpSecurityConfiguration httpSecurityConfiguration = httpSecurityConfigurationClass.getDeclaredConstructor().newInstance();
            httpSecurityConfiguration.configure(FrontController.getHttpSecurity());
        }

        appName = getServletContext().getContextPath();
        urlMapping = getAllUrlMapping(packageName, appName);
        urlMapping.forEach((k, v) -> {
            System.out.println(k + " " + v);
        });

        if(roleConfigName != null) {
            Class<RoleConfiguration> configClass = (Class<RoleConfiguration>) Class.forName(roleConfigName);
            this.roleConfiguration = configClass.getDeclaredConstructor().newInstance();
        }





        firstInit = false;
    }

    @SuppressWarnings("all")
    public void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


        try {
            if (firstInit) {
                doFirstInit(request, response);
            }

            String url = request.getRequestURI();
            System.out.println("My url: " + url);
            Mapping mapping = getMapping(url);

//            if(url.endsWith(".css")){
//                System.out.println("ends with css");
//                request.getRequestDispatcher(url).forward(request, response);
//                return;
//            }

            if (mapping == null) {
                System.out.println("let s go 3");
//                request.getRequestDispatcher(url).forward(request, response);
                return;


            }
//                PrintWriter out = response.getWriter();
                System.out.println("URI:   " + request.getRequestURI());
                mapping.execMapping(request, response, roleConfiguration);
//                }
//                out.close();


        }  catch (FormException formException) {
            formException.printStackTrace();
            Gson gson = new Gson();
            response.setContentType(ContentType.JSON);
            response.setStatus(500);
            formException.getErrors().forEach((k, v) -> {
                System.out.println("error " + k + " " + v);
            });

            String str = gson.toJson(formException.getErrors());
            PrintWriter writer = response.getWriter();
            writer.write(str);
            writer.close();

        } catch (HttpException httpException) {
            httpException.printStackTrace();
            PageError.showPage(response, httpException.getHttpStatus(), httpException.getMessage());

        } catch (Exception e){
            e.printStackTrace();
            PageError.showPage(response, 500, e.getMessage());
//            throw new ServletException(e.getMessage());
        }
    }

    public void checkRoles(HttpServletRequest request)  throws UnallowedRoleException {
        if(roleConfiguration == null) return;
    }



    protected static Mapping getMapping(String url) {
        return urlMapping.get(url);
    }

    public static String getApplicationName() {
        return appName;
    }

    public static HttpSecurity getHttpSecurity() {
        return httpSecurity;
    }

    public static void setHttpSecurity(HttpSecurity httpSecurity) {
        FrontController.httpSecurity = httpSecurity;
    }
}
