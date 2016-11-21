var boardLength = 200;
var squareLength = Math.floor(boardLength / 8);
var bgColor = "rgb(255, 219, 122)";
var gridColor = "rgb(229, 197, 110)";
var player1Color = "rgba(255, 255, 255,1.0)";
var player2Color = "rgba(41, 128, 185,1.0)";
var lineWidth = 3;

var gameState1 = new GameState();
var gameState2 = new GameState();
gameState1.board[0][0] = 2;
gameState1.children.push(gameState2);
//

var svg = d3.select("body").append("svg")
    .attr("width", boardLength)
    .attr("height", boardLength);

svg.append("rect").attrs({
    "x": 0,
    "y": 0,
    "width": boardLength,
    "height": boardLength,
    "fill": bgColor,
    "stroke": "none"
});
// border
svg.append("rect").attrs({
    "x": 0,
    "y": 0,
    "width": boardLength,
    "height": boardLength,
    "fill": "none",
    "stroke": gridColor,
    "stroke-width": Math.floor(lineWidth * 2)
});

// lines
for (var i = 1; i < 8; i++) {
    // vertical
    svg.append("line").attrs({
        "x1": squareLength * i,
        "y1": 0,
        "x2": squareLength * i,
        "y2": boardLength,
        "stroke": gridColor,
        "stroke-width": lineWidth
    });
    // horizontal
    svg.append("line").attrs({
        "x1": 0,
        "y1": squareLength * i,
        "x2": boardLength,
        "y2": squareLength * i,
        "stroke": gridColor,
        "stroke-width": lineWidth
    });
}

var scale = d3.scaleBand()
    .domain([0, 1, 2,3,4,5,6,7])
    .rangeRound([0, boardLength]).padding(1).paddingOuter(0.5);

var drawPawn = function(row, col,player) {
    var fill;

    if(player == 1){
        fill = player1Color;
    }else{
        fill = player2Color;
    }

    svg.append("circle").attrs({
        "cx": scale(col),
        "cy": scale(row),
        "r": squareLength * 0.3,
        "fill": fill
    });
};

for (var row = 0; row < 8; row++) {
    for (var col = 0; col < 8; col++) {
        var cellValue = gameState1.board[row][col];
        if(cellValue != 0){
            drawPawn(row, col,cellValue);
        }
    }
}