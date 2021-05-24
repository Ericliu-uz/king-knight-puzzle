package kingKnight.state;

//import javafx.beans.Observable;
//import javafx.beans.binding.BooleanBinding;
//import javafx.beans.property.*;

import org.tinylog.Logger;

/**
 * Class representing the state of the puzzle.
 */
public class KingKnightState {


    public static final int[][] INITIAL = {
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0}
    };

    private int kingRow;
    private int kingCol;
    private int knightRow;
    private int knightCol;
    public Cell[][] matrix;
    public boolean canChangeImg = true;


    public KingKnightState() {
        this(INITIAL);
    }

    public KingKnightState(int[][] a) {
        if (!isValidMatrix(a)) {
            throw new IllegalArgumentException();
        }
        gameInitial(a);

    }

    private boolean isValidMatrix(int[][] a) {
        if (a == null || a.length != 8) {
            return false;
        }
        boolean foundEmpty = false;
        for (int[] row : a) {
            if (row == null || row.length != 8) {
                return false;
            }
            for (int space : row) {
                if (space < 0 || space >= Cell.values().length) {
                    return false;
                }
                if (space == Cell.KING.getValue()) {
                    if (foundEmpty) {
                        return false;
                    }
                    foundEmpty = true;
                }
            }
        }
        return foundEmpty;
    }

    private void gameInitial(int[][] a) {
        matrix = new Cell[8][8];
        for (var i = 0; i < 8; i++) {
            for (var j = 0; j < 8; j++) {

                if ((matrix[j][i] = Cell.of(a[j][i])) == Cell.KING) {
                    kingCol = j;
                    kingRow = i;
                } else if ((matrix[j][i] = Cell.of(a[j][i])) == Cell.KNIGHT) {
                    knightCol = j;
                    knightRow = i;
                }

            }
        }
    }

//    private void gameInitial(int[][] a) {
//        matrix = new Cell[8][8];
//
//        kingCol = 5;
//        kingRow = 2;
//        knightCol = 5;
//        knightRow = 3;
//
//        matrix[5][3] = Cell.KING;
//        matrix[5][2] = Cell.KNIGHT;
//    }


    public boolean isSolved() {
        return matrix[7][6] == Cell.KING || matrix[7][6] == Cell.KNIGHT;
    }

    public int getKingRow() {
        return kingRow;
    }

    public int getKingCol() {
        return kingCol;
    }

    public int getKnightRow() {
        return knightRow;
    }

    public int getKnightCol() {
        return knightCol;
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

//    public void setMatrix(int row, int col, Cell i) {
//        matrix[row][col] = i;
//    }

//    public boolean canMoveToNext(int row, int col) {
//        if (matrix[row][col] == Cell.KNIGHT)
//            return 0 <= row && row <= 7 && 0 <= col && col <= 7 &&
//                    Math.abs(kingRow - row) == 1 || Math.abs(kingCol - col) == 1 || (Math.abs(kingRow - row) == 1 && Math.abs(kingCol - col) == 1);
//        else if (matrix[row][col] == Cell.KING)
//            return 0 <= row && row <= 7 && 0 <= col && col <= 7 &&
//                    (Math.abs(knightRow - row) == 2 && Math.abs(kingCol - col) == 1) || (Math.abs(knightRow - row) == 1 && Math.abs(kingCol - col) == 2);
//        else
//            return false;
//    }

    public boolean canMoveToNext(int col, int row) {
        if (matrix[col][row] == Cell.KNIGHT)
            return Math.abs(kingRow - row) == 1 || Math.abs(kingCol - col) == 1 || (Math.abs(kingRow - row) == 1 && Math.abs(kingCol - col) == 1);
        else if (matrix[col][row] == Cell.KING)
            return (Math.abs(knightRow - row) == 2 && Math.abs(knightCol - col) == 1) || (Math.abs(knightRow - row) == 1 && Math.abs(knightCol - col) == 2);
        else
            return false;
    }


//    public void moveToNext( int des_row, int des_col, Cell i) {
//        System.out.println(this.matrix[knightRow][knightCol]);
//        if (i == Cell.KNIGHT) {
//            if (((Math.abs(knightRow - des_row) == 2 && Math.abs(knightCol - des_col) == 1) || (Math.abs(knightRow - des_row) == 1 && Math.abs(knightCol - des_col) == 2))) {
//                Logger.debug("Knight at ({},{}) is moved to ({},{})",  knightCol, knightRow, des_col,des_row);
//                this.matrix[des_col][des_row] = Cell.KNIGHT;
//                this.matrix[knightCol][knightRow] = Cell.EMPTY;
//                knightCol = des_col;
//                knightRow = des_row;
//
//            }
//        } else if (i == Cell.KING) {
//            if ((Math.abs(kingRow - des_row) == 1 || Math.abs(kingCol - des_col) == 1 || (Math.abs(kingRow - des_row) == 1 && Math.abs(kingCol - des_col) == 1))) {
//                Logger.debug("King at ({},{}) is moved to ({},{})", kingCol, kingRow, des_col,des_row);
//                this.matrix[des_col][des_row] = Cell.KING;
//                this.matrix[kingCol][kingRow] = Cell.EMPTY;
//                kingCol = des_col;
//                kingRow = des_row;
//            }
//        }
//
//    }

    public void moveToNext(int des_row, int des_col, Cell i) {
        System.out.println(this.matrix[knightRow][knightCol]);
        if (i == Cell.KNIGHT) {
            if (((Math.abs(knightRow - des_row) == 2 && Math.abs(knightCol - des_col) == 1) || (Math.abs(knightRow - des_row) == 1 && Math.abs(knightCol - des_col) == 2))) {
                Logger.debug("Knight at ({},{}) is moved to ({},{})", knightCol, knightRow, des_col, des_row);
                this.matrix[des_col][des_row] = Cell.KNIGHT;
                this.matrix[knightCol][knightRow] = Cell.EMPTY;
                knightCol = des_col;
                knightRow = des_row;
                canChangeImg = true;
            } else {
                canChangeImg = false;
            }
        } else if (i == Cell.KING) {
            if ((Math.abs(kingRow - des_row) == 1 && Math.abs(kingCol - des_col) == 0) || (Math.abs(kingCol - des_col) == 1 && Math.abs(kingRow - des_row) == 0)  || (Math.abs(kingRow - des_row) == 1 && Math.abs(kingCol - des_col) == 1)) {
                Logger.debug("King at ({},{}) is moved to ({},{})", kingCol, kingRow, des_col, des_row);
                this.matrix[des_col][des_row] = Cell.KING;
                this.matrix[kingCol][kingRow] = Cell.EMPTY;
                kingCol = des_col;
                kingRow = des_row;
                canChangeImg = true;
            } else {
                canChangeImg = false;
            }
        }
        else {
            canChangeImg = false;
        }
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : matrix) {
            for (Cell format : row) {
                sb.append(format).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        var state = new KingKnightState();
        System.out.println(state);

    }

}
