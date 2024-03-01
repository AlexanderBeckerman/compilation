package MAIN;

/*
 * The error that will be thrown in case we should print the line number to the output.
 */
public class LineError extends Throwable{

    /**
     * Constructs a LineEror object with "ERROR(" + lineNumber + ")\n" as its detail message.
     *
     * @param lineNumber The line number where the error occurred.
     */
    public LineError(int lineNumber)
    {
        super("ERROR(" + lineNumber + ")\n");
    }
}
