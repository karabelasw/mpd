
package karabelas.ajax;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import karabelas.mpd.TrafficInfractionsDAO;


import org.apache.log4j.Logger;

/**
 * Servlet implementation class UpdateFine
 */
public class UpdateFine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log4J = Logger.getLogger(UpdateFine.class);   
	ServletContext application = null;	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log4J.debug("Infraction Id sent was " + request.getParameter("id"));
		
		String combinedName = request.getParameter("id") + ", "+ request.getParameter("value");
		String fineValue = request.getParameter("value");		
		log4J.debug("Fine value " + fineValue);
		response.setContentType("text/xml");
		
		//make sure params are present and we can save to DB
		if(checkParameters(request) && updateFine(request.getParameter("id"), request.getParameter("value"))){
				response.getWriter().print(fineValue);				
		}else{
				response.getWriter().print("Error: Must provide a numerical value");
		}			
		
		log4J.debug("server received---->" + combinedName);
		
	}
	
	//check to make sure we have an 'id' and 'value' parameter
	private boolean checkParameters(HttpServletRequest request){
		
		String infractionId =  request.getParameter("id");
		String fineValue = request.getParameter("value");
		boolean returnValue = false;	
		
		if(infractionId != null && !infractionId.equals("")){
			if(fineValue != null && !fineValue.equals("")){
				returnValue = true;
			}
		}		
		
		return returnValue;			
	}
	
	/**
	 * Precondition: parameters are not null
	 * Parameters will be converted to Long and Float values
	 * Postcondition: DB-table:trafficinfraction record will be updated
	 * with the newFineValue	 
	 * @param infractionId
	 * @param newFineValue
	 * @return
	 */
	private boolean updateFine(String infractionId, String newFineValue){
		
		boolean returnValue = false;	
		long id = Long.parseLong(infractionId);
		float fineValue = Float.parseFloat(newFineValue);
		try {
				returnValue = TrafficInfractionsDAO.getInfractionsDAOInstance().updateFine(id, fineValue);
				application = this.getServletContext();
				application.setAttribute("lstinfractions", TrafficInfractionsDAO.getInfractionsDAOInstance().listOfInfractions());
		} catch (Exception e) {
			log4J.debug("Error parsing fine value OR updating values inside application", e);
			returnValue = false;
			e.printStackTrace();
		}
		return returnValue;
		
	}
	
	

}
