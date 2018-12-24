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

      Hashtable symtable =  new Hashtable();
      Start ast = parser.parse();
      ast.apply(new myvisitor1(symtable));
      //System.out.println(ast);
    }
    catch (Exception e)
    {
      System.err.println(e);
    }
  }
}

