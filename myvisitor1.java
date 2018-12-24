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
			
			if(def_arguments.size() == node_arguments.size()){
				//tsekaroume an to node_arguments einai iso me to mhden ara den exoume orismata sthn synarthsh mas
				//px foo() 
				if(node_arguments.size()==0){
					System.out.println("Error : in line " + line + " function " + func_name + "is already defined. ");
					return;
				}
				
				//koitame an oi dyo synarthseis exoun idio onoma k idio ari8mo parametrwn kai oi parametroi einai idioi! px an exoume: 
				//def add(x,y,z):
				//	return x
				//
				//def add(x,y,z):
				// return x 
				//
				//8a xtyphsei error sto line me to 2o define 
				int counter_param = 0;
				AArgument arg1 = (AArgument) node_arguments.get(0);
				String[] parts = arg1.toString().split(" ");
				AArgument arg2 = (AArgument) def_arguments.get(0);
				
				if((arg1.getId().toString()).equals(arg2.getId().toString())){
					counter_param++;
					LinkedList list1 = arg1.getEqualValue();
					LinkedList list2 = arg2.getEqualValue();
					list1 = arg1.getCommaIdentifier();
					list2 = arg2.getCommaIdentifier();
				
					for(int i=0; i<list1.size(); i++){
						ACommaIdentifier commaid1 = (ACommaIdentifier) list1.get(i);
						ACommaIdentifier commaid2 = (ACommaIdentifier) list2.get(i);
						if((commaid1.getId().getText()).equals(commaid2.getId().getText())){
							counter_param++;
						}
					}
				}
				
				if(counter_param == parts.length){
					System.out.println("Error : in line " + line + " function " + func_name + "is already defined. ");
				}else{
					symtable.put(func_name,node);
				}
				
				
				
				
				
			}
			
			
			
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
