package karabelas.mpd;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import karabelas.db.DBConnectionPool;
import karabelas.db.SQLAndOrClauseBuilder;
import karabelas.security.PasswordHash;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

/**
 *  Used to interface with database (mpd) table:MPDUsers
 * @author karabelasw
 *
 */

public class MPDUsersDAO {

 
	private static String USER_ACCOUNTS = "select * from mpdusers join mpduserroles on mpdusers.MPDUserID = mpduserroles.MPDUserID join mpdroles on mpduserroles.MPDRoleID = mpdroles.RoleId where Verified = 0";
	private static String INSERT_MPDUSER = "insert into mpdusers (MPDUsername, MPDEmailAddress, LastName, FirstName,DateAccountRequested, MPDUserPassword, Verified) Values (?, ?, ?, ?, ?, ?, ?)";
	private static String UPDATE_SQL_LOCKED_ACCOUNT = "update mpdusers set locked = ? where MPDUserID = ?";
	private static String RESET_ACCOUNT_PASSWORD_ = "update mpdusers set MPDUserPassword = ? where MPDUserID = ?";
	private static String UPDATE_PASSWORD = "update mpdusers set MPDUserPassword = ? where MPDUserName = ?";
	private static String UPDATE_VERIFIED = "update mpdusers set Verified = ? where MPDUserID = ?";
	private static String LOGIN_SQL = "select * from mpdusers join mpduserroles on mpdusers.MPDUserID = mpduserroles.MPDUserID join mpdroles on mpduserroles.MPDRoleID = mpdroles.RoleId where MPDUserName = ? and MPDUserPassword = ? and Verified = 0 and Locked = 0";
	public static String HASH_KEY = "karabelas.com";
	private static String PASSWORD_RESET = "Welcome1";
	private static Logger log4J = Logger.getLogger(MPDUsersDAO.class);
	private static String SELECT_NEW_ACCOUNTSREQUESTED = "select LastName, FirstName, mpdusers.MPDUserID, MPDUserName, MPDEmailAddress from mpdusers left join mpduserroles on mpdusers.mpdUserID = mpduserroles.MPDUserID where mpduserroles.MPDUserId is null";
	private static String DELETE_USER = "delete from mpdusers where MPDUserID =?";	
	//only one instance of this class can be created
	private static MPDUsersDAO SINGLETON = null;
	private DataSource aDataSource;
	//QueryRunner is stated to be thread safe in apache DBUtils API docs
	private QueryRunner run = null; 
	
	/**
	 * Precondition: connection to database (DataSource) is valid
	 * if not then throw an exception
	 * @param theDataSource,
	 * 
	 */
	private MPDUsersDAO(DataSource theDataSource){
		
		if(theDataSource == null){
			throw new IllegalStateException("Datasource is null--->Please check database is in service and the connection parameters");
		}
		aDataSource = theDataSource;
	}
	/**
	 * Precondition: Database connection is valid (good datasource with active pool)  
	 * 
	 * @return the instance MPDUsersDAO
	 */
	public static MPDUsersDAO getMPDUsersDAOInstance()throws Exception{
		
		
		if(SINGLETON == null){			
			try {
				SINGLETON = new MPDUsersDAO(DBConnectionPool.getDBConnectionPool());
			} catch (Exception e) {
				log4J.error(e.getMessage(), e);
				throw new Exception(e.getMessage()+" and contact the database administrator");
			}
		}	
		return SINGLETON;
	}
	
	/**
	 * 
	 * @return a java.util.List of MPDUsers.class
	 * @throws SQLException
	 */
	 public  List<MPDUsers> listOfMPDUsers(){
		
		 List<MPDUsers> lstOfMPDUsers = null;		 
		 run = new QueryRunner(aDataSource);
		 
		 if( run != null){			 
			 ResultSetHandler<List<MPDUsers>> h = new BeanListHandler<MPDUsers>(MPDUsers.class);
		     // Execute the SQL statement and return the results in a List		     
			 try{
				 lstOfMPDUsers = run.query(USER_ACCOUNTS, h);
			 }catch(SQLException sqlException){
				 log4J.error("Error getting users from database:--->" + sqlException.getMessage());
			 }
			 
		 }		 
		 	return lstOfMPDUsers;
	  }
	 /**
		 * 
		 * @param 
		 * @return a java.util.List of MPDUsers.class
		 * @throws SQLException
		 */
		 public List<MPDUsers> listNewMPDUsersRequestingAccounts() {
			
			 List<MPDUsers> lstOfMPDUsers = null;		 
			 run = new QueryRunner(aDataSource);
			 
			 if( run != null){			 
				 ResultSetHandler<List<MPDUsers>> h = new BeanListHandler<MPDUsers>(MPDUsers.class);
			     // Execute the SQL statement and return the results in a List		     
				 try {
					lstOfMPDUsers = run.query(SELECT_NEW_ACCOUNTSREQUESTED, h);
				} catch (SQLException e) {
					 e.printStackTrace();
				}
			 }			 
			 	return lstOfMPDUsers;
		  }
	/**
	 * 
	 * @param MPDUserId
	 * @return an MPDUsers object from database which has anID passed in my the parameter
	 */
	  public static MPDUsers fetchbyId(int anID) {
	    return new MPDUsers();
	  }
	  

