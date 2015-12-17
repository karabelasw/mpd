package karabelas.mpd;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import karabelas.LogRequest;
import karabelas.db.DBConnectionPool;
import karabelas.db.SQLAndOrClauseBuilder;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
/**
 *  ParkingMovingInfractionsDAO
 * @author karabelasw
 *
 */

public class TrafficInfractionsDAO {
	
	private QueryRunner run = null;  
	private DataSource aDataSource;
	
	private static String SELECT_SEARCH_INFRACTIONS = "Select * from trafficinfraction WHERE ";
	private static String UPDATE_INFRACTION_DISPLAY = "update trafficinfraction set Display = ? where ID = ?";
	private static String UPDATE_INFRACTION_FINE = "update trafficinfraction set Fine = ? where ID = ?";
	private static Logger log4J = Logger.getLogger(TrafficInfractionsDAO.class);
	private static TrafficInfractionsDAO INFRACTIONS_SINGLETON = null;
	
	
	private TrafficInfractionsDAO(DataSource theDatasource){
		
		if(theDatasource == null){
			throw new IllegalStateException ("Database connection is unvailable.  Check database service and connection parameters");			
		}
		this.aDataSource = theDatasource;	
		
	}
	
	/**
	 * Precondition: DBConnectionPool datasource has been setup..aka database connection
	 * is valid.  I'm not sure if this thing is thread safe but local variables are
	 *  and the QueryRunner is stated to be threadsafe.  So one instance and all calls
	 *  to DB through this DAO.  
	 * @return TrafficInfractionsDAO
	 */
	public static TrafficInfractionsDAO getInfractionsDAOInstance()throws Exception{
		
		if(INFRACTIONS_SINGLETON == null){			
			try {
				INFRACTIONS_SINGLETON = new TrafficInfractionsDAO(DBConnectionPool.getDBConnectionPool());
			} catch (Exception e) {
				log4J.error(e.getMessage(), e);	
				throw new Exception(e);
			}
		}	
		return INFRACTIONS_SINGLETON;
	}
	/**
	 * 
	 * @return List containing TrafficInfraction records from database ordered by Code
	 * @throws SQLException
	 */
	 public List<TrafficInfraction> listOfInfractions() throws SQLException {
		
		 List<TrafficInfraction> lstOfInfractions = null;		 
		 if(run == null){
			run = new QueryRunner(aDataSource);
		 }
		 if( run != null){	 
			 
			 ResultSetHandler<List<TrafficInfraction>> h = new BeanListHandler<TrafficInfraction>(TrafficInfraction.class);
		     // Execute the SQL statement and return the results in a List of		     
			 lstOfInfractions = run.query("SELECT * FROM trafficinfraction where Display = 0 order by Code", h);
		 }
		 
		 	return lstOfInfractions;		 
	  }
	 	/**
		 * 
		 * @return List containing TrafficInfraction records from database ordered by Code
		 * Displays all regardless of 'Display' value in database (for administrative purposes)
		 * @throws SQLException
		 */
		 public List<TrafficInfraction> listAllInfractions() throws SQLException {
			
			 List<TrafficInfraction> lstOfInfractions = null;		 
			 if(run == null){
				run = new QueryRunner(aDataSource);
			 }
			 if( run != null){	 
				 
				 ResultSetHandler<List<TrafficInfraction>> h = new BeanListHandler<TrafficInfraction>(TrafficInfraction.class);
			     // Execute the SQL statement and return the results in a List of		     
				 lstOfInfractions = run.query("SELECT * FROM trafficinfraction order by Code", h);
			 }
			 
			 	return lstOfInfractions;		 
		  }
	  /**
	  * Return a single {@link LogRequest} identified by its id.
	  */
	  public static TrafficInfraction fetchbyId(int anID) {
	    return new TrafficInfraction();
	  }
	  
	  public static TrafficInfraction fetchByCode(String aCode) {
		    return new TrafficInfraction();
	 }


	  /** Return the number of records in parkingmovinginfractions table.  */
	 public Integer getCountFromParkingMovingInfractions() throws SQLException {
	    return 0;
	  }
	 
	 /**
	  * 
	  * @param optButton =>0 determines the SQL builder
	  * @param searchWords not empty and not null
	  * @param fieldName-- databasefield name to be search
	  * @return returns a List of ParkingMovingInfraction objects or an empty list
	  * @throws SQLException
	  */
	 
