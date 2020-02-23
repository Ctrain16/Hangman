import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * GUI version of hangman
 *
 * @author Cal Trainor
 */
public class HangmanGUI extends JPanel {

    private static final int BASE_WIDTH = 100;
    private static final int HEAD_RADIUS = 36;
    private static final int TOWER_HEIGHT = 380;
    private static final int BEAM_LENGTH = 144;
    private static final int ROPE_LENGTH = 24;
    private static final int BODY_LENGTH = 144;
    private static final int ARM_OFFSET = 28;
    private static final int UPPER_ARM = 72;
    private static final int LOWER_ARM = 44;
    private static final int HIP_WIDTH = 36;
    private static final int LEG_LENGTH = 108;
    private static final int FOOT_LENGTH = 28;


    private WordSelector selector;
    private ArrayList<String> wordList;
    private String wordToGuess = "";
    private String correctWord = "";

    private char[] lettersToGuess;
    private char[] lettersGuessed;

    private int correctGuesses;
    private String incorrectAnswers = "";
    private int incorrectGuesses;
    private boolean gameStarted = false;
    private boolean gameWon = false;
    private boolean gameLost = false;


    /**
     * Constructor
     */
    public HangmanGUI(){
        selector = new WordSelector();
        wordList = selector.getWordList();
        KeyListener listener = new InputListener();
        addKeyListener(listener);
        setFocusable(true);
    }

    /**
     * Resets game
     */
    public void resetGame(){
        correctGuesses = 0;
        incorrectGuesses = 0;
        gameStarted = true;
        setWord();
        incorrectAnswers = "";
        gameWon = false;
        gameLost = false;
    }

    /**
     * Ends game
     */
    public void endGame(){
        gameStarted = false;
    }

    /**
     * Sets word to guess
     */
    public void setWord(){
        int random = (int)(Math.random()* wordList.size());
        wordToGuess = wordList.get(random).toUpperCase();
        correctWord = wordToGuess;
        lettersToGuess = wordToGuess.toCharArray();
        lettersGuessed = new char[wordToGuess.length()];


        //Fill lettersGuessed array with blanks
        for(int i = 0; i < lettersGuessed.length; i++){
            lettersGuessed[i] = '-';
        }

        String secretWord = "";
        for(char c : lettersGuessed){
            secretWord = secretWord + c;
        }
        wordToGuess = secretWord;
    }

    /**
     * Checks if guess is in word
     * @param guess
     */
    public void guess(Character guess){
        boolean incorrectGuess = true;
        String tempGuess = "" + guess;
        tempGuess = tempGuess.toUpperCase();
        guess = tempGuess.charAt(0);

        for(int i = 0; i < lettersToGuess.length; i++){
            if(lettersToGuess[i] == guess){
                incorrectGuess = false;
                correctGuesses++;
                lettersGuessed[i] = lettersToGuess[i];
                lettersToGuess[i] = '-';
            }
        }

        if(incorrectGuess) {
            incorrectGuesses++;
            incorrectAnswers = incorrectAnswers + guess;
        }
        else {
            String secretWord = "";
            for(char c : lettersGuessed){
                secretWord = secretWord + c;
            }
            wordToGuess = secretWord;
        }


        if(incorrectGuesses == 8){
            gameLost = true;
            endGame();
        }
        else if(correctGuesses == wordToGuess.length()){
            gameWon = true;
            endGame();
        }
        repaint();
    }

