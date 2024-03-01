package AST;

public class AST_ACCESS extends AST_Node
{
    public AST_VAR var;
    public String id;
    public AST_EXP_LIST expressions;

    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
    public AST_ACCESS(AST_VAR var, String id, AST_EXP_LIST expressions)
    {

        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();


		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if (expressions == null)
        {
            if (var == null)
            {
                System.out.print("======================access -> ID LPAREN RPAREN\n");
            }
            else
                System.out.print("======================access -> var DOT ID LPAREN RPAREN\n");
        }
        else if (var == null)
            System.out.print("======================access -> ID LPAREN expList RPAREN\n");
        else
            System.out.print("======================access -> var DOT ID LPAREN expList RPAREN\n");


        /*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
        this.var = var;
        this.id = id;
        this.expressions = expressions;
    }

    /*************************************************/
	/* The printing message for a this node */
	/*************************************************/
    public void PrintMe()
    {

        /*************************************/
		/* AST NODE TYPE = AST NODE ACCESS   */
		/*************************************/
        System.out.format("AST NODE ACCESS (%s)\n", id);

        /* print the relevant fields */
        if (var != null) var.PrintMe();
		if (expressions != null) expressions.PrintMe();

        /**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("AST\nACCESS(%s)\n", id));

                
        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
        if (expressions != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,expressions.SerialNumber);

    }
}