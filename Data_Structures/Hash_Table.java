
import java.util.ArrayList;
import java.util.List;

/*
 * An open addressing hash table with linear probing.
 */

public class Hash_Table {
	
	private int capacity;
	private int size;
	private Hash_Item[] arr;
	private static double LOAD_FACTOR = 0.65;

	public static void main(String[] args) {
		Hash_Table ht = new Hash_Table(2);
		System.out.println(ht.remove(1));
		ht.put(152,3);
		ht.put(5234,80);
		ht.put(52345,800);
		System.out.println(ht.capacity);
		System.out.println(ht.search(5234));
		System.out.println(ht.hasKey(5234));
		System.out.println(ht.hasValue(80));
		System.out.println(ht.get(152));
		System.out.println(ht.search(5234));
		ht.put(152,10);
		System.out.println(ht.search(152));
		System.out.println(ht.get(152));
		System.out.println(ht.remove(152));
		System.out.println(ht.get(152));
		System.out.println(ht.remove(12));
		System.out.println(ht.size);
		ht.put(152,3);
		System.out.println(ht.get(152));
		System.out.println(ht.hasValue(800));
	}
	
	public Hash_Table(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.arr = new Hash_Item[capacity];
	}
	
	public int hashCode(int key) {
		return key % this.capacity;
	}
	
	//Return the index of given key.
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
	
	//Return the value of given key.
	public int get(int key) {
		int index = search(key);
		if (index == -1) {
			System.out.print("Key error:");
			return -1;
		}
		return this.arr[index].value;
	}
	
	//Check if the given key exists in the table.
	public boolean hasKey(int key) {
		return search(key) != -1;
	}
	
	//Check if the given value exists in the table.
	public boolean hasValue(int value) {
		for (int i = 0; i < this.capacity; i++) {
			if (this.arr[i] != null && this.arr[i].value == value) {return true;}
		}
		return false;
	}
	
	//Return the key set of current table.
	public List<Integer> keySet(){
		List<Integer> keys = new ArrayList<>();
		for (int i = 0; i < this.capacity; i++) {
			if (this.arr[i] != null) {keys.add(this.arr[i].key);}
		}
		return keys;
	}
	
	//Return the value set of current table.
	public List<Integer> valueSet(){
		List<Integer> values = new ArrayList<>();
		for (int i = 0; i < this.capacity; i++) {
			if (this.arr[i] != null) {values.add(this.arr[i].value);}
		}
		return values;
	}
	
	//Put new item into table.
	public void put(int key, int value) {
		Hash_Item data = new Hash_Item(key, value);
		int index = search(key);
		if (index != -1) {
			this.arr[index] = data;
			return;
		}
		if ((this.size + 1) / this.capacity >= Hash_Table.LOAD_FACTOR) {resize();}
		index = hashCode(key);
		while (this.arr[index] != null) {
			index++;
			index %= this.capacity;
		}
		this.arr[index] = data;
		this.size++;
	}
	
	//Remove the given key from the table, return its value.
	public int remove(int key) {
		int index = search(key);
		if (index == -1) {
			System.out.print("Key error:");
			return -1;
		}
		Hash_Item temp = this.arr[index];
		this.arr[index] = null;
		this.size--;
		return temp.value;
	}
	
	//Double size the table if the load factor surpasses the baseline.
	public void resize() {
		this.capacity *= 2;
		Hash_Item[] newArr = new Hash_Item[this.capacity];
		for (int i = 0; i < this.arr.length; i++) {
			newArr[i] = this.arr[i];
		}
		this.arr = newArr;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public boolean isFull() {
		return this.size == this.capacity;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}
}
