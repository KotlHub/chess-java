package step.learning;

import step.learning.piece.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class GameDeck extends JPanel implements Runnable {
    public static final int Height = 1000;
    public static final int Width = 1000;
    public static final int White = 0;
    public static final int Black = 1;
    int currentColor = White;

    //pieces
    ArrayList<Piece> promoPieces = new ArrayList<>();
    public static ArrayList<Piece> pieces = new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activePiece, checkingPiece;
    public static Piece castlingPiece;

    //bools
    boolean canMove;
    boolean validSquare;
    boolean promotion;
    boolean gameOver;

    //dick and ass
    Board board = new Board();
    Mouse mouse = new Mouse();
    final int FPS = 60;
    Thread gameThread;
    public GameDeck() {
        setPreferredSize(new Dimension(Height, Width));
        setBackground(Color.black);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        setPieces();
        copyPieces(pieces, simPieces);
    }

    public void launchGame()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setPieces ()
    {
        pieces.add(new Pawn(White, 0,6));
        pieces.add(new Pawn(White, 1,6));
        pieces.add(new Pawn(White, 2,6));
        pieces.add(new Pawn(White, 3,6));
        pieces.add(new Pawn(White, 4,6));
        pieces.add(new Pawn(White, 5,6));
        pieces.add(new Pawn(White, 6,6));
        pieces.add(new Pawn(White, 7,6));
        pieces.add(new Rook(White, 0,7));
        pieces.add(new Rook(White, 7,7));
        pieces.add(new Knight(White, 1,7));
        pieces.add(new Knight(White, 6,7));
        pieces.add(new Bishop(White, 2,7));
        pieces.add(new Bishop(White, 5,7));
        pieces.add(new Queen(White, 3,7));
        pieces.add(new King(White, 4,7));


        pieces.add(new Pawn(Black, 0,1));
        pieces.add(new Pawn(Black, 1,1));
        pieces.add(new Pawn(Black, 2,1));
        pieces.add(new Pawn(Black, 3,1));
        pieces.add(new Pawn(Black, 4,1));
        pieces.add(new Pawn(Black, 5,1));
        pieces.add(new Pawn(Black, 6,1));
        pieces.add(new Pawn(Black, 7,1));
        pieces.add(new Rook(Black, 0,0));
        pieces.add(new Rook(Black, 7,0));
        pieces.add(new Knight(Black, 1,0));
        pieces.add(new Knight(Black, 6,0));
        pieces.add(new Bishop(Black, 2,0));
        pieces.add(new Bishop(Black, 5,0));
        pieces.add(new Queen(Black, 3,0));
        pieces.add(new King(Black, 4,0));
    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target)
    {
        target.clear();
        for (int i = 0; i < source.size(); i++) {
            target.add(source.get(i));
        }
    }


    private boolean canPromote() {
        if (activePiece != null && Objects.equals(activePiece.getType(), "PAWN")) {
            if ((activePiece.color == White && activePiece.row == 0) || (activePiece.color == Black && activePiece.row == 7)) {
                promoPieces.clear();
                promoPieces.add(new Queen(activePiece.color, 9, 2));
                promoPieces.add(new Rook(activePiece.color, 9, 3));
                promoPieces.add(new Knight(activePiece.color, 9, 4));
                promoPieces.add(new Bishop(activePiece.color, 9, 5));
                return true;
            }
        }
        return false;
    }

    void promoting()
    {
        if(mouse.pressed)
            for(Piece p : promoPieces)
            {
                if(p.col == mouse.x/Board.squareSize && p.row == mouse.y/Board.squareSize)
                {
                    if (activePiece != null) {
                        String pieceTypeCode = activePiece.getType();

                        switch (pieceTypeCode) {
                            case "QUEEN":
                                simPieces.add(new Queen(activePiece.color, activePiece.col, activePiece.row));
                                break;
                            case "ROOK":
                                simPieces.add(new Rook(activePiece.color, activePiece.col, activePiece.row));
                                break;
                            case "BISHOP":
                                simPieces.add(new Bishop(activePiece.color, activePiece.col, activePiece.row));
                                break;
                            case "KNIGHT":
                                simPieces.add(new Knight(activePiece.color, activePiece.col, activePiece.row));
                                break;
                            default: break;

                        }
                        simPieces.remove(activePiece.getIndex());
                        copyPieces(simPieces, pieces);
                        activePiece = null;
                        promotion = false;
                        changePlayer();
                    }
                }
            }
    }

    private void checkCastling()
    {
        if(castlingPiece != null)
        {
            if(castlingPiece.col == 0)
            {
                castlingPiece.col += 3;
            }
            else if(castlingPiece.col == 7)
            {
                castlingPiece.col -=2;
            }
            castlingPiece.x = castlingPiece.getX(castlingPiece.col);
        }
    }

    private void update() {
        System.out.println("Updating...");
        if(promotion)
        {
            promoting();
        }
        else {
            if (mouse.pressed) {
                if (activePiece == null) {
                    for (Piece p : simPieces) {
                        if (p.color == currentColor &&
                                p.col == mouse.x / Board.squareSize &&
                                p.row == mouse.y / Board.squareSize) {
                            activePiece = p;
                        }
                    }
                }
            } else {
                simulate();
            }

            if (!mouse.pressed) {
                if (activePiece != null) {
                    if (validSquare) {
                        copyPieces(simPieces, pieces);
                        activePiece.updatePosition();
                        if(castlingPiece != null) {
                            castlingPiece.updatePosition();

                        }

//                        if(isCheck())
//                        {
//
//                        }
//                        else {
//                            if(canPromote())
//                            {
//                                promotion = true;
//                            }
//                            else {
//                                changePlayer();
//                            }
//                        }
                        if(canPromote())
                        {
                            promotion = true;
                        }
                        else {
                            changePlayer();
                        }
                    } else {
                        copyPieces(pieces, simPieces);
                        activePiece.resetPosition();
                        activePiece = null;
                    }
                }
            }
        }

    }

    void simulate() {
        canMove = false;
        validSquare = false;

        copyPieces(pieces, simPieces);

        if(castlingPiece != null)
        {
            castlingPiece.col = castlingPiece.preCol;
            castlingPiece.x = castlingPiece.getX(castlingPiece.col);
            castlingPiece = null;
        }

        if (activePiece != null) {
            activePiece.x = mouse.x - Board.halfSquareSize;
            activePiece.y = mouse.y - Board.halfSquareSize;
            activePiece.row = activePiece.getRow(activePiece.y);
            activePiece.col = activePiece.getCol(activePiece.x);

            if (activePiece.canMove(activePiece.col, activePiece.row)) {
                canMove = true;

                if(activePiece.hitting != null) {
                    simPieces.remove(activePiece.hitting.getIndex());
                }
                checkCastling();
                if(!initIllegal(activePiece) && !canCaptureKing())
                {
                    validSquare = true; // Assuming that if the piece can move, the square is valid
                }

            }
        }
    }

    boolean initIllegal(Piece king)
    {
        if (king != null && Objects.equals(king.getType(), "KING")) // DONT FORGET TO CHECK IF EVERYTHING OK
        {
            for(Piece p: simPieces)
            {
                if(p != king && p.color != king.color && p.canMove(king.col, king.row))
                {
                    return true;
                }
            }
        }

        return false;
    }
    boolean isCheck()
    {
        Piece king = getKing(true);
        if(activePiece.canMove(king.col, king.row))
        {
            checkingPiece = activePiece;
            return true;
        }
        else {
            checkingPiece = null;
        }
        return false;
    }

    boolean canCaptureKing()
    {
        Piece king = getKing(false);
        for(Piece p : simPieces)
        {
            if(p.color != king.color && p.canMove(king.col, king.row))
            {
                return true;
            }
        }
        return false;
    }

    Piece getKing(boolean op)
    {
        Piece king = null;
        for(Piece p : simPieces)
        {
            if(op)
            {
                if(p != null && Objects.equals(p.getType(), "KING") && p.color == currentColor)
                {
                    king = p;
                }
            }
            else
            {
                if(p != null && Objects.equals(p.getType(), "KING") && p.color == currentColor)
                {
                    king = p;
                }
            }
        }
        return king;
    }

    public void paintComponent(Graphics graphics) {
        System.out.println("Painting...");
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;
        board.draw(graphics2D);

        for (Piece p : simPieces) // Exception in thread "AWT-EventQueue-0" java.util.ConcurrentModificationException
        {
            p.draw(graphics2D);
        }

        if(promotion)
        {
            for(Piece p : promoPieces)
            {
                graphics2D.drawImage(p.image, p.getX(p.col), p.getY(p.row), Board.squareSize, Board.squareSize, null);
            }
        }

        if(activePiece != null)
        {
            if(canMove) {
                graphics2D.setColor(Color.white);
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                graphics2D.fillRect(activePiece.col*Board.squareSize, activePiece.row*Board.squareSize,
                        Board.squareSize, Board.squareSize);
                graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            activePiece.draw(graphics2D);
        }

    }

    void changePlayer()
    {
        if(currentColor == White)
        {
            currentColor = Black;
        }
        else
        {
            currentColor = White;
        }
        activePiece = null;
    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null)
        {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
}
