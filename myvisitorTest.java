import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class myvisitor1 extends DepthFirstAdapter 
{
	private Hashtable symtable; //hashtable for variables	
	private Boolean loop = false; // ok kalo 8a htan na broume kati kalitero edw alla den h3era ti na kanw
	private HashMap<Node,Variable> FuncParams; 
	private Boolean hasDefaultParams=false;

	myvisitor1(Hashtable symtable) 
	{
		this.symtable = symtable;
	}
	
	

	//check if a function is already defined
	public void inAFunction(AFunction node){
		
		String id = node.getId().getText();
		int line = node.getId().getLine();
        int pos = node.getId().getPos();
		FuncParams = new HashMap<>();
		
		//Analyze args
		LinkedList args = (AArgument) node.getArgument();
		if(args!=null){
			args.apply(this);
		}
		
		//create new function obj
		Function func = new Function();
		func.setId(id);
		func.setLine(line);
		func.setPos(pos);
		ArrayList<Variable> sortedParameters = new ArrayList<>();
		HashMap<String, Variable> params = new HashMap<>();
		for(Variable var : FuncParams.values()){
			if(params.containsKey(var.getId())){
				System.out.println("Error : The parameter " + var.getId() +
                        "' in the line:" + var.getLine() +
                        " has already been defined in line " + params.get(var.getId()).getLine());
                return;
			}
			System.out.println("PRINTING:" + var.getId() + " " + var.gethasDefaultVal());
			params.put(var.getId(),var);
			sortedParameters.add(var);
		}
		func.setVariables(params);
        func.setParams(sortedParameters);
		int defined = 0;
		if(defined == 0){
			symtable.put(func.getId(),node);
		}else{
			System.out.println("Error in line:"+line+"has already defined");
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
	//check if function is already defined
	public void FuncAlreadyExists(Function function){
		
	}

	public void inAArgument(AArgument node){
		
		Variable temp = new Variable();
		TId id = node.getId();
		temp.setId(id.getText());
		temp.setLine(id.getLine());
		temp.setPos(id.getPos());
		FuncParams.put(node,temp);
		
		if(!(symtable.containsKey(temp.getId()))){
			symtable.put(temp.getId(),node);
		}
		
		//check for assign value 
		LinkedList eqval = (AEqualValue) node.getEqualValue();
		if(eqval == null){
			hasDefaultParams = true;
			eqval.apply(this);
		}else{
			hasDefaultParams = false;
		}
		
		// get the rest of the arguments
		LinkedList list_params = (AArgument) node.getArgument();
		for(int i=0; i< list_params.size(); i++){ 
			if (!(symtable.containsKey(temp.getId()))){
				symtable.put(temp.getId(), node);
			}
		} 
	}
	
	public void inAEqualValue(AEqualValue node){
		if(node.parent() instanceof AArgument){
			FuncParams.get(node.parent()).setHasDefaultVal(true);
		}
		PValue value = node.getValue();
		value.apply(this);
	}
	
	public void inANumberValue(ANumberValue node){
		if(node.parent() instanceof AEqualValue){
			FuncParams.get(node.parent()).setType("int");
		}
	}
	
	public void inAStringValue(AStringValue node){
		if(node.parent() instanceof AStringValue){
			FuncParams.get(node.parent()).setType("string");
		}
	}
	
	

}
