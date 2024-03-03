package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

public class AST_ACCESS extends AST_Node
{
    public AST_VAR var;
    public String id;
    public AST_EXP_LIST expressions;
    /******************/
	/* CONSTRUCTOR(S) */
	/******************/

    public AST_ACCESS(String id, AST_EXP_LIST expressions, AST_VAR var)
    {
        
        /******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
        SerialNumber = AST_Node_Serial_Number.getFresh();
        
        
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
        if (expressions == null)
        {
            if (var == null)
            {
                System.out.print("======================access -> ID LPAREN RPAREN\n");
            }
            else
                System.out.print("======================access -> var DOT ID LPAREN RPAREN\n");
        }
        else if (var == null)
            System.out.print("======================access -> ID LPAREN expList RPAREN\n");
        else
            System.out.print("======================access -> var DOT ID LPAREN expList RPAREN\n");


        /*******************************/
		/* COPY INPUT DATA MENBERS ... */
		/*******************************/
        this.var = var;
        this.id = id;
        this.expressions = expressions;
    }

    /*************************************************/
	/* The printing message for a this node */
	/*************************************************/
    public void PrintMe()
    {

        /*************************************/
		/* AST NODE TYPE = AST NODE ACCESS   */
		/*************************************/
        System.out.format("AST NODE ACCESS (%s)\n", id);

        /* print the relevant fields */
        if (var != null) var.PrintMe();
		if (expressions != null) expressions.PrintMe();

        /**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
        AST_GRAPHVIZ.getInstance().logNode(
                SerialNumber,
                String.format("AST\nACCESS(%s)\n", id));

                
        /****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
        if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
        if (expressions != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,expressions.SerialNumber);

    }

    public TYPE SemantMe(){
        SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
        TYPE_LIST expected_args;
        TYPE_LIST given_args;
        TYPE var_type;
        TYPE_FUNCTION func_type;
        TYPE id_type;
        TYPE var_class;

        if (var == null)
        { // if it's not a class function call
            id_type = table.find(this.id);
            if( id_type == null || !id_type.isFunction())
            {
                throw new LineError(lineNumber);
            }
            func_type = (TYPE_FUNCTION) id_type;
            expected_args = func_type.params;
            if (expected_args != null && expressions == null){
                throw new LineError(lineNumber); // Function with name id expects arguments but none were given
            }
            given_args = (TYPE_LIST) expressions.SemantMe(); // EXP_LIST SemantMe will return a list of the function argument types
            if (!TYPE_LIST.compareLists(given_args, expected_args)){ // check if the given arguments match the expected arguments to the func
                throw new LineError(lineNumber);
            }
        }
        else // else its a class function call
        {
            var_type = var.SemantMe();
            if (var_type == null || !var_type.isClassInstance()){
                throw new LineError(lineNumber); // if the given var isnt declared yet or is not a class instance
            }
            var_class = (TYPE_CLASS_INSTANCE) var_type;
            func_type = (TYPE_FUNCTION) ((TYPE_CLASS_INSTANCE)var_class).ctype.findClassVarDec(id);
            if (func_type == null || !func_type.isFunction()){
                throw new LineError(lineNumber); // could not find function or is not a function
            }
            expected_args = func_type.params;
            if (expected_args != null && expressions == null){
                throw new LineError(lineNumber); // Function with name id expects arguments but none were given
            }
            given_args = (TYPE_LIST) expressions.SemantMe(); // EXP_LIST SemantMe will return a list of the function argument types
            if (!TYPE_LIST.compareLists(given_args, expected_args)){ // check if the given arguments match the expected arguments to the func
                throw new LineError(lineNumber);
            }
        }   

               
        return func_type.returnType;
    }

}