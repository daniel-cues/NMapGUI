$(document).ready(function() {
	$(document).foundation();
	CollapsibleLists.apply();
	stopUpdating();
	startUpdating();
});


var loop;
var tempScrollTop;


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
			if(!$("#command").val().includes(option)){
				$("#command").val(option + " " + $("#command").val());
			}
			else{
				$("#command").val($("#command").val().replace(option+" ",""));
			}
		});
});


$(function() {
	$("#scriptList li li .scriptTooltip").click(
		function() {
			var option = $(this).clone().children().remove().end().text().replace(/\s/g, "");
			if(!$("#command").val().includes(option)){
				if($("#command").val().includes("--script")){
					$("#command").val($("#command").val().replace("--script ","--script "+option+","));
				}
				else{
					$("#command").val("--script "+option + " " + $("#command").val());
				}				
			}
			else{
				$("#command").val($("#command").val().replace(option+",",""));
				$("#command").val($("#command").val().replace(","+option,""));
				$("#command").val($("#command").val().replace("--script "+option,""));
			}
		});
});


$(function() {
	$(".vertical_menu select").change(
		function() {
			var select  = $(this).get(0);
			var options = select.options;
			var option  = options[select.selectedIndex].value;
			
			for(var i=0; i<options.length; i++){
				$("#command").val($("#command").val().replace(options[i].value+" ",""));
			}
			if(option!=="none")	
				$("#command").val(option + " " + $("#command").val());
			
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
	var element= $(this).parent().parent().parent().parent().parent();
	var settings = {
		type : "GET",
		url : "/nmap/removeCommand?index=" + element.index()
	};
	/* And writes it on its place */
	$.ajax(settings).done(function(result) {
		element.remove();
	});

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
	updateOngoing()
	updateFinished();
		
}

function updateOngoing(){
	/*If backend tells the app that some command is finished*/
	/* Updates output from backend */
	var settingsEnd = {
			type : "GET",
			url : "/nmap/stopUpdating"
		};
	/* And writes it on its place */
	$.ajax(settingsEnd).done(function(result) {
		if (result===true){
			stopUpdating();
		}
		/* Updates output from backend */
		var settings = {
			type : "GET",
			url : "/nmap/update"
		};
		
		
		/* And writes it on its place */
		$.ajax(settings).done(function(result) {
			tempScrollTop = $("#out-fragment").scrollTop();
			$("#out-fragment").replaceWith(result);
			$("#out-fragment").scrollTop(tempScrollTop);
		});
	});		
}

function updateFinished(){
	/*If backend tells the app that some command is finished*/
	/* Updates output from backend */
	var settingsFinished = {
			type : "GET",
			url : "/nmap/finishedQueued"
	};
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
					enableOutputActions();
			});					
		}
	});
}


function enableOutputActions(){
	$("#out-finished-fragment .loading").addClass("loaded").removeClass("loading");
	$("#out-finished-fragment").on("click", ".command-sidebar-button", outputToggleMenu);
	$("#out-finished-fragment").on("click", ".command-action-close", closeAction);
	$("#out-finished-fragment").on("click", ".command-action-minimize", minimizeAction);
	$("#out-finished-fragment").on("click", ".command-action-maximize", maximizeAction);
	$("#out-finished-fragment").scrollTop(tempScrollTop);}



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
			stopUpdating();
			startUpdating();					
		});
	}

function startUpdating(){
	performPost();
	loop = window.setInterval(performPost, 2000);
}

function stopUpdating(){
	if (loop != null){
		clearInterval(loop);
	}
}

function scriptFilter() {
	// Declare variables
	var input, filter, li, a, i, categories;
	input = document.getElementById("scriptFilter");
	filter = input.value.toLowerCase();
	categories = $("#scriptList > li");

	// Loop through all list items, and hide those who don't match the search query   

	for (var i = 0; i < categories.length; i++) {
		var scripts = categories.eq(i).find("li");
		var remove = true;
		var last = 0;
		for (var j = 0; j < scripts.length; j++) {
			scripts.eq(j).removeClass("lastScript");
			if (scripts[j]["textContent"].toLowerCase().indexOf(filter) > -1) {
				scripts[j].style.display = "";
				remove = false;
				last=j;
			} else {
				scripts[j].style.display = "none";
			}
		}
		scripts.eq(last).addClass("lastScript");
		if (remove){
			categories[i].style.display="none";
		}
		else {
			categories[i].style.display= "";
		}
	}
}