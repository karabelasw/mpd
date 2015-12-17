package karabelas.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import karabelas.mpd.MPDUsers;

/**
 * Servlet Filter implementation class AuthorizedAccess
 */
public class AdminAccess implements Filter {

 
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		  	
			HttpServletRequest req = (HttpServletRequest) request;
	        HttpServletResponse resp = (HttpServletResponse) response;
		
			HttpSession session = req.getSession();			
			MPDUsers aUser = (MPDUsers)session.getAttribute("loggedinUser");
			
			if(aUser == null || !aUser.isAdmin()){
				
			  RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/error/ErrorAccessDenied.jsp");
			  rd.forward(req, resp);
			  return;				
			}
	        chain.doFilter(request, response);	      
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
