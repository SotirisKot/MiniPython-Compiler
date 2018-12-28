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
    
    //Implementation of rule number 2
	public void inAFunctionCall(AFunctionCall node){
		String func_called = node.getId().toString();
		
		if(!(functions_symtable.contains(func_called))){
			System.out.println("Error function: " + func_called + "is not defined!");
		}
	}
    
}