	 public List<TrafficInfraction> listSearchInfractionsByDescription(int optButton, String searchWords, String fieldName) throws SQLException {
			
		 List<TrafficInfraction> lstOfInfractions= null;		 
		 StringBuilder sql = new StringBuilder(SELECT_SEARCH_INFRACTIONS);
		 if(run == null){
			run = new QueryRunner(aDataSource);
		 }
		 if( run != null){
			 			 
			 ResultSetHandler<List<TrafficInfraction>> rsh = new BeanListHandler<TrafficInfraction>(TrafficInfraction.class);
			//build array of parameters to pass queryRunner
			 Object[] arraySqlParameters = SQLAndOrClauseBuilder.buildParametersFromString(searchWords, " ");
			 
			 //this will prefix and suffix each search word with '%' for SQL wildcard 
			 //we wont want to do this for 'exact' searches
			  arraySqlParameters = SQLAndOrClauseBuilder.appendWildCardToSearchWords(arraySqlParameters);			 
			 
			 switch(optButton){
			 case 0://exact was selected, we reset our arrayParameter as one string of search words
				 arraySqlParameters = new Object[1];
				 arraySqlParameters[0] = searchWords;
				 sql.append(fieldName+" LIKE ?");
				break;
			 case 1://'any' was selected build Like OR clause of SQL
				 sql.append(SQLAndOrClauseBuilder.buildLikeOrClause(arraySqlParameters.length, fieldName));
				 break;
			 case 2://'and' was selected build Like AND clause of SQL
				 sql.append(SQLAndOrClauseBuilder.buildLikeAndClause(arraySqlParameters.length, fieldName));
				 break;
			 default:
				 arraySqlParameters = new Object[1];
			 	 arraySqlParameters[0] = searchWords;
				 sql.append("Description LIKE ?");
				 break;		 
			 }
			 sql.append(" AND (Display = 0) Order By Code");
			 
			 log4J.info("*******EXECUTING THE SQL LINE OF*******-->\n" + sql.toString());
			 lstOfInfractions = (List<TrafficInfraction>)run.query(sql.toString(), rsh, arraySqlParameters);
		     // Execute the SQL statement and return the results in a List of		     
			 
		 }
		 
		 	return lstOfInfractions;		 
	  }		 
	 
	 public List<TrafficInfraction> listSearchInfractionsByCode(int optButton, String searchWords, String fieldName) throws SQLException {
			
		 List<TrafficInfraction> lstOfInfractions= null;		 
		 StringBuilder sql = new StringBuilder(SELECT_SEARCH_INFRACTIONS);
		 if(run == null){
			run = new QueryRunner(aDataSource);
		 }
		 if( run != null){
			 			 
			 ResultSetHandler<List<TrafficInfraction>> rsh = new BeanListHandler<TrafficInfraction>(TrafficInfraction.class);
			//build array of parameters to pass queryRunner
			 Object[] arraySqlParameters = SQLAndOrClauseBuilder.buildParametersFromString(searchWords, " ");
			 
			 //this will prefix and suffix each search word with '%' for SQL wildcard 
			 //we wont want to do this for 'exact' searches
			  arraySqlParameters = SQLAndOrClauseBuilder.appendWildCardToSearchWords(arraySqlParameters);			 
			 
			 switch(optButton){
			 case 0://exact was selected, we reset our arrayParameter as one string of search words
				 arraySqlParameters = new Object[1];
				 arraySqlParameters[0] = searchWords;
				 sql.append(fieldName+" LIKE ?");
				break;
			 case 1://'any' was selected build Like OR clause of SQL
				 sql.append(SQLAndOrClauseBuilder.buildLikeOrClause(arraySqlParameters.length, fieldName));
				 break;
			 case 2://'and' was selected build Like AND clause of SQL
				 sql.append(SQLAndOrClauseBuilder.buildLikeAndClause(arraySqlParameters.length, fieldName));
				 break;
			 default:
				 arraySqlParameters = new Object[1];
			 	 arraySqlParameters[0] = searchWords;
				 sql.append("Code LIKE ?");
				 break;		 
			 }
			 sql.append(" AND (Display = 0) Order By Code");
			 
			 log4J.info("*******EXECUTING THE SQL LINE OF*******-->\n" + sql.toString());
			 lstOfInfractions = (List<TrafficInfraction>)run.query(sql.toString(), rsh, arraySqlParameters);
		     // Execute the SQL statement and return the results in a List of		     
			 
		 }
		 
		 	return lstOfInfractions;		 
	  }
	 
		/**
		 * Changes value of 'display' column in database table trafficinfractions
		 * The display field will be set to 0 or 1;
		 * @param id of record to be updateds
		 * @param lockedOrUnlocked (0=Display, 1=NotDisplayed)
		 */
		  public boolean updateDisplay(Long id, int isDisplayed){
				
			  	boolean updateSuccess = false;
		  		run = new QueryRunner(aDataSource);			
		
				try {
						run.update(UPDATE_INFRACTION_DISPLAY, isDisplayed, id );
						updateSuccess = true;
						
				} catch (SQLException e) {
					log4J.error(e.getMessage(), e);					
				}
				
				return updateSuccess;
		}
		  public boolean updateFine(Long id, float floatFine){
				
			  	boolean updateSuccess = false;
		  		run = new QueryRunner(aDataSource);			
		
				try {
						run.update(UPDATE_INFRACTION_FINE, floatFine, id );
						updateSuccess = true;
						
				} catch (SQLException e) {
					log4J.error(e.getMessage(), e);					
				}
				
				return updateSuccess;
		}		  
	 
}
