package AST;

public class AST_TYPE extends AST_Node
{
    int type;
    public final int INT = 0;
    public final int STRING = 1;
    public final int VOID = 2;
    public String value;


    /******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_TYPE(int type)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        this.type = type;
        String stringType = "";
        switch (type) {
            case INT:
                stringType = "INT";
                break;
            case VOID:
                stringType = "VOID";
                break;
            case STRING:
                stringType = "STRING";
        }

        System.out.format("====================== type -> %s\n", stringType);

	}

	public AST_TYPE(String value)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.format("====================== type -> ID(%s)\n", value);

        this.value = value;
	}


	/*************************************************/
	/* The printing message for a  AST node */
	/*************************************************/
	public void PrintMe()
	{
        String stringType = "";

        switch (type) {
            case INT:
                stringType = "INT";
                break;
            case VOID:
                stringType = "VOID";
                break;
            case STRING:
                stringType = "STRING";
        }

		/*************************************/
		/* AST NODE TYPE = AST NODE            */
		/*************************************/
		System.out.format("AST NODE TYPE %s\n", stringType);
		
        if(value == null)
        {
            /***************************************/
            /* PRINT Node to AST GRAPHVIZ DOT file */
            /***************************************/
            AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("TYPE_%s", stringType));
        }
        else
        {
            /**************************************/
            /* PRINT Node to AST GRAPHVIZ DOT file */
            /***************************************/
            AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("TYPE_ID(%s)", value));
        }
	}
}
