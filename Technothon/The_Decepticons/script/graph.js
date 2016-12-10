	// audio_values
	var audio_values = [];
	var image_values = [];
	// emotions name array
	var emotions = ["anger","disgust","fear","joy","sadness"];
	// div ID
	var anger = document.getElementById("anger");
	var disgust = document.getElementById("disgust");
	var sadness = document.getElementById("sadness");
	var joy = document.getElementById("joy");
	var fear = document.getElementById("fear");

		 // for(var i = 0; i<emotions.length;i++){	
			// 	$('#'+emotions[i]).height(audio_values[i]+"px");
		 // }



	// Interval To read data after every 5 seconds
	$(document).ready(function(){
	 	setInterval(function(){
	 		load();
	 	},5000);
	  
	});

   var load = function(){
        $.ajax({type:'GET' ,url: "http://54.201.149.17:3000/results/decepticons",dataType:'JSON', success: function(result){
        	audio_values = [];
        	image_values = [];
        	audio_values.push(Math.round(result.audio.anger*100));
	      	audio_values.push(Math.round(result.audio.disgust*100));
	      	audio_values.push(Math.round(result.audio.fear*100));
	      	audio_values.push(Math.round(result.audio.joy*100));
	      	audio_values.push(Math.round(result.audio.sadness*100));      

	      	image_values.push(Math.round(result.image.anger*100));
	      	image_values.push(Math.round(result.image.disgust*100));
	      	image_values.push(Math.round(result.image.fear*100));
	      	image_values.push(Math.round(result.image.joy*100));
	      	image_values.push(Math.round(result.image.sadness*100)); 

	      	drawChart(audio_values,image_values);
        }});
    }



    	// drawChart by choosing file
/*		 var openFile = function(event) {
		 	var target = event.target.files[0];
		 	var reader = new FileReader();
			reader.onload = function(){
			      	var text = reader.result;
		      		//alert(text);


			      	obj = JSON.parse(text);
			      	audio_values.push(Math.round(obj.Anger*100));
			      	audio_values.push(Math.round(obj.Disgust*100));
			      	audio_values.push(Math.round(obj.Fear*100));
			      	audio_values.push(Math.round(obj.Joy*100));
			      	audio_values.push(Math.round(obj.Sadness*100));
			      	//alert(audio_values);
					drawChart(audio_values);
 	 	  };
 	 	  reader.readAsText(target);
			
 		}
*/
		

		// function to draw Chart
 	   function drawChart(data1,data2){
 	   		var arr = document.getElementById('val1').getElementsByTagName('p');
 	   		for(var i = 0 ;i<arr.length;i++){
 	   			arr[i].innerHTML = data1[i]+"%";
 	   		}
 			for(var i = 0; i<emotions.length;i++){
				$('#'+emotions[i]+'1').height(data1[i]*3+"px");
			}
			arr = document.getElementById('val2').getElementsByTagName('p');
 	   		for(var i = 0 ;i<arr.length;i++){
 	   			arr[i].innerHTML = data2[i]+"%";
 	   		}
			 for(var i = 0; i<emotions.length;i++){
				$('#'+emotions[i]+'2').height(data2[i]*3+"px");
			}

			$('#main').css('background-image','url("images/jpg/wood.jpg")');
			$('#main').css('filter','blur(5px)');

			$('.box').css('display','block');

			$('#project').css('display','block');

			$('#emotiontitle').css('visibility','visible');

			$('#Aimg').css('display','block');
			$('#Uimg').css('display','block');
			$('#Dimg').css('display','block');
			$('#Iimg').css('display','block');
			$('#Oimg').css('display','block');

			$('#IIimg').css('display','block');
			$('#MMimg').css('display','block');
			$('#AAimg').css('display','block');
			$('#GGimg').css('display','block');
			$('#EEimg').css('display','block');
			
			$('#images1').css('display','block');
			$('#images2').css('display','block');

			$('#images1').css('opacity','1');
			$('#images2').css('opacity','1');

			
		}




