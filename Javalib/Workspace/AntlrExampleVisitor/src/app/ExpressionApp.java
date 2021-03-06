package app;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import antlr.ExprLexer;
import antlr.ExprParser;
import expression.AntlrToProgram;
import expression.ExpressionProcessor;
import expression.MyErrorListener;
import expression.Program;

public class ExpressionApp {
	
	public static void main(String[] args) {
		
		if(args.length!=1) {
			System.err.print("Usage: file name");
		}
		else
		{
			String fileName = args[0];
			ExprParser parser=getParser(fileName);
			
			
			//tell ANTLR to build a parse tree
			//parase from the start symbol 'prog'
			ParseTree antlrAST =  parser.prog();
			
			if(MyErrorListener.hasError) {
				//let the syntax error be reported
			}
			else {
			
			AntlrToProgram progVisitor = new AntlrToProgram();
			Program prog = progVisitor.visit(antlrAST);
		
			
			if(progVisitor.sementicErrors.isEmpty()) {
				ExpressionProcessor ep = new ExpressionProcessor(prog.expressions);
				for (String evaluation: ep.getEvaluationResults()) {
					System.out.println(evaluation);
				}
			}
			else {
				for(String err: progVisitor.sementicErrors) {
					System.out.println(err);
				}
			}
			}
			
		}
		
	}
	
	private static ExprParser getParser(String fileName) {
		ExprParser parser = null;
		try {
			CharStream input = CharStreams.fromFileName(fileName);
			ExprLexer lexer = new ExprLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			parser = new ExprParser(tokens);
			
			//syntax error handling:
			
			parser.removeErrorListeners();
			parser.addErrorListener(new MyErrorListener());
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return parser;
	}

}
