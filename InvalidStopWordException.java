
//raised when stopword is not found in the text
public class InvalidStopwordException extends Exception {

    //creates a nessage that includes the stopword that couldn't be dound
    public InvalidStopwordException(String stopword) {
        super("InvalidStopwordException: Couldn't find stopword: " + stopword);
    }
    
    //returns the message that caused the error
    public String toString() {
        return getMessage();
    }
    
}
