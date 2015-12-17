package karabelas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import karabelas.ContentsConstants;
import karabelas.mpd.MPDUsers;
import karabelas.mpd.MPDUsersDAO;
import karabelas.security.PasswordHash;
import karabelas.webutils.ParameterChecker;

import com.jolbox.bonecp.BoneCPDataSource;
import org.apache.log4j.Logger;
/**
 * Servlet AccountRequest
 */
public class AccountRequest extends HttpServlet {
		
	
		private static final long serialVersionUID = 1L;
		private static String DEFAULT_VIEW = "/WEB-INF/jsp/accountrequestform.jsp";
		private static String SUCCESS_VIEW = "/WEB-INF/jsp/successAccountRequest.jsp";
	    private Logger log4J = Logger.getLogger(AccountRequest.class);	    
		private BoneCPDataSource pool = null;
		ServletContext application = null;
	
		
	 public void init(ServletConfig config)throws ServletException{
		super.init(config);				
		log4J.debug("INITIALIZING INIT METHOD REQUEST ACCOUNT SERVLET**!!");
		application = this.getServletContext();		
		pool = (BoneCPDataSource)application.getAttribute("pool");		
		
	}	 
		
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 **/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log4J.debug("*****REQUESTING AN ACCOUNT HERE****!!");		
		RequestDispatcher rd = request.getRequestDispatcher(DEFAULT_VIEW);
		rd.forward(request, response);		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		RequestDispatcher rd = request.getRequestDispatcher(DEFAULT_VIEW);
		
		if(hasValidParameters(request)){
			if(createMPDUser(request)){
				rd = request.getRequestDispatcher(SUCCESS_VIEW);
			}
		}
		
		rd.forward(request, response);
	}
	
	private boolean hasValidParameters(HttpServletRequest request){
		
		log4J.debug("*****CHECKING ACCOUNT REQUEST PARAMETERS VALID*******");			
		String firstName = request.getParameter("firstname");			
		String lastName = request.getParameter("lastname");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmpassword");
		
		boolean hasValidParams = false;
		boolean hasFirstName = false;
		boolean hasLastName = false;
		boolean hasUserName= false;
		boolean hasPassword = false;
		boolean hasConfirmPassword = false;
		boolean passwordsMatch = false;
		boolean userNameExists = false;
		boolean isValidEmailAddress = false;
		
		if(firstName != null  && !firstName.equals("")){
			hasFirstName = true;
		}else{
			request.setAttribute("nofirstname", ContentsConstants.NO_FIRSTNAME_PROVIDED);
		}
		if(lastName != null  && !lastName.equals("")){
			hasLastName = true;
		}else{
			request.setAttribute("nolastname", ContentsConstants.NO_LASTNAME_PROVIDED);
		}
		//was a username provided, if not place a message inside request.attribute
		if(userName != null && !userName.equals("")){			   
			hasUserName = true;			    
		 }else{			 
			 request.setAttribute("nousername", ContentsConstants.NO_USERNAME_PROVIDED);
		 }
		 if(hasUserName && !ParameterChecker.validEmail(userName)){
			 isValidEmailAddress = false;
			 request.setAttribute("invalidemail", ContentsConstants.INVALID_EMAIL);
		 }else{
			 isValidEmailAddress = true;
		 }
		//was a password provided, if not place message to be seen on JSP
		if(password != null && !password.equals("")){			   
			hasPassword = true;			    
		 }else{
			  request.setAttribute("nopassword", ContentsConstants.NO_PASSWORD_PROVIDED);
		 }
		//was a password provided, if not place attribute to be seen on JSP
		if(confirmPassword != null && !confirmPassword.equals("")){			   
			hasConfirmPassword = true;			    
		 }else{
			  request.setAttribute("noconfirmpassword", ContentsConstants.NO_CONFIRMPASSWORD_PROVIDED);
		 }
		 
		if(hasPassword && hasConfirmPassword){
			passwordsMatch = passwordsMatch(password, confirmPassword);
			if(!passwordsMatch){
				  request.setAttribute("passwordsmismatch", ContentsConstants.NO_PASSWORDS_DONTMATCH);
			 }
		}		
		
		hasValidParams = (hasFirstName && hasLastName && hasUserName && hasPassword && hasConfirmPassword && passwordsMatch && isValidEmailAddress)
							? true : false;
		//all valid parameters thus far, last check to see if userName exists in the database
		if(hasValidParams){
				userNameExists = userNameExists(request);
				hasValidParams = (userNameExists) ? false: true;
		}		
		
		
		return hasValidParams;
				
	}
	
	/**
	 * 
	 * @param aPassword
	 * @param bPassword
	 * @return
	 */
	private boolean passwordsMatch(String aPassword, String bPassword){
			
			boolean passwordsMatch = false;
			
			if(aPassword.equals(bPassword)){
				passwordsMatch = true;
			}
			
			return passwordsMatch;
		
		
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private boolean userNameExists(HttpServletRequest request){
			
			boolean userNameExist = false;
			String userName = request.getParameter("username");			
			
			log4J.debug("<------CHECKING TO SEE IF USERNAME EXISTS------>");
			
			try{
				userNameExist = MPDUsersDAO.getMPDUsersDAOInstance().checkForUserNameInMPDUsers(userName);
			}
			catch(Exception e){
				log4J.error(e.getMessage(), e);
				request.setAttribute("ERROR", "ERROR: Unable to service this request--send email to mpd@karabelas.com");
			}
			
			if(userNameExist){
				log4J.debug("***ACCOUNT ALREADY EXISTS*****");
				request.setAttribute("usernameexists", ContentsConstants.ACCOUNT_EXISTS);
				userNameExist = true;
			}			
			return userNameExist;		
	}
	/**
	 * Pre-Condition: hasVaildParameters:true, HttpServletRequest parameters
	 * 
	 * Post-Condition: will transfer validated request Parameters
	 * to create new MPDUsers object
	 * MPDUsers object created has required information to create a 
	 * record in database (table:MPDUsers)
	 * Will then place object into request attribute
	 * MPDUsername, MPDEmailAddress, LastName, FirstName, MPDUserPassword, Verified
	 * @param request
	 * @return
	 */
	private boolean createMPDUser(HttpServletRequest request){
			
			//contstruct MPDUsers object from parameters
			 MPDUsers aUser = MPDUsers.createMPDUser
			(request.getParameter("username"), request.getParameter("username"),
			 request.getParameter("lastname"),
			 request.getParameter("firstname"),			
			 PasswordHash.getMD5Hash(request.getParameter("password"),MPDUsersDAO.HASH_KEY), Integer.valueOf(1));
			 boolean errors = false;
			 try{
				 if(MPDUsersDAO.getMPDUsersDAOInstance().createMPDUserAccount(aUser) != null){	    
					 request.setAttribute("newaccount", aUser);
					 return true;
				 }
			}
			 catch(Exception e){
				 errors = true;
				 log4J.error(e.getMessage(), e);
				 request.setAttribute("ERROR", "ERROR: Unable to service this request--send email to mpd@karabelas.com");
				 return false;
			 }
 
			  return false;
	}
	
}
