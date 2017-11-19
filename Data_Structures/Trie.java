package data_structures;

public class Trie {

    // Returns the node which contains the whole prefix
	public Trie_Node prefix(Trie_Node root, String prefix) {
		Trie_Node temp = root;
		for (char ch : prefix.toCharArray()) {
			if (temp.children.get(ch) != null)
				temp = temp.children.get(ch);  //one char is one level in the tree, so we have to get down by temp = temp.children
			else return null;
		}
		return temp;
	}
	
	// Returns if the word is in the trie.
	public boolean search(Trie_Node root, String word) {
		Trie_Node node = prefix(root, word);
		return node != null && node.isLeaf;
	}
	
	// Returns if there is any word in the trie
    // that starts with the given prefix.
	public boolean start(Trie_Node root, String prefix) {
		Trie_Node node = prefix(root, prefix);
		return node != null;
	}
	
	public void insert(Trie_Node root, String word) {
		Trie_Node temp = root;
		for (char ch : word.toCharArray()) {
			if (temp.children.get(ch) == null)
				temp.children.put(ch, new Trie_Node(ch));
			temp = temp.children.get(ch);
		}
		temp.isLeaf = true;
	}
	
	public static void main(String[] args) {
		Trie t = new Trie();
		Trie_Node root = new Trie_Node();
		String[] strs = new String[] {"this", "is", "a", "test", "testment"};
		for (String str:strs) {t.insert(root, str);}
		System.out.println(t.search(root, "an"));
		t.insert(root, "an");
		System.out.println(t.search(root, "an"));
		t.insert(root, "is");
		System.out.println(t.search(root, "this"));
		System.out.println(t.search(root, "test"));
		System.out.println(t.search(root, "testment"));
		System.out.println(t.start(root, "testmen"));
	}

}
