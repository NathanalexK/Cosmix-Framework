package mg.itu.prom16;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.prom16.util.UrlResolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.net.URISyntaxException;

@WebFilter("/*")
public class StaticResourceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        System.out.println("do filter 6 " + path );
        // Check if the request is for a static resource
        if (isStaticResource(path)) {
            System.out.println("url is static 2");

            // Forward to the default servlet for static resources
            return;
        }

        // Proceed to the next filter or servlet for non-static resources
        try {
            System.out.println("sadsd: " + "/app" + UrlResolver.getRelativeUrl(path));
            request.getRequestDispatcher("/app" + UrlResolver.getRelativeUrl(path)).forward(request, response);
        } catch (URISyntaxException e) {
            throw new SocketException(e);
        }
    }

    private boolean isStaticResource(String path) {
        return path.endsWith(".css") || path.endsWith(".js") || path.endsWith(".png") ||
                path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".gif") ||
                path.endsWith(".ico") || path.endsWith(".html") || path.endsWith(".txt");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic (if needed)
    }

    @Override
    public void destroy() {
        // Cleanup logic (if needed)
    }
}