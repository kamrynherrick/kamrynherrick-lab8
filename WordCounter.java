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
    public static int processText(StringBuffer text, String stopword) {

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
        throw new InvalidStopWordException(stopword); 

    }

    
    
}
