package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;
	private int ln;
	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond,AST_STMT_LIST body, int line)
	{
		this.cond = cond;
		this.body = body;
		this.ln = line;
	}

	public TYPE SemantMe(TYPE_FUNCTION func_type){
		
		SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
		if (cond.SemantMe() != TYPE_INT.getInstance()){
			System.out.format(">> ERROR [%d:%d] while loop condition must be an int.\n", this.ln, this.charPos);
			throw new LineError(this.ln); // not a valid condition
		}
		table.beginScope();
		body.SemantMe(func_type);
		table.endScope();
		return null;
	}

	public void PrintMe(){
        System.out.print("AST NODE WHILE STMT\n");
        cond.PrintMe();
        body.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "WHILE\n");
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
    }
}