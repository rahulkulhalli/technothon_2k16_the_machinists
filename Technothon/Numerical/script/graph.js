
	var values = [];

	var groups = ["marvel","finite_loop","brogrammers","aadhaar","vard",
				"the_decepticons","machinists","openface"];
	


	// Interval To read data after every 5 seconds
	$(document).ready(function(){
	 	setInterval(function(){
	 		load();
	 	},5000);
	  
	});

   var load = function(){
        $.ajax({type:'GET' ,url: "http://54.201.149.17:3000/getNumericalRating",dataType:'JSON', success: function(result){
        	values = [];
        	values.push(result.marvel);
        	values.push(result.finiteloop);
        	values.push(result.brogrammers);
        	values.push(result.aadhaar);
        	values.push(result.vard);
        	values.push(result.decepticons);
        	values.push(result.machinist);
        	values.push(result.openface);
	        drawChart(values);
	      	
        }});
        
    }



		// function to draw Chart
 	   function drawChart(data){
 	   		var arr = document.getElementById('val1').getElementsByTagName('p');

 	   		for(var i = 0 ;i<arr.length;i++){
 	   			arr[i].innerHTML = Math.round(data[i]);
 	   		}

 			for(var i = 0; i<groups.length;i++){
				$('#'+groups[i]).height(data[i]*40+"px");
			}


			$('.box').css('display','block');
			$('#name').css('display','block');
			$('#project').css('display','block');
			$('#Limg').css('display','block');
			$('#Eimg').css('display','block');
			$('#Aimg').css('display','block');
			$('#Rimg').css('display','block');
			$('#Nimg').css('display','block');
			$('#Iimg').css('display','block');
			$('#NNimg').css('display','block');
			$('#Gimg').css('display','block');

			
		}




