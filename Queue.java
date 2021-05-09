public class Queue
{
  private String[] arr;
  private int head;
  private int tail;

  private static final int SIZE = 256; //max tokens

  /**
   * Initializes a new Queue object.
   */
  public Queue () {
    arr = new String [SIZE];
    head = 0;
    tail = 0;
  }

  /**
   * Enqueues String tokens into the queue.
   *
   * @param token the token to be enqueued
   */
  public void enqueue (String token) {
    if (!queueFull ())
      arr [tail++] = token;
    else System.out.println ("Queue Full!");
  }

  /**
   * Dequeues a String token from the queue.
   *
   * @return  a String representation of a token.
   */
  public String dequeue () {
    if (!queueEmpty ())
      return arr[head++];
    else {
      System.out.println ("Queue is Empty!");
      return "?";
    }
  }

  /**
   * Returns the token at the head of the queue.
   *
   * @return  the token at the head of the queue.
   */
  public String getHead () {
    if (!queueEmpty ())
      return arr [head];
    else{
      System.out.println ("Queue is Empty!");
      return "?";
    }
  }

  /**
   * Returns the token at the tail of the queue.
   *
   * @return  the token at the tail of the queue.
   */
  public String getTail () {
    if (!queueEmpty ())
      return arr [tail - 1];
	return "?";
  }

  /**
   * Returns the whole queue array.
   *
   * @return  the queue containing the tokens.
   */
  public String[] getTokens () {
    return arr;
  }

  /**
   * Returns the maximum size of the queue array
   * containing the tokens.
   *
   * @return  the maximum size of the queue array.
   */
  public int getMaxArrSize () {
    return SIZE;
  }

  /**
   * Checks if the queue array is already full.
   *
   * @return  returns true if the queue array is full, otherwise false.
   */
  public boolean queueFull () {
    if (head == (tail + 1) % SIZE)
      return true;
    else return false;
  }

  /**
   * Checks if the queue array is empty.
   *
   * @return  returns true if the queue array is empty, otherwise false.
   */
  public boolean queueEmpty () {
    if (head == tail)
      return true;
    else return false;
  }
}
