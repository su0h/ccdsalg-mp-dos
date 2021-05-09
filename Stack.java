public class Stack
{
  private String[] arr;
  private int top;

  private static final int SIZE = 256; // max tokens that the stack can contain

  /**
   * Initalizes a new Stack object.
   *
   */
  public Stack ()
  {
    arr = new String[SIZE];
    top = 0;
  }

  /**
   * Pushes a String token into the stack.
   *
   * @param token the String token to be pushed
   */
  public void push (String token)
  {
    if (!stackFull ())
      arr[top++] = token;
    else System.out.println ("Stack Full!");
  }

  /**
   * Pops the top String token from the stack.
   *
   * @return  the String token if the stack is not empty, 
   *          otherwise return "?".
   */
  public String pop ()
  {
    if (!stackEmpty ())
      return arr[--top];
    else
      return "?";
  }

  /**
   * Returns the top String token of the stack.
   *
   * @return  the top String token of the stack if the
   *          stack is not empty, otherwise return "?".
   */
  public String getTop ()
  {
    if (!stackEmpty ())
      return arr[top - 1];
    else
      return "?";
  }

  /**
   * Returns the whole stack array.
   *
   * @return  the stack containing the tokens.
   */
  public String[] getTokens ()
  {
    return arr;
  }

  /**
   * Returns the maximum size of the stack array
   * containing the tokens.
   *
   * @return  the maximum size of the stack array.
   */
	public int getMaxArrSize () 
	{
	   return SIZE;
	}

  /**
   * Checks if the stack array is already full.
   *
   * @return  returns true if the stack array is full, otherwise false.
   */
	public boolean stackFull ()
	{
	   return top == SIZE;
	}

  /**
   * Checks if the stack array is empty.
   *
   * @return  returns true if the stack array is empty, otherwise false.
   */
	public boolean stackEmpty ()
	{
	   return top == 0;
	}
}