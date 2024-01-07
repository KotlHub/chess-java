package step.learning.piece;

import step.learning.GameDeck;

public class Rook extends Piece{

    @Override
    public String getType() {
        return "ROOK";
    }
    public Rook(int color, int col, int row)
    {
        super(color, col, row);
        if(color == GameDeck.White) {
            image = getImage("/piece/w-rook");
        }
        else {
            image = getImage("/piece/b-rook");
        }
    }

    public boolean canMove (int targetCol, int targetRow)
    {
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