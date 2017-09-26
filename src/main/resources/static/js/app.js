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

$(function() {
	$(".vertical_menu label").click(
		function() {
			var option = $(this).children("code").text();
			if(!$('#command').val().includes(option)){
				$('#command').val(option + " " + $('#command').val());
			}
			else{
				$('#command').val($('#command').val().replace(option+" ",''));
			}
		});
});


$(function() {
	$(".vertical_menu select").change(
		function() {
			var select  = $(this).get(0);
			var options = select.options
			var option  = options[select.selectedIndex].value;
			
			for(var i=0; i<options.length; i++){
				$('#command').val($('#command').val().replace(options[i].value+" ",''));
			}
			if(option!=="none")	
				$('#command').val(option + " " + $('#command').val());
			
		});
});


function outputToggleMenu() {
	// remove classes from all
	// add class to the one we clicked
	$(this).siblings().removeClass("selected");
	$(this).addClass("selected");
	$(this).parent().parent().parent().find(".outtoggle").addClass("hidden");
	var contains = "*[id*='"+$(this).attr("data-listedElement")+"']";
	$(this).parent().parent().parent().find(contains).removeClass("hidden");

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
	/*If backend tells the app that some command is finished*/
	/* Updates output from backend */
	var settingsEnd = {
		type : "GET",
		url : "/nmap/stopUpdating"
	};
	/* And writes it on its place */
	$.ajax(settingsEnd).done(function(result) {
		if (result===true){
			if(loop!=null){
				clearInterval(loop);
			}
		}
		/* Updates output from backend */
		var settings = {
			type : "GET",
			url : "/nmap/update"
		};
		var settingsFinished = {
				type : "GET",
				url : "/nmap/finishedQueued"
		};
		/* And writes it on its place */
		$.ajax(settings).done(function(result) {
			var tempScrollTop = $("#out-fragment").scrollTop();
				$("#out-fragment").replaceWith(result);
				$("#out-fragment").scrollTop(tempScrollTop);
		});
		
		/* And writes it on its place */
		$.ajax(settingsFinished).done(function(result) {
			if (result===true){
				/* Updates output from backend */
				var settings = {
					type : "GET",
					url : "/nmap/update-finished"
				};
				/* And writes it on its place */
				$.ajax(settings).done(function(result) {
					var tempScrollTop = $("#out-finished-fragment").scrollTop();
						$("#out-finished-fragment").replaceWith(result);
						$("#out-finished-fragment .loading").addClass("loaded").removeClass("loading");
						$("#out-finished-fragment").on("click", ".command-sidebar-button", outputToggleMenu);
						$("#out-finished-fragment").on("click", ".command-action-close", closeAction);
						$("#out-finished-fragment").on("click", ".command-action-minimize", minimizeAction);
						$("#out-finished-fragment").on("click", ".command-action-maximize", maximizeAction);
						$("#out-finished-fragment").scrollTop(tempScrollTop);

				});					
			}
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



