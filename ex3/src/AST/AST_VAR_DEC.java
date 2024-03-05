package AST;

import SYMBOL_TABLE.SYMBOL_TABLE;
import TYPES.*;
import MAIN.*;

public class AST_VAR_DEC extends AST_Node{


    AST_TYPE t;
    String id;
    AST_EXP exp;
    AST_NEW_EXP newExp;

    public AST_VAR_DEC(AST_TYPE t, String id) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.t = t;
        this.id = id;
        System.out.print("====================== varDec -> type ID SEMICOLON\n");
    }

    public AST_VAR_DEC(AST_TYPE t, String id, AST_EXP exp) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.t = t;
        this.id = id;
        this.exp = exp;
        System.out.print("====================== varDec -> type ID ASSIGN exp SEMICOLON\n");
    }

    public AST_VAR_DEC(AST_TYPE t, String id, AST_NEW_EXP newExp) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.t = t;
        this.id = id;
        this.newExp = newExp;
        System.out.print("====================== varDec -> type ID ASSIGN newExp SEMICOLON\n");
    }

    public void PrintMe() {
        /************************************/
        /* AST NODE TYPE = EXP VAR AST NODE */
        /************************************/
        System.out.format("AST NODE VAR DEC( %s )\n", id);

        t.PrintMe();
        if (exp != null) {
            exp.PrintMe();
        } else if (newExp != null) {
            newExp.PrintMe();
        }


        /*********************************/
        /* Print to AST GRAPHIZ DOT file */
        /*********************************/
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VAR\nDEC(%s)", id));

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
        if (exp != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
        }
        if (newExp != null) {
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber);
        }
    }

    /**
     * The SemantMe implementation of AST_VAR_DEC.
     * 
     * @param inClass: true iff the declaration is within a class, meaning it was derived to a cfield by the grammer.
     * @return: The static type of the declared variable.
     */
    public TYPE SemantMe(boolean inClass)
	{
		TYPE t;
	
		/****************************/
		/* [1] Check if type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(this.t.type);
		if (t == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n", lineNumber, charPos, this.t.type);
			throw new LineError(this.lineNumber);
		}
		
		/****************************************************************************************/
		/* [2] Check that the name of the variable does NOT exist in the scope.                 */
		/****************************************************************************************/
		if (SYMBOL_TABLE.getInstance().checkScopeDec(this.id))
		{
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n", lineNumber, charPos, id);
            throw new LineError(this.lineNumber);
		}

        /********************************************************************************************************************/
		/* [3] If this is a class member declaration then newExp muse be null, and exp must be constant if it's isn't null. */
		/********************************************************************************************************************/
        if (inClass) {
            if (newExp != null || (exp != null && !(exp instanceof AST_EXP_INT || exp instanceof AST_EXP_STRING || exp instanceof AST_EXP_NIL))) {
                System.out.format(">> ERROR [%d:%d] data member inside a class can be initialized only with a constant value.\n", this.lineNumber, this.charPos);
                throw new LineError(this.lineNumber);
            }
        }

        
        /*******************************************************************************/
		/* [4] Check that if a value was assigned, its type matches the type declared. */
		/*******************************************************************************/
        TYPE assignedType;
        if (exp != null) {
            assignedType = exp.SemantMe();
        }
        else if(newExp != null)
        {
            assignedType = newExp.SemantMe();
        }
        if ((assignedType != null) && (!TYPE.areMatchingTypes(assignedType, t))) {
            System.out.format(">> ERROR [%d:%d] assigment of %s to a %s is illegal.\n", this.lineNumber, this.charPos, assignedType.name, this.t.type);
            throw new LineError(lineNumber);
        }

		/***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(id,t);

		/***************************************************************************/
		/* [6] Returning the Type for usage in the SemantMe function of AST_CFIELD */
		/***************************************************************************/
		return t;
	}
}