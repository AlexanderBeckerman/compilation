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
	 * @return true if every instance_type object is an instance of the specified type or inherits from it; otherwise, false.
	 * 
	 * @note Type matching for arries is defined by both types being the same,
	 * which means we need to be cautious for AST nodes representing non-terminals that appear on the left-hand side of a derivation
	 * rule where's the right hand sides contains an newExp, and that's because for example for the next code:
	 * 		array idk = int[];
	 * 		idk lol := new int[1];
	 * simply checking that areMatchingTypes(typeOf(idk lol), typeOf(new int[1])) won't suffice (There's no way to extract from "new int[1]"" that that's a match.).
	 * Before changing this design decision please talk to me, alot of thaught has been put to this - Alon.
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
		
		// nil is an instance of every class and array types, and only of those.
		if (instance_type == TYPE_NIL.getInstance()) {
			return ((super_type instanceof TYPE_CLASS) || (super_type instanceof TYPE_ARRAY));
		}

		// If the instance_type is not a class type, compare it directly with the super_type
		return instance_type == super_type;
	}
}