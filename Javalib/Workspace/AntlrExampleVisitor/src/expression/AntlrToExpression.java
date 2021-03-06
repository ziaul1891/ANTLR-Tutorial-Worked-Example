package expression;

import java.util.*;

import org.antlr.v4.runtime.Token;

import antlr.ExprBaseVisitor;
import antlr.ExprParser.AdditionContext;
import antlr.ExprParser.DeclarationContext;
import antlr.ExprParser.MultiplicationContext;
import antlr.ExprParser.NumberContext;
import antlr.ExprParser.VariableContext;

public class AntlrToExpression extends ExprBaseVisitor<Expression>{

	/*
	 * given that all visit_* methods are called in a top-down fashion
	 */
	private List<String> vars; //stores all the variables declared in the program so far
	private List<String> sementicErrors; //duplicate //undeclaration
	
	
	public AntlrToExpression(List<String> sementicErrors) {
		vars=new ArrayList<>();
		this.sementicErrors=sementicErrors;
		
	}
	
	
	@Override
	public Expression visitDeclaration(DeclarationContext ctx) {
		// ID() is a method generated to correspond to the token ID in the source grammar
		Token idToken = ctx.ID().getSymbol(); //equivlaent to ctx.getChild(0).getSymbol(); ID lekha jacche karon grammar e ID lekha decl er vitor
		int line=idToken.getLine();
		int column=idToken.getCharPositionInLine()+1;
		
		String id = ctx.getChild(0).getText();
		if(vars.contains(id)) {
			sementicErrors.add("Error: variable " + id + " already declared (" + line + ", "+column+ ")");
		}
		else
		{
			vars.add(id);
		}
		
		String type = ctx.getChild(2).getText();
		int value = Integer.parseInt(ctx.NUM().getText());
		return new VariableDeclaration(id,type,value); //expression er under e thaka class
	}

	@Override
	public Expression visitMultiplication(MultiplicationContext ctx) {
		Expression left = visit(ctx.getChild(0)); //recursively visit the left subtree of the current multiplication node
		Expression right = visit(ctx.getChild(2)); //recursively visit the right subtree of the current multiplication node
		return new Multiplication(left,right);
	}

	@Override
	public Expression visitAddition(AdditionContext ctx) {
		Expression left = visit(ctx.getChild(0)); //recursively visit the left subtree of the current multiplication node
		Expression right = visit(ctx.getChild(2)); //recursively visit the right subtree of the current multiplication node
		return new Addition(left,right);
	}

	@Override
	public Expression visitVariable(VariableContext ctx) {
		Token idToken = ctx.ID().getSymbol();
		int line=idToken.getLine();
		int column=idToken.getCharPositionInLine()+1;
		
		String id=ctx.getChild(0).getText();
		if(!vars.contains(id)) {
			sementicErrors.add("Error: variable " + id + " not declared (" + line + ", "+column+ ")");
		}
		return new Variable(id);
		
	}

	@Override
	public Expression visitNumber(NumberContext ctx) {
		String numText = ctx.getChild(0).getText();
		int num = Integer.parseInt(numText);
		return new Number(num);
	}
	

}
