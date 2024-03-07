package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

public class AST_STMT_ASSIGN_NEWEXP extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_VAR var;
	public AST_NEW_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN_NEWEXP(AST_VAR var,AST_NEW_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN newExp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + NEW EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}

	public TYPE SemantMe(TYPE_FUNCTION func_type){
				
		TYPE var_type = var.SemantMe();
		TYPE exp_type = exp.SemantMe();

		if (var_type instanceof TYPE_ARRAY) {
			if (!(exp_type instanceof TYPE_ARRAY) || !TYPE.areMatchingTypes(((TYPE_ARRAY) exp_type).dataType, ((TYPE_ARRAY) var_type).dataType)){
				System.out.format(">> ERROR [%d:%d] assigment of %s to a %s is illegal via new.\n", this.lineNumber, this.charPos, exp_type.name, var_type.name);
				throw new LineError(this.lineNumber-1);
			}
		}
		else if(!TYPE.areMatchingTypes(exp_type, var_type)) {
			System.out.format(">> ERROR [%d:%d] assigment of %s to a %s is illegal.\n", this.lineNumber, this.charPos, exp_type.name, var_type.name);
			throw new LineError(this.lineNumber-1);
		}
		return null;
	}
}
