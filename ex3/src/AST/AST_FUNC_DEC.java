package AST;

import AST_TYPE_NAME_LIST;

public class AST_FUNC_DEC extends AST_Node
{
    public AST_TYPE t;
    public String id;
    public AST_FUNC_LIST fl;
    public AST_STMT_LIST sl;

    public AST_FUNC_DEC(AST_TYPE t, String name, AST_STMT_LIST sl){

        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.format("====================== funcDec -> type ID( %s ) LPAREN RPAREN LBRACE stmtList RBRACE\n", name);

        this.t = t;
        this.id = name;
        this.sl = sl;
    }

    public AST_FUNC_DEC(AST_TYPE t, String name, AST_FUNC_LIST fl, AST_STMT_LIST sl){

        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.format("====================== funcDec -> type ID( %s ) LPAREN funcList RPAREN LBRACE stmtList RBRACE\n", name);

        this.t = t;
        this.id = name;
        this.fl = fl;
        this.sl = sl;
    }


    public void PrintMe()
    {
        System.out.format("AST NODE FUNC DEC( %s )\n", id);
        if (t != null) t.PrintMe();
        if (fl != null) fl.PrintMe();
        if (sl != null) sl.PrintMe();

        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("FUNC DEC( %s )\n", id));

        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,t.SerialNumber);
        if (fl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,fl.SerialNumber);
        if (sl != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,sl.SerialNumber);
    }

    public TYPE SemantMe()
	{
		TYPE t;
		TYPE returnType = null;
		TYPE_LIST type_list = null;

		/*******************/
		/* [0] return type */
		/*******************/
		returnType = SYMBOL_TABLE.getInstance().find(returnTypeName);
		if (returnType == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing return type %s\n",6,6,returnType);				
		}
	
		/****************************/
		/* [1] Begin Function Scope */
		/****************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Input Params */
		/***************************/
		for (AST_TYPE_NAME_LIST it = params; it  != null; it = it.tail)
		{
			t = SYMBOL_TABLE.getInstance().find(it.head.type);
			if (t == null)
			{
				System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,it.head.type);				
			}
			else
			{
				type_list = new TYPE_LIST(t,type_list);
				SYMBOL_TABLE.getInstance().enter(it.head.name,t);
			}
		}

		/*******************/
		/* [3] Semant Body */
		/*******************/
		body.SemantMe();

		/*****************/
		/* [4] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/***************************************************/
		/* [5] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name,new TYPE_FUNCTION(returnType,name,type_list));

		/*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}

}