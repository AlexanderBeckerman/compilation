/***********/
/* PACKAGE */
/***********/
package SYMBOL_TABLE;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*******************/
/* PROJECT IMPORTS */
/*******************/
import TYPES.*;

/****************/
/* SYMBOL TABLE */
/****************/
public class SYMBOL_TABLE
{	
	/**********************************************/
	/* The actual symbol table data structure ... */
	/**********************************************/
	/*
	 *  A stack of hash maps, each map contains the mapping of a scope from symbols to their symbol table enteries according to the scope's declarations.
	 * 	The higher a map is on the stack, the more inner is its scope, the maps are pushed and popped into and out of the stack with their beggining and ending,
	 *  respectively.
	 */
	private ArrayList<Map<String, SYMBOL_TABLE_ENTRY>> scopeStack = new ArrayList<>();
	public TYPE_CLASS cls; // This will be used by the ASTs to know what class are they defined at.
	public int top_index = 0;

	/****************************************************************************/
	/* Enter a variable, function, class type or array type to the symbol table */
	/****************************************************************************/
	public void enter(String name,TYPE t)
	{	
		/* Prepare a new symbol table entry with name, type, next and prevtop */
		SYMBOL_TABLE_ENTRY e = new SYMBOL_TABLE_ENTRY(name,t, top_index++);
		
		/*  Print Symbol Table */
		PrintMe();

		/* Add the symbol to the map at the top of scopeStack */
		scopeStack.get(scopeStack.size() - 1).put(name, e);
	}

	/* Find the inner-most scope element by the relevent declaration. */
	public TYPE find(String name)
	{
		
		/*
		 * The order of resolving is inner-most scope that is not global, then class members and functions and then global scope.
		 */
		for(int i = (scopeStack.size() - 1); i > 0; i--)
		{
			if (scopeStack.get(i).containsKey(name)) {
				return scopeStack.get(i).get(name).type;
			}
		}    

		if (cls != null) {
			TYPE temp;
			temp = cls.findClassMethod(name);
			if (temp == null) {
				temp = cls.findClassVariable(name);
				if (temp != null) {
					if (temp instanceof TYPE_CLASS)
					{
						temp = new TYPE_CLASS_INSTANCE(name, (TYPE_CLASS) temp);
					}
					else if(temp instanceof TYPE_ARRAY)
					{
						temp = new TYPE_ARRAY_INSTANCE(name, (TYPE_ARRAY) temp);
					}
					else
					{
						temp = new TYPE_PRIMITIVE_INSTANCE(name, temp);
					}
				}
			}
			if (temp != null) {
				return temp;
			}
		}

		if (scopeStack.get(0).containsKey(name)) {
			return scopeStack.get(0).get(name).type;
		}
		return null;
	}

	/***************************************************************************/
	/* begin scope = Enter the <SCOPE-BOUNDARY> element to the data structure  */
	/***************************************************************************/
	public void beginScope()
	{
		/********************************************************************/
		/* Add a new hashMap for the scope at the top of the scopeStack map */
		/********************************************************************/
		scopeStack.add(new HashMap<>());
		
		/*********************************************/
		/* Print the symbol table after every change */
		/*********************************************/
		PrintMe();
	}

	/********************************************************************************/
	/* pop the the scopeStak. 										                */
	/********************************************************************************/
	public void endScope()
	{
		/***********************************************/
		/* Remove the map at the top of the scopeStack */
		/***********************************************/
		scopeStack.remove(scopeStack.size() - 1 );

		/*********************************************/
		/* Print the symbol table after every change */		
		/*********************************************/
		PrintMe();
	}
	
	public static int n=0;
	
	public void PrintMe()
	{
		int i=0;
		String dirname="./output/";
		String filename=String.format("SYMBOL_TABLE_%d_IN_GRAPHVIZ_DOT_FORMAT.txt",n++);

		try
		{
			/***************************************/
			/* Open Graphviz text file for writing */
			/***************************************/
			PrintWriter fileWriter = new PrintWriter(dirname+filename);

			/*****************************/
			/* Write Graphviz dot prolog */
			/*****************************/
			fileWriter.print("digraph structs {\n");
			fileWriter.print("rankdir = LR\n");
			fileWriter.print("node [shape=record];\n");

			/*********************/
			/* Write list Itself */
			/*********************/
			fileWriter.print("hashTable [label=\"");
			for (i=0;i<scopeStack.size()-1;i++) { fileWriter.format("<f%d>\n%d\n|",i,i); }
			fileWriter.format("<f%d>\n%d\n\"];\n",scopeStack.size()-1,scopeStack.size()-1);
		
			/* Go over scope stack and print all symbol table enteries per scope */
			for(i = 0; i < scopeStack.size(); i++)
			{
				if (scopeStack.get(i).size() != 0) {
					/*********************************************************/
					/* Print hash table scopeStack.get(i) -> entry(i,0) edge */
					/*********************************************************/
					fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n",i,i);	
				}
				
				int j = 0;
				for(SYMBOL_TABLE_ENTRY e : scopeStack.get(i).values())
				{
					/*************************/
					/* Print entry(i,j) node */
					/*************************/
					fileWriter.format("node_%d_%d ",i,j);
					fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n",
						e.name,
						e.type.name,
						e.index);

					if (scopeStack.get(i).values().size() != j + 1) {
						/**********************************************/
						/* Print entry(i,it) -> entry(i,it.next) edge */
						/**********************************************/
						fileWriter.format(
							"node_%d_%d -> node_%d_%d [style=invis,weight=10];\n",
							i,j,i,j+1);
						fileWriter.format(
							"node_%d_%d:f3 -> node_%d_%d:f0;\n",
							i,j,i,j+1);
					}
					j++;
				}
			}
			fileWriter.print("}\n");
			fileWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static SYMBOL_TABLE instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected SYMBOL_TABLE() {}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static SYMBOL_TABLE getInstance()
	{
		if (instance == null)
		{
			/***************************/
			/* The instance itself ... */
			/***************************/
			instance = new SYMBOL_TABLE();

			instance.scopeStack.add(new HashMap<>());
			/*************************************/
			/* Enter primitive types int, string */
			/*************************************/
			instance.enter("int",   TYPE_INT.getInstance());
			instance.enter("string",TYPE_STRING.getInstance());

			/*****************************/
			/* How should we handle void */
			/*****************************/
			instance.enter("void", TYPE_VOID.getInstance());
			/***********************************/
			/* Enter library function PrintInt */
			/***********************************/
			instance.enter(
				"PrintInt",
				new TYPE_FUNCTION(
					TYPE_VOID.getInstance(),
					"PrintInt",
					new TYPE_LIST(
						TYPE_INT.getInstance(),
						null)));
			instance.enter(
				"PrintString",
				new TYPE_FUNCTION(TYPE_VOID.getInstance(),
				 "PrintString",
				  new TYPE_LIST(TYPE_STRING.getInstance(), null))
			);
			
			
		}
		return instance;
	}

	public int getScopeDepth(){
		return scopeStack.size() - 1;
	}

	/**
	 * Checks if a given symbol is declared in the current scope.
	 *
	 * @param symbol The symbol to be checked for existence in the current scope.
	 * @return {@code true} if the symbol is was declared in the current scope, {@code false} otherwise.
	 */
	public boolean checkScopeDec(String symbol)
	{
		return scopeStack.get(scopeStack.size() - 1).containsKey(symbol);
	}
}