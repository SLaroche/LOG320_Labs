function GameState () {
    this.board = [[]];
    this.children = [];
    this.Score = 0;
    this.view = null;

    //Methods
    this.fillEmptyBoard = function(array, width, height) {
        for (var x = 0; x < width; x++) {
            array[x] = [];
            for (var y = 0; y < height; y++) {
                array[x][y] = 0;
            }
        }
    };

    this.setInitialState = function() {
        this.board[0][1] = 1;
        this.board[0][2] = 1;
        this.board[0][3] = 1;
        this.board[0][4] = 1;
        this.board[0][5] = 1;
        this.board[0][6] = 1;

        this.board[7][1] = 1;
        this.board[7][2] = 1;
        this.board[7][3] = 1;
        this.board[7][4] = 1;
        this.board[7][5] = 1;
        this.board[7][6] = 1;

        this.board[1][0] = 2;
        this.board[2][0] = 2;
        this.board[3][0] = 2;
        this.board[4][0] = 2;
        this.board[5][0] = 2;
        this.board[6][0] = 2;

        this.board[1][7] = 2;
        this.board[2][7] = 2;
        this.board[3][7] = 2;
        this.board[4][7] = 2;
        this.board[5][7] = 2;
        this.board[6][7] = 2;
    };
    this.getSVGView = function(){
        var sideLength = 3;
        var svg = d3.select("body").append("svg")
            .attr("width", boardLength)
            .attr("height", boardLength);
        svg.attr("transform", "scale(2.0)");
        var colors = {
            bg: "rgb(255, 219, 122)",
            border: "rgb(229, 197, 110)",
            cross: "rgba(231, 76, 60, 1.0)",
            crossLight: "rgba(231, 76, 60, 0.5)",
            nought: "rgba(41, 128, 185,1.0)",
            noughtLight: "rgba(41, 128, 185, 0.5)"
        };
        var lineWidth = sideLength * 0.02;
        var borderWidth = sideLength * 0.04;

        svg.append("rect")
            .attr({
                "class": "bg",
                "x": 0,
                "y": 0,
                "width": sideLength,
                "height": sideLength,
                "fill": colors.bg,
                "stroke": "none"
            });

        return svg;
    }

    //Constructor
    this.fillEmptyBoard(this.board,8,8);
    this.setInitialState();
}

(function() {
    var GameStateSVG = function(board,sideLength) {
        this.board = board;
        this.sideLength = sideLength;
        this.svg = document.createElement("svg");
        this.svg = d3.select(this.svg).append("g");
        this.svg.attr("transform", "scale(2.0)");
        this.colors = {
            bg: "rgb(255, 219, 122)",
            border: "rgb(229, 197, 110)",
            cross: "rgba(231, 76, 60, 1.0)",
            crossLight: "rgba(231, 76, 60, 0.5)",
            nought: "rgba(41, 128, 185,1.0)",
            noughtLight: "rgba(41, 128, 185, 0.5)"
        };
        this.lineWidth = this.sideLength * 0.02;
        this.borderWidth = this.sideLength * 0.04;
    };

    GameStateSVG.prototype.render = function() {
        this.drawBackground();
        this.drawLines();
        this.drawBorder();
        this.drawSquares();
        return this;
    };

    GameStateSVG.prototype.drawBackground = function() {
        this.svg.append("rect")
            .attr({
                "class": "bg",
                "x": 0,
                "y": 0,
                "width": this.sideLength,
                "height": this.sideLength,
                "fill": this.colors.bg,
                "stroke": "none"
            });
    };

    GameStateSVG.prototype.drawLines = function() {
        for (var i = 1; i < this.board.size; i++) {
            this.drawVerticalLine(i);
            this.drawHorizontalLine(i);
        }
    };

    GameStateSVG.prototype.drawBorder = function() {
        this.svg.append("rect")
            .attr({
                "class": "border",
                "x": 0,
                "y": 0,
                "width": this.sideLength,
                "height": this.sideLength,
                "fill": "none",
                "stroke": this.colors.border,
                "stroke-width": this.borderWidth
            });
    };

    GameStateSVG.prototype.drawHorizontalLine = function (row) {
        this.svg.append("line")
            .attr("x1", 0)
            .attr("y1", (this.sideLength / 3) * row)
            .attr("x2", this.sideLength)
            .attr("y2", (this.sideLength / 3) * row)
            .attr("stroke", this.colors.border)
            .attr("stroke-width", this.lineWidth);
    };

    GameStateSVG.prototype.drawVerticalLine = function (col) {
        this.svg.append("line")
            .attr("x1", (this.sideLength / 3) * col)
            .attr("y1", 0)
            .attr("x2", (this.sideLength / 3) * col)
            .attr("y2", this.sideLength)
            .attr("stroke", this.colors.border)
            .attr("stroke-width", this.lineWidth);
    };

    GameStateSVG.prototype.drawSquares = function() {
        for (var row = 0; row < this.board.size; row++) {
            for (var col = 0; col < this.board.size; col++) {
                var cellValue = this.board[row][col];
                if(cellValue != 0){
                    drawPawn(row, col,cellValue);
                }
            }
        }
    };

    GameStateSVG.prototype.drawPawn = function (row, col, player) {
        var scale = d3.scaleBand()
            .domain([0, 1, 2,3,4,5,6,7])
            .rangeRound([0, boardLength]).padding(1).paddingOuter(0.5);

            var fill;

            if(player == 1){
                fill = player1Color;
            }else{
                fill = player2Color;
            }

            this.svg.append("circle").attrs({
                "cx": scale(col),
                "cy": scale(row),
                "r": squareLength * 0.3,
                "fill": fill
            });
    };

    GameStateSVG.prototype.update = function(event, model) {
        this.board = model;
        this.render();
    };

    GameState.views = GameState.views || {};
    GameState.views.TicTacToeSVG = GameStateSVG(GameState.board,3);
})();