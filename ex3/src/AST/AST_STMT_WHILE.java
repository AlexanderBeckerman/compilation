package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond,AST_STMT_LIST body)
	{
		this.cond = cond;
		this.body = body;
	}

	public TYPE SemantMe(TYPE_FUNCTION func_type){
		
		SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
		if (cond.SemantMe() != TYPE_INT.getInstance()){
			System.out.format(">> ERROR [%d:%d] while loop condition must be an int.\n", this.lineNumber, this.charPos);
			throw new LineError(lineNumber); // not a valid condition
		}
		table.beginScope();
		body.SemantMe(func_type);
		table.endScope();
		return null;
	}
}