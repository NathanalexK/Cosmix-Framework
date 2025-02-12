package mg.itu.prom16;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static mg.itu.prom16.util.Reflect.*;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 15     // 15 MB
)
public class FrontController extends HttpServlet {
    protected static List<String> controllersList = null;
    protected static Map<String, Mapping> urlMapping = null;
    protected static boolean firstInit = true;
    protected static String appName = "";
    protected RoleConfiguration roleConfiguration = new DefaultRoleConfiguration();
//    protected static HttpSecurityConfiguration httpSecurityConfiguration = new HttpSecurityConfiguration();
    protected static HttpSecurity httpSecurity = new HttpSecurity();

    @Override
    public void init() throws ServletException{
        super.init();

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

            PrintWriter out = response.getWriter();
            String url = request.getRequestURI();
            Mapping mapping = getMapping(url);

            if (mapping == null) {
                throw new HttpException(HttpServletResponse.SC_NOT_FOUND, "Il n\'y a pas de methode associé a ce chemin: " + url);
            }
//            Object execMethod = mapping.execMapping(request, response);
            System.out.println("URI:   " + request.getRequestURI());
            mapping.execMapping(request, response, roleConfiguration);


//            if(mapping.isApi()) {
//                response.setContentType("application/json");
//                Gson gson = new MyJSON().getGson();
//                out.write(gson.toJson(execMethod));
//                return;
//            }
//
//            if(execMethod instanceof ModelView mv){
//                mv.getAttributes().forEach((key, value) -> {
//                    request.setAttribute(key, value);
//                });
//                request.getServletContext().getRequestDispatcher(mv.getUrl())
//                        .forward(request, response);
//
//            } else if (execMethod instanceof String str){
//                out.println(str);
//
//            } else {
//                throw new ServletException("Type de retour du methode: '" + mapping.getMethodName() +"' invalide");
//            }
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
            throw new ServletException(e.getMessage());
        }
    }

    public void checkRoles(HttpServletRequest request)  throws UnallowedRoleException {
        if(roleConfiguration == null) return;
    }



    protected static Mapping getMapping(String url) {
//        if(url.endsWith("/")) url = url.substring(0, url.length() - 1);
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
