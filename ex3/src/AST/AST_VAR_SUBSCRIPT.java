package AST;

import MAIN.LineError;
import TYPES.*;

public class AST_VAR_SUBSCRIPT extends AST_VAR
{
	public AST_VAR var;
	public AST_EXP subscript;
	private final int MIN_INDX = 0;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_VAR_SUBSCRIPT(AST_VAR var,AST_EXP subscript)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== var -> var [ exp ]\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.subscript = subscript;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"SUBSCRIPT\nVAR\n...[...]");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var       != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (subscript != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,subscript.SerialNumber);
	}

	/**
     * The SemantMe implementation of AST_VAR_SUBSCRIPT.
	 * 
     * @return: The static type of array's cell contents.
     */
    public TYPE SemantMe()
	{
		TYPE varType = this.var.SemantMe();
		TYPE argType = this.subscript.SemantMe(); 

        if (!(varType instanceof TYPE_ARRAY)) {
            System.out.format(">> ERROR [%d:%d] %s is not an array.\n", this.lineNumber, this.charPos, varType.name);
            throw new LineError(this.lineNumber);
        }

		if (argType != TYPE_INT.getInstance()) {
            System.out.format(">> ERROR [%d:%d] Refrencing an array cell by a value that is not an int.\n", this.lineNumber, this.charPos);
            throw new LineError(this.lineNumber);
        }

		// If the index refrenced is constant it should not be negative.
		if ((subscript instanceof AST_EXP_INT) && (((AST_EXP_INT)subscript).value < MIN_INDX)) {
            System.out.format(">> ERROR [%d:%d] Refrencing an array cell by a negative value.\n", this.lineNumber, this.charPos);
            throw new LineError(this.lineNumber);
        }

		// Returning the static type of the field.
		return ((TYPE_ARRAY) varType).dataType;
	}
}
