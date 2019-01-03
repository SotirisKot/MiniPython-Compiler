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
                    System.exit(0);
                }else{
                    // TODO do something with 4th or 5th rule
                    if(getOut(function.getStatement()) instanceof AAdditionExpression){
                        AAdditionExpression adds = (AAdditionExpression) getOut(function.getStatement());
                        AEqualsStatement eq1 = (AEqualsStatement) variables_symtable.get(adds.getL().toString());
                        AEqualsStatement eq2 = (AEqualsStatement) variables_symtable.get(adds.getR().toString());
                        PValue v1 =  (PValue) getOut(eq1);
                        PValue v2 =  (PValue) getOut(eq2);
                        if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                            System.out.println(" Error :: cannot add string with number.");
                            System.exit(0);
                        }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                            System.out.println(" Error :: cannot add number with string.");
                            System.exit(0);
                        }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                            setOut(node, new AStringValue());
                        }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                            setOut(node, new ANumberValue());
                        }
                    }else if(getOut(function.getStatement()) instanceof ADivisionExpression){
                        ADivisionExpression divs = (ADivisionExpression) getOut(function.getStatement());
                        AEqualsStatement eq1 = (AEqualsStatement) variables_symtable.get(divs.getL().toString());
                        AEqualsStatement eq2 = (AEqualsStatement) variables_symtable.get(divs.getR().toString());
                        PValue v1 =  (PValue) getOut(eq1);
                        PValue v2 =  (PValue) getOut(eq2);
                        if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                            System.out.println(" Error :: cannot divide string with number.");
                            System.exit(0);
                        }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                            System.out.println(" Error :: cannot divide number with string.");
                            System.exit(0);
                        }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                            setOut(node, new AStringValue());
                        }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                            setOut(node, new ANumberValue());
                        }
                    }else if(getOut(function.getStatement()) instanceof ASubtractionExpression){
                        ASubtractionExpression subs = (ASubtractionExpression) getOut(function.getStatement());
                        AEqualsStatement eq1 = (AEqualsStatement) variables_symtable.get(subs.getL().toString());
                        AEqualsStatement eq2 = (AEqualsStatement) variables_symtable.get(subs.getR().toString());
                        PValue v1 =  (PValue) getOut(eq1);
                        PValue v2 =  (PValue) getOut(eq2);
                        if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                            System.out.println(" Error :: cannot subtract string with number.");
                            System.exit(0);
                        }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                            System.out.println(" Error :: cannot subtract number with string.");
                            System.exit(0);
                        }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                            setOut(node, new AStringValue());
                        }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                            setOut(node, new ANumberValue());
                        }
                    }else if(getOut(function.getStatement()) instanceof APowerExpression){
                        APowerExpression power = (APowerExpression) getOut(function.getStatement());
                        AEqualsStatement eq1 = (AEqualsStatement) variables_symtable.get(power.getL().toString());
                        AEqualsStatement eq2 = (AEqualsStatement) variables_symtable.get(power.getR().toString());
                        PValue v1 =  (PValue) getOut(eq1);
                        PValue v2 =  (PValue) getOut(eq2);
                        if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                            System.out.println(" Error :: cannot use power with string and number.");
                            System.exit(0);
                        }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                            System.out.println(" Error :: cannot use power with number and string.");
                            System.exit(0);
                        }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                            setOut(node, new AStringValue());
                        }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                            setOut(node, new ANumberValue());
                        }
                    }else if(getOut(function.getStatement()) instanceof AStringValue){
                        AStringValue str = (AStringValue) getOut(function.getStatement());
                        setOut(node, str);
                    }else if(getOut(function.getStatement()) instanceof ANumberValue){
                        ANumberValue num = (ANumberValue) getOut(function.getStatement());
                        setOut(node, num);
                    }
                }
            }else{
                AArgument arg1 = (AArgument) func_arguments.get(0);
                LinkedList commaId1 = arg1.getCommaIdentifier();
                ACommaIdentifier argument1;
                LinkedList eqval1 = arg1.getEqualValue();
                Boolean hasDefault = false;
                Boolean all_defaults = true;
                int number_of_defaults = 0;
                if (eqval1.size() != 0){
                    hasDefault = true;
                    number_of_defaults++;
                }else{
                    all_defaults = false;
                }
                for(int i=0; i<commaId1.size(); i++){
                    argument1 = (ACommaIdentifier) commaId1.get(i);
                    eqval1 = argument1.getEqualValue();
                    if (eqval1.size() != 0){
                        hasDefault = true;
                        number_of_defaults++;
                    }else{
                        all_defaults = false;
                    }
                }
                //
                if(args_func_call.size() == 0 && !all_defaults){
                    System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name);
                    System.exit(0);
                }else if(args_func_call.size() == 0 && all_defaults){
                    //
                    if(getOut(function.getStatement()) instanceof AAdditionExpression){
                        //
                        AAdditionExpression adds = (AAdditionExpression) getOut(function.getStatement());
                        PValue v1;
                        PValue v2;
                        if(!temp.containsKey(adds.getL().toString())){
                            AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(adds.getL().toString());
                            v1 = (PValue) getOut(eq);
                        }else{
                            v1 = (PValue) temp.get(adds.getL().toString());
                        }
                        if(!temp.containsKey(adds.getR().toString())){
                            AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(adds.getR().toString());
                            v2 = (PValue) getOut(eq);
                        }else{
                            v2 = (PValue) temp.get(adds.getR().toString());
                        }
                        
                        if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                            System.out.println(" Error :: cannot add string with number.");
                            System.exit(0);
                        }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                            System.out.println(" Error :: cannot add number with string.");
                            System.exit(0);
                        }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                            setOut(node, new AStringValue());
                        }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                            setOut(node, new ANumberValue());
                        }
                        //
                    }else if(getOut(function.getStatement()) instanceof ADivisionExpression){
                        //
                        ADivisionExpression divs = (ADivisionExpression) getOut(function.getStatement());
                        PValue v1;
                        PValue v2;
                        if(!temp.containsKey(divs.getL().toString())){
                            AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(divs.getL().toString());
                            v1 = (PValue) getOut(eq);
                        }else{
                            v1 = (PValue) temp.get(divs.getL().toString());
                        }
                        if(!temp.containsKey(divs.getR().toString())){
                            AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(divs.getR().toString());
                            v2 = (PValue) getOut(eq);
                        }else{
                            v2 = (PValue) temp.get(divs.getR().toString());
                        }
                        
                        if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                            System.out.println(" Error :: cannot add string with number.");
                            System.exit(0);
                        }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                            System.out.println(" Error :: cannot add number with string.");
                            System.exit(0);
                        }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                            setOut(node, new AStringValue());
                        }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                            setOut(node, new ANumberValue());
                        }
                        //
                    }else if(getOut(function.getStatement()) instanceof ASubtractionExpression){
                        //
                        ASubtractionExpression subs = (ASubtractionExpression) getOut(function.getStatement());
                        PValue v1;
                        PValue v2;
                        if(!temp.containsKey(subs.getL().toString())){
                            AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(subs.getL().toString());
                            v1 = (PValue) getOut(eq);
                        }else{
                            v1 = (PValue) temp.get(subs.getL().toString());
                        }
                        if(!temp.containsKey(subs.getR().toString())){
                            AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(subs.getR().toString());
                            v2 = (PValue) getOut(eq);
                        }else{
                            v2 = (PValue) temp.get(subs.getR().toString());
                        }
                        
                        if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                            System.out.println(" Error :: cannot add string with number.");
                            System.exit(0);
                        }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                            System.out.println(" Error :: cannot add number with string.");
                            System.exit(0);
                        }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                            setOut(node, new AStringValue());
                        }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                            setOut(node, new ANumberValue());
                        }
                        //
                    }else if(getOut(function.getStatement()) instanceof APowerExpression){
                        //
                        APowerExpression power = (APowerExpression) getOut(function.getStatement());
                        PValue v1;
                        PValue v2;
                        if(!temp.containsKey(power.getL().toString())){
                            AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(power.getL().toString());
                            v1 = (PValue) getOut(eq);
                        }else{
                            v1 = (PValue) temp.get(power.getL().toString());
                        }
                        if(!temp.containsKey(power.getR().toString())){
                            AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(power.getR().toString());
                            v2 = (PValue) getOut(eq);
                        }else{
                            v2 = (PValue) temp.get(power.getR().toString());
                        }
                        
                        if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                            System.out.println(" Error :: cannot add string with number.");
                            System.exit(0);
                        }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                            System.out.println(" Error :: cannot add number with string.");
                            System.exit(0);
                        }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                            setOut(node, new AStringValue());
                        }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                            setOut(node, new ANumberValue());
                        }
                        //
                    }else if(getOut(function.getStatement()) instanceof AStringValue){
                        AStringValue str = (AStringValue) getOut(function.getStatement());
                        setOut(node, str);
                    }else if(getOut(function.getStatement()) instanceof ANumberValue){
                        ANumberValue num = (ANumberValue) getOut(function.getStatement());
                        setOut(node, num);
                    }else if(getOut(function.getStatement()) instanceof AId2Expression){
                        AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(function.getStatement().toString());
                        PValue v1 = (PValue) getOut(eq);
                        setOut(node, v1);
                    }
                    //
                }else if (args_func_call.size() != 0){
                    //
                    if (hasDefault){
                        //
                        int number_of_given_args = args_func_call.get(0).toString().split(" ").length;
                        int number_of_func_args  = func_arguments.get(0).toString().split(" ").length - number_of_defaults;
                        if(number_of_given_args >= number_of_func_args - number_of_defaults && number_of_given_args <= number_of_func_args){
                            //
                            AArgument arg = (AArgument) func_arguments.get(0);
                            LinkedList commaId = arg.getCommaIdentifier();
                            ACommaIdentifier argument;
                            TId first_arg = arg.getId();
                            AArglist args = (AArglist) args_func_call.get(0);                          
                            LinkedList rest_args = args.getCommaExpression();
                            //
                            if(args.getExpression() instanceof AId2Expression){
                                AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(args.getExpression().toString());
                                PValue v1 = (PValue) getOut(eq);
                                temp.put(first_arg.toString(), v1);
                            }else{
                                AValExpression first_given_arg = (AValExpression) args.getExpression();
                                temp.put(first_arg.toString(), first_given_arg.getValue());
                            }
                            //
                            for(int i=0; i<number_of_given_args-1; i++){
                                argument = (ACommaIdentifier) commaId.get(i);
                                ACommaExpression comma = (ACommaExpression) rest_args.get(i);
                                if(comma.getExpression() instanceof AId2Expression){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(comma.getExpression().toString());
                                    PValue v2 = (PValue) getOut(eq);
                                    temp.put(argument.getId().toString(), v2);
                                }else{
                                    AValExpression current_arg = (AValExpression) comma.getExpression();
                                    temp.put(argument.getId().toString(), current_arg.getValue());
                                }
                            }
                            //
                            if(getOut(function.getStatement()) instanceof AAdditionExpression){
                                //
                                AAdditionExpression adds = (AAdditionExpression) getOut(function.getStatement());
                                PValue v1;
                                PValue v2;
                                if(!temp.containsKey(adds.getL().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(adds.getL().toString());
                                    v1 = (PValue) getOut(eq);
                                }else{
                                    v1 = (PValue) temp.get(adds.getL().toString());
                                }
                                if(!temp.containsKey(adds.getR().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(adds.getR().toString());
                                    v2 = (PValue) getOut(eq);
                                }else{
                                    v2 = (PValue) temp.get(adds.getR().toString());
                                }
                                
                                if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                                    System.out.println(" Error :: cannot add string with number.");
                                    System.exit(0);
                                }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                                    System.out.println(" Error :: cannot add number with string.");
                                    System.exit(0);
                                }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                                    setOut(node, new AStringValue());
                                }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                                    setOut(node, new ANumberValue());
                                }
                                //
                            }else if(getOut(function.getStatement()) instanceof ADivisionExpression){
                                //
                                ADivisionExpression divs = (ADivisionExpression) getOut(function.getStatement());
                                PValue v1;
                                PValue v2;
                                if(!temp.containsKey(divs.getL().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(divs.getL().toString());
                                    v1 = (PValue) getOut(eq);
                                }else{
                                    v1 = (PValue) temp.get(divs.getL().toString());
                                }
                                if(!temp.containsKey(divs.getR().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(divs.getR().toString());
                                    v2 = (PValue) getOut(eq);
                                }else{
                                    v2 = (PValue) temp.get(divs.getR().toString());
                                }
                                
                                if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                                    System.out.println(" Error :: cannot add string with number.");
                                    System.exit(0);
                                }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                                    System.out.println(" Error :: cannot add number with string.");
                                    System.exit(0);
                                }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                                    setOut(node, new AStringValue());
                                }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                                    setOut(node, new ANumberValue());
                                }
                                //
                            }else if(getOut(function.getStatement()) instanceof ASubtractionExpression){
                                //
                                ASubtractionExpression subs = (ASubtractionExpression) getOut(function.getStatement());
                                PValue v1;
                                PValue v2;
                                if(!temp.containsKey(subs.getL().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(subs.getL().toString());
                                    v1 = (PValue) getOut(eq);
                                }else{
                                    v1 = (PValue) temp.get(subs.getL().toString());
                                }
                                if(!temp.containsKey(subs.getR().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(subs.getR().toString());
                                    v2 = (PValue) getOut(eq);
                                }else{
                                    v2 = (PValue) temp.get(subs.getR().toString());
                                }
                                
                                if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                                    System.out.println(" Error :: cannot add string with number.");
                                    System.exit(0);
                                }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                                    System.out.println(" Error :: cannot add number with string.");
                                    System.exit(0);
                                }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                                    setOut(node, new AStringValue());
                                }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                                    setOut(node, new ANumberValue());
                                }
                                //
                            }else if(getOut(function.getStatement()) instanceof APowerExpression){
                                //
                                APowerExpression power = (APowerExpression) getOut(function.getStatement());
                                PValue v1;
                                PValue v2;
                                if(!temp.containsKey(power.getL().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(power.getL().toString());
                                    v1 = (PValue) getOut(eq);
                                }else{
                                    v1 = (PValue) temp.get(power.getL().toString());
                                }
                                if(!temp.containsKey(power.getR().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(power.getR().toString());
                                    v2 = (PValue) getOut(eq);
                                }else{
                                    v2 = (PValue) temp.get(power.getR().toString());
                                }
                                
                                if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                                    System.out.println(" Error :: cannot add string with number.");
                                    System.exit(0);
                                }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                                    System.out.println(" Error :: cannot add number with string.");
                                    System.exit(0);
                                }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                                    setOut(node, new AStringValue());
                                }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                                    setOut(node, new ANumberValue());
                                }
                                //
                            }else if(getOut(function.getStatement()) instanceof AStringValue){
                                AStringValue str = (AStringValue) getOut(function.getStatement());
                                setOut(node, str);
                            }else if(getOut(function.getStatement()) instanceof ANumberValue){
                                ANumberValue num = (ANumberValue) getOut(function.getStatement());
                                setOut(node, num);
                            }else if(getOut(function.getStatement()) instanceof AId2Expression){
                                AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(function.getStatement().toString());
                                PValue v1 = (PValue) getOut(eq);
                                setOut(node, v1);
                            }
                        }else{
                            System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name);
                            System.exit(0);
                        }
                    }else{
                        int number_of_given_args = args_func_call.get(0).toString().split(" ").length;
                        int number_of_func_args = func_arguments.get(0).toString().split(" ").length;
                        if(number_of_given_args == number_of_func_args){
                            AArgument arg = (AArgument) func_arguments.get(0);
                            LinkedList commaId = arg.getCommaIdentifier();
                            ACommaIdentifier argument;
                            TId first_arg = arg.getId();
                            AArglist args = (AArglist) args_func_call.get(0);                          
                            LinkedList rest_args = args.getCommaExpression();
                            //
                            if(args.getExpression() instanceof AId2Expression){
                                AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(args.getExpression().toString());
                                PValue v1 = (PValue) getOut(eq);
                                temp.put(first_arg.toString(), v1);
                            }else{
                                AValExpression first_given_arg = (AValExpression) args.getExpression();
                                temp.put(first_arg.toString(), first_given_arg.getValue());
                            }
                            //
                            for(int i=0; i<number_of_given_args-1; i++){
                                argument = (ACommaIdentifier) commaId.get(i);
                                ACommaExpression comma = (ACommaExpression) rest_args.get(i);
                                if(comma.getExpression() instanceof AId2Expression){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(comma.getExpression().toString());
                                    PValue v2 = (PValue) getOut(eq);
                                    temp.put(argument.getId().toString(), v2);
                                }else{
                                    AValExpression current_arg = (AValExpression) comma.getExpression();
                                    temp.put(argument.getId().toString(), current_arg.getValue());
                                }
                            }
                            if(getOut(function.getStatement()) instanceof AAdditionExpression){
                                //
                                AAdditionExpression adds = (AAdditionExpression) getOut(function.getStatement());
                                PValue v1;
                                PValue v2;
                                if(!temp.containsKey(adds.getL().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(adds.getL().toString());
                                    v1 = (PValue) getOut(eq);
                                }else{
                                    v1 = (PValue) temp.get(adds.getL().toString());
                                }
                                if(!temp.containsKey(adds.getR().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(adds.getR().toString());
                                    v2 = (PValue) getOut(eq);
                                }else{
                                    v2 = (PValue) temp.get(adds.getR().toString());
                                }
                                
                                if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                                    System.out.println(" Error :: cannot add string with number.");
                                    System.exit(0);
                                }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                                    System.out.println(" Error :: cannot add number with string.");
                                    System.exit(0);
                                }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                                    setOut(node, new AStringValue());
                                }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                                    setOut(node, new ANumberValue());
                                }
                                //
                            }else if(getOut(function.getStatement()) instanceof ADivisionExpression){
                                //
                                ADivisionExpression divs = (ADivisionExpression) getOut(function.getStatement());
                                PValue v1;
                                PValue v2;
                                if(!temp.containsKey(divs.getL().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(divs.getL().toString());
                                    v1 = (PValue) getOut(eq);
                                }else{
                                    v1 = (PValue) temp.get(divs.getL().toString());
                                }
                                if(!temp.containsKey(divs.getR().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(divs.getR().toString());
                                    v2 = (PValue) getOut(eq);
                                }else{
                                    v2 = (PValue) temp.get(divs.getR().toString());
                                }
                                
                                if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                                    System.out.println(" Error :: cannot add string with number.");
                                    System.exit(0);
                                }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                                    System.out.println(" Error :: cannot add number with string.");
                                    System.exit(0);
                                }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                                    setOut(node, new AStringValue());
                                }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                                    setOut(node, new ANumberValue());
                                }
                                //
                            }else if(getOut(function.getStatement()) instanceof ASubtractionExpression){
                                //
                                ASubtractionExpression subs = (ASubtractionExpression) getOut(function.getStatement());
                                PValue v1;
                                PValue v2;
                                if(!temp.containsKey(subs.getL().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(subs.getL().toString());
                                    v1 = (PValue) getOut(eq);
                                }else{
                                    v1 = (PValue) temp.get(subs.getL().toString());
                                }
                                if(!temp.containsKey(subs.getR().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(subs.getR().toString());
                                    v2 = (PValue) getOut(eq);
                                }else{
                                    v2 = (PValue) temp.get(subs.getR().toString());
                                }
                                
                                if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                                    System.out.println(" Error :: cannot add string with number.");
                                    System.exit(0);
                                }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                                    System.out.println(" Error :: cannot add number with string.");
                                    System.exit(0);
                                }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                                    setOut(node, new AStringValue());
                                }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                                    setOut(node, new ANumberValue());
                                }
                                //
                            }else if(getOut(function.getStatement()) instanceof APowerExpression){
                                //
                                APowerExpression power = (APowerExpression) getOut(function.getStatement());
                                PValue v1;
                                PValue v2;
                                if(!temp.containsKey(power.getL().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(power.getL().toString());
                                    v1 = (PValue) getOut(eq);
                                }else{
                                    v1 = (PValue) temp.get(power.getL().toString());
                                }
                                if(!temp.containsKey(power.getR().toString())){
                                    AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(power.getR().toString());
                                    v2 = (PValue) getOut(eq);
                                }else{
                                    v2 = (PValue) temp.get(power.getR().toString());
                                }
                                
                                if (v1 instanceof AStringValue && v2 instanceof ANumberValue){
                                    System.out.println(" Error :: cannot add string with number.");
                                    System.exit(0);
                                }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                                    System.out.println(" Error :: cannot add number with string.");
                                    System.exit(0);
                                }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                                    setOut(node, new AStringValue());
                                }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                                    setOut(node, new ANumberValue());
                                }
                                //
                            }else if(getOut(function.getStatement()) instanceof AStringValue){
                                AStringValue str = (AStringValue) getOut(function.getStatement());
                                setOut(node, str);
                            }else if(getOut(function.getStatement()) instanceof ANumberValue){
                                ANumberValue num = (ANumberValue) getOut(function.getStatement());
                                setOut(node, num);
                            }else if(getOut(function.getStatement()) instanceof AId2Expression){
                                AEqualsStatement eq = (AEqualsStatement) variables_symtable.get(function.getStatement().toString());
                                PValue v1 = (PValue) getOut(eq);
                                setOut(node, v1);
                            }
                        }else{
                            System.out.println("Error : in line " + line + " wrong number of arguments for function " + func_name +". Takes " + number_of_func_args + ", were given "+ number_of_given_args);
                            System.exit(0);
                        }
                    }
                }    
            }
        }else{
            System.out.println("Error : in line " + line + " function " + func_name + "is not defined. ");
            System.exit(0);
        }
    }

    //tsekare oles tis periptwseis an einai addition, subtract, power klp h apla id kai kane setOut afto
    //sthn outAFunction kane getOut to return statement kai me bash to arglist prospa8ise na breis an ola einai ok h oxi.
    public void outAReturnStatement(AReturnStatement node){
        if(node.getExpression() instanceof AValExpression){
            AValExpression val = (AValExpression) node.getExpression();
            setOut(node, val.getValue());
        }else{
            setOut(node, node.getExpression());
        }
    }

    public void outAEqualsStatement(AEqualsStatement node) {
        String varName = node.getId().toString();
        PValue type = (PValue) getOut(node.getExpression());
        setOut(node, type);
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
            temp.put(node.getId().toString(), val.getValue());
        }
        for(int i=0; i<commaId1.size(); i++){
            argument1 = (ACommaIdentifier) commaId1.get(i);
            eqval1 = argument1.getEqualValue();
            if (eqval1.size() != 0){
                AEqualValue val = (AEqualValue) eqval1.get(0);
                temp.put(argument1.getId().toString(), val.getValue());
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
                System.exit(0);
            }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                System.out.println(" Error :: cannot add number with string.");
                System.exit(0);
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
                System.exit(0);
            }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                System.out.println(" Error :: cannot divide number with string.");
                System.exit(0);
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
                System.exit(0);
            }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                System.out.println(" Error :: cannot subtract number with string.");
                System.exit(0);
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
                System.exit(0);
            }else if(v1 instanceof ANumberValue && v2 instanceof AStringValue){
                System.out.println(" Error :: cannot use power with: string and number.");
                System.exit(0);
            }else if (v1 instanceof AStringValue && v2 instanceof AStringValue){
                setOut(node, new AStringValue());
            }else if (v1 instanceof ANumberValue && v2 instanceof ANumberValue){
                setOut(node, new ANumberValue());
            }
        }
    }

}