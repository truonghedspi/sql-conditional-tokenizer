package tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;

public class Tokenizer {
	  private StreamTokenizer tokenizer;
	  
	  public Tokenizer(Reader r) {
		    tokenizer = new StreamTokenizer(r);
		    configTokenizer();
	  }
	  
	  private void configTokenizer() {
			tokenizer.resetSyntax();
			tokenizer.wordChars('a', 'z');
			tokenizer.wordChars('A', 'Z');
			tokenizer.wordChars('0', '9');
			tokenizer.wordChars('.', '.');
			tokenizer.wordChars('_', '_');
			tokenizer.wordChars('-', '-');
			tokenizer.wordChars('>', '>');
			tokenizer.wordChars('<', '<');
			tokenizer.wordChars('=', '=');
			tokenizer.wordChars('!', '!');
			tokenizer.whitespaceChars('\u0000', ' ');
			tokenizer.whitespaceChars('\n', '\t');
			tokenizer.quoteChar('\'');
			tokenizer.quoteChar('"');
			tokenizer.ordinaryChar(')');
			tokenizer.ordinaryChar('(');
			tokenizer.ordinaryChar(',');
	  }
	  
	  public String nextToken() {
		  try {
			int token = tokenizer.nextToken();
			String nextToken = null;
			switch(tokenizer.ttype) {
				case '\'':
					nextToken = tokenizer.sval;
					break;
				case ',':
					nextToken = ",";
					break;
				case ')':
					nextToken = ")";
					break;
				case '(':
					nextToken = "(";
					break;
				case StreamTokenizer.TT_WORD:
					nextToken = tokenizer.sval;
					break;
				default:
					throw new IllegalArgumentException("Invalid charactor type :" + ((char) tokenizer.ttype));
			}
			System.out.println("Token:" + nextToken);
			return nextToken;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		  
	  }
}
