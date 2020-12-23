import java.io.*;

public class Dictionary{
     
    public String input[]; 

    public Dictionary(){
        input = load("C:\\Users\\Deivid\\OneDrive\\CS210_AlgosData\\Project\\words.txt");  
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
            int i = 120; ///First Few lines seem like nonsense, do not use
            while (( line = input.readLine()) != null)
            {
                if(line.matches("[A-Za-z]+")) ////Only include english language characters, no symbols
                {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
                if(line.length() == 3 && Hangman.easyWordsStart == 0) ///If the words are minimum 
                {
                    
                }
                i++;
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