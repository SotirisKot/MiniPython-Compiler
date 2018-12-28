import java.io.*;
import minipython.lexer.Lexer;
import minipython.parser.Parser;
import minipython.node.Start;
import java.util.*;


public class ParserTest2
{
  public static void main(String[] args)
  {
    try
    {
      Parser parser = 
        new Parser(
        new Lexer(
        new PushbackReader(
        new FileReader(args[0].toString()), 1024)));

      Hashtable variables_symtable =  new Hashtable();
      Hashtable functions_symtable =  new Hashtable();
      Start ast = parser.parse();
      myvisitor1 visitor1 = new myvisitor1(variables_symtable, functions_symtable);
      ast.apply(visitor1);
      //System.out.println(ast);
      myvisitor2 visitor2 = new myvisitor2(visitor1.getVariableTable(), visitor1.getFunctionTable());
      ast.apply(visitor2);
    }
    catch (Exception e)
    {
      System.err.println(e);
    }
  }
}

