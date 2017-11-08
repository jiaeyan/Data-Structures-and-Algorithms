
/*
 * Use top to maintain the stack.
 */

public class Stack {
	private int top;      //the index of the top item
	private int size;     //the maximum capacity of the stack
	private int[] stack;  //the data structures used to store data
	
	public static void main(String[] args) {
		Stack s = new Stack(2);
		System.out.println(s.isFull());
		System.out.println(s.isEmpty());
		System.out.println(s.peek());
		s.push(1);
		System.out.println(s.peek());
		s.push(2);
		System.out.println(s.peek());
		s.push(3);
		System.out.println(s.peek());
		System.out.println(s.isFull());
		System.out.println(s.isEmpty());
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.isEmpty());
	}
	
	public Stack(int size) {
		this.top = -1;
		this.size = size;
		this.stack = new int[size];
	}
	
	public void push(int item) {
		if (isFull()) {
			System.out.println("Push failed because the stack is full.");
		}
		else {this.stack[++this.top] = item;}
	}
	
	public int pop() {
		if (isEmpty()) {
			System.out.println("Pop failed because the stack is empty.");
			return -1;
		}
		return this.stack[this.top--];
	}
	
	public int peek() {
		if (isEmpty()) {
			System.out.println("Peek failed because the stack is empty.");
			return -1;
		}
		return this.stack[top];
	}
	
	public boolean isFull() {
		return this.top == this.size - 1;
	}
	
	public boolean isEmpty() {
		return this.top == -1;
	}
	

}
