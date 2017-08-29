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
	

	
	
	var force = d3.layout.force()
	    .nodes(d3.values(nodes))
	    .links(links)
	    .size([2000, 800])
	    .linkDistance(60)
	    .charge(-200)
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
	    .attr("class", "link");
	
	var node = svg.selectAll(".node")
	    .data(force.nodes())
	    .enter().append("g")
	    .attr("class", "node")
	    .on("mouseover", mouseover)
	    .on("mouseout", mouseout)
	    .call(force.drag);

	
	node.append("circle")
    	.attr("r", 8);
		
	node.append("text")
	    .attr("x", 12)
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
	      .attr("x1", function(d) { return d.source.x; })
	      .attr("y1", function(d) { return d.source.y; })
	      .attr("x2", function(d) { return d.target.x; })
	      .attr("y2", function(d) { return d.target.y; });

	  node
	      .attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
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



