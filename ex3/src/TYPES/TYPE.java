package TYPES;

public abstract class TYPE
{
	/******************************/
	/*  Every type has a name ... */
	/******************************/
	public String name;

	/*************/
	/* isClass() */
	/*************/
	public boolean isClass(){ return false;}

	/*************/
	/* isArray() */
	/*************/
	public boolean isArray(){ return false;}

	public boolean isFunction(){ return false;}

	public boolean isClassInstance(){return false; }

	public boolean isArrayInstance(){ return false; }

	public static boolean areMatchingTypes(TYPE t1, TYPE t2)
	{
		/* TODO: IMPLEMENT LOGIC FOR CHECKING IF TYPES ARE MATCHING */
		return true;
	}
}