	  /**
	   * PreCondition: parameters are not null or empty
	   * @param userName
	   * @param userPassword
	   * @return will fetch (return) the corresponding MPDUsers record from the database
	   * otherwise will return null
	   * @throws SQLException
	   */
	  public MPDUsers loginMPDUser(String userName, String userPassword){
		  	
			MPDUsers aUser = null;
			run = new QueryRunner(DBConnectionPool.getDBConnectionPool());		  
		  
			Object[] params = new Object[2];   
			params[0] = userName;
			params[1] = PasswordHash.getMD5Hash(userPassword, HASH_KEY);			
			
			ResultSetHandler<MPDUsers> rsh = new BeanHandler<MPDUsers>(MPDUsers.class);
			try{
				aUser = run.query(LOGIN_SQL, rsh, params);
				if(aUser != null){
					updateLastLogin(aUser);
				}
			}
			catch (SQLException sqlException){
				log4J.error("ERROR GETTING MPDUsers from database " + sqlException.getMessage());
				return null;
			}
		    return aUser;
	  }
	  /**
	   * 
	   * 
	   * @param aUserName
	   * @return
	   */
	  public boolean checkForUserNameInMPDUsers(String aUserName){
		  
		  boolean userExists = false;
		  MPDUsers aUser = null;
		  run = new QueryRunner(DBConnectionPool.getDBConnectionPool());  
		  Object[] params = SQLAndOrClauseBuilder.buildParametersFromStrings(aUserName);		    	
		  ResultSetHandler<MPDUsers> rsh = new BeanHandler<MPDUsers>(MPDUsers.class);
		        
	    	try{
	        	aUser = run.query("SELECT * from mpdusers where MPDUserName = ?", rsh, params);
	        	if(aUser != null){
		        		userExists = true;
	        	}
		    }
		    catch (SQLException sqlException){
		        	System.out.println("ERROR GETTING mpdusers from database " + sqlException.getMessage());
		        	return true;
		    }
		       		        	 		 
			 return userExists;
	  }
	  /**
	   * 
	   * @param aUser
	   * @throws SQLException
	   */
	  private void updateLastLogin(MPDUsers aUser) throws SQLException{
		  java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		  Object param = new java.sql.Timestamp(date.getTime());
		  
		    if(aUser != null){
		    	run.update( "UPDATE mpdusers SET LastLogin=? WHERE MPDUserID = ?", param, aUser.getMPDUserID());
		    }	  
	  }
	  /**
	   * Precondition: Parameter object:MPDUsers is not null,
	   * MPDUsers fields contain data in:
	   * MPDUsername, MPDEmailAddress, LastName, FirstName, MPDUserPassword, Verified
	   * Postcondition: Will insert record into database table: mpdusers
	   * @param aUser
	   * @return
	   */
	  public MPDUsers createMPDUserAccount(MPDUsers aUser){
		    
		  	MPDUsers newUser = null;
			run = new QueryRunner(aDataSource);				
			ResultSetHandler<MPDUsers> rsh = new BeanHandler<MPDUsers>(MPDUsers.class);
			
			try{
				newUser = run.insert(INSERT_MPDUSER, rsh, aUser.getMPDUserName(), aUser.getMPDEmailAddress(),
						aUser.getLastName(), aUser.getFirstName(),
						new java.sql.Timestamp(System.currentTimeMillis()),
						aUser.getMPDUserPassword(), aUser.getVerified()
						);
			}
			catch (SQLException sqlException){
				log4J.error("ERROR INSERTING into mpdusers table " + sqlException.getMessage());
				return null;
			}		  
		  
		  return newUser;		  
		  
	  }
	  /**
	   * Changes password in database for userName (MPDUserName)
	   * @param String:newPassword
	   * @param String:userName
	   * @return
	   */
	  public int changeMPDUserPassword(String newPassword, String userName){
		    
		  	
			run = new QueryRunner(DBConnectionPool.getDBConnectionPool());				
			int i = 0;
			
			try{
				i = run.update(UPDATE_PASSWORD, newPassword, userName);
							}
			catch (SQLException sqlException){
				log4J.error("---ERROR updating password in mpdusers table--> " + sqlException.getMessage());
				return -1;
			}		  
		  
		  return i;		  
		  
	  }
	 
	  /**
	   * Deletes record from database
	   * @param mpdUserID
	   * @return
	   */
	  public int deleteUser(Long mpdUserID){		    
		  	
			run = new QueryRunner(aDataSource);				
			int i = 0;
			
			try{
				i = run.update(DELETE_USER, mpdUserID);
							}
			catch (SQLException sqlException){
				log4J.error("---ERROR deleting user from mpdusers table--> " + sqlException.getMessage());
				return -1;
			}		  
		  
		  return i;		  
		  
	  }
	  
	    /**
		 * Changes value of locked column in database table mpdusers
		 * @param mpdUserId
		 * @param verified (0=verified, 1=not verified)
		 */
	  public void updateVerified(Long mpdUserId, Integer verified){
			
	  		run = new QueryRunner(aDataSource);			
  	
			try {
					int updates = run.update(UPDATE_VERIFIED, verified, mpdUserId );				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	/**
	 * Changes value of locked column in database table mpdusers
	 * @param mpdUserId
	 * @param lockedOrUnlocked (0=unlocked, 1=locked)
	 */
	  public void updateLocked(Long mpdUserId, Integer lockedOrUnlocked){
			
	  		run = new QueryRunner(aDataSource);			
	
			try {
					int updates = run.update(UPDATE_SQL_LOCKED_ACCOUNT, lockedOrUnlocked, mpdUserId );
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	  
	  public void resetPassWord(Long mpdUserId) {
	 		
		  run = new QueryRunner(aDataSource);			
	 		
			try {
					int updates = run.update(RESET_ACCOUNT_PASSWORD_, PasswordHash.getMD5Hash(PASSWORD_RESET, HASH_KEY), mpdUserId );
					
			} catch (SQLException e) {
				e.printStackTrace();
			}		  
		  
		 } 
	  
}
