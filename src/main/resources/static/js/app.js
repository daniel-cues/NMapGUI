$(document).ready(function() {
	$(document).foundation();
});

$(function() {
	$(".sidebar-button").click(
			function() {
				// remove classes from all
				$(".sidebar-button").removeClass("selected");
				// add class to the one we clicked
				$(this).addClass("selected");
				$('.off-canvas').not(
						document.getElementById($(this).attr('data-toggle')))
						.foundation('close');

			});
});

function performPost() {
	/* Updates output from backend */
	var settings = {
		type : 'GET',
		url : '/nmap/update'
	};
	/* And writes it on its place */
	$.ajax(settings).done(function(result) {
		$('#out-fragment').html(result);
	});
}

var loop;

function startLoop() {

	/* Get Url to send the command */
	var urlvar = '/nmap-exe';

	if (document.getElementById('command').value != '') {
		urlvar = urlvar + '?code=' + document.getElementById('command').value;
	}
	/* Execute ajax command request */
	var settings = {
		type : 'GET',
		url : urlvar
	};
	$.ajax(settings)
	/* Stops any current output refreshing and starts live feed from console */
	if (loop != null)
		clearInterval(loop);
	loop = window.setInterval(performPost, 1000);
}
