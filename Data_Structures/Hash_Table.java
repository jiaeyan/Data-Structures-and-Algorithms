package data_structures;

/*
 * An open addressing hash table with linear probing.
 */

public class Hash_Table {
	
	private int capacity;
	private int size;
	private Hash_Item[] arr;

	public static void main(String[] args) {
		Hash_Table ht = new Hash_Table(10);
		ht.insert(152,3);
		ht.insert(5234,80);
		System.out.println(ht.search(152));
		System.out.println(ht.get(152));
		System.out.println(ht.search(5234));
		ht.insert(152,10);
		System.out.println(ht.search(152));
		System.out.println(ht.get(152));
		System.out.println(ht.remove(152));
		System.out.println(ht.get(152));
		System.out.println(ht.remove(12));
		System.out.println(ht.size);
		ht.insert(152,3);
		System.out.println(ht.get(152));
	}
	
	public Hash_Table(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.arr = new Hash_Item[capacity];
	}
	
	public int hashCode(int key) {
		return key % this.capacity;
	}
	
	//Return the index of given key, -1 if doesn't exist.
	public int search(int key) {
		int index = hashCode(key);
		while (this.arr[index] != null) {
			if (this.arr[index].key == key) {
				return index;
			}
			index++;
			index %= this.capacity;
		}
		return -1;
	}
	
	public int get(int key) {
		if (search(key) == -1) {
			System.out.print("Key error:");
			return -1;
		}
		int index = hashCode(key);
		while (this.arr[index] != null) {
			if (this.arr[index].key == key) {
				return this.arr[index].value;
			}
			index++;
			index %= this.capacity;
		}
		return -1;
	}
	
	public void insert(int key, int value) {
		Hash_Item data = new Hash_Item(key, value);
		int index = hashCode(key);
		if (search(key) != -1) {
			this.arr[index] = data;
			return;
		}
		while (this.arr[index] != null) {
			index++;
			index %= this.capacity;
		}
		this.arr[index] = data;
		this.size++;
	}
	
	public Hash_Item remove(int key) {
		if (search(key) == -1) {
			System.out.print("Key error:");
			return null;
		}
		int index = hashCode(key);
		while (this.arr[index] != null) {
			if (this.arr[index].key == key) {
				Hash_Item temp = this.arr[index];
				this.arr[index] = null;
				this.size--;
				return temp;
			}
			index++;
			index %= this.capacity;
		}
		return null;
	}

}
