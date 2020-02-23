import java.util.ArrayList;
import java.util.Scanner;

/**
 * CUI versions of Hangman
 *
 * @author Cal Trainor
 */
public class Hangman {

    private WordSelector selector;
    private ArrayList<String> wordList;
    private String wordToGuess = "";
    private String correctWord = "";
    private String incorrectGuesses;

    private char[] lettersToGuess;
    private char[] lettersGuessed;

    private int incorrectGuessesLeft;
    private int correctGuesses;

    private Scanner scan;

    /**
     * Constructor
     */
    public Hangman() {
        selector = new WordSelector();
        wordList = selector.getWordList();
        scan = new Scanner(System.in);
        setWord();
    }


    /**
     * Selects the word for the game
     */
    public void setWord() {
        int random = (int) (Math.random() * wordList.size());
        wordToGuess = wordList.get(random).toUpperCase();
        correctWord = wordToGuess;
        lettersToGuess = wordToGuess.toCharArray();
        lettersGuessed = new char[wordToGuess.length()];

        //Fill lettersGuessed array with blanks
        for (int i = 0; i < lettersGuessed.length; i++) {
            lettersGuessed[i] = '-';
        }
        incorrectGuesses = "";
    }


    /**
     * Allows the user to play a game of hangman
     */
    public void playGame() {
        incorrectGuessesLeft = 8;
        correctGuesses = 0;

        System.out.println("===Welcome to Hangman====\n");

        while (incorrectGuessesLeft != 0 && correctGuesses < wordToGuess.length()) {
            boolean incorrectGuess = true;
            System.out.print("Secret word : ");
            for (char c : lettersGuessed) {
                System.out.print(c);
            }

            System.out.println("");
            System.out.print("Your guess: ");
            String guess = scan.next();
            guess = guess.toUpperCase();
            Character guessed = guess.charAt(0);

            for (int i = 0; i < lettersToGuess.length; i++) {
                if (lettersToGuess[i] == guessed) {
                    incorrectGuess = false;
                    correctGuesses++;
                    lettersGuessed[i] = lettersToGuess[i];
                    lettersToGuess[i] = '-';
                }
            }

            if (incorrectGuess) {
                incorrectGuessesLeft--;
                incorrectGuesses = incorrectGuesses + guess;
                System.out.println("Try another letter");
                System.out.println("Incorrect guesses left: " + incorrectGuessesLeft);
            } else {
                String secretWord = "";
                for (char c : lettersGuessed) {
                    secretWord = secretWord + c;
                }
                System.out.println("Good Guess!");
            }
            System.out.println("Incorrect guesses: " + incorrectGuesses + "\n");
        }


        if (incorrectGuessesLeft == 0) {
            System.out.println("Better luck next time.\nCorrect word : " + correctWord);
        } else {
            for (char c : lettersGuessed) {
                System.out.print(c);
            }
            System.out.println("\nCongrats!!!!!");
        }

        System.out.print("Would you like to play again? (Y or N) ");
        String playAgain = scan.next();

        if (playAgain.equals("Y")) {
            setWord();
            playGame();
        } else {
            System.out.println("Okay, have a nice day!");
        }
    }

    public static void main(String[] args) {
        Hangman game = new Hangman();
        game.playGame();
    }
}
