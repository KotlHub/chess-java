package step.learning.piece;

import step.learning.GameDeck;
import step.learning.Type;

import java.awt.image.BufferedImage;

public class Pawn extends Piece
{

    @Override
    public String getType() {
        return "PAWN";
    }
    public Pawn(int color, int col, int row)
    {
        super(color, col, row);
        if(color == GameDeck.White) {
            image = getImage("/piece/w-pawn");
        }
        else {
            image = getImage("/piece/b-pawn");
        }
    }

    public boolean canMove (int targetCol, int targetRow)
    {
        if(insideBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow))
        {
            int moveDir;
            if(color == GameDeck.White)
                moveDir = -1;
            else
                moveDir = 1;

            hitting = getHitting(targetCol, targetRow);

            if(targetCol == preCol && targetRow == preRow + moveDir && hitting == null)
            {
                return true;
            }

            if(targetCol == preCol && targetRow == preRow + moveDir * 2 && hitting == null && !moved && !pieceOnTheLine(targetCol, targetRow))
            {
                return true;
            }

            if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveDir && hitting != null && hitting.color != color)
            {
                return true;
            }
        }
        return false;
    }
}
