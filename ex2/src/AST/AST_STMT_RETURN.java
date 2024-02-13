package AST;

public class AST_STMT_RETURN extends AST_STMT
{
    public AST_EXP exp;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
    public AST_STMT_RETURN(AST_EXP exp)
    {
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if (exp != null)
            System.out.print("====================== stmt -> RETURN exp SEMICOLON\n");
        else
            System.out.print("====================== stmt -> RETURN SEMICOLON\n");

        /*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/    
        this.exp = exp;
    }

    /***********************************************/
	/* The default message for return stmt AST NODE */
	/***********************************************/
    public void PrintMe()
    {
        /************************************/
		/* AST NODE TYPE = RETURN STMT AST NODE */
		/************************************/
        System.out.print("AST NODE RETURN STMT\n");

        /*****************************/
		/* RECURSIVELY PRINT         */
		/*****************************/
        if(exp != null)
            exp.PrintMe();

        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "RETURN\n");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
        if(exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
    }
}
