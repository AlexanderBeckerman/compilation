package TYPES;

public class TYPE_CLASS extends TYPE
{
	/*********************************************************************/
	/* If this class does not extend a father class this should be null  */
	/*********************************************************************/
	public TYPE_CLASS father;

	/**************************************************/
	/* Gather up all data members in one place        */
	/* Note that data members coming from the AST are */
	/* packed together with the class methods         */
	/**************************************************/
	public TYPE_LIST funcList;
	public TYPE_LIST varList; 
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name, TYPE_LIST funcList, TYPE_LIST varList)
	{
		this.name = name;
		this.father = father;
		this.funcList = funcList;
		this.varList = varList;
	}
}
