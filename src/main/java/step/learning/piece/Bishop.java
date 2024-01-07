package step.learning.piece;

import step.learning.GameDeck;

public class Bishop extends Piece{

    @Override
    public String getType() {
        return "BISHOP";
    }
    public Bishop(int color, int col, int row)
    {
        super(color, col, row);
        if(color == GameDeck.White) {
            image = getImage("/piece/w-bishop");
        }
        else {
            image = getImage("/piece/b-bishop");
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
        return false;
    }
}
