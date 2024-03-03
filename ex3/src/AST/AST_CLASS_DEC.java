package AST;

import MAIN.LineError;
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
        SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
        TYPE fatherType;
        TYPE_CLASS fatherClassType = null;
        TYPE_LIST clistTypes;
        TYPE_CLASS classType;

        if (table.getScopeDepth() != 0 || table.find(id1) != null){
            throw new LineError(lineNumber); // if not global scope or we already have class with same name
        }
        if (id2 != null)
        {
            fatherType = table.find(id2);
            if (fatherType == null || !fatherType.isClass()){
                throw new LineError(lineNumber); // no such extendable class exists or its not a class
            }
            fatherClassType = (TYPE_CLASS)fatherType;
        }

        table.beginScope();
        classType = new TYPE_CLASS(fatherClassType, id1, cfieldList.SemantMe());
        table.endScope();
        table.enter(id1, classType);
        return classType;
        
	}
}