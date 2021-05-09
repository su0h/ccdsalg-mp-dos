import java.lang.Math; // for pow () method, executes exponential / power operation

public class Postfix {
	/**
	 * Stack used in storing the operands of an equation.
	 */
	private Stack operands;
	/**
	 * Stack used in storing the operators of an equation.
	 */
	private Stack operators;
	/**
	 * Queue used in storing the converted Postfix equation.
	 * Also used for evaluating the Postfix equation.
	 */
	private Queue postfixTokens;
	/**
	 * String containing the final converted Postfix equation.
	 */
	private String postfix;
	/**
	 * String containing the final result evaluted from
	 * the Postfix equation.
	 */
	private String result;

	public static final int[] ICP = {
		1, 2, 2, 3, 4, 4,
		4, 4, 6, 6, 7, 7,
		7, 9, 11, 12, -1,
		-1
	};
	public static final int[] ISP = {
		1, 2, 2, 3, 4, 4,
		4, 4, 6, 6, 7, 7,
		7, 8, 10, -1, -1,
		-1
	};
	public static final String[] VALID_OPERATORS = {
		"||", "!=", "==",
		"&&", ">", "<", ">=",
		"<=", "+", "-", "*",
		"%", "/", "^", "!",
		"(", ")"
	};

	/**
	 * Initializes a Postfix object to be used in Infix
	 * conversion and Postfix evaluation.
	 */
	public Postfix () {
		operands = new Stack();
		operators = new Stack();
		postfixTokens = new Queue();
		postfix = new String ();
		result = new String ();
	}

	/**
	 * Converts a String Infix equation input into
	 * a String Postfix equation.
	 *
	 * @param	infix	the Infix equation to be converted
	 */
	public void convertToPostfix (String infix) {
		String token = new String ();
		String dump = new String ();
		int i, k;
		i = k = 0;

		/* WHILE INPUT TOKEN IS NOT EMPTY */
		while (i < infix.length()) {
			//System.out.println (postfix + " | " + infix.substring (i, infix.length()) + " | " + operators.getTop());
			/* IF TOKEN IS A NUMBER */
			if (isNum (infix.charAt (i))) {
				k = i + 1;

				/* GET INDEX OF LAST OPERAND BEFORE NEXT OPERATOR */
				while (k < infix.length() && isNum (infix.charAt(k)))
					k+=1;

				/* ASSIGN SUBSTRING TO TOKEN */
				token = infix.substring (i, k);

				/* ENQUEUE TOKEN TO POSTFIX QUEUE */
				postfix += token + " "; // add to postfix expression string
				postfixTokens.enqueue (token); // enqueue to postfix queue

				/* SET i TO NEXT OPERAND */
				i = k;
			/* IF TOKEN IS AN OPERATOR */
			} else {
				/* EXTRACT OPERATOR */
				token = Character.toString(infix.charAt(i));
				/* IF DOUBLE-CHARACTER OPERATOR */
				if (!token.equalsIgnoreCase (")") &&
					hasValidSecondChar (infix.charAt(i+1)) &&
					i != infix.length()-1) {
					/* CONCAT SECOND OPERATOR */
					token += Character.toString (infix.charAt(i+1));
					/* ADJUST i */
					i+=1;
				}
				//System.out.println ("ICP[" + token + "]: " + getICP(token) + " vs " + "ISP[" + operators.getTop() + "] :" + getISP(operators.getTop()));
				/* IF ICP > ISP */
				if (getICP (token) > getISP (operators.getTop()))
					operators.push (token);
				/* IF ICP <= ISP */
				else {
					/* POP STACK UNTIL ICP > ISP OR IF "(" HAS BEEN ENCOUNTERED */
					while ((getICP (token) <= getISP (operators.getTop())) &&
						   !operators.stackEmpty() && !dump.equalsIgnoreCase ("(")) {
						//System.out.println (operators.getTop());
						dump = operators.pop();
						/* IF POPPED TOKEN IS NOT "(" NOR ")" */
						if (!(dump.equalsIgnoreCase ("(") ||
							dump.equalsIgnoreCase (")"))) {
							postfix += dump + " "; // add to postfix expression result
							postfixTokens.enqueue (dump); // add to postfix queue
						}
					}
					/* TO PREVENT ")" FROM BEING PUSHED INTO OPERATORS STACK */
					if (!token.equalsIgnoreCase (")"))
						operators.push (token);
				}
				//System.out.println (operators.getTop());
				/* SET i TO NEXT OPERAND */
				i+=1;
			}
			/* RESET TOKEN AND DUMP VAR */
			token = dump = "";
		}

		/* POP AND ENQUEUE REMAINING STACK ELEMENTS */
		while (!operators.stackEmpty()) {
			dump = operators.pop();
			if (!(dump.equalsIgnoreCase ("(") ||
				dump.equalsIgnoreCase (")"))) {
				postfix += dump + " ";
				postfixTokens.enqueue(dump);
			}
		}
	}

