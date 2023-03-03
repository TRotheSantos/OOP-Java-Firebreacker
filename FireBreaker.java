import edu.kit.informatik.Terminal;
import edu.kit.informatik.application.Application;
import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.OwnExceptions.InvalidArgumentFormatExc;


/**
 * Class containing the main-method
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class FireBreaker {


    // private constructor
    private FireBreaker() {
    }

    /**
     * Main to run the program, the terminal input goes to to the application method.
     * Also data is initialized (an array with the regex specifications)
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        boolean programStop = false;

        try {
            Data.initGame(args);
        } catch (InvalidArgumentFormatExc e) {
            Terminal.printError(e.getMessage());
            programStop = true;
        } catch (NumberFormatException e) {
            Terminal.printError("Row and Column need to be an integer value.");
            programStop = true;
        }

        while (!programStop) {
            String inputLine = Terminal.readLine();
            programStop = Application.application(inputLine);

        }
    }
}
