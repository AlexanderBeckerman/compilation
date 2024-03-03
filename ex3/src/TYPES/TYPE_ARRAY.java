package TYPES;
/* type of declared arrays in global scope */
public class TYPE_ARRAY extends TYPE{

    public TYPE dataType;

    public TYPE_ARRAY(String name, TYPE dataType)
    {
        this.name = name;
        this.dataType = dataType;
    }
}
