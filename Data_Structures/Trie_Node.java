package data_structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie_Node {
	
	char c;
	Map<Character, Trie_Node> children = new HashMap<>(); //the children can be just a list, use map for search convenience
	boolean isEnd; // "test" is parent of "testment", so we have to tag "test" as a word, then we know along this path there are 2 words
	
	public Trie_Node() {} // this is for initializing root node
	
	public Trie_Node(char c) {
		this.c = c;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		List<String> list = new ArrayList<>();
		showWords(this, sb, list);
		return String.join(", ", list);
	}
	
	public void showWords(Trie_Node root, StringBuilder sb, List<String> list){
		if (root == null) return;
		for (Map.Entry<Character, Trie_Node> entry : root.children.entrySet()) {
			Character ch = entry.getKey();
			Trie_Node node = entry.getValue();
			sb.append(ch);
			if (node.isEnd) list.add(sb.toString());
			showWords(node, sb, list);
			sb.deleteCharAt(sb.length()-1);
		}
	}
}
