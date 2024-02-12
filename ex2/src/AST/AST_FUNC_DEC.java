package AST;

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

}