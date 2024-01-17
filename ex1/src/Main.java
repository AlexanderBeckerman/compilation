
import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;

public class Main {
    static public void main(String argv[]) {
        Lexer l;
        Symbol s;
        FileReader file_reader;
        PrintWriter file_writer;
        String inputFilename = argv[0];
        String outputFilename = argv[1];

        try {
            /********************************/
            /* [1] Initialize a file reader */
            /********************************/
            file_reader = new FileReader(inputFilename);

            /********************************/
            /* [2] Initialize a file writer */
            /********************************/
            file_writer = new PrintWriter(outputFilename);

            /******************************/
            /* [3] Initialize a new lexer */
            /******************************/
            l = new Lexer(file_reader);

            /***********************/
            /* [4] Read next token */
            /***********************/
            s = l.next_token();
            /********************************/
            /* [5] Main reading tokens loop */
            /********************************/
            while (s.sym != TokenNames.EOF) {

                /*********************/
                /* [7] Print to file */
                /*********************/
                int line_number = l.getLine();
                int char_pos = l.getTokenStartPosition();
                int token_id = s.sym;
                String token_name = TokenNames.names[token_id];

                switch (token_id) {
                    case TokenNames.INT:
                        if ((Integer) s.value > 32767 || (Integer) s.value < -32767)
                            throw new Exception();
                        break;
                    case TokenNames.BAD_COMMENT:
                    case TokenNames.BAD_ZERO:
                        throw new Exception();
                }


                file_writer.print(token_name);
                if (token_name.equals("INT") || token_name.equals("STRING") || token_name.equals("ID"))
                    file_writer.print("(" + s.value + ")");
                file_writer.print("[");
                file_writer.print(line_number);
                file_writer.print(",");
                file_writer.print(char_pos);
                file_writer.print("]");

                file_writer.print("\n");

                /***********************/
                /* [8] Read next token */
                /***********************/
                s = l.next_token();
            }

            /******************************/
            /* [9] Close lexer input file */
            /******************************/
            l.yyclose();

            /**************************/
            /* [10] Close output file */
            /**************************/
            file_writer.close();
        }
        catch (Exception e) {
            printError(outputFilename);
        }
        catch (java.lang.Error e){
            printError(outputFilename);
        }
    }

    private static void printError(String filename) {
        try { // overwrite the file with "ERROR"
            PrintWriter f_writer = new PrintWriter(filename);
            f_writer.println("ERROR");
            f_writer.close();
        } catch (IOException e2) {
            System.out.println("Error writing to output file");
        }
    }

}


