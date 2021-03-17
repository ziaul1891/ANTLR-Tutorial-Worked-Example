package expression;


import java.util.ArrayList;
import java.util.List;

import antlr.ExprBaseVisitor;
import antlr.ExprParser.ProgramContext;

public class AntlrToProgram extends ExprBaseVisitor<Program>{

	public List<String> sementicErrors; //to be accessed by main application
	
	@Override
	public Program visitProgram(ProgramContext ctx) {
		Program prog=new Program();
		sementicErrors = new ArrayList<>();
		AntlrToExpression exprVisitor=new AntlrToExpression(sementicErrors); // a helper visitor for transforming each subtree into an Expression object
		for (int i=0;i<ctx.getChildCount();i++)
		{
			if(i==ctx.getChildCount()-1) {
				/*last child of the start symbol prog is EOF */
				//do not visit this child and attemp to convert it to Expression object
				
			}
			else
			{
				prog.addExpression(exprVisitor.visit(ctx.getChild(i)));
			}
		}
		return prog;
	}

}
