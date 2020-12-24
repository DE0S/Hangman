/*
7 Lives

TO DO;
-Top of Hangman not printing
-Words not showing correctly

Difficulty settings
*/

import java.util.*;

public class Hangman
{
    private static String[] allWords;
    private static String chosenWord = "";
    private static String guessedWord = "";
    private static boolean gameOver = false;
    private static int health = 7;
    private static String allGuessedLetters = "";
    ///0 = Easy, 1 = Medium, 2 = Hard

    public static int easyWordsStart = 0, mediumWordsStart = 0, hardWordsStart = 0;


    public static void main(String args[])
    {
        ResetVars();
        Scanner sc = new Scanner(System.in);

        System.out.println("Please choose difficulty: Easy, Medium, Hard");
        boolean difficultySelected = false;

        while(!difficultySelected)
        {
            String difficultyInput = sc.nextLine();
            difficultyInput = difficultyInput.toLowerCase();

            if(difficultyInput.equals("easy"))
            {
                difficultySelected = true;
                GameSetup(0);   
            }
            else if(difficultyInput.equals( "medium"))
            {
                difficultySelected = true;
                GameSetup(1);
            }
            else if(difficultyInput.equals("hard"))
            {
                difficultySelected = true;
                GameSetup(2);
            }
            else
            {
                System.out.println("Please only use inputs as required above" );
            }
        }

        //Let the player keep guessing until the game is over
        while(!gameOver)
        {
            Guess(sc.nextLine());
        }

        ///End game state
        if(guessedWord.equals(chosenWord))
        {
            System.out.println(".:::: Congrats, you win! ::::.");
        }
        else
        {
            System.out.println(".:::: Game Over :( ::::.");
        }

        RetrySetup();

    }




    ///Method to initally set up the game based on inputted parameters
    public static void GameSetup(int difficulty)
    {
        Dictionary dictionary = new Dictionary();

        allWords = dictionary.input;
        Sort(0, allWords.length-1);

        ///Generate random word based on given range
        Random r = new Random();
        ///Pick word based on selected difficulty above
        switch(difficulty)
        {
            case 0:///Easy  
                //                              (Max Value)       + Min Value
                chosenWord = allWords[r.nextInt(mediumWordsStart-1) + easyWordsStart];   ///Chose random word in given range, starting at pos 120
            break;

            case 1:///Medium
                chosenWord = allWords[r.nextInt(hardWordsStart-1) + mediumWordsStart];
            break;

            case 2:///Hard
                chosenWord = allWords[r.nextInt(allWords.length-1) + hardWordsStart];
            break;
        }
        
        chosenWord = chosenWord.toLowerCase();
        
        System.out.println("The word is " + chosenWord); ///For debugging
        
        ///Change guessedword to only contain "_" characters
        guessedWord = chosenWord.replaceAll("[A-Za-z]", "_");
        System.out.println(guessedWord);
        System.out.println("Word Length: " + guessedWord.length());
    }



    ///Method to get guess from player
    public static void Guess(String guessedChar)
    {
        guessedChar = guessedChar.toLowerCase();

        if(guessedChar.length() > 1)/// If guessed char is longer than 1 letter
        {
            System.out.println("Can only guess 1 character at a time!");
            return;
        }

        if(!guessedChar.matches("[A-Za-z]+"))
        {
            System.out.println("Please only use alphabetical characters to guess");
            return;
        }

        ///If guessed character equals a character in the word
        if(chosenWord.contains(guessedChar))
        {
            System.out.println("Correct Guess!");
            ///Make changes to accomodate correct guess
            for(int i = 0; i < chosenWord.length();i++)
            {
                if(guessedChar.charAt(0) == chosenWord.charAt(i))
                {
                    guessedWord = guessedWord.substring(0,i) + chosenWord.charAt(i) + guessedWord.substring(i+1);
                }
            }

            System.out.println(guessedWord);
        }
        else///Else if an incorrect character was guessed
        {
            allGuessedLetters += guessedChar + " ";
            health-=1;
    
            System.out.println("Wrong! Lives Left: " + health);
            ShowLives(7 - health);
            System.out.println("Guessed Letters: " + allGuessedLetters);
        }

        if(guessedWord.equals(chosenWord) || health <= 0 ) gameOver = true; 
        
        return;

    }


    ///Method to show the different life step the player is currently on
    public static void ShowLives(int health)
    {
        String[][] livesString = HangmanSteps.bigLivesString;

        ///Formulas to get current position based on health
        int start = (6 * health) - 6 + health;
        int end = (6 * health) + health; 

        for(int i = start; i < end;i++)
        {
            for(int j = 0; j < livesString[i].length;j++)
            {
                System.out.print(livesString[i][j]);
            }
            System.out.println();
        }
    }


        //Method to handle if user wants to retry the game or not
        public static void RetrySetup()
        {
            Scanner sc = new Scanner(System.in);
            boolean retrySelected = false;
            System.out.println("Play again? Y N");

            while(!retrySelected)
            {
                String input = sc.nextLine();
                input = input.toLowerCase();
    
                if(input.equals("y") || input.equals("yes"))
                {
                    retrySelected = true;
                    main(null); ///Call main method again
                }
                else if(input.equals("n") || input.equals( "no"))
                {
                    System.out.println("Goodbye");
                    retrySelected = true;
                    sc.close();
                    return; ///Terminate program if user no longer wants to play
                }
                else 
                {
                    System.out.println("Please only use inputs as required above");
                }
            }
        }
    


    ///Method resetting variables to their original state
    public static void ResetVars()
    {
            chosenWord = "";
            guessedWord = "";
            gameOver = false;
            health = 7;
            allGuessedLetters = "";
            easyWordsStart = 0;
            mediumWordsStart = 0;
            hardWordsStart = 0;
    }

    //Sort all Words in array by length, shortest -> longest
    ///Bitta Merge Sort
    public static void MergeAlgo(int p, int q, int r) 
    {
        int n1 = q - p + 1; ///Length of subarray 1 (mid - start +1)
        int n2 = r - q; ///Length of subarray 2 (end - mid)

        ///Left and Right sides of array
        ///Temp arrays
        String[] L = new String[n1];    ///extra "+1" position holds the sentinal card
        String[] R = new String[n2];

        for (int i = 0; i < n1; i++) ///Copy subarray A[p..q] to L[0..n1]
        {
            L[i] = allWords[p + i];
        }
        for (int j = 0; j < n2; j++) 
        {
            R[j] = allWords[q + j + 1];
        }

        int i = 0;
        int j = 0;
        int k=p;

        ///Merge arrays
        while(i < n1 && j < n2){
            if(L[i].length() <= R[j].length())
            {
                allWords[k] = L[i];
                i++;
            }
            else
            {
                allWords[k] = R[j];
                j++;
            }
            k++;
        }

        //Copy remaining elements if any
        while(i < n1)
        {
            allWords[k] = L[i];
            i++;
            k++;
        }

        while(j < n2)
        {
            allWords[k] = R[j];
            j++;
            k++;
        }
       
    }


    public static void Sort(int p, int r)
    {
        if(p < r)
        {
            int q = (p+r)/2;

            //Sort 1st & second half
            Sort(p,q);
            Sort(q+1,r);

            //Merge the sorted halves
            MergeAlgo(p, q, r);
        }
    }

}