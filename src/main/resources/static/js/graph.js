function computeGraph(traceroute){
	var links = traceroute;
	var nodes = {};
	
	// Compute the distinct nodes from the links.
	links.forEach(function(link) {
		link.source = nodes[link.source.address.address] || (nodes[link.source.address.address] = {host: link.source});
		link.target = nodes[link.target.address.address] || (nodes[link.target.address.address] = {host: link.target});
	});
	

	var radius=8;
	
	function zoomed() {
		svg.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
	}
	
	var force = d3.layout.force()
		.nodes(d3.values(nodes))
		.links(links)
		.size([2000, 800])
		.linkDistance(80)
		.charge(-300)
		.on("tick", tick)
		.start();

	force.drag().on("dragstart", function() { d3.event.sourceEvent.stopPropagation(); });
	
	var zoom = d3.behavior.zoom()
	.scaleExtent([1, 10])
	.on("zoom", zoomed);


	var wrap = d3.select("body").append("svg")
		.attr("preserveAspectRatio", "xMidYMid slice")
		.attr("viewBox", "0 0 2000 800")
		.classed("svg-content", true).call(zoom);
		
	var svg = wrap
		.append("svg:g");
	
	var link = svg.selectAll(".link")
		.data(force.links())
		.enter().append("line")
		.attr("class", "link")
		.attr("marker-end", "url(#triangle)");
	
	function displayTag(element){
		//Reorder elements to raise tooltip to the top ofd the view
		svg.selectAll(".node").sort(function (a, b) { 						// select the parent and sort the path's
			if (a.host.address.address !== d3.select(element).data()[0].host.address.address) {return -1;}  // a is not the hovered element, send "a" to the back
			else {return 1;}                             						// a is the hovered element, bring "a" to the front
		});
		d3.select(element).select("g").classed("tipHidden", false);
		d3.select(element).select("text").classed("tipHidden", true);
	}
	
	function hideTag(element){		 
		d3.select(element).select("g").classed("tipHidden", true);
		d3.select(element).select("text").classed("tipHidden", false);
	}
	
	function mouseover(d) {		  
		d3.select(this).select("circle").transition()
			.duration(500)
			.attr("r", radius*2);
		displayTag(this);

	}
	
	function mouseout() {
		d3.select(this).select("circle").transition()
			.duration(500)
			.attr("r", radius);
		hideTag(this);
	}
	function linkSX(d) {
		var sourceX = d.source.x;
		var sourceY = d.source.y;
		var targetX = d.target.x;
		var targetY = d.target.y;

		var theta = Math.atan((targetX - sourceX) / (targetY - sourceY));

		var sinTheta = radius * Math.sin(theta);

		if (d.target.y > d.source.y) {
			sourceX = sourceX + sinTheta;
		}
		else {
			sourceX = sourceX - sinTheta;
		}

		return sourceX;
	}
	function linkSY(d) {
		var sourceX = d.source.x;
		var sourceY = d.source.y;
		var targetX = d.target.x;
		var targetY = d.target.y;

		var theta = Math.atan((targetX - sourceX) / (targetY - sourceY));

		var cosTheta = radius * Math.cos(theta);
	   
		if (d.target.y > d.source.y) {
			sourceY = sourceY + cosTheta;
		}
		else {
			sourceY = sourceY - cosTheta;
		}
		return sourceY;
	}	    
	function linkTX(d) {
		var sourceX = d.source.x;
		var sourceY = d.source.y;
		var targetX = d.target.x;
		var targetY = d.target.y;

		var phi = Math.atan((targetY - sourceY) / (targetX - sourceX));

		var cosPhi = radius * Math.cos(phi);

		if (d.source.x > d.target.x) {
			targetX = targetX + cosPhi;
		}
		else {
			targetX = targetX - cosPhi;
		}
		return targetX;
	}				    
	function linkTY(d) {
		var sourceX = d.source.x;
		var sourceY = d.source.y;
		var targetX = d.target.x;
		var targetY = d.target.y;

		var phi = Math.atan((targetY - sourceY) / (targetX - sourceX));

	    
		var sinPhi = radius * Math.sin(phi);

		if (d.source.x > d.target.x) {
			targetY = targetY + sinPhi;    
		}
		else {
			targetY = targetY - sinPhi;   
		}
	    
		return targetY;
	    
	}
	function tick() {
		link
			.attr("x1", linkSX)
			.attr("y1", linkSY)
			.attr("x2", linkTX)
			.attr("y2", linkTY);
	
		node
			.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
	}
	
	 
	var node = svg.selectAll(".node")
		.data(force.nodes())
		.enter()
		.append("g")
		.attr("class", "node")
		.on("mouseover", mouseover)
		.on("mouseout", mouseout)
		.call(force.drag);

	svg.append("svg:defs").append("svg:marker")
		.attr("id", "triangle")
		.attr("refX", 10)
		.attr("refY", 6)
		.attr("markerWidth", 30)
		.attr("markerHeight", 30)
		.attr("orient", "auto")
		.append("path")
		.attr("d", "M 0 2 10 6 0 10 3 6")
		.style("fill", "#999");
	
	
	node.append("circle")
		.attr("class", function(d) { return  d.host.status.state.$name; })
		.attr("r", radius);
		
	node.append("text")
		.attr("x", radius+radius/2)
		.attr("dy", ".35em")
		.attr("text-anchor", "start")
		.text(function(d) { return d.host.address.address; });
	
	var tooltip = node.append("g")
		.attr("class","tip tipHidden")
		.attr("transform", function(d) { return "translate(" + radius*5 + ", -" + radius*2.5 + ")"; });
	

	tooltip.append("rect")	
		.attr("x", 0)
		.attr("y", 1)
		.attr("width", 10)
		.attr("height", 10);
	  
	
	tooltip.append("path")
		.attr("d", "M "+-20+" 20 l 21 -10 0 20 z");
	
	var toolText = tooltip.append("text")
		.attr("transform", function(d) { return "translate(" + 10 + "," + 5 + ")"; })
		.attr("dy", ".35em");
	    
	toolText.append("tspan")
		.attr("x", "0")
		.attr("dy", "1.2em")
		.attr("class","tipTitle")
		.text("IP Address: ");
	
	toolText.append("tspan")
		.text(function(d) { return d.host.address.address; });
	
	toolText.append("tspan")
		.attr("x", "0")
		.attr("dy", "1.2em")
		.attr("class","tipTitle")	 	
		.text("Host Names: ");
	
	toolText.append("tspan")
		.text(function(d) { 
			var hostnames = "";
			for(var i = 0; i < d.host.hostNames.length; i++){
				if(d.host.hostNames[i].hostname !== null){
					var separator = (i<d.host.hostNames.length-1) ? ", " : "";
					hostnames += d.host.hostNames[i].hostname + separator;
					}
			}
			return hostnames;
		});
	
	toolText.append("tspan")
		.attr("x", "0")
		.attr("dy", "1.2em")
		.text(" ");
	toolText.append("tspan")
		.attr("x", "0")
		.attr("dy", "1.2em")
		.attr("class","tipTitle")
		.text("Ports:");
	 
	var ports = toolText
		.selectAll(".port")
		.data(function(d) { return d.host.ports; })
		.enter();
	 	
	ports.append("tspan")
		.attr("x", "0")
		.attr("dy", "1.2em")
		.attr("class", "port")
		.attr("class", function(d) { return  d.state.$name; })
		.text(function(d) { 
			var port = d.portId+"/"+ d.protocol + "\u00a0".repeat(6) + d.state.$name ;	
			return port;
			});
	 
	tooltip.selectAll("rect")
		.attr("width",  function(d) {return this.parentNode.getBBox().width;})
		.attr("height", function(d) {return this.parentNode.getBBox().height+7;});
	
	return wrap.remove();
}	



