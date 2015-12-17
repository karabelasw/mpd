package karabelas.struts.admin;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import karabelas.mpd.MPDUserRolesDAO;
import karabelas.mpd.MPDUsers;
import karabelas.mpd.MPDUsersDAO;
import karabelas.mpd.TrafficInfraction;
import karabelas.mpd.TrafficInfractionsDAO;
import org.apache.log4j.Logger;
import org.apache.struts2.util.ServletContextAware;

public class AdministerInfractionsAction extends ActionSupport implements ServletContextAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1051752962952103877L;	
	private Long theId;
    private List<TrafficInfraction> listOfInfractions;
    private String DisplayUserMessage;
    private String button;  
    private static Logger log4J = Logger.getLogger(AdministerInfractionsAction.class);
    private ServletContext context;
	public String execute(){
		
		try{
			executeCMD(button);
			setListOfInfractions(TrafficInfractionsDAO.getInfractionsDAOInstance().listAllInfractions());
		}
		catch(Exception e){			
			log4J.error(e.getMessage(), e);
			return "ERROR";
		}
		
		//get list of infractions that have accounts verified (verified = 0 in DB)
		
		return getButton();		 
	}		
		
	public Long getTheId() {
		return theId;
	}
	public void setTheId(Long mpdUserId) {
		this.theId = mpdUserId;
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

	
	/**
	 * Precondition:
	 * Effects:
	 * @param ACmd
	 * @throws SQLException 
	 */
	private void executeCMD(String ACmd) throws Exception{
		
		log4J.debug("---------********************EXECUTING THE COMMAND*****----*******------------*");
				
		if(ACmd !=null){
							
				TrafficInfractionsDAO.getInfractionsDAOInstance().updateDisplay(getTheId(), buttonValue(button));
				context.setAttribute("lstinfractions", TrafficInfractionsDAO.getInfractionsDAOInstance().listOfInfractions());
				setDisplayUserMessage("Record set to be " + BUTTON_ACTION.valueOf(button));			
		
		}		
	}

	public void setListOfInfractions(List<TrafficInfraction> listOfInfractions) {
		this.listOfInfractions = listOfInfractions;
	}

	public List<TrafficInfraction> getListOfInfractions() {
		return listOfInfractions;
	}	
	
	
	private int buttonValue(String buttonSelected){
		
		int returnValue = -1; 
		
		switch (BUTTON_ACTION.valueOf(buttonSelected)) {
		
		case Show:
			returnValue = 0;
			break;
		case Hide:
			returnValue = 1;
			 break;
         default:
        	 returnValue = 0;
         
     }
		return returnValue;		
	}

	/**
	 * Button enum type
	 */
	enum BUTTON_ACTION{
	  Show, Hide, Delete, Edit
	}
	/**
	 * Test Driver for enums button
	 * @param args
	 
		public static void main(String... args){
			AdministerInfractionsAction anInf = new AdministerInfractionsAction();
			String show = "Show";
			String hide = "Hide";
			System.out.println (BUTTON_ACTION.valueOf(show) + " == " + anInf.buttonValue(show));
		}
	**/

	@Override
	public void setServletContext(ServletContext contextFromAction) {
		context = contextFromAction;		
		
	}
}

