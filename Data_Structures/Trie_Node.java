package data_structures;

import java.util.HashMap;
import java.util.Map;

public class Trie_Node {
	
	char c;
	Map<Character, Trie_Node> children = new HashMap<>(); //the children can be just a list, use map for search convenience
	boolean isLeaf;
	
	public Trie_Node() {} // this is for initializing root node
	
	public Trie_Node(char c) {
		this.c = c;
	}
}
