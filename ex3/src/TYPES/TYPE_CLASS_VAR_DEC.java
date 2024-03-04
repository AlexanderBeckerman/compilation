package TYPES;
/* describes a type of a class attribute. can be a simple var(int x, Person p ...) or a function */
public class TYPE_CLASS_VAR_DEC extends TYPE {
    
    public TYPE t;
    // public TYPE_LIST funcArgs; // if its a simple var and not a function, this will stay null

    public TYPE_CLASS_VAR_DEC(TYPE t, String name){
        this.t = t;
        // this.funcArgs = funcArgs;
        this.name = name;
    }





}
