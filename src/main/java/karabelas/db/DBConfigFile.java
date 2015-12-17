package karabelas.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jolbox.bonecp.BoneCPConfig;
 
public final class DBConfigFile {
	
	private static InputStream inputStream;
    private static Logger log4j = Logger.getLogger(DBConfigFile.class);
	
    /**
    * @param
    * @return 
    *  
    * 
    */
	public static BoneCPConfig createBoneCPConfig(String propFileName) throws IOException {
 
		try {
			BoneCPConfig config = null;
			Properties prop = new Properties(); 
			inputStream = new FileInputStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			config = createBoneCPConfigFromProperties(prop);
			
			if (config != null) 
				return config;
			else
				throw new Exception("Unable to locate the Config Property File Specified in Parameter!");			
			
			
		} catch (Exception e) {
			log4j.error("Exception: " + e.getMessage());
			log4j.error("Exception:path to property file/name " + propFileName );
		} finally {
			inputStream.close();
		}
		//should never get here
		return null;
	
	}
	/**
	 * 
	 * @param prop
	 * @return
	 * @throws Exception
	 */
	private static BoneCPConfig createBoneCPConfigFromProperties(Properties prop) throws Exception{
		
		log4j.info("PROPERTY FILE TO STRING ---> " + prop.toString());		
		
		BoneCPConfig config = new BoneCPConfig();
		// get the property value and print it out	
     
		config.setJdbcUrl(prop.getProperty("jdbcUrl")); 
		config.setUsername(prop.getProperty("Username")); 
		config.setPassword(prop.getProperty("Password"));
		config.setMinConnectionsPerPartition(Integer.parseInt(prop.getProperty("minConnectionsPerPartition")));
		config.setMaxConnectionsPerPartition(Integer.parseInt(prop.getProperty("maxConnectionsPerPartition")));
		config.setPartitionCount(Integer.parseInt(prop.getProperty("partitionCount")));
		log4j.info("BONE CONFIG FILE TO STRNG " + config);	
		return config;
		
	}
	
}