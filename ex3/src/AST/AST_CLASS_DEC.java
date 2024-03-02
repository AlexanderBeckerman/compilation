package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_CLASS_DEC extends AST_Node{
    public String id1, id2;
    public AST_CFIELD_LIST cfieldList;

    public AST_CLASS_DEC(String id1, AST_CFIELD_LIST clist) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.id1 = id1;
        if (clist != null){
            cfieldList = clist;
            System.out.print("====================== classDec -> CLASS ID LBRACE cfieldList RBRACE\n");
        }
    }
    public AST_CLASS_DEC(String id1, String id2, AST_CFIELD_LIST clist) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.id1 = id1;
        this.id2 = id2;
        if (clist != null){
            cfieldList = clist;
            System.out.print("====================== classDec -> CLASS ID EXTENDS ID LBRACE cfieldList RBRACE\n");
        }
    }

    public void PrintMe(){
        if (id2 == null){
            System.out.format("AST NODE CLASS DEC(%s)", id1);
        }
        else{
            System.out.format("AST NODE CLASS DEC(%s) EXTENDS CLASS(%s)", id1, id2);
        }
        cfieldList.PrintMe();
        if (id2 == null){
            AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS\nDEC(%s)", id1));
        }
        else {
            AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS DEC(%s)\n EXTENDS (%s) ", id1, id2));
        }
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cfieldList.SerialNumber);
    }

    	public TYPE SemantMe()
	{	
		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Data Members */
		/***************************/
        if (id2 == null)
        {
            TYPE_CLASS t = new TYPE_CLASS(null,name,data_members.SemantMe());
        }

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [4] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(name,t);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
}