package tokenizer;

import java.security.KeyStore.Builder;

public class RecursiveDescentParser {
	private Tokenizer tokenizer;
	
	private String currentToken;
	
	private StringBuilder conditionalExpression;
	
	public RecursiveDescentParser(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
		conditionalExpression = new StringBuilder();
	}
	
	public StringBuilder parse() {
		conditionalExpression();
		return conditionalExpression;
	}
	
	private void conditionalExpression() {
		conditionalTerm();
		while (currentToken.equalsIgnoreCase(SqlKeyword.OR)) {
			
			conditionalExpression.append(" OR ");
			conditionalTerm();
		}
	}
	
	private void conditionalTerm() {
		conditionalPrime();
		while (currentToken.equalsIgnoreCase(SqlKeyword.AND)) {
			
			conditionalExpression.append(" AND ");
			conditionalPrime();
		}
	}
	
	private void conditionalPrime() {
		currentToken = tokenizer.nextToken();
		conditionalExpression.append(" " + currentToken + " ");
		if (currentToken.equals(SqlKeyword.LEFT_PARENTHESE)) {
			conditionalExpression();			
		
			Assert.isTrue(currentToken.equals(SqlKeyword.RIGHT_PARENTHESE), "Expecting )");
			conditionalExpression.append(" " + currentToken + " ");
			
			currentToken = tokenizer.nextToken();
			return;
		}
			
		currentToken = tokenizer.nextToken();
		switch (currentToken.toUpperCase()) {
			case SqlKeyword.NOT:
				conditionalExpression.append(" " + currentToken + " ");
				currentToken = tokenizer.nextToken();
				
				Assert.isTrue(
						currentToken.equalsIgnoreCase(SqlKeyword.IN) || currentToken.equalsIgnoreCase(SqlKeyword.LIKE),
						"Required IN or LIKE");
				
				if (currentToken.equalsIgnoreCase(SqlKeyword.IN)) {
					conditionalExpression.append(" " + currentToken + " ");
					inExpression();
				} else if (currentToken.equalsIgnoreCase(SqlKeyword.LIKE)) {
					conditionalExpression.append(" " + currentToken + " ");
					likeExpression();
				}
				break;
			case SqlKeyword.IN:
				conditionalExpression.append(" " + currentToken + " ");
				inExpression();
				break;
			case SqlKeyword.LIKE:
				conditionalExpression.append(" " + currentToken + " ");
				likeExpression();
				break;
			case SqlKeyword.IS:
				conditionalExpression.append(" " + currentToken + " ");
				nullComparisionExpression();
				break;
			case SqlKeyword.EQUAL:
			case SqlKeyword.GREATER_THAN:
			case SqlKeyword.GREATER_THAN_OR_EQUAL:
			case SqlKeyword.LESS_THAN:
			case SqlKeyword.LESS_THAN_OR_EQUAL:
			case SqlKeyword.NOT_EQUAL:
				conditionalExpression.append(" " + currentToken + " ");
				comparisionExpression();
				currentToken = tokenizer.nextToken();
				break;
			default:
				throw new IllegalArgumentException("Unknown keyword: " + currentToken);
		}
	}
	
	private void inExpression() {
		currentToken = tokenizer.nextToken();
		Assert.isTrue(currentToken.equals(SqlKeyword.LEFT_PARENTHESE), "Missing ( ");
		conditionalExpression.append(" " + currentToken + " ");
		
		currentToken = tokenizer.nextToken();
		conditionalExpression.append(" " + currentToken + " ");
		currentToken = tokenizer.nextToken();
		while (currentToken.equals(SqlKeyword.COMMA)) {
			//append comma to result
			conditionalExpression.append(" " + currentToken + " ");
			
			//get next value
			currentToken = tokenizer.nextToken();
			conditionalExpression.append(" " + currentToken + " ");
			
			currentToken = tokenizer.nextToken();
		}
		
		Assert.isTrue(currentToken.equals(SqlKeyword.RIGHT_PARENTHESE), "Missing ) ");
		conditionalExpression.append(" " + currentToken + " ");
	}
	
	private void likeExpression() {
		currentToken = tokenizer.nextToken();
		conditionalExpression.append(" " + currentToken + " ");
	}
	
	private void nullComparisionExpression() {
		conditionalExpression.append(" NULL ");
	}
	
	private void comparisionExpression() {
		currentToken = tokenizer.nextToken();
		conditionalExpression.append(" " + currentToken + " ");
	}
	
}
