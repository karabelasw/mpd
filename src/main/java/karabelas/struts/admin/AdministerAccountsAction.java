package karabelas.struts.admin;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import karabelas.mpd.MPDUsers;
import karabelas.mpd.MPDUsersDAO;
import org.apache.log4j.Logger;

public class AdministerAccountsAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1051752962952103877L;	
	private Long theId;
    private List<MPDUsers> listOfMPDUsers;
    private String DisplayUserMessage;
    private String button;
    private String mpdUser;
    private static Logger log4J = Logger.getLogger(AdministerAccountsAction.class);


	public String execute(){
		boolean encounteredError = false;
		try{
			executeCMD(button);
			//repopulate list of users display(verified = 0 in DB))
			listOfMPDUsers = MPDUsersDAO.getMPDUsersDAOInstance().listOfMPDUsers();
		}catch(Exception e){
			encounteredError = true;
			log4J.error(e.getMessage(), e);
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
  

	public String getDisplayUserMessage() {
		return DisplayUserMessage;
	}

	public void setDisplayUserMessage(String displayUserMessage) {
		DisplayUserMessage = displayUserMessage;
	}
	/**
	 * JSP administer-accounts.jsp has two submit buttons for the form 
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

	public String getMpdUser() {
		return mpdUser;
	}

	public void setMpdUser(String mpdUser) {
		this.mpdUser = mpdUser;
	}
	
	/**
	 * Precondition:
	 * Effects:
	 * @param ACmd
	 */
	private void executeCMD(String ACmd)throws Exception{
		System.out.println("---------********************EXECUTING THE COMMAND*****----*******------------*");
		if(ACmd !=null){
			if(button.equalsIgnoreCase("Unlock")){
				MPDUsersDAO.getMPDUsersDAOInstance().updateLocked(getTheId(), 0);
				setDisplayUserMessage("account has been unlocked");
			}			
			if(button.equalsIgnoreCase("Lock")){
				MPDUsersDAO.getMPDUsersDAOInstance().updateLocked(getTheId(), 1);
				setDisplayUserMessage("account has been Locked");
			}
			if(button.equalsIgnoreCase("Reset Password")){
				MPDUsersDAO.getMPDUsersDAOInstance().resetPassWord(getTheId());
				setDisplayUserMessage("account password been reset");
			}
		}		
	}	
	
}
