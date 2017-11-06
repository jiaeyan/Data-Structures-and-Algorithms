package data_structures;

/*
 * Use front and rear to maintain queue.
 * Front to control dequeue, rear to control enqueue.
 */

public class Queue {
	
	private int front, rear, size, capacity;
	private int[] queue;

	public static void main(String[] args) {
		Queue q = new Queue(2);
		q.enqueue(1);
		
		q.enqueue(2);
		System.out.println(q.dequeue());
		System.out.println(q.dequeue());
		q.enqueue(3);
		q.enqueue(4);
		
		
		System.out.println(q.dequeue());
	}
	
	//We use %capacity to roll back and circulate, thus capacity and size are both needed.
	//Thus when enqueue, it doesn't have to be ordered in the queue.
	public Queue(int capacity) {
		this.front = this.size = 0;
		this.rear = capacity - 1;
		this.capacity = capacity;
		this.queue = new int[capacity];
	}
	
	public void enqueue(int item) {
		if (isFull()) {
			System.out.println("Enqueue failed because the queue is full.");
			return;
		}
		this.rear = (this.rear + 1) % this.capacity;
		this.size++;
		this.queue[rear] = item;
	}
	
	public int dequeue() {
		if (isEmpty()) {
			System.out.println("Dequeue failed because the queue is empty.");
			return -1;
		}
		int item = this.queue[this.front];
		this.front = (this.front + 1) % this.capacity;
		this.size--;
		return item;
	}
	
	public int front() {
		if (isEmpty()) {
			System.out.println("Get front failed because the queue is empty.");
			return -1;
		}
		return this.queue[front];
	}
	
	public int rear() {
		if (isEmpty()) {
			System.out.println("Get rear failed because the queue is empty.");
			return -1;
		}
		return this.queue[rear];
	}
	
	public boolean isFull() {
		return this.size == this.capacity;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}

}
