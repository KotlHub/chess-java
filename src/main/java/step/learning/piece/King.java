package step.learning.piece;

import step.learning.GameDeck;

public class King extends Piece{

    @Override
    public String getType() {
        return "KING";
    }
    public King(int color, int col, int row)
    {
        super(color, col, row);
        if(color == GameDeck.White) {
            image = getImage("/piece/w-king");
        }
        else {
            image = getImage("/piece/b-king");
        }
    }

    public boolean canMove (int targetCol, int targetRow)
    {
        if(insideBoard(targetCol, targetRow))
        {
            if(Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1 ||
                    Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1)
            {
                if(isValidSquare(targetCol, targetRow)) {
                    return true;
                }
            }

            //castling
            if(!moved)
            {
                //short
                if(targetCol == preCol + 2 && targetRow == preRow && !pieceOnTheLine(targetCol, targetRow))
                {
                    for(Piece p: GameDeck.simPieces)
                    {
                        if(p.col == preCol + 3 && !p.moved)
                        {
                            GameDeck.castlingPiece = p;
                            return true;
                        }
                    }
                }

                //long
                if(targetCol == preCol - 2 && targetRow == preRow && !pieceOnTheLine(targetCol, targetRow))
                {
                    Piece piece[] = new Piece[2];
                    for(Piece p: GameDeck.simPieces)
                    {
                        if(p.col == preCol - 3 && p.row == targetRow)
                        {
                            piece[0] = p;
                        }
                        if(p.col == preCol - 4 && p.row == targetRow)
                        {
                            piece[1] = p;
                        }

                        if(piece[0] == null && !piece[1].moved)
                        {
                            GameDeck.castlingPiece = piece[1];
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }
}
