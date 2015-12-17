package karabelas;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.*;
import java.io.*;

public class EmailMsg
{

  private String toEmail, fromEmail, subject;
  private String ccAddress;
  private String messagBody;
  private String Author;

  private java.util.Properties properties;
  private Session mailsession = null;
  public String smtp_server = null;



  public EmailMsg(String anSmtpServer){
	     
	  properties = System.getProperties();
	  smtp_server = anSmtpServer;
	  
	  if(smtp_server != null){
	        properties.put("mail.host", smtp_server);
	        properties.put("mail.transport.protocol", "smtp");
	        mailsession = Session.getInstance(properties, null);
	   }
	  
  }
  
  
	  public String getAuthor(){
	    return Author;
	  }
	  public void setAuthor(String Author) {
	    this.Author = Author;
	  }

	  public String getFromEmail() {
	    return fromEmail;
	  }
	  
	  public void setFromEmail(String fromEmail) {
		    this.fromEmail = fromEmail;
	  }
	  
	  public String getMessagBody() {
	    return messagBody;
	  }
	  public void setMessagBody(String messagBody) {
	    this.messagBody = messagBody;
	  }
	  public String getSubject() {
	    return subject;
	  }
	  public void setSubject(String subject) {
	    this.subject = subject;
	  }
	  public String getToEmail() {
	    return toEmail;
	  }
	  public void setToEmail(String toEmail) {
	    this.toEmail = toEmail;
	  }

	  public void setCCAddress(String anAddress){
	      this.ccAddress = anAddress;
	    }

	public String getCCAddress(String anAddress) {
	      return this.ccAddress;
	    }

	/*
	METHOD:
	
	PRE-CONDITION:
	
	POSTCONDITION:
	*/
	public String buildToAddress(List<String> lstEmailAddresses){
		int numOfObj = lstEmailAddresses.size();
	    StringBuffer theAddresses = new StringBuffer(500);
	    String anEmailAddress = null;
	    
	    for(int index = 0; index < numOfObj ; index++){
		    anEmailAddress = lstEmailAddresses.get(index);
		    theAddresses.append(anEmailAddress);
		    if(index != numOfObj -1)
		           theAddresses.append(", ");
	        }
	     	return theAddresses.toString();
	
	}


	/**

		Precondition: a valid EmailMsg object exists (valid to, from, subject and msgbody
	
	**/
	
	public boolean mailIt()throws Exception{
	       
			boolean mailedIt = true;
	
			try{
				// Construct the message
				MimeMessage message = new MimeMessage(mailsession);
	
				// Set the from address
				Address fromAddress = new InternetAddress(this.getFromEmail());
				message.setFrom(fromAddress);
	
				// Parse and set the recipient addresses
				Address[] toAddresses = InternetAddress.parse(this.getToEmail());
	
				message.setRecipients(Message.RecipientType.TO,toAddresses);
				Address[] ccAddresses = null;
		        
				if(this.ccAddress != null){
				      ccAddresses = InternetAddress.parse(ccAddress);
				      message.setRecipients(Message.RecipientType.CC,ccAddresses);
				}
	
				// Set the subject and text
				message.setSubject(this.getSubject());
				message.setText(this.getMessagBody());
				// send the message
				Transport.send(message);
				return mailedIt;
			}
			catch (AddressException e){
				throw new IOException("There was an error parsing the addresses. " + e);
			}
			catch (SendFailedException e){
				throw new IOException("<h1>Sorry,</h1><h2>There was an error sending the message.</h2>" + e);
			}
			catch (MessagingException e){
				throw new IOException("There was an unexpected error. " + e);
			}
	
	
	   }//END METHOD

/**
Example usage below:
*/

   public static void main(String... args){

	   java.util.ArrayList<String> listToEmails = new ArrayList<String>();
	   listToEmails.add("william.karabelas@dc.gov");
	   listToEmails.add("william@karabelas.com");
	   
	   EmailMsg anEmail = new EmailMsg("smtp.cox.net");
	   anEmail.setToEmail(anEmail.buildToAddress(listToEmails));
	   //anEmail.setToEmail("william.karabelas@dc.gov");
	   anEmail.setFromEmail("timothy.evans@dc.gov");
	   anEmail.setSubject("testing my API--version 4.6");
	   anEmail.setMessagBody("this could be you!");
	   try{
		   	anEmail.mailIt();
	   }
		catch(Exception e){

			e.printStackTrace();
		}
   }


}
