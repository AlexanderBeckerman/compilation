package AST;

import TYPES.*;
import MAIN.LineError;
import SYMBOL_TABLE.*;

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

    public TYPE SemantMe(boolean inClass, TYPE_CLASS class_type)
	{
		SYMBOL_TABLE table = SYMBOL_TABLE.getInstance();
		TYPE_FUNCTION func_type;
		TYPE_LIST func_params = null;

		TYPE find_type = table.find(id);
		if (table.checkScopeDec(id)){
			// if we are a class method and a variable/func/something with same name already exists
			System.out.format(">> ERROR [%d:%d] function name %s already exists in scope!\n", lineNumber, charPos, id);
			throw new LineError(lineNumber);
		}
		find_type = t.SemantMe();
		if (find_type == null || (!(find_type instanceof TYPE_CLASS) && !(find_type instanceof TYPE_ARRAY) && !(find_type instanceof TYPE_INT) 
			&& !(find_type instanceof TYPE_STRING) && !(find_type instanceof TYPE_VOID))){
				System.out.format(">> ERROR [%d:%d] invalid or non existing function type!\n", lineNumber, charPos);
				throw new LineError(lineNumber); // if no such return type exists or its not an array/class/int/string/void we return error
			}
		func_type = new TYPE_FUNCTION(find_type, id, null);
		table.enter(id, func_type);
		table.beginScope();
		if (fl != null){
			func_params = fl.SemantMe();
		}
		func_type.params = func_params;
		sl.SemantMe((TYPE_FUNCTION)func_type);
		table.endScope();

		if (inClass && class_type != null){
			TYPE prev_var = class_type.findClassVariable(id);
			TYPE prev_method = class_type.findClassMethod(id);
			if (prev_var != null){
				// defining a method that shadows a previously defined variable is illegal
				System.out.format(">> ERROR [%d:%d] trying to define a method that shadows a variable with the same name - %s!\n", lineNumber, charPos, id);
				throw new LineError(lineNumber);
			}
			if (prev_method != null && prev_method instanceof TYPE_FUNCTION && !TYPE_FUNCTION.isOverriding((TYPE_FUNCTION)prev_method, func_type)){
				// if we have a previously defined method with the same name and the new function isnt overriding but overloading
				System.out.format(">> ERROR [%d:%d]  previously defined method with the same name and the new function isnt overriding but overloading\n", lineNumber, charPos);
				throw new LineError(lineNumber);
			}
		}


		return func_type;
	}

}