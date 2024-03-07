package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

public class AST_EXP_BINOP extends AST_EXP
{	
	public int OP;
	public AST_EXP left;
	public AST_EXP right;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,int OP)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		String sOP="";
		
		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
		if (OP == 2) {sOP = "*";}
		if (OP == 3) {sOP = "/";}
		if (OP == 4) {sOP = "<";}
		if (OP == 5) {sOP = ">";}
		if (OP == 6) {sOP = "=";}
		
		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.format("AST NODE BINOP(%s) EXP\n", sOP);

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("EXP\nBINOP(%s)",sOP));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
	public TYPE SemantMe()
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (left  != null) t1 = left.SemantMe();
		if (right != null) t2 = right.SemantMe();

		if (OP < 6 && (t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance()))
		{
			if (OP == 3 && ((AST_EXP_INT)right).value == 0) // division by zero
			{
				System.out.format(">> ERROR [%d:%d] division by zero!\n", lineNumber, charPos);
				throw new LineError(lineNumber);
			}
			return TYPE_INT.getInstance(); // binary operation between two ints
		}
		if (OP == 0 && (t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance())){
			return TYPE_STRING.getInstance(); // adding two strings
		}
		if (OP == 6 && (TYPE.areMatchingTypes(t1, t2) || TYPE.areMatchingTypes(t2, t1))){
			return TYPE_INT.getInstance();
		}
		System.out.format(">> ERROR [%d:%d] invalid binop=%d operation between %s and %s\n", lineNumber, charPos,this.OP ,t1.name, t2.name);
		throw new LineError(lineNumber);

	}
}


