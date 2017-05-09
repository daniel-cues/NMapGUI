$(document).ready(function() {
	$(document).foundation();
});

var loop;


$(function() {
	$(".sidebar-button").click(
		function() {
			// remove classes from all
			$(".sidebar-button").removeClass("selected");
			// add class to the one we clicked
			$(this).addClass("selected");
			$(".off-canvas").not(
					document.getElementById($(this).attr("data-toggle")))
					.foundation("close");
		});
});



function performPost() {
	/*If backend tells the app that command is finished, stops executing*/
	/* Updates output from backend */
	var settingsEnd = {
		type : "GET",
		url : "/nmap/update-finished"
	};
	/* And writes it on its place */
	$.ajax(settingsEnd).done(function(result) {
		if (result===true){
			clearInterval(loop);
		}
	});

	/* Updates output from backend */
	var settings = {
		type : "GET",
		url : "/nmap/update"
	};
	/* And writes it on its place */
	$.ajax(settings).done(function(result) {
		$("#out-container").html(result);
		
		$('#out-container').on("click", ".command-sidebar-button", function() {
			// remove classes from all
			$(".command-sidebar-button").removeClass("selected");
			// add class to the one we clicked
			$(this).addClass("selected");
		});
	});
	
	
	
}

//var outputList = [];
//var counter = 0;

//function updateElements() {
//	
//	var updateList = {
//			type : "GET",
//			url : "/nmap/update-finished-list"
//		};
//		/* And writes it on its place */
//	$.ajax(updateList).done(function(resultList) {			
//		outputList=resultList;
//	});
//		
//	for (var i = 0; i < outputList.length; i++) {		
//		var settings = {
//				type : "GET",
//				url : "/nmap/updateid" + "?id=" + outputList[i]
//			};
//		/* And writes it on its place */
//		$.ajax(settings).done(function(result) {
//			$("#out" + outputList[i]).html(result);
//		});
//	}	
//	
//	
//	
//}

function startLoop() {

	/* Get Url to send the command */
	var urlvar = "/nmap-exe";

	if (document.getElementById("command").value !== "") {
		urlvar = urlvar + "?code=" + document.getElementById("command").value;
	}
	/* Execute ajax command request */
	var settings = {
		type : "GET",
		url : urlvar
	};
	$.ajax(settings);
	/* Stops any current output refreshing and starts live feed from console */
	if (loop != null){
		clearInterval(loop);
	}
	
//	counter++;
//	var div=document.createElement("div");
//	div.setAttribute("id", "out" + counter);
//
//	div.className = "single-output";
//	outputList.push(counter);
//
//  $("#out-fragment").append(div);
//	
	loop = window.setInterval(performPost, 1000);
}
