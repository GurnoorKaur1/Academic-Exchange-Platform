package com.algonquin.aep.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authentication Filter for securing web application resources.
 * This filter intercepts all incoming requests ("/*") and checks if the user is authenticated
 * before allowing access to protected resources. Public resources (like login pages, static files)
 * are accessible without authentication.
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    /**
     * Performs the filtering process for each request.
     * Checks if the requested resource is public or if the user is authenticated.
     * Redirects to login page if authentication is required but not present.
     *
     * @param request The servlet request
     * @param response The servlet response
     * @param chain The filter chain for invoking the next filter or resource
     * @throws IOException If an I/O error occurs during filtering
     * @throws ServletException If a servlet error occurs during filtering
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        if (isPublicResource(uri) || (session != null && session.getAttribute("userId") != null)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    /**
     * Determines if a requested resource is public and accessible without authentication.
     * Public resources include login/registration pages, static resources (CSS, JS, images),
     * and authentication-related endpoints.
     *
     * @param uri The URI of the requested resource
     * @return true if the resource is public, false if authentication is required
     */
    private boolean isPublicResource(String uri) {
        return uri.endsWith("login.jsp") || uri.endsWith("register.jsp") ||
               uri.endsWith("registerProfessional.jsp") || uri.endsWith("registerInstitution.jsp") ||
                uri.endsWith("login") || uri.endsWith("register") ||
                uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/");
    }

    /**
     * Initializes the filter.
     * This method is called by the web container to indicate that the filter is being placed into service.
     *
     * @param filterConfig The filter configuration object
     * @throws ServletException If a servlet error occurs during initialization
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * Destroys the filter.
     * This method is called by the web container to indicate that the filter is being taken out of service.
     */
    @Override
    public void destroy() {}
}
