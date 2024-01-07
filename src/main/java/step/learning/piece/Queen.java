package step.learning.piece;

import step.learning.GameDeck;

public class Queen extends Piece{

    @Override
    public String getType() {
        return "QUEEN";
    }
    public Queen(int color, int col, int row)
    {
        super(color, col, row);
        if(color == GameDeck.White) {
            image = getImage("/piece/w-queen");
        }
        else {
            image = getImage("/piece/b-queen");
        }
    }

    public boolean canMove (int targetCol, int targetRow)
    {
        if(insideBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow))
        {
            if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow))
            {
                if(isValidSquare(targetCol, targetRow) && !pieceOnDiag(targetCol, targetRow)) {
                    return true;
                }
            }
        }

        if(insideBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow))
        {
            if(targetCol == preCol || targetRow == preRow)
            {
                if(isValidSquare(targetCol, targetRow) && !pieceOnTheLine(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }
}
