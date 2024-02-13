   
import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;

public class Main
{
	static final int ERROR_RETURN = 0;
	static final String CORECT_MSG = "OK\n";
	static final String LEXIAL_ERR_MSG = "ERROR\n";
	static String SYNTAX_ERR_MSG(int lineNumber)
	{
		return "ERROR(" + lineNumber + ")\n";
	}

	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_PROGRAM AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		try{
			/* Initialize a file reader */
			file_reader = new FileReader(inputFilename);

			/* Initialize a file writer */
			file_writer = new PrintWriter(outputFilename);

			/* Initialize a new lexer */
			l = new Lexer(file_reader);
				
			/* Initialize a new parser */
			p = new Parser(l);
			try
			{
				/* 3 ... 2 ... 1 ... Parse !!! */
				AST = (AST_PROGRAM) p.parse().value;
				
				/* Print the AST ... */
				AST.PrintMe();
				
				/* Finalize AST GRAPHIZ DOT file */
				AST_GRAPHVIZ.getInstance().finalizeFile();

				// Parsed succefuly, printintg appropriate output to the output file.
				file_writer.write(CORECT_MSG);
			}
			catch(RuntimeException e){
				file_writer.write(LEXIAL_ERR_MSG);
			}
			catch(Exception e)
			{
				// A sytax error was found, printing appropriate output to the output file.
				file_writer.write(SYNTAX_ERR_MSG(p._lineNumber));
			}
			catch(Error e)
			{
				// A lexical error was found, printing appropriate output to the output file.
				file_writer.write(LEXIAL_ERR_MSG);
			}

			finally{
				/* Close output file */
				file_writer.close();
				System.exit(0);
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
	}
}

