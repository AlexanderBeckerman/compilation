package AST;

import MAIN.*;

public abstract class AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	public int lineNumber; // The line in which the symbol appeared.
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}

	public AST_Node()
	{
		this.lineNumber = Main.l.getLine();
	}
}
