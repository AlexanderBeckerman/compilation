package AST;

public class AST_FUNC_NODE extends AST_Node
{
    public AST_TYPE t;
    public String id;

    public AST_FUNC_NODE(AST_TYPE t, String name){

        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.format("====================== funcNode -> type ID( %s )\n",name);

        this.t = t;
        this.id = name;
    }

    public void PrintMe(){

        System.out.format("AST NODE FUNC NODE( %s )\n", id);
        t.PrintMe();
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("FUNC\nNODE\n(%s)",id));
        if (t != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, t.SerialNumber);
    }
}