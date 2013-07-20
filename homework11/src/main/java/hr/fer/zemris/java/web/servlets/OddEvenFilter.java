package hr.fer.zemris.java.web.servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Web filter mapped to <code>/stories/*</code> URL that is allowing access to
 * the pages only if current minute is even. If it is odd, request is forwarded
 * to <code>unavailable.jsp</code> page.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebFilter("/stories/*")
public class OddEvenFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if ((Calendar.getInstance().get(Calendar.MINUTE) & 1) == 1) {
			request.getRequestDispatcher("/WEB-INF/pages/unavailable.jsp")
					.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}

}
