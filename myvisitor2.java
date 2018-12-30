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

    public void inAFunctionCall(AFunctionCall node){
        String func_name = node.getId().toString();
        int line = ((TId) node.getId()).getLine();

        if(functions_symtable.containsKey(func_name)){
            //
            LinkedList args_func_call = node.getArglist();
            AFunction function = (AFunction) functions_symtable.get(func_name);
            LinkedList func_arguments = function.getArgument();
            // first we must check if the signature of the function has default arguments
            //
            AArgument arg1 = (AArgument) func_arguments.get(0);
            LinkedList equalv1 = arg1.getEqualValue();
            LinkedList commaId1 = arg1.getCommaIdentifier();
            ACommaIdentifier argument1;
            LinkedList eqval1;
            Boolean hasDefault = false;
            int number_of_defaults = 0;
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
                int number_of_given_args = args_func_call.get(0).toString().replace(" ", "").length();
                int number_of_func_args =  func_arguments.get(0).toString().replace(" ", "").length() - number_of_defaults;
                if(number_of_given_args >= number_of_func_args - number_of_defaults && number_of_given_args <= number_of_func_args){
                    //everything is fine do something
                    //TODO 
                }else{
                    System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name);
                    return;
                }
            }else{
                int number_of_given_args = args_func_call.get(0).toString().replace(" ", "").length();
                int number_of_func_args =  func_arguments.get(0).toString().replace(" ", "").length();
                if(number_of_given_args == number_of_func_args){
                    System.out.println(number_of_given_args);
                    System.out.println(number_of_func_args);
                    // to some stuff maybe
                }else{
                    System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name +". Takes " + number_of_func_args + ", were given "+ number_of_given_args);
                    return;
                }
            }
        }else{
            System.out.println("Error : in line " + line + " function " + func_name + "is not defined. ");
            return;
        }
    }

}