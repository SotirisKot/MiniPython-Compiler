import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class myvisitor1 extends DepthFirstAdapter 
{
	private Hashtable functions_symtable; //hashtable for functions
	private Hashtable variables_symtable; //hashtable for variables	
	private Boolean loop = false; //

	myvisitor1(Hashtable variables_symtable, Hashtable functions_symtable) 
	{
		this.variables_symtable = variables_symtable;
		this.functions_symtable = functions_symtable;
	}

	public Hashtable getFunctionTable(){
		return this.functions_symtable;
	}

	public Hashtable getVariableTable(){
		return this.variables_symtable;
	}
	
	//check if a function is already defined
    //
	public void inAFunction(AFunction node){
		
		String func_name = node.getId().toString();
		int line = node.getId().getLine();
		
		if(functions_symtable.containsKey(func_name)){
			//Apo8hkeuoyme ta arguments tis synarthshs pou molis synanthse o compiler
			//vriskoume ta arguments tis synarthshs poy hdh exoume mesa sto symtamble
			//sygrinoume meta3i tous gia na doume an exoun idia args h kapoio apo ta args xrhsimopoiei 
			//default timh
			LinkedList node_arguments = node.getArgument();
			AFunction def_function = (AFunction) functions_symtable.get(func_name);  
			LinkedList def_arguments = def_function.getArgument();

			if(def_arguments.size() == node_arguments.size()){

				if(node_arguments.size()==0){
					System.out.println("Error : in line " + line + " function " + func_name + "is already defined. ");
					System.exit(0);
				}
				//
				AArgument arg1 = (AArgument) node_arguments.get(0);
				AArgument arg2 = (AArgument) def_arguments.get(0);
				//
				LinkedList equalv1 = arg1.getEqualValue();
				LinkedList equalv2 = arg2.getEqualValue();
				//
				LinkedList commaId1 = arg1.getCommaIdentifier();
				LinkedList commaId2 = arg2.getCommaIdentifier();
				//
				// Check if the second function with the same name has a default argument before a non-default one
				ACommaIdentifier argument;
				Boolean notDefault = true;
				Boolean only_defaults = false;
				Boolean only_non_defaults = true;
				if(equalv1.size() != 0){
					notDefault = false;
					only_defaults = true;
				}
				for (int i=0; i<commaId1.size(); i++){
					argument = (ACommaIdentifier) commaId1.get(i);
					equalv1 = argument.getEqualValue();
					if(equalv1.size() == 0 && !notDefault){
						//System.out.println(argument2);
						notDefault = true;
						only_defaults = false;
						only_non_defaults = false;
						break;
					}else if (equalv1.size() != 0){
						notDefault = false;
						only_defaults = true;
						only_non_defaults = true;
					}
				}
				if (notDefault && commaId1.size() != 0 && !only_defaults && !only_non_defaults){
					System.out.println("Error : in line " + line + ". A non default argument cannot follow a default one at function " + func_name);
				 	System.exit(0);
				}
				//
				arg1 = (AArgument) node_arguments.get(0);
				arg2 = (AArgument) def_arguments.get(0);
				//
				equalv1 = arg1.getEqualValue();
				equalv2 = arg2.getEqualValue();
				//
				commaId1 = arg1.getCommaIdentifier();
				commaId2 = arg2.getCommaIdentifier();
				//
				if(equalv1.size() != 0 && equalv2.size() == 0 && commaId1.size() != 0 && commaId2.size() != 0){
					System.out.println("Error : in line " + line + " function " + func_name + "." +"Repeated signature with a default/non default argument.");
					System.exit(0);
				}else if(equalv2.size() != 0 && equalv1.size() == 0 && commaId1.size() != 0 && commaId2.size() != 0){
					System.out.println("Error : in line " + line + " function " + func_name + "." +"Repeated signature with a default/non default argument.");
			 		System.exit(0);
				}else if(equalv1.size() != 0 && equalv2.size() == 0 && commaId1.size() == 0 && commaId2.size() == 0){
					System.out.println("Error : in line " + line + " function " + func_name + "." +"Repeated signature with a default/non default argument.");
			 		System.exit(0);
				}else if(equalv1.size() == 0 && equalv2.size() != 0 && commaId1.size() == 0 && commaId2.size() == 0){
					System.out.println("Error : in line " + line + " function " + func_name + "." +"Repeated signature with a default/non default argument.");
			 		System.exit(0);
				}
				//
				if (commaId1.size() == commaId2.size()){
					//
					ACommaIdentifier argument1;
					ACommaIdentifier argument2;
					LinkedList eqval1;
					LinkedList eqval2;
					Boolean already_defined = false;
					//
					for(int i=0; i<commaId1.size(); i++){
						//
						argument1 = (ACommaIdentifier) commaId1.get(i);
						argument2 = (ACommaIdentifier) commaId2.get(i);
						//
						eqval1 = argument1.getEqualValue();
						eqval2 = argument2.getEqualValue();

						//
						if(eqval1.size() != 0 && eqval2.size() == 0){
							System.out.println("Error : in line " + line + " function " + func_name + "." +"Repeated signature with a default/non default argument.");
			 				System.exit(0);
						}else if(eqval2.size() != 0 && eqval1.size() == 0){
							System.out.println("Error : in line " + line + " function " + func_name + "." +"Repeated signature with a default/non default argument.");
			 				System.exit(0);
						}else{
							already_defined = true;
						}
					}
					if (already_defined){
						System.out.println("Error : in line " + line + " function " + func_name + "is already defined. ");
						System.exit(0);
					}
				}else if (commaId1.size() > commaId2.size()){
					//
					ACommaIdentifier argument1;
					LinkedList eqval1;
					Boolean hasDefault = false;
					//
					for (int i=0; i<commaId1.size(); i++){
						//
						argument1 = (ACommaIdentifier) commaId1.get(i);
						eqval1 = argument1.getEqualValue();
						if (eqval1.size() != 0){
							hasDefault = true;
							break;
						}
					}
					//
					if (hasDefault){
						System.out.println("Error : in line " + line + " function " + func_name + "." +"Repeated signature with a default/non default argument.");
			 			System.exit(0);
					}else{
						functions_symtable.put(func_name,node);
					}
				}else{
					//
					ACommaIdentifier argument2;
					LinkedList eqval2;
					Boolean hasDefault = false;
					//
					for (int i=0; i<commaId2.size(); i++){
						//
						argument2 = (ACommaIdentifier) commaId2.get(i);
						eqval2 = argument2.getEqualValue();
						if (eqval2.size() != 0){
							hasDefault = true;
							break;
						}
					}
					//
					if (hasDefault){
						System.out.println("Error : in line " + line + " function " + func_name + "." +"Repeated signature with a default/non default argument.");
			 			System.exit(0);
					}else{
						functions_symtable.put(func_name,node);
					}
				}
			}
		}else{
			// prepei na elegxei an yparxei non default prin apo default
			LinkedList def_arguments = node.getArgument();
			if (def_arguments.size() == 0){
				functions_symtable.put(func_name,node);
			}else{
				AArgument arg2 = (AArgument) def_arguments.get(0);
				LinkedList equalv2 = arg2.getEqualValue();
				LinkedList commaId2 = arg2.getCommaIdentifier();
				ACommaIdentifier argument2;
				Boolean notDefault = true;
				Boolean only_defaults = false;
				Boolean only_non_defaults = true;
				if(equalv2.size() != 0){
					notDefault = false;
					only_defaults = true;
				}
				for (int i=0; i<commaId2.size(); i++){
					argument2 = (ACommaIdentifier) commaId2.get(i);
					equalv2 = argument2.getEqualValue();
					if(equalv2.size() == 0 && !notDefault){
						//System.out.println(argument2);
						notDefault = true;
						only_defaults = false;
						only_non_defaults = false;
						break;
					}else if (equalv2.size() != 0){
						notDefault = false;
						only_defaults = true;
						only_non_defaults = true;
					}
				}
				if (notDefault && commaId2.size() != 0 && !only_defaults && !only_non_defaults){
					System.out.println("Error : in line " + line + ". A non default argument cannot follow a default one at function " + func_name);
				 	System.exit(0);
				}
				functions_symtable.put(func_name,node);
			}
		}
	}
	
	//check if variable is not defined
	public void inAId2Expression(AId2Expression node){
		String var_name = node.getId().toString();
		int line = node.getId().getLine();
		if(!variables_symtable.containsKey(var_name) && !loop){
			System.out.println(" Error : in line " + line + " variable " + var_name + "is not defined. ");
			System.exit(0);
		}else{
			loop = false;
		}
	}


	public void inAArgument(AArgument node){
		String var_name = node.getId().toString();
		if(!(variables_symtable.containsKey(var_name))){
			variables_symtable.put(var_name,node);
		}
		// get the rest of the arguments
		LinkedList list_arguments = node.getCommaIdentifier();
		for(int i=0; i< list_arguments.size(); i++){
			var_name = ((ACommaIdentifier) list_arguments.get(i)).getId().toString();
			if (!(variables_symtable.containsKey(var_name))){
				variables_symtable.put(var_name, node);
			}
		} 
	}

	public void outANumberValue(ANumberValue node){
		setOut(node, node);
	}

	public void outAValExpression(AValExpression node){
		PValue type = (PValue) getOut(node.getValue());
		setOut(node, type); 
	}

	public void outAStringValue(AStringValue node){
		setOut(node, node);
	}

	public void outAAdditionExpression(AAdditionExpression node){
		PValue v1 = null; 
		PValue v2 = null;
		if(node.getL() instanceof AId2Expression){
			if (variables_symtable.get(node.getL().toString()) instanceof AEqualsStatement){
				AEqualsStatement nodeEq = (AEqualsStatement) variables_symtable.get(node.getL().toString());
				v1 = (PValue) getOut(nodeEq);
			}
		}else{
			v1 = (PValue) getOut(node.getL());
		}
		if(node.getR() instanceof AId2Expression){
			if (variables_symtable.get(node.getR().toString()) instanceof AEqualsStatement){
				AEqualsStatement nodeEq = (AEqualsStatement) variables_symtable.get(node.getR().toString());
				v2 = (PValue) getOut(nodeEq);
			}
		}else{
			v2 = (PValue) getOut(node.getR());
		}

		if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
			System.out.println(" Error :: cannot add string: "+ v1 +"with number:" + v2);
			System.exit(0);
		}else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
			System.out.println(" Error :: cannot add number: "+ v1 +"with string:" + v2);
			System.exit(0);
		}else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
			setOut(node, new AStringValue());
		}else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
			setOut(node, new ANumberValue());
		}
	}

	public void outASubtractionExpression(ASubtractionExpression node){
		PValue v1 = null; 
		PValue v2 = null;
		if(node.getL() instanceof AId2Expression){
			if (variables_symtable.get(node.getL().toString()) instanceof AEqualsStatement){
				AEqualsStatement nodeEq = (AEqualsStatement) variables_symtable.get(node.getL().toString());
				v1 = (PValue) getOut(nodeEq);
			}
		}else{
			v1 = (PValue) getOut(node.getL());
		}
		if(node.getR() instanceof AId2Expression){
			if (variables_symtable.get(node.getR().toString()) instanceof AEqualsStatement){
				AEqualsStatement nodeEq = (AEqualsStatement) variables_symtable.get(node.getR().toString());
				v2 = (PValue) getOut(nodeEq);
			}
		}else{
			v2 = (PValue) getOut(node.getR());
		}

		if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
			System.out.println(" Error :: cannot subtract string: "+ v1 +"with number: " + v2);
			System.exit(0);
		}else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
			System.out.println(" Error :: cannot subtract number: "+ v1 +"with string: " + v2);
			System.exit(0);
		}else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
			setOut(node, new AStringValue());
		}else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
			setOut(node, new ANumberValue());
		}
	}

	public void outADivisionExpression(ADivisionExpression node){
		PValue v1 = null; 
		PValue v2 = null;
		if(node.getL() instanceof AId2Expression){
			if (variables_symtable.get(node.getL().toString()) instanceof AEqualsStatement){
				AEqualsStatement nodeEq = (AEqualsStatement) variables_symtable.get(node.getL().toString());
				v1 = (PValue) getOut(nodeEq);
			}else if (variables_symtable.get(node.getL().toString()) instanceof AArgument){
				AArgument nodeEq = (AArgument) variables_symtable.get(node.getL().toString());
				v1 = (PValue) getOut(nodeEq);
			}
		}else{
			v1 = (PValue) getOut(node.getL());
		}
		if(node.getR() instanceof AId2Expression){
			if (variables_symtable.get(node.getR().toString()) instanceof AEqualsStatement){
				AEqualsStatement nodeEq = (AEqualsStatement) variables_symtable.get(node.getR().toString());
				v2 = (PValue) getOut(nodeEq);
			}else if (variables_symtable.get(node.getR().toString()) instanceof AArgument){
				AArgument nodeEq = (AArgument) variables_symtable.get(node.getR().toString());
				v2 = (PValue) getOut(nodeEq);
			}
		}else{
			v2 = (PValue) getOut(node.getR());
		}

		if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
			System.out.println(" Error :: cannot divide string: "+ v1 +"with number: " + v2);
			System.exit(0);
		}else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
			System.out.println(" Error :: cannot divide number: "+ v1 + "with string: "+ v2);
			System.exit(0);
		}else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
			setOut(node, new AStringValue());
		}else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
			setOut(node, new ANumberValue());
		}
	}

	public void outAPowerExpression(APowerExpression node){
		PValue v1 = null; 
		PValue v2 = null;
		if(node.getL() instanceof AId2Expression){
			if (variables_symtable.get(node.getL().toString()) instanceof AEqualsStatement){
				AEqualsStatement nodeEq = (AEqualsStatement) variables_symtable.get(node.getL().toString());
				v1 = (PValue) getOut(nodeEq);
			}else if (variables_symtable.get(node.getL().toString()) instanceof AArgument){
				AArgument nodeEq = (AArgument) variables_symtable.get(node.getL().toString());
				v1 = (PValue) getOut(nodeEq);
			}
		}else{
			v1 = (PValue) getOut(node.getL());
		}
		if(node.getR() instanceof AId2Expression){
			if (variables_symtable.get(node.getR().toString()) instanceof AEqualsStatement){
				AEqualsStatement nodeEq = (AEqualsStatement) variables_symtable.get(node.getR().toString());
				v2 = (PValue) getOut(nodeEq);
			}else if (variables_symtable.get(node.getR().toString()) instanceof AArgument){
				AArgument nodeEq = (AArgument) variables_symtable.get(node.getR().toString());
				v2 = (PValue) getOut(nodeEq);
			}
		}else{
			v2 = (PValue) getOut(node.getR());
		}

		if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
			System.out.println(" Error :: cannot use power with: string: "+ v1 + "and number: " + v2);
			System.exit(0);
		}else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
			System.out.println(" Error ::cannot use power with: number: " + v1 +" and string: " + v2);
			System.exit(0);
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
		if (!(variables_symtable.containsKey(varName))){
			variables_symtable.put(varName, node);
		}
	}

	public void inAForStatement(AForStatement node) {
		
		String varName1 = node.getId1().toString();
		String varName2 = node.getId2().toString();
		if (!variables_symtable.containsKey(varName2)) {
			int line = ((TId) node.getId2()).getLine();
			System.out.println(" Error : in line " + line + " variable " + varName2 + "is not defined.");
			loop = true;
		}else{
			if (!variables_symtable.containsKey(varName1)) {
				variables_symtable.put(varName1, node);
			}
		}
	}

}
