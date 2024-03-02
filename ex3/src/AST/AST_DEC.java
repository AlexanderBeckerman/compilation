package AST;

import TYPES.*;

public class AST_DEC extends AST_Node{

    public AST_VAR_DEC varDec;
    public AST_FUNC_DEC funcDec;
    public AST_CLASS_DEC classDec;
    public AST_ARRAY_TYPE_DEF arrayTypedef;


    public AST_DEC(AST_VAR_DEC varDec){
        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.print("====================== dec -> varDec\n");
        this.varDec = varDec;
    }
    public AST_DEC(AST_FUNC_DEC funcDec){
        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.print("====================== dec -> funcDec\n");
        this.funcDec = funcDec;
    }
    public AST_DEC(AST_CLASS_DEC classDec){
        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.print("====================== dec -> classDec\n");
        this.classDec = classDec;
    }
    public AST_DEC(AST_ARRAY_TYPE_DEF arrayTypedef){
        SerialNumber = AST_Node_Serial_Number.getFresh();
        System.out.print("====================== dec -> arrayTypedef\n");
        this.arrayTypedef = arrayTypedef;
    }

    public void PrintMe()
    {
        System.out.print("AST NODE DEC\n");

        if (varDec != null){
            varDec.PrintMe();
            log_help();
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
        }
        if (funcDec != null){
            funcDec.PrintMe();
            log_help();
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcDec.SerialNumber);
        }
        if (classDec != null){
            classDec.PrintMe();
            log_help();
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,classDec.SerialNumber);
        }
        if (arrayTypedef != null){
            arrayTypedef.PrintMe();
            log_help();
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,arrayTypedef.SerialNumber);
        }
    }
    private void log_help(){
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "DEC\n");
    }

    public TYPE SemantMe()
	{
		return null;
	}
}
