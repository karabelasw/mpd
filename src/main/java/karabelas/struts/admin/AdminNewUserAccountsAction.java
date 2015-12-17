package karabelas.struts.admin;

import java.sql.SQLException;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.log4j.Logger;

import karabelas.mpd.MPDUserRolesDAO;
import karabelas.mpd.MPDUsers;
import karabelas.mpd.MPDUsersDAO;


public class AdminNewUserAccountsAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1051752962952103877L;	
	private Long theId;
    private List<MPDUsers> listOfMPDUsers;
    private String DisplayUserMessage;
    private String button;
    private static Logger log4J = Logger.getLogger(AdminNewUserAccountsAction.class);
    
	public String execute(){
		boolean encounteredError = false;
		//either activates or deletes user from database
		try{
			executeCMD(button);
			//fetch list of users that have requested accounts
			listOfMPDUsers = MPDUsersDAO.getMPDUsersDAOInstance().listNewMPDUsersRequestingAccounts();
		}catch(Exception e){
			log4J.error(e.getMessage(), e);
			encounteredError = true;	
		}
			
		return encounteredError ? "ERROR" : getButton();		 
	}		
		
	public Long getTheId() {
		return theId;
	}
	public void setTheId(Long mpdUserId) {
		this.theId = mpdUserId;
	}
	
	public List<MPDUsers> getListOfMPDUsers() {
		return listOfMPDUsers;
	}

	public void setListOfMPDUsers(List<MPDUsers> listOfMPDUsers) {
		this.listOfMPDUsers = listOfMPDUsers;
	}
	/**
	 * Precondition:
	 * Effects:
	 * @param ACmd
	 */
	private void executeCMD(String ACmd)throws Exception{
		log4J.debug("---------********************EXECUTING THE COMMAND*****----*******------------*");
		if(ACmd !=null){
			if(button.equalsIgnoreCase("DELETE")){
				MPDUsersDAO.getMPDUsersDAOInstance().deleteUser(theId);
				setDisplayUserMessage("USER ACCOUNT DELETED");
			}
			
			if(button.equalsIgnoreCase("ACTIVATE")){
				MPDUserRolesDAO.getMPDUserRolesDAOInstance().grantUserPermission(getTheId());
				MPDUsersDAO.getMPDUsersDAOInstance().updateVerified(getTheId(), 0);
				setDisplayUserMessage("USER ACCOUNT ACTIVATED");
			}			
		}		
	}	  

	public String getDisplayUserMessage() {
		return DisplayUserMessage;
	}

	public void setDisplayUserMessage(String displayUserMessage) {
		DisplayUserMessage = displayUserMessage;
	}
	/**
	 * JSP new-user-accounts-requested.jsp has two submit buttons for the form 
	 * button value field is set  here the first time the page is loaded DEFAULT
	 * is returned
	 * @return
	 */
	public String getButton() {
		if(button == null || button.equals("")){
			button = "DEFAULT";
		}
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}
	
}
