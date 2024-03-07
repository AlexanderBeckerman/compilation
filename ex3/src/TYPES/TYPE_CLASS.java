package TYPES;

public class TYPE_CLASS extends TYPE_TYPE
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
	public TYPE_LIST methods;
	public TYPE_LIST variables;
	
	/****************/
	/* CTROR(S) ... */
	/****************/
	public TYPE_CLASS(TYPE_CLASS father,String name,TYPE_LIST methods, TYPE_LIST variables)
	{
		this.name = name;
		this.father = father;
		this.methods = methods;
		this.variables = variables;
	}

	public TYPE findClassVariable(String attrName)
	{
		TYPE_CLASS curr_class = this;
		while(curr_class != null)
		{ // loop over the classes in the inheritance tree looking for the given attribute
			TYPE_LIST curr_attrs = curr_class.variables;
			while (curr_attrs != null)
			{ // loop over the attributes of the current class we are searching
				TYPE_CLASS_VAR_DEC curr_attr = (TYPE_CLASS_VAR_DEC) curr_attrs.head;
				
				if (curr_attr != null && curr_attr.name.equals(attrName))
				{
					return curr_attr.t; // return the type of the found attribute
				}
				curr_attrs = curr_attrs.tail;	
			}
			curr_class = curr_class.father;
		}
		return null;
	}

	public TYPE findClassMethod(String attrName)
	{
		TYPE_CLASS curr_class = this;
		while(curr_class != null)
		{ // loop over the classes in the inheritance tree looking for the given attribute
			TYPE_LIST curr_attrs = curr_class.methods;
			while (curr_attrs != null)
			{ // loop over the attributes of the current class we are searching
				TYPE_FUNCTION curr_attr = (TYPE_FUNCTION) curr_attrs.head;
				if (curr_attr != null && curr_attr.name.equals(attrName))
				{
					return ((TYPE_FUNCTION)curr_attr); // return the type of the found attribute
				}
				curr_attrs = curr_attrs.tail;	
			}
			curr_class = curr_class.father;
		}
		return null;
	}

	
}
