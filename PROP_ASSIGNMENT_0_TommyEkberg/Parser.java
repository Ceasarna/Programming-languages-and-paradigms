package prop.assignment0;

import java.io.IOException;

public class Parser implements IParser{
	
	int flag = 0;
	Tokenizer tok = null;
	StringBuilder builder = new StringBuilder();
	int tabs = 1;
	
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		tok = new Tokenizer();
		tok.open(fileName);
		tok.moveNext();
	}

	@Override
	  public INode parse() throws IOException, TokenizerException, ParserException {
	    if (tok == null) {
	    	throw new IOException("No open file.");
	    }
	    return new AssignmentNode(tok);
	  }

	@Override
	public void close() throws IOException {
	     if (tok != null) {
	    	 tok.close();
	     }
   }
	
	class AssignmentNode implements INode{
		
		double value = 0;
		Lexeme id = null;
		Lexeme equal = null;
		ExpressionNode expn = null;
		Lexeme semiColon = null;
		
		public AssignmentNode(Tokenizer tok) {
			try {
				id = tok.current();
				tok.moveNext();
				
				equal = tok.current();
				tok.moveNext();
				
			    } catch (Exception e) {
				System.out.println(e);
			    }

			expn = new ExpressionNode(tok);

			try {
				semiColon = tok.current();
				tok.moveNext();
				
			    } catch (Exception e) {
				System.out.println(e);
			    }	
			}
		
		public void buildString(StringBuilder builder, int tabs) {

			builder.append("AssignmentNode\n");
			
			builder.append("\t".repeat(tabs) + id.toString() + "\n");
			builder.append("\t".repeat(tabs) + equal.toString() + "\n");
			
		    expn.buildString(builder, tabs + 1);
		    
		    builder.append("\t".repeat(tabs) + semiColon.toString() + "\n");
		    }

		@Override
		public Object evaluate(Object[] args) throws Exception {

			value = (double) expn.evaluate(null);
			
			return value;
		}
	}

		class ExpressionNode implements INode{
			
			double value = 0;
			TermNode tn = null;
			Lexeme plusMinus = null;
			ExpressionNode expn = null;
			
			public ExpressionNode(Tokenizer tok) {
				tn = new TermNode(tok);
				
				if(tok.current().token() == Token.ADD_OP || tok.current().token() == Token.SUB_OP) {
					try {
						plusMinus = tok.current();
						tok.moveNext();
						
					    } catch (Exception e) {
						System.out.println(e);
					    }
					
					expn = new ExpressionNode(tok);
				}
			}	
			
			public void buildString(StringBuilder builder, int tabs) {
				builder.append("\t".repeat(tabs) + "ExpressionNode\n");
				tn.buildString(builder, tabs + 1);
				if(plusMinus != null) {
					builder.append("\t".repeat(tabs + 1) + plusMinus.toString() + "\n");
					expn.buildString(builder, tabs + 1);
				}				
			}

			@Override
			public Object evaluate(Object[] args) throws Exception {
				double plusMinusValue = 0;
				value = (double) tn.evaluate(null);
				if(plusMinus != null) {
					plusMinusValue = (double) expn.evaluate(null);
				
					if(plusMinus.token() == Token.SUB_OP) {
						return value - plusMinusValue;
					}
				}
				return value + plusMinusValue;
			}
		}
		
		class TermNode implements INode{
			
			double value = 0;
			FactorNode fn = null;
			Lexeme multDiv = null;
			TermNode tn = null;
			
			public TermNode(Tokenizer tok) {
				fn = new FactorNode(tok);
				
				if(tok.current().token() == Token.MULT_OP || tok.current().token() == Token.DIV_OP) {
					try {
						System.out.println(tok.current().token() + " TERMNODE");
						multDiv = tok.current();
						tok.moveNext();
						
					    } catch (Exception e) {
						System.out.println(e);
					    }
					
					tn = new TermNode(tok);
				}
			}	
			
			public void buildString(StringBuilder builder, int tabs) {
				
				builder.append("\t".repeat(tabs) + "TermNode\n");
				fn.buildString(builder, tabs + 1);
				if(multDiv != null) {
					builder.append("\t".repeat(tabs + 1) + multDiv.toString() + "\n");
					tn.buildString(builder, tabs + 1);
				}
			}

			@Override
			public Object evaluate(Object[] args) throws Exception {
				double multDivValue = 1;
				value = (double) fn.evaluate(null);
				
				if(multDiv != null) {
					multDivValue = (double) tn.evaluate(null);
					if(multDiv.token() == Token.DIV_OP) {
						return value / multDivValue;
					}
				}
				
				return value * multDivValue;
			}
				
		}
		
		class FactorNode implements INode{
			double value = 0;
			Lexeme i = null;
			Lexeme leftBracket = null;
			ExpressionNode expn = null;
			Lexeme rightBracket = null;

			public FactorNode(Tokenizer tok) {
				if(tok.current().token() == Token.INT_LIT) {
					try {
						i = tok.current();
						tok.moveNext();
						
						
					    } catch (Exception e) {
					    	System.out.println(e);
					    }
				}else if(tok.current().token() == Token.LEFT_PAREN){
					try {
						leftBracket = tok.current();
						tok.moveNext();
						
						expn = new ExpressionNode(tok);
						
						rightBracket = tok.current();
						tok.moveNext();
						flag++;
						
					    } catch (Exception e) {
					    	System.out.println(e);
					    }					
				}
			}
			
			public void buildString(StringBuilder builder, int tabs) {
				
				builder.append("\t".repeat(tabs) + "FactorNode\n");
				if(i != null) {
					builder.append("\t".repeat(tabs + 1) + i.toString() + "\n");
				}else{
					builder.append("\t".repeat(tabs + 1) + leftBracket.toString() + "\n");
					expn.buildString(builder, tabs + 1);
					builder.append("\t".repeat(tabs + 1) +rightBracket.toString() + "\n");
				}
			}

			@Override
			public Object evaluate(Object[] args) throws Exception {
				if(i != null) {
					return (double) i.value();
				}
				else {
					
					value = (double) expn.evaluate(null);
					return value;
				}
			}
		}
	}

