package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/app/reg")
public class RegFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader("Access-Control-Allow-Origin", "*");

        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, ngrok-skip-browser-warning, access-control-allow-origin");

        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
