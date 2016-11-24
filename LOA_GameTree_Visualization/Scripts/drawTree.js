var boardLength = 80;
var squareLength = Math.floor(boardLength / 8);
var bgColor = "rgb(255, 219, 122)";
var gridColor = "rgb(229, 197, 110)";
var player1Color = "rgba(255, 255, 255,1.0)";
var player2Color = "rgba(41, 128, 185,1.0)";
var lineWidth = 1;


var JSONstringFromFile = "empty";

document.getElementById('file').onchange = function () {

    var file = this.files[0];


    var reader = new FileReader();
    reader.onload = function (progressEvent) {
        JSONstringFromFile = this.result;


        var root = JSON.parse(JSONstringFromFile);


// set the dimensions and margins of the diagram
        var margin = {top: 100, right: 100, bottom: 100, left: 100},
            width = 2000000 - margin.left - margin.right,
            height = 2000 - margin.top - margin.bottom;

// declares a tree layout and assigns the size
        var treemap = d3.tree()
            .size([width, height]);

//  assigns the data to a hierarchy using parent-child relationships
        var nodes = d3.hierarchy(root);

// maps the node data to the tree layout
        nodes = treemap(nodes);

// append the svg obgect to the body of the page
// appends a 'group' element to 'svg'
// moves the 'group' element to the top left margin
        var svg = d3.select("body").append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom),
            g = svg.append("g")
                .attr("transform",
                    "translate(" + margin.left + "," + margin.top + ")");

// adds the links between the nodes
        var link = g.selectAll(".link")
            .data(nodes.descendants().slice(1))
            .enter().append("path")
            .attr("class", "link")
            .attr("d", function (d) {
                return "M" + d.x + "," + d.y
                    + "C" + d.x + "," + (d.y + d.parent.y) / 2
                    + " " + d.parent.x + "," + (d.y + d.parent.y) / 2
                    + " " + d.parent.x + "," + d.parent.y;
            });

// adds each node as a group
        var node = g.selectAll(".node")
            .data(nodes.descendants())
            .enter().append("g")
            .attr("transform", function (d) {
                return "translate(" + d.x + "," + d.y + ")";
            })
            .each(function (node) {
                var n = d3.select(this);

                //backGround
                n.append("rect").attrs({
                    "x": -boardLength / 2,
                    "y": -boardLength / 2,
                    "width": boardLength,
                    "height": boardLength,
                    "fill": bgColor,
                    "stroke": "none"
                });
// border
                n.append("rect").attrs({
                    "x": -boardLength / 2,
                    "y": -boardLength / 2,
                    "width": boardLength,
                    "height": boardLength,
                    "fill": "none",
                    "stroke": gridColor,
                    "stroke-width": Math.floor(lineWidth * 2)
                });
// lines
                for (var i = 1; i < 8; i++) {
                    // vertical
                    n.append("line").attrs({
                        "x1": squareLength * i - boardLength / 2,
                        "y1": 0 - boardLength / 2,
                        "x2": squareLength * i - boardLength / 2,
                        "y2": boardLength - boardLength / 2,
                        "stroke": gridColor,
                        "stroke-width": lineWidth
                    });
                    // horizontal
                    n.append("line").attrs({
                        "x1": 0 - boardLength / 2,
                        "y1": squareLength * i - boardLength / 2,
                        "x2": boardLength - boardLength / 2,
                        "y2": squareLength * i - boardLength / 2,
                        "stroke": gridColor,
                        "stroke-width": lineWidth
                    });
                    //text: Score
                    n.append("text")
                        .attr("dy", ".35em")
                        .attr("y",50)
                        .style("text-anchor", "middle")
                        .text(function(d) { return d.data.score;});

                }
                var scale = d3.scaleBand()
                    .domain([0, 1, 2, 3, 4, 5, 6, 7])
                    .rangeRound([0, boardLength]).padding(1).paddingOuter(0.5);

                var drawPawn = function (row, col, player) {
                    var fill;

                    if (player == 1) {
                        fill = player1Color;
                    } else {
                        fill = player2Color;
                    }

                    n.append("circle").attrs({
                        "cx": scale(col) - boardLength / 2,
                        "cy": scale(row) - boardLength / 2,
                        "r": squareLength * 0.3,
                        "fill": fill,
                        "stroke-width": 0
                    });
                };

                for (var row = 0; row < 8; row++) {
                    for (var col = 0; col < 8; col++) {
                        var cellValue = node.data.gameState.board[col][row];
                        if (cellValue != 0) {
                            drawPawn(row, col, cellValue);
                        }
                    }
                }

            });
    };
    reader.readAsText(file);
};