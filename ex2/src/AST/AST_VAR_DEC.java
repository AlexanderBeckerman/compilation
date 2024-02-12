package AST;

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

}