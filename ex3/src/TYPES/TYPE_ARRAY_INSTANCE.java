package TYPES;

public class TYPE_ARRAY_INSTANCE extends TYPE{

    public TYPE_ARRAY arr_type;
    
    public TYPE_ARRAY_INSTANCE(String name, TYPE_ARRAY arr_type)
    {
        this.name = name;
        this.arr_type = arr_type;
    }

    public boolean isArrayInstance() {return true;}
}