import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Retrieves wordlist from file
 *
 * @author Cal Trainor
 */
public class WordSelector {

    /**
     * Returns wordlist
     * @return
     */
    public ArrayList<String> getWordList(){
        ArrayList<String> wordList = new ArrayList<>();
        try{
            File file = new File("src/wordlist.txt");
            Scanner scan = new Scanner(file);

            while(scan.hasNextLine()){
                wordList.add(scan.nextLine());
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return wordList;
    }
}
