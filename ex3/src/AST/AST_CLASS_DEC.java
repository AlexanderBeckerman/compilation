package AST;

import MAIN.LineError;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_CLASS_DEC extends AST_Node{
    public String id1, id2;
    public AST_CFIELD_LIST cfieldList;
    private int ln;

    public AST_CLASS_DEC(String id1, AST_CFIELD_LIST clist, int line) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.id1 = id1;
        this.ln = line;
        if (clist != null){
            cfieldList = clist;
            System.out.print("====================== classDec -> CLASS ID LBRACE cfieldList RBRACE\n");
        }
    }
    public AST_CLASS_DEC(String id1, String id2, AST_CFIELD_LIST clist, int line) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        this.id1 = id1;
        this.id2 = id2;
        this.ln = line;

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
        TYPE father_type;
        TYPE_CLASS father_class_type = null;
        TYPE_CLASS class_type;

        if (table.getScopeDepth() != 0 || table.find(id1) != null){
            System.out.format(">> ERROR [%d:%d] trying to declare %s class not in global scope or such class name already exists!\n", ln, charPos, this.id1);
            throw new LineError(this.ln); // if not global scope or we already have class with same name
        }
        if (id2 != null)
        {
            father_type = table.find(id2);
            if (father_type == null || !(father_type instanceof TYPE_CLASS)){
                System.out.format(">> ERROR [%d:%d] didnt find a class with name %s\n", ln, charPos, this.id2);
                throw new LineError(this.ln); // no such extendable class exists or its not a class
            }
            father_class_type = (TYPE_CLASS)father_type;
        }

        class_type = new TYPE_CLASS(father_class_type, id1, new TYPE_LIST(null, null), new TYPE_LIST(null, null));
        table.enter(id1, class_type);
        table.beginScope();
        table.cls = class_type;
        cfieldList.SemantMe(class_type);
        table.cls = null;
        table.endScope();
        
        
        return class_type;
        
	}
}