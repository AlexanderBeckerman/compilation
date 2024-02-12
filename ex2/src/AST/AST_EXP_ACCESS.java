package AST;

public class AST_EXP_ACCESS extends AST_EXP
{
	public AST_EXP_ACCESS access;
	
    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_ACCESS(AST_EXP_ACCESS access)
	{

        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> access\n");

        /*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
		this.access = access;
	}

    /*************************************************/
	/* The printing message for a this node */
	/*************************************************/
	public void PrintMe()
	{

        /*************************************/
		/* AST NODE TYPE = AST NODE EXP ACCESS   */
		/*************************************/
		System.out.print("AST NODE EXP ACCESS\n");

        /* print the relevant field */
		access.PrintMe();

        /**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ACCESS\nEXP");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,access.SerialNumber);
	}
}