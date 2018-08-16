window.matrix_PeriodicTable = function() {
  var diagramElement = this.getElement();
  var rpcProxy = this.getRpcProxy();

  this.onStateChange = function() {
    var test = this.getState().elements;
    draw_table(test);
  };

  function draw_table(elements) {
    var box_w = 30;
    var box_h = 30;
    var margin = 15;
    var max_Y = 0, max_X = 0;

    // find maximum "coordinates" of elements
    for ( var key in elements) {
      var e = elements[key];
      max_X = Math.max(max_X, e.col);
      max_Y = Math.max(max_Y, e.row);
    }
    // minimum width needed to make the graph look nice
    var min_width = 300;

    var svg_width = 600;
    var svg_height = 400;
    
    var svg = d3.select(diagramElement).append("svg").attr("width", svg_width)
            .attr("height", svg_height);
    elements.forEach(function(e) {
      var x = margin + e.col * box_w + 10;
      var y = margin + e.row * box_h + 10;
      svg.append("rect").attr("x", x).attr("y", y).attr("width",
              box_w).attr("height", box_h).style("stroke", "black").style(
              "fill", "steelblue").on('click', function() {
        rpcProxy.onElementClick(e);
      }).on("mouseover", function() {
        d3.select(this).attr("opacity", 0.5);
      }).on("mouseout", function() {
        d3.select(this).attr("opacity", 1);
      });
      svg.append("text").text(e.abbreviation).attr("font-family",
              "sans-serif").attr("font-size", "14px").attr("stroke", "white")
              .attr("text-anchor", "middle").attr("x", x + (box_w / 2)).attr(
                      "y", y + (box_h / 2));
    });
  }
};