import java.util.*;

public class Driver {
	public static void main (String[] args) {
		Scanner kb = new Scanner (System.in); // required class to get user input 
		Postfix postfix = new Postfix (); // create instance of Postfix class for conversion and evaluation
		String infix; // for infix expression input

		do {
			/* PROMPT INFIX INPUT */
			infix = kb.nextLine ();

			/* CHECK IF USER OPTED TO QUIT */
			if (!infix.equalsIgnoreCase ("QUIT")) {
				/* CONVERT INFIX TO POSTFIX */
				postfix.convertToPostfix (infix);
				/* TO DISPLAY POSTFIX */
				System.out.println (postfix.getPostfix ());
				/* TO EVALUATE POSTFIX */
				postfix.evaluate ();
				/* TO DISPLAY RESULT */
				System.out.println (postfix.getResult () + "\n");
				/* RESET STACKS AND QUEUES FOR NEXT INFIX EXPRESSION INPUT */
				postfix.clear ();
			}
		} while (!infix.equalsIgnoreCase ("QUIT"));

		kb.close ();
	}
}
