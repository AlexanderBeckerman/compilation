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

	/**
	 * Checks if an object of a specific type is an instance of another type.
	 *
	 * @param instance_type The type of the object to be checked.
	 * @param super_type    The type to check against as a potential superclass.
	 * @return True if every instance_type object is an instance of the specified type or inherits from it; otherwise, false.
	 */
	public static boolean areMatchingTypes(TYPE instance_type, TYPE super_type) {

		
		if (instance_type instanceof TYPE_CLASS) {
			TYPE_CLASS temp = (TYPE_CLASS) instance_type;
			// Traverse the class hierarchy using the father reference
			while (temp != null) {
				if (super_type == temp) {
					return true;
				}
				temp = temp.father;
			}
			// If the super_type is not found in the class hierarchy, return false
			return false;
		}
		
		// nil is an instane of every class type.
		if (instance_type == TYPE_NIL.getInstance() && super_type instanceof TYPE_CLASS) {
			return true;
		}

		// If the instance_type is not a class type, compare it directly with the super_type
		return instance_type == super_type;
	}
}
