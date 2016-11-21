function GameState () {
    this.board = [[]];
    this.children = [];
    this.Score = 0;
    this.view = null;

    //Methods
    this.fillEmptyBoard = function (array, width, height) {
        for (var x = 0; x < width; x++) {
            array[x] = [];
            for (var y = 0; y < height; y++) {
                array[x][y] = 0;
            }
        }
    };

    this.setInitialState = function () {
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
    this.getSVGView = function () {
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
    this.fillEmptyBoard(this.board, 8, 8);
    this.setInitialState();
}