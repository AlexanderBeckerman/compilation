package AST;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_IF(AST_EXP cond, AST_STMT_LIST body)
	{

		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> IF LPAREN exp RPAREN LBRACE stmtList RBRACE\n");

		/*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
		this.cond = cond;
		this.body = body;
	}

	/***********************************************/
	/* The default message for an if stmt AST NODE */
	/***********************************************/
	public void PrintMe()
	{

		/************************************/
		/* AST NODE TYPE = IF STMT AST NODE */
		/************************************/
		System.out.print("AST NODE IF STMT\n");

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
				"IF\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}
}