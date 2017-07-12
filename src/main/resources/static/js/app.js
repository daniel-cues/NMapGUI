$(document).ready(function() {
	$(document).foundation();
});

var loop;


$(function() {
	$(".sidebar-button").click(
		function() {
			// remove classes from all
			if(!$(this).hasClass("selected")){
				$(".sidebar-button").removeClass("selected");
				$(this).addClass("selected");
			}
			else{ $(".sidebar-button").removeClass("selected"); }
			// add class to the one we clicked
			
			$(".off-canvas").not(
					document.getElementById($(this).attr("data-toggle")))
					.foundation("close");
		});
});



function outputToggleMenu() {
	// remove classes from all
	// add class to the one we clicked
	$(this).siblings().removeClass("selected");
	$(this).addClass("selected");
	$(this).parent().parent().parent().find(".outtoggle").addClass("hidden");
	$(this).parent().parent().parent().find("#"+$(this).attr("data-listedElement")).removeClass("hidden");

}

function closeAction() {
	// remove classes from all
	// add class to the one we clicked
	$(this).parent().parent().parent().parent().parent().remove();

}
function minimizeAction() {
	// remove classes from all
	// add class to the one we clicked
	$(this).addClass("hidden");
	$(this).parent().find(".command-action-maximize").removeClass("hidden");
	$(this).parent().parent().parent().parent().find("tbody").addClass("hidden");

}
function maximizeAction() {
	// remove classes from all
	// add class to the one we clicked
	$(this).addClass("hidden");
	$(this).parent().find(".command-action-minimize").removeClass("hidden");
	$(this).parent().parent().parent().parent().find("tbody").removeClass("hidden");

}

function performPost() {
	/*If backend tells the app that command is finished, stops executing*/
	/* Updates output from backend */
	var settingsEnd = {
		type : "GET",
		url : "/nmap/update-finished"
	};
	/* And writes it on its place */
	var finished = false;
	$.ajax(settingsEnd).done(function(result) {
		if (result===true){
			finished=result;
			if(loop!=null)
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
		var tempScrollTop = $("#out-fragment").scrollTop();
		if(finished){
			$("#out-fragment").html(null);
			$("#out-container-finished").prepend(result);
			$("#out-container-finished #out-fragment").replaceWith($("#out-container-finished #out-fragment").children());
			$("#out-container-finished .loading").addClass("loaded").removeClass("loading");
			$("#out-container-finished").on("click", ".command-sidebar-button", outputToggleMenu);
			$("#out-container-finished").on("click", ".command-action-close", closeAction);
			$("#out-container-finished").on("click", ".command-action-minimize", minimizeAction);
			$("#out-container-finished").on("click", ".command-action-maximize", maximizeAction);
			
		}else{
			$("#out-fragment").replaceWith(result);
			$("#out-fragment").scrollTop(tempScrollTop);
		}
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
	$.ajax(settings).done(
		function(){
			/* Stops any current output refreshing and starts live feed from console */
			if (loop != null){
				clearInterval(loop);
			}
			
			//counter++;
			//var div=document.createElement("div");
			//div.setAttribute("id", "out" + counter);
						//
			//div.className = "single-output";
			//outputList.push(counter);
			//
			//  $("#out-fragment").append(div);
			//
			performPost();
			loop = window.setInterval(performPost, 2000);				
		});
	}



