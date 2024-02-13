package AST;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_WHILE(AST_EXP cond, AST_STMT_LIST body)
	{

		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> WHILE LPAREN exp RPAREN LBRACE stmtList RBRACE\n");

		/*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
		this.cond = cond;
		this.body = body;
	}

	/***********************************************/
	/* The default message for a while stmt AST NODE */
	/***********************************************/
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = WHILE STMT AST NODE */
		/************************************/
		System.out.print("AST NODE WHILE STMT\n");

		/*****************************/
		/* RECURSIVELY PRINT         */
		/*****************************/
		cond.PrintMe();
		body.PrintMe();

		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
				SerialNumber,
				"WHILE\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}
}