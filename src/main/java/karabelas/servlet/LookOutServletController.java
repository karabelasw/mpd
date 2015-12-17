package karabelas.servlet;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import karabelas.ContentsConstants;
import karabelas.mpd.MPDUsers;
import karabelas.mpd.MPDUsersDAO;
import karabelas.webutils.ParameterChecker;

import org.apache.log4j.Logger;

import com.jolbox.bonecp.BoneCPDataSource;



/**
 *Prior to using Struts 2, I was going to write each controller
 *for all requests.  After looking at Struts 2, I decided to start
 *using it.  
 */
public class LookOutServletController extends HttpServlet {
	

	   private static final long serialVersionUID = 1L;	
	   private static String DEFAULT_VIEW = "/WEB-INF/jsp/login.jsp";
	   BoneCPDataSource pool = null;
	   ServletContext application = null;	
	   Logger log4J = Logger.getLogger(LookOutServletController.class);
	
	
	   public void init(ServletConfig config)throws ServletException{
			super.init(config);				
			log4J.info("INITIALIZING INIT METHOD LOOKOUT CONTROLLER SERVLET**!!");
			application = this.getServletContext();		
			pool = (BoneCPDataSource)application.getAttribute("pool");			
			
		}	 
		
		
		public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			
			log4J.debug("<-----doGetENTERING LOOKOUT CONTROLLER Servlet-----> ");			
			RequestDispatcher rd = request.getRequestDispatcher(getRequestURL(request));
			rd.forward(request, response);

		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			
			log4J.info("<--***---POST TIME LOOKOUT CONTROLLER Servlet---**--> ");
			RequestDispatcher rd = request.getRequestDispatcher(getRequestURL(request));
			rd.forward(request, response);	
		}
		/**
		 * 
		 * @param request
		 * @return
		 * 
		 */
	    private String getRequestURL(HttpServletRequest request){
	    	String url = request.getRequestURI();
	    	String VIEW_JSP = DEFAULT_VIEW;
	    	
	    	if(url != null){
	    		log4J.info("REQUEST URL IS " + url);
	    		
	    		if(url.contains("login")){	    			
	    			
	    			if(validLoginParameters(request)){
	    				  
	    					if(hasAccount(request)){
	    			  			VIEW_JSP = "/WEB-INF/jsp/patrol/patrolentry.jsp";	    			  			
	    			  			return VIEW_JSP;
	    			  		}else{
	    			  			//invalid username or password
	    			  			placeInvalidUserNamePassword(request);
	    			  		}
	    			}
	    		}
	    		if(url.contains("logout")){
	    			log4J.info("******-----SIGNING GOUT-----*********");
	    			ParameterChecker.killUserSession(request);
	    			return VIEW_JSP;
	    		}
	    	}	    	
	    	return VIEW_JSP;	    	
	    }
	    
	private boolean validLoginParameters(HttpServletRequest request){
			
			log4J.info("*****CHECKING LOGGIN PARAMETERS VALID*******");			
			String userName = request.getParameter("j_username");			
			String userPassword = request.getParameter("j_password");
			boolean hasValidParams = false;
			boolean hasUserName = false;
			boolean hasUserPassword = false;
			
			//was a username provided, if not place a message inside reqattribute
			if(userName != null && !userName.equals("")){			   
				hasUserName = true;			    
			 }else{
				 
				 request.setAttribute("nousername", ContentsConstants.NO_USERNAME_PROVIDED);
			 }
			//was a password provided, if not place attribute to be seen on JSP
			if(userPassword != null && !userPassword.equals("")){			   
				hasUserPassword = true;			    
			 }else{
				  request.setAttribute("nopassword", ContentsConstants.NO_PASSWORD_PROVIDED);
			 }
			 
			
			hasValidParams = (hasUserName && hasUserPassword) ? true : false;
			
			
			return hasValidParams;
					
		}
	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean hasAccount(HttpServletRequest request){
			
			boolean hasAccount = false;
			String userName = request.getParameter("j_username");			
			String userPassword = request.getParameter("j_password");
			log4J.info("<------CHECKING HAS ACCOUNT------>");
			MPDUsers aUser = null;
			
			try{
				aUser = MPDUsersDAO.getMPDUsersDAOInstance().loginMPDUser(userName, userPassword);				
			}
			catch(Exception e){
				log4J.error(e.getMessage(), e);
				hasAccount = false;
				request.setAttribute("ERROR","Error: unable to login at this time!");
				return hasAccount;
			}
			
			if(aUser != null){
				log4J.debug("I HAVE AN ACCCONT!");
				createUserSession(request, aUser);
				hasAccount = true;
			}
			
			return hasAccount;		
	}
	
	private void createUserSession(HttpServletRequest request, MPDUsers aUser){
		log4J.info("***CREATING USER SESSION******");
		HttpSession session = request.getSession();
		session.setAttribute("loggedIn", Boolean.TRUE);
		session.setAttribute("loggedinUser", aUser);
	
	}

	private void placeInvalidUserNamePassword(HttpServletRequest request){
		request.setAttribute("invalidusernameorpassword", ContentsConstants.INVALID_USERNAME_PASSWORD);
		
	}
	
	
}