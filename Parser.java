/* Public class Parser
method isValid determines whether an arithmetic expression with +, -, parentheses, and integers is valid
*/

public class Parser {

    public static enum parseType {EXP, OP};


    /* Boolean isValid
     * returns a boolean stating whether an arithmetic string can be parsed
     * can read +, *, integers, and parentheses
     */
    public static boolean isValid(String str) {

	if(str.length() == 0) return false;

	boolean valid = true; //return boolean
	parseType previousType = parseType.EXP; //EXP can be followed by EXP or OP

	// remove all blank spaces in the string we're parsing
	str = str.replaceAll(" ","");

	int counter = 0; //keeps track of our place in the string

	while(counter < str.length()) {
	    char currentChar = str.charAt(counter);

	    if(currentChar == '*' || currentChar == '+') {

		if(previousType == parseType.OP) return false;
		else {
		    valid = false; //can't end with an operation
		    previousType = parseType.OP;
		    counter++;
		    continue;
		}

	    }

	    else if(isDigit(currentChar)) {
		valid = true; //can end with an operation
		previousType = parseType.EXP;
		while(true) {
		    counter++;
		    if(counter >= str.length()) break;
		    if(!isDigit(str.charAt(counter))) break;
		}
	    }

	    else if(currentChar == '(') {

		valid = true; //can end with an expression enclosed in parentheses
		//First, find the closing parentheses
		int openPar  = counter;
		int parCounter = 1; //+1 for '(', -1 for ')'
		counter++;
		while(counter < str.length()) {
		    if(str.charAt(counter) == '(') parCounter++;
		    if(str.charAt(counter) == ')') parCounter--;
		    if (parCounter == 0) { //We've found the closing parentheses
			String nextExpression = str.substring(openPar + 1, counter);
			boolean isSubValid = isValid(nextExpression);
			if(!isSubValid) return false;
			else {
			    counter++;
			    previousType = parseType.EXP;
			    break;
			}
		    }
		    counter++;
		    if(counter == str.length()) return false; //didn't find closing parentheses
		}

	    }

	    else return false;

	}

	return valid;

    }

    /*
    private static boolean parenMatch(String str) {
	int parenCounter = 0;

	for(int i = 0; i < str.length(); i++) {
	    char currentChar = str.charAt(i);
	    if (currentChar == '(') parenCounter++;
	    else if (currentChar == ')') parenCounter--;
	}

	if (parenCounter == 0) return true;
	return false;
    }
    */

    public static boolean isDigit(char c) {
	if ((int) c > 47 && (int) c < 58) return true;
	return false;
    }
}
