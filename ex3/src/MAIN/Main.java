package MAIN;

import java.io.*;

import AST.AST_DEC_LIST;
import AST.AST_GRAPHVIZ;
import java_cup.runtime.Symbol;

public class Main
{
	static final int ERROR_RETURN = 0;
	static final String CORECT_MSG = "OK\n";
	static final String LEXIAL_ERR_MSG = "ERROR\n";
	public static Lexer l;
	

	static public void main(String argv[])
	{
		Parser p;
		Symbol s;
		AST_DEC_LIST AST;
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
				AST = (AST_DEC_LIST) p.parse().value;
				
				/* Print the AST ... */
				AST.PrintMe();

				/* Semant the AST ... */
				AST.SemantMe();
				
				/* Finalize AST GRAPHIZ DOT file */
				AST_GRAPHVIZ.getInstance().finalizeFile();

				// Parsed succefuly, printintg appropriate output to the output file.
				file_writer.write(CORECT_MSG);
			}
			catch(LineError e)
			{
				// A syntax/semantic error was found, printing appropriate output to the output file.
				file_writer.write(e.getMessage());
			}
			catch(Error e)
			{
				// A lexical error was found, printing appropriate output to the output file.
				file_writer.write(LEXIAL_ERR_MSG);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			finally{
				/* Close writer and reader. */
				file_writer.close();
				file_reader.close();
				System.exit(0);
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
	}
}

