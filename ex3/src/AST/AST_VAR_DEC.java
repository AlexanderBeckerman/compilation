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
     * @param inClass: true iff the declaration is within a class, meaning it was reduced to a cfield by the grammer.
     * @return: The static type of the declared variable.
     */
    public TYPE SemantMe(boolean inClass)
	{
		TYPE t;
	
		t = this.t.SemantMe(); // The type's existance is checked within t's SemantMe.

        if (t == TYPE_VOID.getInstance())
		{
			System.out.format(">> ERROR [%d:%d] You cannot declare a variable as void.\n", lineNumber, charPos);
            throw new LineError(this.lineNumber);
		}

		if (SYMBOL_TABLE.getInstance().checkScopeDec(this.id)) // Check that id isn't redeclared in the scope.
		{
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n", lineNumber, charPos, id);
            throw new LineError(this.lineNumber);
		}

        if (inClass) { // Checking the class declaration rules.
            if (newExp != null || (exp != null && !(exp instanceof AST_EXP_INT || exp instanceof AST_EXP_STRING || exp instanceof AST_EXP_NIL))) {
                System.out.format(">> ERROR [%d:%d] data member inside a class can be initialized only with a constant value.\n", this.lineNumber, this.charPos);
                throw new LineError(this.lineNumber);
            }
        }

        TYPE assignedType = null;
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

        /*
         * This should not be like this, instead, all instance's types should be the same, and there should not be more than one TYPE instance for each type.
         * But I wrote it like this so code won't get thrown away.
         */
        TYPE instanceType = null;
        if (t instanceof TYPE_CLASS)
        {
            instanceType = new TYPE_CLASS_INSTANCE("This is fucking stupid", (TYPE_CLASS) t);
        }
        else if(t instanceof TYPE_ARRAY)
        {
            instanceType = new TYPE_ARRAY_INSTANCE("I fucking hate this", (TYPE_ARRAY) t);
        }
        else if((t instanceof TYPE_STRING) || (t instanceof TYPE_INT))
        {
            instanceType = new TYPE_PRIMITIVE_INSTANCE("Why did I start this degree", t);
        }
    


		/* Enter the Function Type to the Symbol Table */
		SYMBOL_TABLE.getInstance().enter(id, instanceType);

		// Returning the Type for usage in the SemantMe function of AST_CFIELD
		return t;
	}
}