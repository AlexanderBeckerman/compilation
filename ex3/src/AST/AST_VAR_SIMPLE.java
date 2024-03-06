package AST;

import MAIN.LineError;
import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.TYPE;
import TYPES.TYPE_ARRAY;
import TYPES.TYPE_ARRAY_INSTANCE;
import TYPES.TYPE_CLASS;
import TYPES.TYPE_CLASS_INSTANCE;
import TYPES.TYPE_INSTANCE;
import TYPES.TYPE_INT;
import TYPES.TYPE_PRIMITIVE_INSTANCE;
import TYPES.TYPE_STRING;

public class AST_VAR_SIMPLE extends AST_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SIMPLE(String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== var -> ID( %s )\n",name);

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}


	/**
     * The SemantMe implementation of AST_VAR_SIMPLE.
     * 
     * @return: The static type of the field.
     */
    public TYPE SemantMe()
	{
		TYPE varType = SYMBOL_TABLE.getInstance().find(name);
	
        if (varType == null) {
            System.out.format(">> ERROR [%d:%d] %s is undeclared.\n", this.lineNumber, this.charPos, name);
            throw new LineError(this.lineNumber);
        }
		if (!(varType instanceof TYPE_INSTANCE)) {
            System.out.format(">> ERROR [%d:%d] %s isn't an instance.\n", this.lineNumber, this.charPos, name);
            throw new LineError(this.lineNumber);
        }

		/*
         * This should not be like this, instead, all instance's types should be the same, and there should not be more than one TYPE instance for each type.
         * But I wrote it like this so code won't get thrown away.
         */
        TYPE returned = null;
        if (varType instanceof TYPE_CLASS_INSTANCE)
        {
            returned = ((TYPE_CLASS_INSTANCE) varType).ctype;
        }
        else if(varType instanceof TYPE_ARRAY_INSTANCE)
        {
            returned = ((TYPE_ARRAY_INSTANCE) varType).arr_type;
        }
        else
        {
            returned = ((TYPE_PRIMITIVE_INSTANCE) varType).type;
        }

		// Returning the static type of the field.
		return returned;
	}
}
