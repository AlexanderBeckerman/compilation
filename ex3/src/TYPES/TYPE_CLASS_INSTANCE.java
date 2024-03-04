
package TYPES;
/* describes an instance of a class. for example Person p = new Person, so p type will be class instance */
public class TYPE_CLASS_INSTANCE extends TYPE{

    public TYPE_CLASS ctype; // the class type of the instance

    public TYPE_CLASS_INSTANCE(String name, TYPE_CLASS ctype){
        this.ctype = ctype;
        this.name = name;
    }

    public boolean isClassInstance(){ return true; }
    
}