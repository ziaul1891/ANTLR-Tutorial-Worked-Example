package expression;

import java.util.*;

public class Program {
	public List<Expression> expressions;

	public Program() {
		this.expressions = new ArrayList<>();
	}
	
	public void addExpression(Expression e) {
		expressions.add(e);
	}

}
