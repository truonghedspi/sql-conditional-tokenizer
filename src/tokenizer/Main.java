package tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class Main {
	public static void main(String[] args) throws IOException {
		Reader reader = new StringReader(
				"(last_Name0 >= 0 OR address.capital = 'Ha )noi' )  firstName IN ('Nguyen', 'Quoc', '%Truong') () ");
		Tokenizer tokenizer = new Tokenizer(reader);
		
		RecursiveDescentParser parser = new RecursiveDescentParser(tokenizer);
		StringBuilder res = parser.parse();
		System.out.println(res.toString());
	}
}
