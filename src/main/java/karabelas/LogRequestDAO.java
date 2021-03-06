package karabelas;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import karabelas.db.DBConnectionPool;

import org.apache.commons.dbutils.QueryRunner;

/*
 * 
  SELECT Id, IpAddress, RequestURI, AuthUser, DateTimeOfRequest FROM RequestLog WHERE Id=?


LIST_LOG 
  SELECT Id, IpAddress, RequestURI, AuthUser, DateTimeOfRequest RequestLog ORDER BY DateTimeOfRequest

ADD_LOG
  INSERT INTO RequestLog(IpAddress, RequestURI, AuthUser, DateTimeOfRequest, ParameterNames) VALUES (?,?,?,?,?)

COUNT_ACTIVE_LOG 
  SELECT COUNT(*) FROM RequestLog
  
  //INSERT INTO RequestLog(IpAddress, RequestURI, AuthUser, DateTimeOfRequest, ParameterNames) VALUES (?,?,?,?,?)

 */

public class LogRequestDAO {

	
	private static String INSERT_REQUEST = "INSERT INTO requestlog(IpAddress, RequestURI, AuthUser, ParameterNames) VALUES (?,?,?,?)";
	  
	
	 public List<LogRequest> list() throws SQLException {
	    return new ArrayList();
	  }

	  /**
	  * Return a single {@link LogRequest} identified by its id.
	  */
	  public LogRequest fetch(int aLogsId) {
	    return new LogRequest();
	  }

	  /**
	  * 
	  *
	  * @return the autogenerated database id.
	  */
	  public int add(LogRequest aRequestToBeLogged) throws SQLException {
		 if(DBConnectionPool.getDBConnectionPool() != null){
			//pass the connection and the SQL String 
			QueryRunner dbSQL = new QueryRunner(DBConnectionPool.getDBConnectionPool());
			int i = dbSQL.update(INSERT_REQUEST, aRequestToBeLogged.getIpAddress(), aRequestToBeLogged.getRequestURI(),aRequestToBeLogged.getAuthUser(), aRequestToBeLogged.getLogParameterNames());
			return i;
			 
		 }	 
			return -1;
	  }

	  /** Return the number of records in RequestLog.  */
	  Integer getCountFromRequestLog() throws SQLException {
	    return 0;
	  }
}
