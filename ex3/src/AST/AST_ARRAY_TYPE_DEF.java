package AST;

import MAIN.LineError;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_ARRAY_TYPE_DEF extends AST_Node{
    AST_TYPE typy;
	String name;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ARRAY_TYPE_DEF(String id, AST_TYPE typy)
	{
        this.typy = typy;
		this.name = id;
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		this.SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== arrayTypedef -> ARRAY ID EQ type '[' ']' ';'\n");
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST BINOP EXP */
		/*************************************/
		System.out.print("AST NODE AST_ARRAY_TYPE_DEF \n");

		/**************************************/
		/* RECURSIVELY PRINT the attribute ... */
		/**************************************/
		typy.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("arryTypeDef")
		);
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,typy.SerialNumber);
	}

	public TYPE SemantMe(){
		SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
		if (table.getScopeDepth() != 0)
		{
			System.out.format(">> ERROR [%d:%d] trying to declare an array not in global scope!\n", lineNumber, charPos);
			throw new LineError(this.typy.lineNumber); // must be declared in global scope
		}
		if (table.find(this.name) != null){
			System.out.format(">> ERROR [%d:%d] %s different given and expected arguments to function %s\n", lineNumber, charPos, this.name);
			throw new LineError(this.typy.lineNumber); // array name already exists
		}
		TYPE arrayType = typy.SemantMe();
		if (arrayType == null || arrayType instanceof TYPE_VOID || (arrayType instanceof TYPE_INSTANCE) || arrayType instanceof TYPE_FUNCTION){
			System.out.format(">> ERROR [%d:%d] given array type is null or void or an instance or a function\n", lineNumber, charPos);
			throw new LineError(this.typy.lineNumber); // no such type or void array not allowed or is a function name or its an instance 
		}
		
		table.enter(name, new TYPE_ARRAY(name, arrayType));
		return null;
	}
}