    /**
     * Paints hangman game
     * @param g
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Font titleFont = new Font("Verdana",Font.BOLD,30);
        g.setFont(titleFont);
        g.drawString("Hangman", getWidth()/2 - 80, 50);

        Font normalFont = new Font("Times New Roman", Font.PLAIN, 18);
        g.setFont(normalFont);

        if(!gameStarted)
            g.drawString("Any key to start",getWidth()/2 - 60, 80);
        if(gameLost) {
            g.drawString("Better luck next time", getWidth() / 2 - 80, 100);
            g.drawString("Correct word : " + correctWord, getWidth()/2 - 90,120);
        }
        if(gameWon)
            g.drawString("Congrats you guessed the word!!",getWidth()/2 - 120,100);

        g.drawLine(getWidth()/2 - BEAM_LENGTH,getHeight()/6,getWidth()/2 - BEAM_LENGTH,getHeight()/6 + TOWER_HEIGHT);
        g.drawLine(getWidth()/2 - BEAM_LENGTH,getHeight()/6,getWidth()/2, getHeight()/6);
        g.drawLine(getWidth()/2,getHeight()/6,getWidth()/2,getHeight()/6 + ROPE_LENGTH);
        g.drawLine(getWidth()/2 - BEAM_LENGTH - BASE_WIDTH/2,getHeight()/6 + TOWER_HEIGHT,
                getWidth()/2 - BEAM_LENGTH + BASE_WIDTH/2,getHeight()/6 + TOWER_HEIGHT);



        g.drawString("Word to guess: ",getWidth()/8,getHeight() - 100);
        g.drawString(wordToGuess,getWidth()/8 + 120, getHeight() - 100);
        g.drawString("Incorrect guesses: ", getWidth()/8,getHeight() - 80);
        g.drawString(incorrectAnswers,getWidth()/8 + 130,getHeight() - 80);

        if(incorrectGuesses >= 1)
            g.drawOval(getWidth()/2 - HEAD_RADIUS,getHeight()/6 + ROPE_LENGTH,
                    2 * HEAD_RADIUS,2 * HEAD_RADIUS);
        if(incorrectGuesses >= 2)
            g.drawLine(getWidth()/2,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS),
                    getWidth()/2,getHeight()/6+ROPE_LENGTH + (2 * HEAD_RADIUS) + BODY_LENGTH);

        if(incorrectGuesses >= 3) {
            g.drawLine(getWidth() / 2 - UPPER_ARM, getHeight() / 6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + ARM_OFFSET, getWidth() / 2, getHeight() / 6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + ARM_OFFSET);

            g.drawLine(getWidth()/2 - UPPER_ARM,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + ARM_OFFSET, getWidth()/2 - UPPER_ARM,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + ARM_OFFSET + LOWER_ARM);
        }

        if(incorrectGuesses >= 4){
            g.drawLine(getWidth() / 2 + UPPER_ARM, getHeight() / 6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + ARM_OFFSET, getWidth() / 2, getHeight() / 6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + ARM_OFFSET);

            g.drawLine(getWidth()/2 + UPPER_ARM,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + ARM_OFFSET, getWidth()/2 + UPPER_ARM,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + ARM_OFFSET + LOWER_ARM);
        }
        if(incorrectGuesses >= 5) {
            g.drawLine(getWidth()/2, getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                + BODY_LENGTH, getWidth()/2 - HIP_WIDTH,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH);

            g.drawLine(getWidth()/2 - HIP_WIDTH, getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH, getWidth()/2 - HIP_WIDTH,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH + LEG_LENGTH);
        }
        if(incorrectGuesses >= 6){
            g.drawLine(getWidth()/2, getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH, getWidth()/2 + HIP_WIDTH,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH);

            g.drawLine(getWidth()/2 + HIP_WIDTH, getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH, getWidth()/2 + HIP_WIDTH,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH + LEG_LENGTH);
        }
        if(incorrectGuesses >= 7)
            g.drawLine(getWidth()/2 - HIP_WIDTH,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH + LEG_LENGTH, getWidth()/2 - HIP_WIDTH - FOOT_LENGTH, getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH + LEG_LENGTH);
        if(incorrectGuesses >= 8)
            g.drawLine(getWidth()/2 + HIP_WIDTH,getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH + LEG_LENGTH, getWidth()/2 + HIP_WIDTH + FOOT_LENGTH, getHeight()/6 + ROPE_LENGTH + (2 * HEAD_RADIUS)
                    + BODY_LENGTH + LEG_LENGTH);

    }


    private class InputListener extends KeyAdapter{
        /**
         * Listens for user input
         * @param event
         */
        public void keyPressed(KeyEvent event){
            char key = event.getKeyChar();
            if(!gameStarted)
                resetGame();
            else{
                guess(key);
            }
            repaint();
        }
    }
}
