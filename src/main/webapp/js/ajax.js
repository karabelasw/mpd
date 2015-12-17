	
				
				xmlhttp = new XMLHttpRequest();
				
			    //**-------BEGIN---------**//
				function updateFine(anId, aCode){					
					
					var infraction = document.getElementById("fine"+anId);
					//alert(infraction);
					var postString= 'id=' + anId + '&value='+infraction.value;
					
					xmlhttp.onreadystatechange = 
					function(){
					
					 if (xmlhttp.readyState == 4 && xmlhttp.status == 200){	 
						
						 infraction.value = xmlhttp.responseText
						 var newFine = document.getElementById('x'+aCode);
						 newFine.firstChild.data = xmlhttp.responseText;
						 
						 //showPanel('x' + aCode, xmlhttp.responseText ) //= xmlhttp.responseText;	
						 //document.getElementById('rName').value = xmlhttp.responseText;
						 						 
					 }else{
						 //  alert('working....');
						 //document.getElementById('sayhello').innerHTML = "waiting for response";
					 }
					 			
				}				
				
				xmlhttp.open("POST", "/mpd/updatefine.do", true);
				xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				
				xmlhttp.send(postString);
				
				
				}
				//**END UPDATE FINE FUNCTION**//
				
				
				//*****NEW FUNCTION HERE****************************//
				function showPanel(fieldName, value) {
					//alert(fieldName);
					 var fieldNameElement = document.getElementById(fieldName);
					  while(fieldNameElement.childNodes.length >= 1) {
					    fieldNameElement.removeChild(fieldNameElement.firstChild);
					  }
					  fieldNameElement.appendChild(fieldNameElement.ownerDocument.createTextNode(value));
				}
				
