package AST;
import SYMBOL_TABLE.*;
import TYPES.*;

public class AST_CFIELD_LIST extends AST_Node
{

    /****************/
    /* DATA MEMBERS */
    /****************/
    public AST_CFIELD head;
    public AST_CFIELD_LIST tail;


    /******************/
    /* CONSTRUCTOR(S) */
    /******************/
    public AST_CFIELD_LIST(AST_CFIELD head,AST_CFIELD_LIST tail)
    {
        /******************************/
        /* SET A UNIQUE SERIAL NUMBER */
        /******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();

        /***************************************/
        /* PRINT CORRESPONDING DERIVATION RULE */
        /***************************************/
        if (tail != null) System.out.print("====================== cFieldList -> cField cFieldList\n");
        if (tail == null) System.out.print("====================== cFieldList -> cField      \n");

        /*******************************/
        /* COPY INPUT DATA NENBERS ... */
        /*******************************/
        this.head = head;
        this.tail = tail;
    }

    /******************************************************/
    /* The printing message for a statement list AST node */
    /******************************************************/
    public void PrintMe()
    {
        /**************************************/
        /* AST NODE TYPE = AST STATEMENT LIST */
        /**************************************/
        System.out.print("AST NODE CFIELD LIST\n");

        /*************************************/
        /* RECURSIVELY PRINT HEAD + TAIL ... */
        /*************************************/
        if (head != null) head.PrintMe();
        if (tail != null) tail.PrintMe();

        /**********************************/
        /* PRINT to AST GRAPHVIZ DOT file */
        /**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                "CFIELD\nLIST\n");

        /****************************************/
        /* PRINT Edges to AST GRAPHVIZ DOT file */
        /****************************************/
        if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
        if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
    }
    public TYPE_LIST SemantMeMethods(TYPE_CLASS class_type){

        SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
        TYPE head_type = null;
        if (head != null){
            if (head.funcDec != null)
            {
                head_type = head.SemantMe(class_type);
                class_type.methods.append(head_type);
                table.enter(head_type.name, head_type);
            }
        }
        if (tail != null){
            tail.SemantMeMethods(class_type);
        }
        return class_type.methods;
    } 
    public TYPE_LIST SemantMeVariables(TYPE_CLASS class_type){
        SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
        TYPE head_type = null;
        if (head != null){
            if (head.varDec != null)
            {
                head_type = head.SemantMe(class_type);
                class_type.variables.append(head_type);
                table.enter(head_type.name, head_type);
            }
        }
        if (tail != null){
            tail.SemantMeVariables(class_type);
        }
        return class_type.variables;
    }
}