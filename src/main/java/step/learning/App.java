package step.learning;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        JFrame window = new JFrame("Chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GameDeck gd = new GameDeck();
        window.add(gd);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gd.launchGame();
    }
}
