import javax.swing.*;

/**
 * Driver for the hangman game
 *
 * @author Cal Trainor
 */
public class DriverGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Ctrain Studios Presents : Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,800);

        frame.add(new HangmanGUI());

        frame.setVisible(true);
    }
}
