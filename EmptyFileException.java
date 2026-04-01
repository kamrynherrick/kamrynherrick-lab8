import java.io.IOException;
 
//when a file exists but has no content
public class EmptyFileException extends IOException {

    public EmptyFileException(String message) {
        super(message);
    }

    //returns the message as is
    public String toString() {
        return getMessage();
    }
}