	/**
	 * Checks if a given character is a number or not.
	 *
	 * @param	c	character to be checked
	 * @return	returns true if c is a number, otherwise false.
	 */
	private boolean isNum (char c) {
		boolean temp = false;
		char[] nums = {'0', '1', '2', '3', '4',
					   '5', '6', '7', '8', '9'};

		for (int i = 0; i < 10; i++)
			if (c == nums[i])
				temp = true;

		return temp;
	}


	/**
	 * Checks if a given character is a valid secondary equation
	 * character. This method is used for double-character operators
	 * such as ">=", "==", "&&, and "||".
	 *
	 * @param	c	character to be checked
	 * @return	returns true if c is a valid second character, otherwise false.
	 */
	private boolean hasValidSecondChar (char c) {
		if (c == '=' || c == '&' || c == '|')
			return true;
		return false;
	}

	/**
	 * Gets the Incoming Priority (ICP) of an operator.
	 *
	 * @param	s	operator whose ICP is to be returned
	 * @return	an integer corresponding to the ICP of the operator.
	 */
	private int getICP (String s) {
		for (int i = 0; i < VALID_OPERATORS.length; i++)
			if (s.equalsIgnoreCase (VALID_OPERATORS[i]))
				return ICP[i];
		return -1;
	}

	/**
	 * Gets the In-Stack Priority (ISP) of an operator.
	 *
	 * @param	s	operator whose ISP is to be returned
	 * @return	an integer corresponding to the ISP of the operator.
	 */
	private int getISP (String s) {
		for (int i = 0; i < VALID_OPERATORS.length; i++)
			if (s.equalsIgnoreCase (VALID_OPERATORS[i]))
				return ISP[i];
		return -1;
	}

	/**
	 * Evaluates the Postfix equation stored in this object.
	 */
	public void evaluate () {
		int operand1, operand2;
		String operator;

		operand1 = operand2 = 0;

		/*
		   WHILE QUEUE HAS OPERATORS/OPERANDS INSIDE
		   AND THERE IS NO ZERO DIVISION/MODULO YET
		*/
		while (!postfixTokens.queueEmpty() &&
			     !operands.getTop().equalsIgnoreCase ("Division by zero error!") &&
				   !operands.getTop().equalsIgnoreCase ("Modulo zero error!")) {
			/* IF HEAD OF QUEUE IS A NUMBER */
			if (isNum (postfixTokens.getHead().charAt (0)))
				operands.push (postfixTokens.dequeue());
			/* IF HEAD OF QUEUE IS AN OPERATOR */
			else {
				/* DEQUEUE OPERATOR FROM QUEUE */
				operator = postfixTokens.dequeue();
				/* POP OPERANDS FROM STACK */
				operand2 = Integer.parseInt (operands.pop());
				/* if operator is unary "!", do not pop the second operand */
				if (!operands.stackEmpty() && !operator.equalsIgnoreCase ("!"))
					operand1 = Integer.parseInt (operands.pop());
				/* EVALUATE OPERANDS USING OPERATOR */
				operands.push (getResult (operand1, operand2, operator));
			}
		}

		/*
		   IF RESULT IS A NUMBER, CHECKS SECOND CHARACTER AS WELL IN CASE FIRST
		   CHARACTER IS "-" WHICH INDICATES THAT THE RESULT IS A NEGATIVE VALUE
		*/
		if (isNum (operands.getTop().charAt(0)) ||
			isNum (operands.getTop().charAt(1)))
			result += Integer.parseInt (operands.pop()); // parse to integer
		/* IF RESULT IS NOT A NUMBER (DIVISON BY 0) */
		else
			result += operands.pop();
	}

