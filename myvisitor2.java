import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class myvisitor2 extends DepthFirstAdapter {

    private Hashtable functions_symtable; //hashtable for functions
    private Hashtable variables_symtable; //hashtable for variables 

    myvisitor2(Hashtable variables_symtable, Hashtable functions_symtable) 
    {
        this.variables_symtable = variables_symtable;
        this.functions_symtable = functions_symtable;
    }
    
}