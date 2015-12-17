package karabelas.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import karabelas.ContentsConstants;
import karabelas.mpd.TrafficInfractionsDAO;
import karabelas.mpd.TrafficInfraction;
import org.apache.log4j.Logger;
import org.apache.commons.collections4.CollectionUtils;
import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Servlet implementation class TrafficParkingInfraction
 * @author karabelasw
 * 
 */
public class TrafficParkingInfraction extends HttpServlet {
	

		private static final long serialVersionUID = 1L;
		BoneCPDataSource pool = null;
		ServletContext application = null;	
		Logger log4J = Logger.getLogger(TrafficParkingInfraction.class);
		
		 public void init(ServletConfig config)throws ServletException{
			super.init(config);				
			log4J.debug("INITIALIZING INIT METHOD TRAFFICPARKINGINFRACTION SERVLET**!!");
			application = this.getServletContext();		
			pool = (BoneCPDataSource)application.getAttribute("pool");
			
			try {
					application.setAttribute("lstinfractions", TrafficInfractionsDAO.getInfractionsDAOInstance().listOfInfractions());
				
			}catch (Exception e) {					
					log4J.error("Exception: "+ e.getMessage(), e);
			}			
		}	 
		
		 /**
		  * 
		  */
		public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			
						
			if(request.getParameter("Reset") != null){				
				response.sendRedirect("/mpd/listinfractions.jsp");
				return;				
			}				
				
			if(validParameters(request)){
				placeSearchResultsIntoUserSession(request);			
			}
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/listinfractions.jsp");
			rd.forward(request, response);
		}
		
		public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
			log4J.info("Servlet: TrafficParkingInfraction: Method: doPost ");
			doGet(request, response);	
		}

		/**
		 * only description (search words) need to be provided to make request valid
		 * Effects: if no search words are provided the default behavior is to display
		 * the entire list of parkingmovinginfractions from the application level scope (loaded on init of this)
		 * @param request
		 * @return
		 */
		private boolean validParameters(HttpServletRequest request){
			
			log4J.debug("*****PROCESSING PARAMETERS*******");			
			String description = request.getParameter("description");			
			boolean hasValidParams = false;
			
			//were search words provided
			if(description != null && !description.equals("")){			   
			    hasValidParams = true;			    
			 }else if(request.getParameter("submit") != null){
				 //System.out.println("<--******INSIDE ELSE OF VALIDPARAMETERS******-->");
				 request.setAttribute("providesearchcriteria", ContentsConstants.PROVIDESEARCHWORDS);
			 }
			return hasValidParams;					
		}		
	
		/**
		 * Precondition: 'description' parameter (search criteria) inside HttpServletRequest exists
		 * Postcondition: Database call which creates a java.util.List of ParkingMovingInfraction objects
		 * and places them in request.attribute
		 * 
		 * @param request
		 */
		private void placeSearchResultsIntoUserSession(HttpServletRequest request){
			
			String strDescription = request.getParameter("description");
			strDescription = strDescription == null ? "" : strDescription;
			List <TrafficInfraction> infractionsByDescription = null;
			List <TrafficInfraction> infractionsByCode = null;
			
			try {
					infractionsByDescription = TrafficInfractionsDAO.getInfractionsDAOInstance().listSearchInfractionsByDescription(typeOfSearch(request), strDescription, "Description");
					//request.setAttribute("lstinfractions", TrafficInfractionsDAO.getInfractionsDAOInstance().listSearchInfractionsByDescription(typeOfSearch(request), strDescription, "Description"));
					if ((request.getParameter("include_codes") != null) && (!request.getParameter("include_codes").equals("")) ){
					
						infractionsByCode = TrafficInfractionsDAO.getInfractionsDAOInstance().listSearchInfractionsByCode(typeOfSearch(request), strDescription, "Code");
						infractionsByDescription = combineResults(infractionsByDescription, infractionsByCode);
					
					}
				
					request.setAttribute("lstinfractions", infractionsByDescription);
			} catch (Exception e) {
				
				request.setAttribute("error", "Unable to search database at this time");
				log4J.error("ERROR GETTING SEARCH RESULTS " + e.getMessage());
			}
			
		}
		/**
		 * 
		 * @param request
		 * @return an int which represents what radio button was selected
		 * when the search form was submitted. 
		 *  
		 */
		private int typeOfSearch(HttpServletRequest request){
			
				log4J.debug("*****TYPE OF SEARCH *******");
				String optionBtn = request.getParameter("sgroup");				
				optionBtn = optionBtn == null ? "zero" : optionBtn;
		   //arbitrarily give parameters a number value starting with 0 until 2		
				if(optionBtn.equals("exact")){
					return 0;
				}
			    if(optionBtn.equals("any")){
			    	return 1;	    	
			    }
			    if(optionBtn.equals("all")){
			    	return 2;
			    }		
									
			 return 1;
		}
		/**
		 * Precondition:
		 * @param searchList
		 * @param codeList
		 * @return
		 */
		private List<TrafficInfraction> combineResults(List<TrafficInfraction> searchList, List<TrafficInfraction> codeList){
			List<TrafficInfraction> combinedResults = null;
			
			if(searchList != null && codeList!= null ){
				log4J.info("<------------LISTS ARE NOT NULL------UNION THESE RESULTS-------------------->");
				combinedResults = (List<TrafficInfraction>) CollectionUtils.union(searchList, codeList);
				Collections.sort(combinedResults);
			}
			return combinedResults;
			
		}
			  
	}