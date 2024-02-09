package AST;


public class AST_PROGRAM extends AST_Node {

    public AST_DEC_LIST decList;

    public AST_PROGRAM(AST_DEC_LIST decList) {
        SerialNumber = AST_Node_Serial_Number.getFresh();

        if (decList != null) System.out.print("====================== program -> decs\n");

        this.decList = decList;
    }

    public void PrintMe()
    {
        /*********************************/
        /* AST NODE TYPE = AST PROGRAM */
        /*********************************/
        System.out.print("AST NODE PROGRAM\n");

        /**********************************************/
        /* RECURSIVELY PRINT ... */
        /**********************************************/
        if (decList != null) decList.PrintMe();
//        System.out.format("FIELD NAME( %s )\n",fieldName);

        /***************************************/
        /* PRINT Node to AST GRAPHVIZ DOT file */
        /***************************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "PROGRAM\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (decList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,decList.SerialNumber);
    }


}