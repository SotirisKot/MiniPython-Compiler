//represents variable that is used in statements,expressions etc.

public class Variable{
	
	private String id;
	private int line;
	private int pos;
	private boolean hasDefaultVal = false;
	private int IntDefault;
	private String StringDefault;
	private String type;
	
	public Variable(){}
	
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

    public boolean gethasDefaultVal() {
        return hasDefaultVal;
    }

    public void setHasDefaultVal(boolean hasDefaultVal) {
        this.hasDefaultVal = hasDefaultVal;
    }

    public int getIntDefault() {
        return IntDefault;
    }

    public void setIntDefault(int IntDefault) {
        this.IntDefault = IntDefault;
    }

    public String getStringDefault() {
        return StringDefault;
    }

    public void setDefStringValue(String StringDefault) {
        this.StringDefault = StringDefault;
    } 
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
}