/*
--------Make sure to change path of Words.txt to your directory--------------


Hangman game with 1 player playing against computer.

@author Deividas Ovsianikovas
*/


import java.io.*;
import java.util.*;

public class Hangman {

      //1 long string containing all of the lives a player can have split into 7x7 arrays
      public static String[][] bigLivesString = 
      {
          {" "," "," "," "," "," "," "},
          {" "," "," "," "," "," "," "},
          {" "," "," "," "," "," "," "},
          {" "," "," "," "," "," "," "},
          {" "," "," "," "," "," "," "},
          {" "," "," "," "," "," "," "},
          {"-","-","-","-","-","-","-"},///Break 1
  
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {"-","-","-","-","-","-","-"},///Break 
  
          {" "," ","|","-","-","-"," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {"-","-","-","-","-","-","-"},///Break 
  
          {" "," ","|","-","-","-"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {"-","-","-","-","-","-","-"},//Break 
  
          {" "," ","|","-","-","-"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","O"," "},
          {" "," ","|"," "," "," "," "},
          {" "," ","|"," "," "," "," "},
          {"-","-","-","-","-","-","-"}, //Break 
  
          {" "," ","|","-","-","-"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","O"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," "," "," "},
          {"-","-","-","-","-","-","-"},  ///Break 
  
          {" "," ","|","-","-","-"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","O"," "},
          {" "," ","|"," ","-","|","-"},
          {" "," ","|"," "," "," "," "},
          {"-","-","-","-","-","-","-"},  ///Break 
  
          {" "," ","|","-","-","-"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","|"," "},
          {" "," ","|"," "," ","O"," "},
          {" "," ","|"," ","-","|","-"},
          {" "," ","|"," ","/"," ","\\"},
          {"-","-","-","-","-","-","-"}   ///Final
      };

    private static String[] allWords;    ///String array containing all of the words from the words.txt document, later sorted
    private static String chosenWord = "";   ///The Computer's random chosen word
    private static String guessedWord = "";     //The player's current guessed word
    private static boolean gameOver = false;    ///States whether the game is over or not
    private static boolean hasRestarted = false;    ///States whether the player has chosen to restart the game
    private static int health = 7;    ///Player's current health
    private static int easyWordsStart = 0, mediumWordsStart = 0, hardWordsStart = 0; ///Variables setting where each difficulties word lengths start
    private static String allGuessedLetters = "";   ///Lists all characters that have been guessed already

    public static void main(String args[]) {
        Start(); ///Start the game session
    }

    /// Method to Start the game
    public static void Start() {
        /// Reset variables to their default state
        ResetVars();

        Scanner sc = new Scanner(System.in);
        boolean difficultySelected = false;

        System.out.println("Welcome to Hangman! Would you like to hear the rules first? Y N");

        if (!hasRestarted) {
            boolean answer = YesNoAnswer();
            if (answer) {
                System.out
                        .println("-----------------------------------------------------------------------------------");
                System.out.println(
                        "This is the classic game of Hangman where you have to figure out a word that has been\nchosen randomly by the computer by guessing it letter by letter.\nYou have 8 lives, for every incorrect guess you make you lose a life.\nYou can win by guessing all of the correct letters of the word, and lose if you run out of lives.\nYour current health is shown by an ascii representation of Hangman.");
                System.out.println();
                System.out.println("Written by Deividas Ovsianikovas for CS210");
                System.out
                        .println("-----------------------------------------------------------------------------------");
            } else if (!answer) // If player does not want to hear rules, continue
            {
                System.out.println();
            } else {
                System.out.println("Please only use inputs as required above");
            }
        }

        System.out.println("Select your difficulty: Easy, Medium, Hard");

        /// Difficulty select -> length of word depends on this
        while (!difficultySelected) {
            String difficultyInput = sc.nextLine();
            difficultyInput = difficultyInput.toLowerCase();

            if (difficultyInput.equals("easy")) {
                difficultySelected = true;
                GameSetup(0);
            } else if (difficultyInput.equals("medium")) {
                difficultySelected = true;
                GameSetup(1);
            } else if (difficultyInput.equals("hard")) {
                difficultySelected = true;
                GameSetup(2);
            } else {
                System.out.println("Please only use inputs as required above");
            }
        }

        // Let the player keep guessing until the game is over
        while (!gameOver) {
            Guess(sc.nextLine());
        }

        /// End game state, if player guessed word equals computer chosen word then player wins, loses otherwise
        if (guessedWord.equals(chosenWord)) {
            System.out.println("...:::: Congrats, you win! ::::...");
        } else {
            System.out.println("...:::: Game Over :( ::::...");
        }

        /// Ask if player wants to play again/quit
        RetrySetup();
    }

    /// Method to initally set up the game based on inputted parameters
    public static void GameSetup(int difficulty) {
        /// Only run this section if the game has been restarted, no need to sort & loop if the game has been restarted
        if (!hasRestarted) {
            Dictionary dictionary = new Dictionary();
            allWords = dictionary.input;
            Sort(0, allWords.length - 1);/// Sort words smallest to largest

            /// Loop to set the positions of where each difficulties word length starts in array
            for (int i = 0; i < allWords.length; i++) {
                if (allWords[i].length() == 4 && Hangman.easyWordsStart == 0){ /// If the words are at a minimum length of 3 and var has not been assigned yet
                    easyWordsStart = i;
                } else if (allWords[i].length() == 6 && Hangman.mediumWordsStart == 0) {
                    mediumWordsStart = i;
                } else if (allWords[i].length() == 9 && Hangman.hardWordsStart == 0) {
                    hardWordsStart = i;
                }
            }
        }

        /// Used to generate random word based on given range
        Random r = new Random();

        /// Pick word based on selected difficulty above
        switch (difficulty) {
            case 0:/// Easy
                   // (Max Value) + Min Value
                chosenWord = allWords[r.nextInt(mediumWordsStart - 1) + easyWordsStart]; /// Chose random word in given range
                break;

            case 1:/// Medium
                chosenWord = allWords[r.nextInt(hardWordsStart - 1) + mediumWordsStart];
                break;

            case 2:/// Hard
                chosenWord = allWords[r.nextInt(allWords.length - 1) + hardWordsStart];
                break;
        }

        chosenWord = chosenWord.toLowerCase();

        // System.out.println("The word is " + chosenWord); /// For debugging

        /// Change guessedword to only contain "_" characters
        guessedWord = chosenWord.replaceAll("[A-Za-z]", "_");
        System.out.println(guessedWord);
        System.out.println("Word Length: " + (guessedWord.length() - 1)); /// Show the player how many characters are in the word
    }

    /// Method to get guessed characters from player
    public static void Guess(String guessedChar) {
        guessedChar = guessedChar.toLowerCase();

        /// -----Error states-----
        if (guessedChar.length() > 1)
        {
            System.out.println("Only guess 1 character at a time!");
            return;
        }

        if (!guessedChar.matches("[A-Za-z]+")) {
            System.out.println("Please only use alphabetical characters to guess");
            return;
        }

        if (guessedWord.contains(guessedChar) || allGuessedLetters.contains(guessedChar)) {
            System.out.println("You have already guessed this letter! Try something else");
            return;
        }
        /// -----End of Error states-----


        /// If guessed character equals a character in the word
        if (chosenWord.contains(guessedChar)) {
            System.out.println("Correct Guess!");

            /// Add guessed character to correct positions in the guessedWord variable
            for (int i = 0; i < chosenWord.length(); i++) {
                if (guessedChar.charAt(0) == chosenWord.charAt(i)) {
                    guessedWord = guessedWord.substring(0, i) + chosenWord.charAt(i) + guessedWord.substring(i + 1);
                }
            }

            System.out.println(guessedWord);
        } else/// Else if an incorrect character was guessed
        {
            allGuessedLetters += guessedChar + " ";
            health -= 1;

            System.out.println("Wrong! Lives Left: " + health);
            ShowLives(8 - health); /// 8 - health as 8 is the starting health
            System.out.println("Guessed Letters: " + allGuessedLetters);
        }

        ///End the game if the player guessed word equals the computer chosen word or if health <= 0
        if (guessedWord.equals(chosenWord) || health <= 0)
            gameOver = true;

        return;
    }

    /// Method to show the different life step the player is currently on and print out the corresponding Hangman state
    public static void ShowLives(int health) {
        String[][] livesString = bigLivesString;

        /// Formulas to get current step based on health
        int start = (6 * health) - 6 + health;
        int end = (6 * health) + health;

        //Nested loop to print out the required step from livesString 2D array
        for (int i = start - 1; i < end; i++) {
            for (int j = 0; j < livesString[i].length; j++) {
                System.out.print(livesString[i][j]);
            }
            System.out.println();
        }
    }

    // Method to handle if user wants to retry the game or not
    public static void RetrySetup() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Play again? Y N");

        boolean answer = YesNoAnswer();

        if (answer) {
            hasRestarted = true;
            Start();/// Start game again
        } else if (!answer) {
            System.out.println("Goodbye");
            sc.close();
            return; /// Terminate program if user no longer wants to play
        } else {
            System.out.println("Please only use inputs as required above");
        }
    }

    /// Method resetting variables to their original state
    public static void ResetVars() {
        chosenWord = "";
        guessedWord = "";
        gameOver = false;
        health = 8;
        allGuessedLetters = "";
    }

    // Method so we would not have to keep writing if("yes") clauses, takes up less memory & time
    public static boolean YesNoAnswer() {
        Scanner sc = new Scanner(System.in);
        boolean answerGiven = false;

        while (!answerGiven) {
            String input = sc.nextLine();
            input = input.toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                answerGiven = true;
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                answerGiven = true;
                return false;
            } else {
                System.out.println("Please only use inputs as required above");
            }
        }
        return false;
    }

    /// Sort all Words in array by length, shortest -> longest
    /// Merge Sort, Big o = nlogn
    public static void MergeAlgo(int p, int q, int r) {
        int n1 = q - p + 1; /// Length of subarray 1 (mid - start +1)
        int n2 = r - q; /// Length of subarray 2 (end - mid)

        /// Left and Right sides of array
        /// Temp arrays
        String[] L = new String[n1];
        String[] R = new String[n2];

        for (int i = 0; i < n1; i++) /// Copy subarray A[p..q] to L[0..n1]
        {
            L[i] = allWords[p + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = allWords[q + j + 1];
        }

        int i = 0;
        int j = 0;
        int k = p;

        /// Merge arrays
        while (i < n1 && j < n2) {
            if (L[i].length() <= R[j].length()) {
                allWords[k] = L[i];
                i++;
            } else {
                allWords[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements if any
        while (i < n1) {
            allWords[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            allWords[k] = R[j];
            j++;
            k++;
        }

    }

    public static void Sort(int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;

            // Sort 1st & second half
            Sort(p, q);
            Sort(q + 1, r);

            // Merge the sorted halves
            MergeAlgo(p, q, r);
        }
    }

}

class Dictionary
{
     
    public String input[]; 

    public Dictionary()
    {
        input = load("C:\\Users\\Deivid\\Documents\\AnotherHangmanProjectCS210\\words.txt");  ///<--------------Insert path to words.txt doc-----------
    }
    
    public int getSize(){
        return input.length;
    }
    
    public String getWord(int n){
        return input[n];
    }
    
    private String[] load(String file) 
    {
        File aFile = new File(file);     
        StringBuffer contents = new StringBuffer();
        BufferedReader input = null;
        try {
            input = new BufferedReader( new FileReader(aFile) );
            String line = null; 
            input.skip(120); ///First Few lines seem like nonsense, do not use
            while ((line = input.readLine()) != null)
            {
                if(line.matches("[A-Za-z]+")) ////Only include english language characters, no symbols
                {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            }
        }catch (FileNotFoundException ex){
            System.out.println("Can't find the file - are you sure the file is in this location: "+file);
            ex.printStackTrace();
        }catch (IOException ex){
            System.out.println("Input output exception while processing file");
            ex.printStackTrace();
        }finally{
            try {
                if (input!= null) {
                    input.close();
                }
            }catch (IOException ex){
                System.out.println("Input output exception while processing file");
                ex.printStackTrace();
            }
        }
        String[] array = contents.toString().split("\n");
        for(String s: array){
            s.trim();
        }
        return array;
    }
}
