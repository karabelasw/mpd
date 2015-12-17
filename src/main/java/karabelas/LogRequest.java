package karabelas;

public class LogRequest {

	
	String IpAddress = "";
	String logParameterNames;
	String requestURI;
	String authUser;
	String referer;
	private final long dateTimeOfRequest;
	
	public LogRequest(){
		 dateTimeOfRequest = new java.util.Date().getTime();
	}
	
	public String getIpAddress() {
		return IpAddress;
	}
	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}
	public String getLogParameterNames() {
		return logParameterNames;
	}
	public void setLogParameterNames(String logParameterNames) {
		this.logParameterNames = logParameterNames;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	public String getAuthUser() {
		return authUser;
	}
	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	
	public long getDateTimeOfRequest() {
		return dateTimeOfRequest;
	}
	



}
