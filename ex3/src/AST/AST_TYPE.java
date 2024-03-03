package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_TYPE extends AST_Node
{
    private String type;


    
    /*
     * A simple constructor.
     * 
     * @param type A string with the name of the type.
     */
    public AST_TYPE(String type)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if (type.equals("int") || type.equals("string") || type.equals("void")) {
            System.out.format("====================== type -> %s\n", type);
        }
		else
        {
            System.out.format("====================== type -> ID(%s)\n", type);
        }
        this.type = type;
	}
 


	/*************************************************/
	/* The printing message for a AST_TYPE */
	/*************************************************/
	public void PrintMe()
	{

		/*************************************/
		/* AST NODE TYPE = AST NODE          */
		/*************************************/
		System.out.format("AST NODE TYPE %s\n", type);
		
        /**************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        if (this.type.equals("int") || this.type.equals("string") || this.type.equals("void")) {
            AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("TYPE_%s", type));
        }
		else
        {
            AST_GRAPHVIZ.getInstance().logNode(
            SerialNumber,
            String.format("TYPE_ID(%s)", type));
        }
	}

    public TYPE SemantMe(){
        return null;
    }
}