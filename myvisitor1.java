import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class myvisitor1 extends DepthFirstAdapter 
{
	private Hashtable symtable; //hashtable for variables	

	myvisitor1(Hashtable symtable) 
	{
		this.symtable = symtable;
	}
	
	//check if a function is already defined
	public void inAFunction(AFunction node){
		
		String func_name = node.getId().toString();
		int line = ((TId) node.getId()).getLine();
			
		if(symtable.containsKey(func_name)){
			//Apo8hkeuoyme ta arguments tis synarthshs pou molis synanthse o compiler
			//vriskoume ta arguments tis synarthshs poy hdh exoume mesa sto symtamble
			//sygrinoume meta3i tous gia na doume an exoun idia args h kapoio apo ta args xrhsimopoiei 
			//default timh
			LinkedList node_arguments = node.getArgument();
			AFunction def_function = (AFunction) symtable.get(func_name);  
			LinkedList def_arguments = def_function.getArgument();
			
			System.out.println(" Error : in line " + line + " function " + func_name + " already defined. ");
			
		}else{
			
			symtable.put(func_name,node);
		
		}
		
	}
	
	//check if variable is already defined
	public void inAId2Expression(AId2Expression node){
		
		String var_name = node.getId().toString();
		int line = ((TId)node.getId()).getLine();
		
		if(symtable.containsKey(var_name)){
			System.out.println(" Error : in line " + line + " variable " + var_name + "is already defined. ");
		}
	}

}