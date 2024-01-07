package step.learning.piece;

import step.learning.Board;
import step.learning.GameDeck;
import step.learning.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {
    public String type;
    public int lol = 0;
    public BufferedImage image;
    public int x, y, col, row, preCol, preRow, color;
    public Piece hitting;
    public boolean moved = false;

    public Piece(int color, int col, int row) {
        this.color = color;
        this.col = col;
        this.row = row;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;
    }

    public BufferedImage getImage(String imagePath)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public String getType() {
        return type;
    }

    public int getX(int col)
    {
        return col * Board.squareSize;
    }public int getY(int row)
    {
        return row * Board.squareSize;
    }

    public int getCol(int x)
    {
        return (x + Board.halfSquareSize)/Board.squareSize;
    }

    public int getRow(int y)
    {
        return (y + Board.halfSquareSize)/Board.squareSize;
    }

    public int getIndex() {
        for(int index = 0; index < GameDeck.simPieces.size(); index++) {
            if(GameDeck.simPieces.get(index) == this) {
                return index;
            }
        }
        return 0;
    }
    public void updatePosition() {
        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);
        moved = true;
    }

    public void resetPosition() {
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }
    public boolean isMoved(int targetCol, int targetRow)
    {
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
        return false;
    }

    public boolean canMove (int targetCol, int targetRow)
    {
        return false;
    }
    public boolean insideBoard (int targetCol, int targetRow) {
        if(targetCol >= 0 && targetCol <= 7 && targetRow >= 0 && targetRow <= 7)
        {
            return true;
        }
        return false;

    }

    public Piece getHitting(int targetCol, int targetRow) {
        for(Piece p: GameDeck.simPieces){
            if(p.col == targetCol && p.row == targetRow && p != this)
            {
                return p;
            }
        }
        return null;
    }

    public boolean isValidSquare(int targetCol, int targetRow) {
        hitting = getHitting(targetCol, targetRow);

        if(hitting == null) {
            return true;
        }
        else {
            if(hitting.color != this.color) {
                return true;
            }
            else {
                hitting = null;
            }
        }
        return false;
    }

    public boolean isSameSquare (int targetCol, int targetRow)
    {
        if(targetCol == preCol && targetRow == preRow)
        {
            return true;
        }
        return false;
    }

    public boolean pieceOnTheLine (int targetCol, int targetRow)
    {
        //piece moving right
        for (int c = preCol - 1; c > targetCol; c--)
        {
            for(Piece p: GameDeck.simPieces)
            {
                if(p.col == c && p.row == targetRow)
                {
                    hitting = p;
                    return true;
                }
            }
        }
        //piece moving left
        for (int c = preCol + 1; c < targetCol; c++)
        {
            for(Piece p: GameDeck.simPieces)
            {
                if(p.col == c && p.row == targetRow)
                {
                    hitting = p;
                    return true;
                }
            }
        }
        //piece moving up
        for (int c = preRow - 1; c > targetRow; c--)
        {
            for(Piece p: GameDeck.simPieces)
            {
                if(p.col == targetCol && p.row == c)
                {
                    hitting = p;
                    return true;
                }
            }
        }
        //piece moving down
        for (int c = preRow + 1; c < targetRow; c++)
        {
            for(Piece p: GameDeck.simPieces)
            {
                if(p.col == targetCol && p.row == c)
                {
                    hitting = p;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pieceOnDiag (int targetCol, int targetRow)
    {
        if(targetRow < preRow)
        {
            // up right
            for (int c = preCol - 1; c > targetCol; c--)
            {
                int diff = Math.abs(c - preCol);
                for(Piece p: GameDeck.simPieces)
                {
                    if(p.col == c && p.row == preRow - diff)
                    {
                        hitting = p;
                        return true;
                    }
                }
            }
            // up left
            for (int c = preCol + 1; c < targetCol; c++)
            {
                int diff = Math.abs(c - preCol);
                for(Piece p: GameDeck.simPieces)
                {
                    if(p.col == c && p.row == preRow - diff)
                    {
                        hitting = p;
                        return true;
                    }
                }
            }

        }

        if(targetRow > preRow)
        {
            //down right
            for (int c = preCol - 1; c > targetCol; c--)
            {
                int diff = Math.abs(c - preCol);
                for(Piece p: GameDeck.simPieces)
                {
                    if(p.col == c && p.row == preRow + diff)
                    {
                        hitting = p;
                        return true;
                    }
                }
            }
            //down left
            for (int c = preCol + 1; c < targetCol; c++)
            {
                int diff = Math.abs(c - preCol);
                for(Piece p: GameDeck.simPieces)
                {
                    if(p.col == c && p.row == preRow + diff)
                    {
                        hitting = p;
                        return true;
                    }
                }
            }

        }
        return false;
    }
    public void draw(Graphics2D graphics2D)
    {
        graphics2D.drawImage(image, x, y, Board.squareSize, Board.squareSize, null);
    }

}
