package AST;

import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_CFIELD extends AST_Node
{

    public AST_VAR_DEC varDec;
    public AST_FUNC_DEC funcDec;

    public AST_CFIELD(AST_VAR_DEC vard){

        SerialNumber = AST_Node_Serial_Number.getFresh();
        if (vard != null){
            System.out.print("====================== cField -> varDec\n");
        }
        this.varDec = vard;
    }
    public AST_CFIELD(AST_FUNC_DEC funcd){

        SerialNumber = AST_Node_Serial_Number.getFresh();
        if (funcd != null){
            System.out.print("====================== cField -> funcDec\n");
        }
        this.funcDec = funcd;
    }
    public void PrintMe(){

        if(varDec != null){
            System.out.print("AST NODE CFIELD\n");
            varDec.PrintMe();
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    "AST NODE CFIELD");
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
        }
        else{
            System.out.print("AST NODE CFIELD\n");
            funcDec.PrintMe();
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    "AST NODE CFIELD");
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcDec.SerialNumber);
        }
    }
    
    public TYPE SemantMe(){
        if (varDec != null){
            return varDec.SemantMe(true);
        }
        else{
            return funcDec.SemantMe();
        }
    }
}