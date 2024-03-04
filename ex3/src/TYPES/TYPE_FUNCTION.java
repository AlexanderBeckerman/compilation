package TYPES;

public class TYPE_FUNCTION extends TYPE
{
	/***********************************/
	/* The return type of the function */
	/***********************************/
	public TYPE returnType;

	/*************************/
	/* types of input params */
	/*************************/
	public TYPE_LIST params;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_FUNCTION(TYPE returnType,String name,TYPE_LIST params)
	{
		this.name = name;
		this.returnType = returnType;
		this.params = params;
	}

	public boolean isFunction(){ return true;}

	public static boolean isOverriding(TYPE_FUNCTION f1, TYPE_FUNCTION f2)
	{
		if(!(f1.name.equals(f2.name) && f1.returnType.name.equals(f2.returnType.name)))
		{
			// if we have a different func name or func return type
			return false;
		}
		TYPE_LIST curr_f1_params = f1.params;
		TYPE_LIST curr_f2_params = f2.params;
		while (curr_f1_params != null && curr_f2_params != null)
		{
			TYPE curr_f1_param = curr_f1_params.head;
			TYPE curr_f2_param = curr_f2_params.head;
			if (curr_f1_param == null || curr_f2_param == null)
			{
				if (curr_f1_param == null && curr_f2_param == null)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			if (!(curr_f1_param.name.equals(curr_f2_param.name)))
			{
				return false;
			}
			curr_f1_params = curr_f1_params.tail;
			curr_f2_params = curr_f2_params.tail;
		}
		if (curr_f1_params != null || curr_f2_params != null)
		{
			// different parameters lengths
			return false;
		}
		return true;
	}
}
