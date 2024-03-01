package AST;

public class AST_ARRAY_TYPE_DEF extends AST_Node{
    AST_TYPE typy;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_ARRAY_TYPE_DEF(AST_TYPE typy)
	{
        this.typy = typy;

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
}
