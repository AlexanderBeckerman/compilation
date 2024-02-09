package AST;

public class AST_CLASS_DEC {
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
            System.out.print("====================== classDec -> CLASS ID LBRACE cfieldList RBRACE\n");
        }
    }

    public void PrintMe(){
        if (id2 == null){
            System.out.format("AST NODE CLASS DEC(%s)", id1)
        }
        else{
            System.out.format("AST NODE CLASS DEC(%s) EXTENDS CLASS(%s)", id1, id2)
        }
        cfieldList.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS\nDEC(%s)", id1));
        AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cfieldList.SerialNumber);
    }
}