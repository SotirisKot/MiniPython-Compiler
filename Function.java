//Class represents every function on symTable
//def add(a,y,z) 
import java.util.ArrayList;
import java.util.HashMap;

public class Function{
	
	private String id;
	private String returnType;
	private int line;
    private int pos;
	private ArrayList<Variable> parameters;
	private HashMap<String,Variable> variables;

	
	public Function(){
		variables = new HashMap<>();
	}
	
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
	
	public String getReturnType(){
		return returnType;
	}
	
	public void setReturnType(String returnType){
		this.returnType = returnType;
	}
	
	public ArrayList<Variable> getParams(){
		return parameters;
	}
	
	public void setParams(ArrayList<Variable> parameters) {
        this.parameters = parameters;
	}
	
	public HashMap<String, Variable> getVariables() {
        return variables;
    }
	
	public void setVariables(HashMap<String,Variable> variables){
		this.variables = variables;
	}
	
}