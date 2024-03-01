package AST;

public class AST_STMT_VARDEC extends AST_STMT
{
    public AST_VAR_DEC varDec;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
    public AST_STMT_VARDEC(AST_VAR_DEC varDec)
    {

        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        System.out.print("====================== stmt -> varDec\n");

        /*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
        this.varDec = varDec;
    }

    /***********************************************/
	/* The default message for this node */
	/***********************************************/
    public void PrintMe()
    {

        /************************************/
		/* AST NODE TYPE = STMT VARDEC AST NODE */
		/************************************/
        System.out.print("AST NODE VARDEC STMT\n");

        /*****************************/
		/* RECURSIVELY PRINT         */
		/*****************************/
        varDec.PrintMe();

        /*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "STMT\nVAR DEC\n");

        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, varDec.SerialNumber);
    }
}