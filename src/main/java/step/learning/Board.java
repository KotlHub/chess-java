package step.learning;

import java.awt.*;

public class Board {
    final int col = 8;
    final int row = 8;
    public static final int squareSize = 100;
    public static final int halfSquareSize = squareSize / 2;

    public void draw(Graphics2D graphics2D) {

        int color = 0;
        for (int rows = 0; rows < row; rows++) {
            for (int cols = 0; cols < col; cols++) {
                if (color == 0) {
                    graphics2D.setColor(new Color(255, 236, 140));
                    color = 1;
                } else {
                    graphics2D.setColor(new Color(148, 56, 10));
                    color = 0;
                }

                // Используйте переменные rows и cols для определения положения каждого квадрата
                int x = cols * squareSize;
                int y = rows * squareSize;
                graphics2D.fillRect(x, y, squareSize, squareSize);
            }
            if (col % 2 == 0) {
                color = 1 - color; // Измените цвет на каждой строке, если количество столбцов четное
            }
        }
    }
}