package karabelas.mpd;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import karabelas.db.DBConnectionPool;


public class MPDUserRolesDAO {

	private static String INSERT_USER_ROLE = "insert into mpduserroles (MPDUserID, MPDRoleID, RecordCreatedOn) Values (?, (select RoleId from mpdroles where RoleName=?), ?)";

	private static MPDUserRolesDAO MPDUserRolesDAO_SINGLETON = null;
	private DataSource aDataSource;
	private QueryRunner run;
	
	private MPDUserRolesDAO(DataSource theDatasource){
		this.aDataSource = theDatasource;		
	}

	/**
	 * Precondition: DBConnectionPool datasource has been setup..aka database connection
	 * is valid.  I'm not sure if this thing is thread safe but I always thought
	 * local variables were and the QueryRunner is stated to be threadsafe.
	 * @return
	 */
	public static MPDUserRolesDAO getMPDUserRolesDAOInstance(){
		
		if(MPDUserRolesDAO_SINGLETON == null){			
			MPDUserRolesDAO_SINGLETON = new MPDUserRolesDAO(DBConnectionPool.getDBConnectionPool());
		}	
		return MPDUserRolesDAO_SINGLETON;
	}
	
	public void grantUserPermission(Long mpdUserId){
		
	  		run = new QueryRunner(aDataSource);			
    	
			try {
					int updates = run.update(INSERT_USER_ROLE, mpdUserId, "user", new java.sql.Timestamp(System.currentTimeMillis()) );				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
}
