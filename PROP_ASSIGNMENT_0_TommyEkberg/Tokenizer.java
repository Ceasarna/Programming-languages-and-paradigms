package prop.assignment0;

import java.io.IOException;

public class Tokenizer implements ITokenizer{

	private Scanner scanner = null;
	private Lexeme currentLexeme = null;
	private Lexeme nextLexeme = null;
	
	@Override
	public void open(String fileName) throws IOException, TokenizerException {
		scanner = new Scanner();
		scanner.open(fileName);
		scanner.moveNext();
		nextLexeme = getNextLexeme();

	}
	
	private void consumeWhiteSpaces() throws IOException {
		while (Character.isWhitespace(scanner.current())){
		    scanner.moveNext();
		}
	}
	private Lexeme getNextLexeme() throws IOException, TokenizerException  {
		consumeWhiteSpaces();
		
		Character character = scanner.current();
	
		if(character == Scanner.EOF) {
			return new Lexeme(character, Token.EOF);
		}else if(Character.isLetter(character)) {
			scanner.moveNext();
			return new Lexeme(character, Token.IDENT);
		}else if (Character.isDigit(character)) {
			double value = Character.getNumericValue(character);  
			return new Lexeme(value, Token.INT_LIT);
		}else if(character == '=') {
			return new Lexeme(character, Token.ASSIGN_OP);
		}else if(character == '+') {
			return new Lexeme(character, Token.ADD_OP);
		}else if(character == '-') {
			return new Lexeme(character, Token.SUB_OP);
		}else if(character == '/') {
			return new Lexeme(character, Token.DIV_OP);
		}else if(character == '*') {
			return new Lexeme(character, Token.MULT_OP);
		}else if(character == '(') {
			return new Lexeme(character, Token.LEFT_PAREN);
		}else if(character == ')') {
			return new Lexeme(character, Token.RIGHT_PAREN);
		}else if(character == '{') {
			return new Lexeme(character, Token.LEFT_CURLY);
		}else if(character == '}') {
			return new Lexeme(character, Token.RIGHT_CURLY);
		}else if(character == ';') {
			return new Lexeme(character, Token.SEMICOLON);
		}else {
			throw new TokenizerException("Unknown character: " + String.valueOf(character));
		}
	}
	

	@Override
	public Lexeme current() {
		return currentLexeme;
	}	
	
	@Override
	public void moveNext() throws IOException, TokenizerException {
		if(scanner == null) {
			throw new IOException("No open file");
		}
		currentLexeme = nextLexeme;
		if(nextLexeme.token() != Token.EOF) {
			nextLexeme = getNextLexeme();
			scanner.moveNext();
		}		
	}

	@Override
	public void close() throws IOException {
		if (scanner != null) {
			scanner.close();
		}
    }
	
}
