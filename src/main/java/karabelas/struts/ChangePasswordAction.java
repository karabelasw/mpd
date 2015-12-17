package karabelas.struts;

import com.opensymphony.xwork2.ActionSupport;

import karabelas.mpd.MPDUsers;
import java.util.Map;
import org.apache.struts2.interceptor.SessionAware;
import karabelas.security.PasswordHash;
import karabelas.mpd.MPDUsersDAO;
import org.apache.log4j.Logger;

public class ChangePasswordAction extends ActionSupport implements SessionAware {
	
	private String newPassword; 
    private String confirmPassword;
    private String currentPassword;
	private Map session;
	private MPDUsers user;
	private static Logger log4J = Logger.getLogger(ChangePasswordAction.class);
	
	
	//value="%{#session.loggedinUser.MPDUserName}"
	public String execute(){
		boolean encounteredError = false;
		try {
			MPDUsersDAO.getMPDUsersDAOInstance().changeMPDUserPassword(PasswordHash.getMD5Hash(newPassword,MPDUsersDAO.HASH_KEY), user.getMPDUserName());
		} catch (Exception e) {
			encounteredError = true;
			log4J.error(e.getMessage(),e);			
		}
		return encounteredError ? "ERROR" : "SUCCESS";
	}
	
	public void validate(){
		
		log4J.info("******^^^^^^^^^^---VALIDATING CHANGE PASSWORD REQUEST PARAMETERS-----^^^^^^^******");		
		boolean hasNewPassword = true;
		boolean hasConfirmedNewPassword = true;
		boolean hasCurrentPassword = true;
		
		if ( newPassword.length() == 0 ){			
			addFieldError( "newPassword", "Please provide a new password");
			hasNewPassword = false;
		}
		if ( confirmPassword.length() == 0 ){			
			addFieldError( "confirmPassword", "Please confirm your new password");
			hasConfirmedNewPassword = false;
		}
		if ( currentPassword.length() == 0 ){			
			addFieldError( "currentPassword", "Please provide your current password");
			hasCurrentPassword = false;
		}
		if ( hasNewPassword && hasConfirmedNewPassword  ){			
			if(!newPassword.equals(confirmPassword)){
				addFieldError("newPassword","Passwords provided are not equal.");
			}			
		}	
		
		if(hasCurrentPassword)
			if(!getUsersCurrentPassword().equals(PasswordHash.getMD5Hash(currentPassword,"karabelas.com"))){
			addFieldError("currentPassword","Current Password Provided is incorrect");
		}
		
	}

	public void setSession(Map session) {
		log4J.debug("******************-------------INSIDE SETTING SESSIONS MAP OF EDIT ACOUNT INFO-----**");
		this.session = session;		
	}
	
	public Map getSession(){
		return session;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getNewPassword(){
		return newPassword;
	}	
	
	public void setNewPassword(String strPassword){
			this.newPassword = strPassword;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	public String getUsersCurrentPassword(){
		
		user = (MPDUsers)session.get("loggedinUser");
		
		if(user != null){
			
		    return user.getMPDUserPassword();
		}
		return "";
	}
	/*
	 * user = (MPDUsers)session.get("loggedinUser");
		if(user != null)
			System.out.println(user);
	 */
}