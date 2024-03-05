package AST;

import MAIN.LineError;
import TYPES.*;

public class AST_VAR_FIELD extends AST_VAR
{
	public AST_VAR var;
	public String fieldName;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_FIELD(AST_VAR var,String fieldName)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> var DOT ID(%s)\n",fieldName);

		/*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
		this.var = var;
		this.fieldName = fieldName;
	}

	/*************************************************/
	/* The printing message for a AST_VAR_FIELD node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.print("AST NODE FIELD VAR\n");

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null) var.PrintMe();
		System.out.format("FIELD NAME( %s )\n",fieldName);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR(%s)",fieldName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}


	/**
     * The SemantMe implementation of AST_VAR_FIELD.
     * 
     * @return: The static type of the field.
     */
    public TYPE SemantMe()
	{
		TYPE varType;
	
		varType = this.var.SemantMe(); // The type's existance is checked within t's SemantMe.
		
        if (!(varType instanceof TYPE_CLASS_INSTANCE)) {
            System.out.format(">> ERROR [%d:%d] evaluated %s-type which is not a class and therefore instance member cannot be accessed.\n", this.lineNumber, this.charPos, varType.name);
            throw new LineError(lineNumber);
        }

		TYPE returned = ((TYPE_CLASS) varType).findClassVariable(fieldName); // Getting the type of fieldName.

		if (returned == null) {
            System.out.format(">> ERROR [%d:%d] %s doesn't have a member called %s.\n", this.lineNumber, this.charPos, varType.name, fieldName);
            throw new LineError(lineNumber);
        }

		// Returning the static type of the field.
		return returned;
	}
}
