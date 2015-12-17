package karabelas.mpd;
import java.sql.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MPDUsers {
	
	private int MPDUserID;
	private String MPDUserName;
	private String MPDEmailAddress;
	private String LastName;
	private String FirstName;
	private String MiddleName;
	private Date DateAccountRequested;
	private String MPDUserPassword;	
	private int verified;
	private String RoleName;
	private StringBuilder FullName;
	private int locked;


	//default
	public MPDUsers(){
		
		FullName = new StringBuilder(25);
	};
	
	public int getMPDUserID() {
		return MPDUserID;
	}
	public void setMPDUserID(int userID) {
		MPDUserID = userID;
	}
	public String getMPDUserName() {
		return MPDUserName;
	}
	public void setMPDUserName(String userName) {
		MPDUserName = userName;
	}
	public String getMPDEmailAddress() {
		return MPDEmailAddress;
	}
	public void setMPDEmailAddress(String emailAddress) {
		MPDEmailAddress = emailAddress;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public Date getDateAccountRequested() {
		
		return DateAccountRequested;
		
	}
	public void setDateAccountRequested(Date dateAccountRequested) {
		DateAccountRequested = dateAccountRequested;
	}
	public String getMPDUserPassword() {
		return MPDUserPassword;
	}
	public void setMPDUserPassword(String userPassword) {
		MPDUserPassword = userPassword;
	}
	public String getMiddleName() {
		return MiddleName;
	}
	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}
	public int getVerified() {
		return verified;
	}

	public void setVerified(int verified) {
		this.verified = verified;
	}
	
	public String getRoleName() {
		return RoleName;
	}

	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	
	public boolean isAdmin(){
		boolean admin = false;
		if(this.RoleName != null && this.RoleName.equals("admin"))
			admin = true;		
		return admin;
	}
	public String getFullName(){
		
		if(this.FirstName != null)
			this.FullName.append(this.FirstName + " ");
		if(this.MiddleName != null)
			this.FullName.append(this.MiddleName +" ");
		if(this.LastName != null)
			this.FullName.append(this.LastName);
		
		return this.FullName.toString();
	}
	public void setFullName(String aName){}	
	
	
	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}
	
	
	public String toString(){
		 return new ToStringBuilder(this).
	       append("MPDUserID:", this.MPDUserID).
	       append("MPDUserName:", this.MPDUserName).
	       append("MPDEmailAddress:", this.MPDEmailAddress).
	       append("LastName:", this.LastName).
	       append("FirstName:", this.FirstName).
	       append("MiddleName:", this.MiddleName).
	       append("DateAccountRequested:", this.DateAccountRequested).
	       append("MPDUserPassword:",this.MPDUserPassword).
	       append("Verified:", this.verified).
	       append("RoleName:", this.RoleName).
	       append("Locked:", this.locked).
	       toString();	  
	  }	

	/**
	 * Creates a new MDPUsers object from the parameter Object... args
	 * Precondition: order of arguments to be [0]=MPDUserName, [1]=MPDEmailAddress
	 * [2]=LastName, [3]=FirstName, [4]=MPDUserPassword, [5]=Verified
	 * (MPDUsername, MPDEmailAddress, LastName, FirstName, DateAccountRequested, MPDUserPassword, Verified)
	 * 
	 * @param args
	 * @return
	 */
	
	public static MPDUsers createMPDUser(Object... args){
		MPDUsers aUser = new MPDUsers();
		aUser.MPDUserName = (String) args[0];
		aUser.MPDEmailAddress = (String) args[1];
		aUser.LastName = (String) args[2];
		aUser.FirstName = (String) args[3];
		aUser.DateAccountRequested = new java.sql.Date(System.currentTimeMillis());
		aUser.MPDUserPassword = (String)args[4];
		aUser.verified = (Integer)args[5];
		return aUser;
		
	}
}
