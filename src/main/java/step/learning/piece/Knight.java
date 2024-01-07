package step.learning.piece;

import step.learning.GameDeck;

public class Knight extends Piece{
    @Override
    public String getType() {
        return "KNIGHT";
    }
    public Knight(int color, int col, int row)
    {
        super(color, col, row);
        if(color == GameDeck.White) {
            image = getImage("/piece/w-knight");
        }
        else {
            image = getImage("/piece/b-knight");
        }
    }

    public boolean canMove (int targetCol, int targetRow)
    {
        if(insideBoard(targetCol, targetRow))
        {
            if(Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 2)
            {
                if(isValidSquare(targetCol, targetRow)) {
                    return true;
                }
            }
        }
        return false;
    }
}