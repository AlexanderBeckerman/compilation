package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

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

    public TYPE SemantMe(){

        TYPE arg_t = t.SemantMe();
        TYPE finalType = arg_t;
        SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
        if (table.checkScopeDec(id)) {
            System.out.format(">> ERROR [%d:%d] %s is already declared in the scope!\n", lineNumber, charPos, id);
            throw new LineError(lineNumber);
        }
        if (table.find(arg_t.name) == null || arg_t instanceof TYPE_FUNCTION || arg_t instanceof TYPE_INSTANCE || arg_t instanceof TYPE_VOID){
            // if the type doesnt exist
            System.out.format(">> ERROR [%d:%d] could not find type with id %s!\n", lineNumber, charPos, id);
            throw new LineError(lineNumber);
        }
        if (arg_t instanceof TYPE_ARRAY){ // type func(int[] arr){...}
            finalType = new TYPE_ARRAY_INSTANCE(id, (TYPE_ARRAY)arg_t);
        }
        else if(arg_t instanceof TYPE_CLASS){ // type func(Person p){...}
            finalType = new TYPE_CLASS_INSTANCE(id, (TYPE_CLASS)arg_t);
        }

        else if((arg_t instanceof TYPE_STRING) || (arg_t instanceof TYPE_INT))
        {
            finalType = new TYPE_PRIMITIVE_INSTANCE(id, arg_t);
        }
        table.enter(id, finalType);
        return arg_t;

    }
}