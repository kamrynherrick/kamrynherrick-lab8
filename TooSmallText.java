// used when the total word count in the text is below 5
public class TooSmallText extends Exception {

    //builds a message that includes how many words were actually found
    public TooSmallText(int count) {
        super("TooSmallText: Only found " + count + " words.");
    }

    //returns the message that caused the problem/prints it out directly
    public String toString() {
        return getMessage(); 
    }
    
}
