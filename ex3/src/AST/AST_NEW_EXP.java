package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

public class AST_NEW_EXP extends AST_Node {

    public AST_TYPE type;
    public AST_EXP exp;

    public AST_NEW_EXP(AST_TYPE t) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        if (t != null) {
            System.out.print("====================== newExp -> NEW type\n");
        }
        this.type = t;
    }

    public AST_NEW_EXP(AST_TYPE t, AST_EXP e) {
        SerialNumber = AST_Node_Serial_Number.getFresh();
        if (t != null && e != null) {
            System.out.print("====================== newExp -> NEW type LBRACK exp RBRACK\n");
        }
        this.type = t;
        this.exp = e;
    }

    public void PrintMe() {
        if (type != null && exp == null) {
            System.out.print("AST NODE NEWEXP\n");
            type.PrintMe();
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    "AST NODE NEWEXP");
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
        } else if (type != null && exp != null) {
            System.out.print("AST NODE NEWEXP\n");
            type.PrintMe();
            exp.PrintMe();
            AST_GRAPHVIZ.getInstance().logNode(
                    SerialNumber,
                    "AST NODE NEWEXP");
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, type.SerialNumber);
            AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
        }
    }

    public TYPE SemantMe(){


        TYPE this_type = type.SemantMe();

        if (this_type == null){
            // if no such type exists
            throw new LineError(lineNumber);
        }
       if (exp == null){
            if (!(this_type instanceof TYPE_CLASS)){
                throw new LineError(lineNumber);
            }
       }
        else
        {
            if (exp.SemantMe() != TYPE_INT.getInstance() || (exp instanceof AST_EXP_INT && ((AST_EXP_INT)exp).value <= 0)){
                throw new LineError(lineNumber);
            }
            return new TYPE_ARRAY(null, this_type);
        }
        return this_type;
       }

}
