package karabelas.servlet;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import karabelas.LogRequest;
import karabelas.LogRequestDAO;
import karabelas.webutils.ParameterChecker;

import org.apache.log4j.Logger;


/**
 * 
 * @author William Karabelas
 * @version 1.1
 * 'logs' data in the database -- places data in the 'RequestLog' table
 * for auditing purposes.  I want to know who was here, where they were, where they
 * were going and what they were after.
 * 
 */
public class RequestLogger implements ServletRequestListener {
	
	private static ServletContext srvCntx = null; 	
	private static final Logger log4J = Logger.getLogger(RequestLogger.class);
	
	
	public void requestDestroyed(ServletRequestEvent requestEvent){	
		log4J.debug("RequestLogger instance  has been destroyed");		
	}
	
	public void requestInitialized(ServletRequestEvent requestEvent){
		
		if (srvCntx == null){			
			srvCntx = requestEvent.getServletContext();			
		}
		LogRequest requestToBeLogged = new LogRequest();		
		log4J.debug("servletContext.getRealPath is " + srvCntx.getRealPath("/"));		
		
	    //Used to get information about a new request
	    ServletRequest request = requestEvent.getServletRequest();
	    requestToBeLogged.setLogParameterNames(logParameterNames(request));
	    //System.out.println("Param names for request are " +  request.getParameterNames());
	    
	    
	    if(request instanceof HttpServletRequest){	    	
	       	
	    	 requestToBeLogged.setIpAddress(((HttpServletRequest)request).getRemoteAddr()); 
		     requestToBeLogged.setRequestURI(((HttpServletRequest)request).getRequestURI());
		     
		     if(ParameterChecker.getLoggedInUser((HttpServletRequest) request) != null){
		    	 requestToBeLogged.setAuthUser(ParameterChecker.getLoggedInUser((HttpServletRequest) request).getMPDUserName()); 
		    }
		    else{
		    	 requestToBeLogged.setAuthUser("unknown");
		     }	     
		     
		     requestToBeLogged.setIpAddress(((HttpServletRequest)request).getRemoteAddr());
		     requestToBeLogged.setReferer(((HttpServletRequest)request).getHeader("Referer"));
		     //log the ServletRequest to the database   
		     attemptAdd(requestToBeLogged);	   
		   
	   }		
	}

	  /**
	  *
	  * @author BK	  
	  * @param LogRequest
	  * 
	  * 
	  */
	  private void attemptAdd(LogRequest aRequestToLog) {
		  
		LogRequestDAO logRequestDAO = new LogRequestDAO();		
		int id = -1;
	    
		try {
	    	  id = logRequestDAO.add(aRequestToLog);
	    }
	    catch(Exception ex){	       	 
	    	   log4J.error("SHOCK DE BOOM *****-----> : " + ex.getMessage());
	    }
	  }
	  
	  /**
	   * Gets the "parameterNames" from the <code>ServletRequest</code> parameter
	   * Builds a string of all the parameter names sent with the ServletRequest
	   * putting the parameterName inside a block of <-- ParameterName -->   * 
	   *
	   *
	   * @param request
	   * @return
	   */
	  
	 
	private String logParameterNames(ServletRequest request){
		  
		  StringBuilder sb = new StringBuilder(50);
		  Enumeration<String> enumParams = request.getParameterNames();
		  String paramName = null;
		  if(enumParams != null)
			  
		  while(enumParams.hasMoreElements()){
			  sb.append("<--");
			  paramName = (String)enumParams.nextElement();
			  sb.append(paramName+"="+request.getParameter(paramName));
			  sb.append("-->");
		  }
		  
		  log4J.debug("REQUESTPARAMETERS TO STRING OF PARAM NAMES IS " + sb.toString());
		  return sb.toString();
	  }
	  
	  
	
}
