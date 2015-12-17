package karabelas.webutils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import karabelas.mpd.MPDUsers;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
public class ParameterChecker {
	
	private ParameterChecker(){};
	private static Logger log4J = Logger.getLogger(ParameterChecker.class);	
	
	public static String isHidden(HttpServletRequest request){
			
			String retValue = "hidden";			
			
			if( (request.getAttribute("enterusername") != null) || (request.getAttribute("enterpassword") != null) 
					|| (request.getAttribute("enteruserpassword") != null)){
				
				retValue = "";
			}
			
			return retValue;
	}
	
	
	public static boolean existsAttribute(HttpServletRequest request, String attributeName){
		
		boolean exists = false;			
		
		if( (request.getAttribute(attributeName) != null) ){			
			exists = true;
		}
		
		return exists;	
	}
	
	public static boolean existsParam(HttpServletRequest request, String parameter){
		
		boolean exists = false;			
		
		if( (request.getParameter(parameter) != null) ){			
			exists = true;
		}		
		return exists;	
	}
	
	
	
	public static boolean isAdmin(String role){
		
		boolean retValue = false;
		
		if(role != null && !role.trim().equals("")){
			
			return role.equalsIgnoreCase("admin");
		}
		
		return retValue;
		
	}
	
	public static void killUserSession(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		session.removeAttribute("loggedIn");
		session.invalidate();
	
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static MPDUsers getLoggedInUser(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		MPDUsers aUser = (MPDUsers) session.getAttribute("loggedinUser");
		if(aUser != null)
			return aUser;
		else return null;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	 public static boolean isUserLoggedIn(HttpServletRequest request) {
			log4J.debug("ENTERING IS USER LOGGED IN******* ");
		 	boolean isLoggedIn = false;
			HttpSession session = request.getSession();
			Boolean aBoolean = (Boolean)session.getAttribute("loggedIn");			
			if(aBoolean != null)
				isLoggedIn = aBoolean;
			return isLoggedIn;	
	 }
/**
 * 
 * @param anEmail
 * @return
 */
	 public static boolean validEmail(String anEmail){
		    EmailValidator eValidator = EmailValidator.getInstance();
			return eValidator.isValid(anEmail);
		 
	 }
	 /**
	  * Purpose: Used on each hyperlink in the header which is the menu
	  * for the application across top of page. Highlights the
	  * menu bar which the user last clicked
	  * Precondition:  the parameter linkToActivate is not null
	  * Effects: will return the String: class="active" if the 
	  * requested URL and the menu item are a match.  
	  * @param request
	  * @param linkToActivate
	  * @return
	  */
	public static String activateLink(HttpServletRequest request, String linkToActivate){
			//request.getContextPath()+"/listinfractions.jsp")
		String requestedURL = null;
		
		if(request.getAttribute("javax.servlet.forward.request_uri") != null){
			requestedURL = request.getAttribute("javax.servlet.forward.request_uri").toString();
			
			if(requestedURL != null){
				if(requestedURL.contains(linkToActivate))
					return "class=\"active\"";
			}
			
		}	
			return "";	
		}
	 
}