	/**
	 * Computes the result of a certain operation between two integers.
	 *
	 * @param	num1		first operand
	 * @param	num2		second operand
	 * @param	operator	String representation of operator to be used
	 * @return	a String value corresponding to the result of the computation.
	 */
	private String getResult (int num1, int num2, String operator) {
		int operatorIndex = 0;
		boolean bool1 = false;
		boolean bool2 = false;

		/* GET INDEX OF OPERATOR TO BE USED */
		for (int i = 0; i < VALID_OPERATORS.length; i++)
			if (operator.equalsIgnoreCase (VALID_OPERATORS[i]))
				operatorIndex = i;

		/* GET BOOLEAN VALUES OF NUMBERS */
		bool1 = intToBoolean (num1);
		bool2 = intToBoolean (num2);

		/* ACTUAL COMPUTATION */
		switch (operatorIndex) {
			case 0: return booleanToNum (bool1 || bool2);
			case 1:	return booleanToNum (num1 != num2);
			case 2: return booleanToNum (num1 == num2);
			case 3:	return booleanToNum (bool1 && bool2);
			case 4:	return booleanToNum (num1 > num2);
			case 5:	return booleanToNum (num1 < num2);
			case 6:	return booleanToNum (num1 >= num2);
			case 7:	return booleanToNum (num1 <= num2);
			case 8: return Integer.toString (num1 + num2);
			case 9: return Integer.toString (num1 - num2);
			case 10: return Integer.toString (num1 * num2);
			case 11: return num2 == 0 ? "Modulo zero error!" : Integer.toString (num1 % num2);
			case 12: return num2 == 0 ? "Division by zero error!" : Integer.toString (num1 / num2);
			case 13: 
			int pow = (int) Math.pow ((double) num1, (double) num2);
			System.out.println ("POWPOWPOW: " + pow);
			//return Integer.toString (pow);
			return (num1 == 0 && (num2 < 0)) ? "Division by zero error!" : Integer.toString (pow);
			case 14: return booleanToNum (!bool2);
			default: return "-1";
		}
	}

	/**
	 * Converts a given integer input into a boolean value.
	 *
	 * @param	num	number to be converted
	 * @return	returns true if integer corresponds to true, otherwise false.
	 */
	private boolean intToBoolean (int num) {
		return num == 0 ? false : true;
	}

	/**
	 * Converts a given boolean input into its numerical equivalent.
	 *
	 * @param	bool	the boolean input to be converted
	 * @return	a String corresponding to the numerical value of the boolean input.
	 */
	private String booleanToNum (boolean bool) {
		return bool == true ? "1" : "0";
	}

	/**
	 * Returns the Postfix equation computed by this object.
	 *
	 * @return	a String that contains the Postfix equation.
	 */
	public String getPostfix () {
		return postfix;
	}

	/**
	 * Returns the evaluated result of the Postfix equation.
	 *
	 * @return	a String that contains the result of the Postfix evaluation.
	 */
	public String getResult () {
		return result;
	}

	/**
	 * Reinstantiates the attributes of this object to accommodate
	 * next equations to be processed.
	 */
	public void clear () {
		operands = new Stack();
		operators = new Stack();
		postfixTokens = new Queue();
		postfix = new String ();
		result = new String ();
	}
}
