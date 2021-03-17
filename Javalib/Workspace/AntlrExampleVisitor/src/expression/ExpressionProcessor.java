package expression;

import java.util.*;

public class ExpressionProcessor {
	
	List<Expression> list;
	public Map<String,Integer> values; //symbol table for storing values of variables
	
	public ExpressionProcessor(List<Expression>list) {
		this.list=list;
		values=new HashMap<>();
	}
	
	public List<String> getEvaluationResults(){
		List<String> evaluations = new ArrayList<>();
		
		for(Expression e:list) {
			if (e instanceof VariableDeclaration) {
				VariableDeclaration decl=(VariableDeclaration) e;
				values.put(decl.id, decl.value);
			}
			else //e instanceof Number, Variable, Addition, Subtraction
			{
				String input = e.toString();
				int result = getEvalResult(e);
				evaluations.add(input+ " is "+ result);
			}
		}
		
		return evaluations;
	}
	
	private int getEvalResult(Expression e) {
		int result = 0;
		if(e instanceof Number) {
			Number num = (Number) e;
			result = num.num;
		}
		else if (e instanceof Variable) {
			Variable var = (Variable) e;
			result = values.get(var.id);
		}
		else if (e instanceof Addition) {
			Addition add = (Addition) e;
			int left = getEvalResult(add.left);
			int right = getEvalResult(add.right);
			result = left + right;
		}
		else
		{
			Multiplication mul = (Multiplication) e;
			int left = getEvalResult(mul.left);
			int right = getEvalResult(mul.right);
			result = left * right;
		}
		
		return result;
	}

}
