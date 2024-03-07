package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

public class AST_STMT_ACCESS extends AST_STMT
{
	public AST_ACCESS access;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_ACCESS(AST_ACCESS access)
	{

        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> access SEMICOLON\n");

        /*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
		this.access = access;
	}

    /***********************************************/
	/* The default message for access stmt AST NODE */
	/***********************************************/
	public void PrintMe()
	{
        /************************************/
		/* AST NODE TYPE = ACCESS STMT AST NODE */
		/************************************/
		System.out.print("AST NODE STMT ACCESS\n");

        /*****************************/
		/* RECURSIVELY PRINT         */
		/*****************************/
		access.PrintMe();

        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ACCESS\nSTMT");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(
			SerialNumber,
			access.SerialNumber);
	}

	public TYPE SemantMe(TYPE_FUNCTION func_type){
		return access.SemantMe();
	}
}
