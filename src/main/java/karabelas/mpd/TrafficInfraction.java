package karabelas.mpd;

public class TrafficInfraction implements Comparable <TrafficInfraction> {
	
	
	private int id;
	private String code;
	private String description;
	private String shortDescription;
	private float fine;
	private int display;
	
	
	public TrafficInfraction(){
		
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getShortDescription() {
		return shortDescription;
	}


	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}


	public float getFine() {
		return Float.valueOf(fine);
	}


	public void setFine(float fine) {
		this.fine = fine;
	}


	public int getDisplay() {
		return display;
	}


	public void setDisplay(int display) {
		this.display = display;
	}

  
	public String toString(){
	  StringBuffer sb = new StringBuffer(500);
	  sb.append("ID:"+this.id);
	  sb.append(", Code:"+this.code);
	  sb.append(", Description:"+this.description);
	  sb.append(",Fine:"+this.fine);
	  return sb.toString();	  
  }
	public boolean equals(Object obj) {

		   if (obj == null) { return false; }

		   if (obj == this) { return true; }

		   if (obj.getClass() != getClass()) {
		     return false;
		   }

		   TrafficInfraction argObject = (TrafficInfraction) obj;

		   if(this.id != argObject.id){
		   		return false;
			}
		   
		   if(this.code != null && argObject != null){		   
			   if(!this.code.equalsIgnoreCase(argObject.code)){
				   return false;
			   }
		   }
		   
		   
		   return true;

	}
	
	public int hashCode(){
		int result = 17;
		result = 37 * result + id;
		
		if(code != null)
			result = 37 * result + code.hashCode();
		
		return result;
	}
	
	public int compareTo(TrafficInfraction compareInfraction) {
		
		int returnValue = 0;
		returnValue = this.code.compareTo(compareInfraction.getCode());
		
		return returnValue;
		
	}
	
/**
 * Test driver for equals
 * @param args

	public static void main(String... args){
		
		TrafficInfraction t1 = new TrafficInfraction();
		TrafficInfraction t2 = new TrafficInfraction();
		TrafficInfraction t3 = new TrafficInfraction();
		TrafficInfraction t4 = new TrafficInfraction();
		
		t1.setCode("P001");
		t2.setCode("P002");
		t3.setCode("P003");
		
		t4.setCode("P001");
		
		t1.setId(1);
		t2.setId(2);
		t3.setId(3);
		t4.setId(1);
		
		System.out.println("Does t1 equal to t2 " + t1.equals(t2));
		System.out.println("Does t1 equal to t4 " + t1.equals(t4));
		
	}
*/
}
