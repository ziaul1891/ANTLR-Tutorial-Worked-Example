grammar Expr;
/* the grammar name and file name must match */
@header {
	package antlr;
}

//import antlr package in every java file
// start variable
prog: (decl | expr)+ EOF
	;	
decl: ID ':' INT_TYPE '=' NUM
	;	
/* ANTLR resolves ambiguities in favor of the alternative given first */
expr: expr '*' expr
	| expr '+' expr
	| ID
	| NUM
	;	
/* Tokens */
ID: [a-z][a-zA-Z0-9_]*; //identifiers
NUM: '0' | '-'?[1-9][0-9]*;
INT_TYPE: 'INT';
COMMENT: '--' ~[\r\n]* ->skip;
WS: [ \t\n]+ ->skip;