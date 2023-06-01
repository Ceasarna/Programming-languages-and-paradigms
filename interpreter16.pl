
/***
   Tommy Ekberg
   toek3476
   1/10/2023

   I did not attempt the Grammar2. This code should work for Grammar1. Evaluator almost works, the printing at the end is a bit different.   
 */

/***
A skeleton for Assignment 2 on PROP HT2020.
Peter Idestam-Almquist, 2020-12-09.
***/

/*** 
Load the tokenizer (tokenize/2) and the file_writer (write_to_file/3).
***/
:- [tokenizer].
:- [filewriter].

/***
The top level predicate run/2 of the solution.
To be called like this:
?- run('program1.txt','parsetree1.txt').
***/

run(InputFile,OutputFile):-
	tokenize(InputFile,Program),
	parse(ParseTree,Program,[]),
	evaluate(ParseTree,[],VariablesOut),
	write_to_file(OutputFile,ParseTree,VariablesOut).

parse(assignment(Ident, Op, Value, End)) -->
        ident(Ident),
        assign_op(Op), 
        expression(Value),
        semicolon(End).

ident(ident(Head), [Head | Tail], Tail) :-
        atom(Head).

expression(expression(Value)) -->
        term(Value).

expression(expression(Term, Op, Expr)) -->
        term(Term),
        add_op(Op),
        expression(Expr).

expression(expression(Term, Op, Expr)) -->
        term(Term),
        sub_op(Op),
        expression(Expr).

term(term(Value)) -->
	factor(Value).

term(term(Factor, Op, Term)) -->
        factor(Factor),
        mult_op(Op),
        term(Term).

term(term(Factor, Op, Term)) -->
        factor(Factor),
       	div_op(Op),
        term(Term).

factor(factor(left_paren, Value, right_paren)) -->
        ['('],
        expression(Value),
        [')'].

factor(factor(Value)) -->
        int(Value).

int(int(Value), [Value | Tail], Tail) :-
        number(Value).

assign_op(assign_op) --> [=].

semicolon(semicolon) --> [;]. 

add_op(add_op) --> [+].

sub_op(sub_op) --> [-].

mult_op(mult_op) --> [*]. 

div_op(div_op) --> [/].




evaluate(ParseTree, In, Value):-
	interpret(ParseTree, Value, VariablesIn, VariablesOut).

interpret(assignment(Id, Op, Expr, End), [IdName, Value] ) --> 
	interpret(Id, IdName),
	[=],
	interpret(Expr, Value),
	[;].

interpret(expression(Term), Value) --> interpret(Term, Value).

interpret(expression(Term, add_op, Expr), Value) -->
  interpret(Term, FirstValue),
  interpret(Expr, SecondValue),
  { Value is FirstValue + SecondValue }.

interpret(expression(Term, sub_op, Expr), Value) -->
  interpret(Term, FirstValue),
  interpret(Expr, SecondValue),
  { Value is FirstValue - SecondValue }.

interpret(term(Factor), Value) --> interpret(Factor, Value).

interpret(term(Factor, mult_op, Expr), Value) -->
  interpret(Factor, FirstValue),
  interpret(Expr, SecondValue),
  { Value is FirstValue * SecondValue }.

interpret(term(Factor, div_op, Expr), Value) -->
  interpret(Factor, FirstValue),
  interpret(Expr, SecondValue),
  { Value is FirstValue / SecondValue }.

interpret(factor(Int), Value) --> 
	interpret(Int, Value).

interpret(factor(left_paren, Expr, right_paren), Value)-->
	['('],
	interpret(Expr, Value),
	[')'].

interpret(int(Value), Value) --> 
	[Value], {number(Value)}.

interpret(ident(Id), Id) -->
	[Id].

