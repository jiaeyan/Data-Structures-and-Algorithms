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
		return node != null && node.isEnd;
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
		temp.isEnd = true;
	}
	
	//should check the coverage of target word in current trie
	public void delete(Trie_Node root, String word) {
		if (word.length() == 0 || word == null) {return;}
		Trie_Node endNode = prefix(root, word); //this is the end node of the word in the trie
		if (endNode == null || !endNode.isEnd) {
			System.out.println("Error: no such word in current trie.");
			return;
		}
		deleteHelper(root, endNode, word);
	}
	
	/*
	 * We delete a node by removing it from its parent's children list.
	 * So when we find a node that is deletable, we still have to go up to
	 * its parent to remove it, so the function is: we have a node now, we want
	 * to check if its next child can be deleted or not. If yes, delete it from
	 * children list, otherwise keep it, and tell the parent node of current node
	 * if current node can be deleted or not.
	 * http://www.ideserve.co.in/learn/trie-delete
	 */
	public boolean deleteHelper(Trie_Node root, Trie_Node endNode, String word) {
//		if (root != null) { //if endNode != null, then root & its descents will never be null
			if (root.equals(endNode)) {  // when reach the end, check if it can be deleted, if no children, delete it; otherwise other keys share this end, cannot delete it
				root.isEnd = false;      // the last char stands for the whole word, deleting the word means unmarking the end label
				return root.children.size() == 0;
			}
			Trie_Node child = root.children.get(word.charAt(0));
			if (deleteHelper(child, endNode, word.substring(1, word.length()))) {
				root.children.remove(child.c);
				return !root.isEnd && root.children.size() == 0; //if child is deleted, then check current node: if isEnd, current node is shared with other key, cannot be deleted; if has other children, shared with other key, cannot be deleted, when both fail, current node can be deleted
			}
			else return false; //if child is not deleted, the child must be shared with other key, current node cannot be deleted 
//		}
//		return false;
	}
	
	//Display all words in given tire.
	public void showWords(Trie_Node root){
		System.out.println(root.toString());
	}
	
	public static void main(String[] args) {
		Trie t = new Trie();
		Trie_Node root = new Trie_Node();
		String[] strs = new String[] {"angle", "angel", "this", "is", "a", "test", "tested", "testment"};
		for (String str:strs) {t.insert(root, str);}
//		System.out.println(t.search(root, "an"));
		t.insert(root, "an");
//		System.out.println(t.search(root, "an"));
		t.insert(root, "is");
//		System.out.println(t.search(root, "this"));
//		System.out.println(t.search(root, "test"));
//		System.out.println(t.search(root, "testment"));
//		System.out.println(t.start(root, "testmen"));
		t.delete(root, "test");
		t.delete(root, "ang");
		t.showWords(root);
	}

}
