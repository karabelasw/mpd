


//Gets text input cursor position
function getCursorPosition(element) {
	return Math.abs(document.selection.createRange().moveStart("character", -10^5));
}
function displayHidden(divID){
	
	var item = document.getElementById(divID);
	//var pusher = document.getElementById(pushId);
  	
  	if (item) {
    	item.className = (item.className=='hidden') ? 'unhidden':'hidden';
  	}
}


function editButtonText(anId, aCode){
	    
	    //item is the button that was pushed 
		var item = document.getElementById(anId);
		
		//used to hide/unhide form field inside JSP
		var fineField = document.getElementById(aCode);	  	
	  	
		var theAction = item.value;
		
	  	if(item){	  		
	  		
	  		 //alert(theAction);
	  		if(theAction == 'Save'){
	  			updateFine(anId, aCode);
	  		}
	  		
	  		item.value = (item.value=='Edit') ? 'Save':'Edit';	  		
	  		fineField.className = (fineField.className=='hidden') ? 'unhidden':'hidden';
	  	} 
	  	return false;
	}
	
function displayLeaveComment(divID, pushId){
	
	var item = document.getElementById(divID);
	var pusher = document.getElementById(pushId);
  	
  	if (item) {
    	item.className = (item.className=='hidden') ? 'unhidden':'hidden';
  	} 
  	
  	if(pusher){
  		pusher.value = (pusher.value=='Leave Comment') ? 'Cancel Leaving Comment':'Leave Comment';
  	}  
  	
  	document.getElementById('comment').focus();
}
	function sayHello(){
		
		alert("Hello World!");		
	}
	
