package prop.assignment0;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, TokenizerException, ParserException {

		String inputFileName = "C:\\Users\\tommy\\eclipse-workspace\\PROP_ASSIGNMENT_0\\src\\program1.txt";
		Parser p = new Parser();

		try {
		    p.open(inputFileName);
		    INode root = p.parse();
		    StringBuilder b = new StringBuilder();
		    root.buildString(b, 1);
		    System.out.println(b);
		    double a = (double) root.evaluate(null);
		    System.out.println("Evaluate: " + a);
		    p.close();
		}
		catch (Exception exception) {
		    System.out.println("EXCEPTION: " + exception);
		}
	}
}