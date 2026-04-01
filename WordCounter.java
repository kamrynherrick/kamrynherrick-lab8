import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class WordCounter {

    //counts words in text up to the stopword, or all text if the stopword is null
    //throws tooSmallText if total word count is less than 5
    //throws invalidStopwordException if stopword is not found in the text
    public static int processText(StringBuffer text, String stopword) throws InvalidStopwordException, TooSmallText {

        //define word as a sequence of letters, digits, or quotes
        Pattern regex = Pattern.compile("[a-zA-Z0-9]+"); 
        Matcher m = regex.matcher(text); 

        //first pass: count every word in the text
        int total = 0; 
        while (m.find()) {
            total++; 
        }

        //fewer than 5 words will cause text too small to be called
        if (total < 5) throw new TooSmallText(total); 

        //no stopword means to count all words, total will return what we already have as wordcount
        if (stopword == null) {
            return total; 
        }

        //second pass: count words until hit the stopword
        m.reset(); 
        int count = 0; 
        while(m.find()) {
            count++; 

            if (m.group().equals(stopword)) {
                return count; 
            }
        }

        //if the loop finishes without stopword, raise exception
        throw new InvalidStopwordException(stopword); 

    }

    //reads file into stringbuffer and returns it
    public static StringBuffer processFile(String path) throws EmptyFileException {
        Scanner keyboard = new Scanner(System.in);
        File file = new File(path); 

        //ask for filename until one that exists is provided
        while (!file.exists()) {
            System.out.println("File not found. Enter a new filename:");
            path = keyboard.nextLine(); 
            file = new File(path); 
        }

        //read file one character at a time into stringbuffer
        StringBuffer sb = new StringBuffer(); 
        try {
            BufferedReader br = new BufferedReader (new FileReader(file)); 
            int ch; 
            while ((ch = br.read()) != -1) {
                sb.append((char) ch); 
            }
            br.close(); 
        } catch (IOException e) {
            //file existed but couldnt be read
            System.out.println("Error reading file.");
        }

        //empty file cant be processed
        if (sb.length()==0) {
            throw new EmptyFileException("EmptyFileException: " + path + " was empty"); 
        }
        return sb;

    }

    public static void main(String[] args) {
        //first argument is a file path or text string
        String input = args[0]; 
        //second is a stopword, null if not provided
        String stopword; 
        if(args.length >= 2) {
            stopword = args[1]; 
        } else {
            stopword = null; 
        } 
        Scanner keyboard = new Scanner(System.in); 
        StringBuffer text = new StringBuffer("");

        int choice = 0; 
        while (choice != 1 && choice != 2) {
            System.out.println("Enter 1 for file, 2 for text:");
            String line = keyboard.nextLine(); 
            try {
                choice = Integer.parseInt(line);
            }
            catch (NumberFormatException e) {
                //input was not a number, so will ask for a new one
            }
        }

        //load text from file
        if (choice ==1) {
            try {
                text = processFile(input); 
            } catch (EmptyFileException e) {
                System.out.println(e); 
            }
        } else {
            text = new StringBuffer(input); 
        } 

        try {
            
            System.out.println("Found " + processText(text, stopword) + " words.");
        } catch (TooSmallText e) {
            System.out.println(e);
        } catch (InvalidStopwordException e) {
            System.out.println("Stopword not found. Enter a new stopword:");
            stopword = keyboard.nextLine();
            try {
                System.out.println("Found " + processText(text, stopword) + " words.");
            } catch (TooSmallText e2) {
                System.out.println(e2);
            } catch (InvalidStopwordException e2) {
                System.out.println("Stopword '" + stopword + "' was also not found.");
            }
        }


    }
    
}
