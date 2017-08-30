function computeGraph(traceroute){
	var selected_node = null,
    selected_link = null,
    mousedown_link = null,
    mousedown_node = null,
    mouseup_node = null;

	var links = traceroute;
	var nodes = {};
	
	// Compute the distinct nodes from the links.
	links.forEach(function(link) {
	  link.source = nodes[link.source.address.address] || (nodes[link.source.address.address] = {host: link.source});
	  link.target = nodes[link.target.address.address] || (nodes[link.target.address.address] = {host: link.target});
	});
	

	var radius=8;
	
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
	
	
	var wrap = d3.select("body").append("svg").remove()
		.attr("preserveAspectRatio", "xMidYMid slice")
		.attr("viewBox", "0 0 2000 800")
		.classed("svg-content", true).call(zoom);
		
	var svg = wrap
		.append('svg:g');
	
	var link = svg.selectAll(".link")
	    .data(force.links())
	    .enter().append("line")
	    .attr("class", "link")
	    .attr("marker-end", "url(#triangle)");
	
	var node = svg.selectAll(".node")
	    .data(force.nodes())
	    .enter().append("g")
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
	    .text(function(d) { return d.host.address.address; });
	
	

	function mouseover() {
	  d3.select(this).select("circle").transition()
	      .duration(750)
	      .attr("r", 16);
	}

	function mouseout() {
	  d3.select(this).select("circle").transition()
	      .duration(750)
	      .attr("r", 8);
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
	function zoomed() {
		  svg.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
		}
	
	function dragstarted(d) {
		  d3.event.sourceEvent.stopPropagation();

		  d3.select(this).classed("dragging", true);
		  force.start();
		}

	function dragged(d) {

	  d3.select(this).attr("cx", d.x = d3.event.x).attr("cy", d.y = d3.event.y);

	}

	function dragended(d) {

	  d3.select(this).classed("dragging", false);
	}
	
	return wrap;
}	



