
import java.io.*;
import java.io.PrintWriter;
import java.util.Objects;

import java_cup.runtime.Symbol;
import jdk.internal.org.jline.terminal.TerminalBuilder;

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
                Integer int_value = -9999999; // Set to default value
                String str_value = "";

                switch (token_id) {
                    case TokenNames.INT:
                        int_value = (Integer) s.value;
						if(int_value > 32767 || int_value < -32767)
                            throw new Exception();
						break;
					case TokenNames.STRING:
					case TokenNames.ID:
						str_value = (String) s.value;
                        break;
					default:
                        break;
                }

                file_writer.print(token_name);
                if(token_name.equals("INT")){
                    file_writer.print("(" + int_value + ")");
                }
                else if (token_name.equals("ID") || token_name.equals("STRING")) {
                    file_writer.print("(\"" + str_value + "\")");
                }
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
        } catch (Exception e) {
            try (PrintWriter printwriter = new PrintWriter(outputFilename)){
                printwriter.println("ERROR");
            }
            catch (IOException e2){
                System.out.println("Error writing to output file");
            }
        }
    }

}


