package TYPES;

import MAIN.LineError;

public class TYPE_LIST
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public TYPE head;
	public TYPE_LIST tail;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public TYPE_LIST(TYPE head,TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public static boolean compareLists(TYPE_LIST t1, TYPE_LIST t2)
	{
		TYPE_LIST t1_curr = t1;
        TYPE_LIST t2_curr = t2;
        while (t1_curr != null && t2_curr != null)
        {
            if (!TYPE.areMatchingTypes(t1_curr.head, t2_curr.head)){
                return false;
            }
            t1_curr = t1_curr.tail;
            t2_curr = t2_curr.tail;
        }
        if( (t1_curr != null && t2_curr == null) || (t1_curr == null && t2_curr != null))
        { // different number of arguments given and expected
            return false;
        }
		return true;
	}
}
