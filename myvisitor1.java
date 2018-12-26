import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class myvisitor1 extends DepthFirstAdapter 
{
	private Hashtable symtable; //hashtable for variables	
	private Boolean loop = false; // ok kalo 8a htan na broume kati kalitero edw alla den h3era ti na kanw

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
	
	//check if variable is not defined
	public void inAId2Expression(AId2Expression node){
		String var_name = node.getId().toString();
		int line = ((TId)node.getId()).getLine();
		if(!symtable.containsKey(var_name) && !loop){
			System.out.println(" Error : in line " + line + " variable " + var_name + "is not defined. ");
		}else{
			loop = false;
		}
	}


	public void inAArgument(AArgument node){

		String var_name = node.getId().toString();
		if(!(symtable.containsKey(var_name))){
			symtable.put(var_name,node);
		}
		// get the rest of the arguments
		LinkedList list_arguments = node.getCommaIdentifier();
		for(int i=0; i< list_arguments.size(); i++){
			var_name = ((ACommaIdentifier) list_arguments.get(i)).getId().toString();
			if (!(symtable.containsKey(var_name))){
				symtable.put(var_name, node);
			}
		} 
	}

	public void outANumberValue(ANumberValue node){
		setOut(node, new ANumberValue());
	}

	public void outAValExpression(AValExpression node){
		PValue type = (PValue) getOut(node.getValue());
		setOut(node, type); 
	}

	public void outAStringValue(AStringValue node){
		setOut(node, new AStringValue());
	}

	public void outAAdditionExpression(AAdditionExpression node){
		PValue v1 = null; 
		PValue v2 = null;
		if (node.getL() instanceof AId2Expression || (node.getR() instanceof AId2Expression)){
			if(node.getL() instanceof AId2Expression){
				AEqualsStatement nodeEq = (AEqualsStatement) symtable.get(node.getL().toString());
				v1 = (PValue) getOut(nodeEq);
				v2 = (PValue) getOut(node.getR());	
			}
			if(node.getR() instanceof AId2Expression){
				AEqualsStatement nodeEq = (AEqualsStatement) symtable.get(node.getL().toString());
				v2 = (PValue) getOut(nodeEq);
				v1 = (PValue) getOut(node.getL());	
			}
		}else{
			v1 = (PValue) getOut(node.getL());
			v2 = (PValue) getOut(node.getR());	
		}
		if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
			System.out.println(" Error :: cannot add string with number.");
		}else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
			System.out.println(" Error :: cannot add number with string.");
		}else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
			setOut(node, new AStringValue());
		}else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
			setOut(node, new ANumberValue());
		}
	}

	public void outAEqualsStatement(AEqualsStatement node) {
		String varName = node.getId().toString();
		PValue type = (PValue) getOut(node.getExpression());
		setOut(node, type);
	}

	public void inAEqualsStatement(AEqualsStatement node) {
		String varName = node.getId().toString();
		symtable.put(varName, node);
	}

	public void inAForStatement(AForStatement node) {
		
		String varName1 = node.getId1().toString();
		String varName2 = node.getId2().toString();
		if (!symtable.containsKey(varName2)) {
			int line = ((TId) node.getId2()).getLine();
			System.out.println(" Error : in line " + line + " variable " + varName2 + "is not defined.");
			loop = true;
		}else{
			if (!symtable.containsKey(varName1)) {
				symtable.put(varName1, node);
			}
		}
	}

}
