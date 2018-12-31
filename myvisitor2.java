import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class myvisitor2 extends DepthFirstAdapter {

    private Hashtable functions_symtable; //hashtable for functions
    private Hashtable variables_symtable; //hashtable for variables 
    private Hashtable temp = new Hashtable();

    myvisitor2(Hashtable variables_symtable, Hashtable functions_symtable) 
    {
        this.variables_symtable = variables_symtable;
        this.functions_symtable = functions_symtable;
    }

    public void outAFunctionCall(AFunctionCall node){
        String func_name = node.getId().toString();
        int line = ((TId) node.getId()).getLine();
        Hashtable variables_args =  new Hashtable();
        if(functions_symtable.containsKey(func_name)){
            //
            LinkedList args_func_call = node.getArglist();
            AFunction function = (AFunction) functions_symtable.get(func_name);
            LinkedList func_arguments = function.getArgument();
            // first we must check if the signature of the function has default arguments
            // or if it has any arguments at all
            if (func_arguments.size() == 0){
                // the function has no arguments so we must check if we give any arguments when we call it
                if(args_func_call.size() > 0){
                    System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name);
                    return;
                }else{
                    // TODO do something with 4th or 5th rule
                    if(getOut(function.getStatement()) instanceof AAdditionExpression){
                        AAdditionExpression adds = (AAdditionExpression) getOut(function.getStatement());
                        setOut(node, adds);
                    }else if(getOut(function.getStatement()) instanceof AStringValue){
                        AStringValue str = (AStringValue) getOut(function.getStatement());
                        setOut(node, str);
                    }else if(getOut(function.getStatement()) instanceof ANumberValue){
                        ANumberValue num = (ANumberValue) getOut(function.getStatement());
                        setOut(node, num);
                    }
                }
            }else{
                if(args_func_call.size() == 0){
                    System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name);
                    return;
                }else{
                    AArgument arg1 = (AArgument) func_arguments.get(0);
                    LinkedList equalv1 = arg1.getEqualValue();
                    LinkedList commaId1 = arg1.getCommaIdentifier();
                    ACommaIdentifier argument1;
                    LinkedList eqval1;
                    Boolean hasDefault = false;
                    int number_of_defaults = 0;
                    //
                    // PValue type = (PValue) getOut(args.getExpression());
                    // LinkedList rest_args = args.getCommaExpression();
                    // System.out.println(type.getClass());
                    // for(int i=0; i<rest_args.size(); i++){
                    //     ACommaExpression expr = (ACommaExpression) rest_args.get(i);
                    //     type = (PValue) getOut(expr.getExpression());
                    //     System.out.println(type.getClass());
                    // }
                    //
                    for(int i=0; i<commaId1.size(); i++){
                        argument1 = (ACommaIdentifier) commaId1.get(i);
                        eqval1 = argument1.getEqualValue();
                        if (eqval1.size() != 0){
                            hasDefault = true;
                            number_of_defaults++;
                        }
                    }
                    //
                    if (hasDefault || (equalv1.size() != 0)){
                        //
                        int number_of_given_args = args_func_call.get(0).toString().split(" ").length;
                        int number_of_func_args  = func_arguments.get(0).toString().split(" ").length - number_of_defaults;
                        if(number_of_given_args >= number_of_func_args - number_of_defaults && number_of_given_args <= number_of_func_args){
                            //everything is fine do something
                            //TODO something with 4th or 5th rule
                            if(getOut(function.getStatement()) instanceof AAdditionExpression){
                                AAdditionExpression adds = (AAdditionExpression) getOut(function.getStatement());
                                //TId id = (TId) adds.getL();
                                System.out.println(adds.getL());
                                AEqualValue val = (AEqualValue) temp.get(adds.getL().toString());
                                System.out.println(val.getValue().getClass());
                                System.exit(0);
                                // AValExpression val = (AValExpression) adds.getR();
                                // System.out.println(val.getValue().getClass());
                            }else if(getOut(function.getStatement()) instanceof AStringValue){
                                AStringValue str = (AStringValue) getOut(function.getStatement());
                                setOut(node, str);
                            }else if(getOut(function.getStatement()) instanceof ANumberValue){
                                ANumberValue num = (ANumberValue) getOut(function.getStatement());
                                setOut(node, num);
                            }
                        }else{
                            System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name);
                            return;
                        }
                    }else{
                        int number_of_given_args = args_func_call.get(0).toString().split(" ").length;
                        int number_of_func_args = func_arguments.get(0).toString().split(" ").length;

                        AArglist args = (AArglist) args_func_call.get(0);
                        if(number_of_given_args == number_of_func_args){
                            // System.out.println(number_of_given_args);
                            // System.out.println(number_of_func_args);
                            //TODO something with 4th or 5th rule
                            if(getOut(function.getStatement()) instanceof AAdditionExpression){
                                AAdditionExpression adds = (AAdditionExpression) getOut(function.getStatement());
                                setOut(node, adds);
                            }else if(getOut(function.getStatement()) instanceof AStringValue){
                                AStringValue str = (AStringValue) getOut(function.getStatement());
                                setOut(node, str);
                            }else if(getOut(function.getStatement()) instanceof ANumberValue){
                                ANumberValue num = (ANumberValue) getOut(function.getStatement());
                                setOut(node, num);
                            }
                            
                            
                        }else{
                            System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name +". Takes " + number_of_func_args + ", were given "+ number_of_given_args);
                            return;
                        }
                    }
                }
            }
        }else{
            System.out.println("Error : in line " + line + " function " + func_name + "is not defined. ");
            return;
        }
    }

    //tsekare oles tis periptwseis an einai addition, subtract, power klp h apla id kai kane setOut afto
    //sthn outAFunction kane getOut to return statement kai me bash to arglist prospa8ise na breis an ola einai ok h oxi.
    public void outAReturnStatement(AReturnStatement node){
        // System.out.println(node);
        // System.out.println(node.getExpression().getClass());
        if(node.getExpression() instanceof AValExpression){
            AValExpression val = (AValExpression) node.getExpression();
            setOut(node, val.getValue());
        }else{
            setOut(node, node.getExpression());
        }
        
    }

    public void outANumberValue(ANumberValue node){
        setOut(node, new ANumberValue());
    }

    public void outAStringValue(AStringValue node){
        setOut(node, new AStringValue());
    }

    public void outAValExpression(AValExpression node){
        PValue type = (PValue) getOut(node.getValue());
        setOut(node, type); 
    }

    public void outAArgument(AArgument node){
        ACommaIdentifier argument1;
        LinkedList eqval1 = node.getEqualValue();
        LinkedList commaId1 = node.getCommaIdentifier();
        if(eqval1.size() != 0){
            AEqualValue val = (AEqualValue) eqval1.get(0);
            System.out.println(val);
            temp.put(node.getId().toString(), val);
        }
        for(int i=0; i<commaId1.size(); i++){
            argument1 = (ACommaIdentifier) commaId1.get(i);
            eqval1 = argument1.getEqualValue();
            if (eqval1.size() != 0){
                AEqualValue val = (AEqualValue) eqval1.get(0);
                temp.put(argument1.getId().toString(), val);
            }
        }
    }

    public void outAAdditionExpression(AAdditionExpression node){
        PValue v1 = null; 
        PValue v2 = null;
        if (node.getL() instanceof AFuncCallExpression || node.getR() instanceof AFuncCallExpression){
            if(node.getL() instanceof AFuncCallExpression){
                AFuncCallExpression funcL = (AFuncCallExpression) node.getL();
                v1 = (PValue) getOut(funcL.getFunctionCall());
            }else{
                v1 = (PValue) getOut(node.getL());
            }
            if(node.getR() instanceof AFuncCallExpression){
                AFuncCallExpression funcR = (AFuncCallExpression) node.getR();
                v2 = (PValue) getOut(funcR.getFunctionCall());
            }else{
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
    }

    public void outADivisionExpression(ADivisionExpression node){
        PValue v1 = null; 
        PValue v2 = null;
        if (node.getL() instanceof AFuncCallExpression || node.getR() instanceof AFuncCallExpression){
            if(node.getL() instanceof AFuncCallExpression){
                AFuncCallExpression funcL = (AFuncCallExpression) node.getL();
                v1 = (PValue) getOut(funcL.getFunctionCall());
            }else{
                v1 = (PValue) getOut(node.getL());
            }
            if(node.getR() instanceof AFuncCallExpression){
                AFuncCallExpression funcR = (AFuncCallExpression) node.getR();
                v2 = (PValue) getOut(funcR.getFunctionCall());
            }else{
                v2 = (PValue) getOut(node.getR());
            }
            if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                System.out.println(" Error :: cannot divide string with number.");
            }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                System.out.println(" Error :: cannot divide number with string.");
            }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                setOut(node, new AStringValue());
            }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                setOut(node, new ANumberValue());
            }
        }
    }

    public void outASubtractionExpression(ASubtractionExpression node){
        PValue v1 = null; 
        PValue v2 = null;
        if (node.getL() instanceof AFuncCallExpression || node.getR() instanceof AFuncCallExpression){
            if(node.getL() instanceof AFuncCallExpression){
                AFuncCallExpression funcL = (AFuncCallExpression) node.getL();
                v1 = (PValue) getOut(funcL.getFunctionCall());
            }else{
                v1 = (PValue) getOut(node.getL());
            }
            if(node.getR() instanceof AFuncCallExpression){
                AFuncCallExpression funcR = (AFuncCallExpression) node.getR();
                v2 = (PValue) getOut(funcR.getFunctionCall());
            }else{
                v2 = (PValue) getOut(node.getR());
            }
            if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                System.out.println(" Error :: cannot subtract string with number.");
            }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                System.out.println(" Error :: cannot subtract number with string.");
            }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                setOut(node, new AStringValue());
            }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                setOut(node, new ANumberValue());
            }
        }
    }

    public void outAPowerExpression(APowerExpression node){
        PValue v1 = null; 
        PValue v2 = null;
        if (node.getL() instanceof AFuncCallExpression || node.getR() instanceof AFuncCallExpression){
            if(node.getL() instanceof AFuncCallExpression){
                AFuncCallExpression funcL = (AFuncCallExpression) node.getL();
                v1 = (PValue) getOut(funcL.getFunctionCall());
            }else{
                v1 = (PValue) getOut(node.getL());
            }
            if(node.getR() instanceof AFuncCallExpression){
                AFuncCallExpression funcR = (AFuncCallExpression) node.getR();
                v2 = (PValue) getOut(funcR.getFunctionCall());
            }else{
                v2 = (PValue) getOut(node.getR());
            }
            if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                System.out.println(" Error :: cannot use power with: string and number.");
            }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                System.out.println(" Error :: cannot use power with: string and number.");
            }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                setOut(node, new AStringValue());
            }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                setOut(node, new ANumberValue());
            }
        }
    }

}