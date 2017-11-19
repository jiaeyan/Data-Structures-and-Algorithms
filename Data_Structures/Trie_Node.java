package data_structures;

import java.util.HashMap;
import java.util.Map;

public class Trie_Node {
	
	char c;
	Map<Character, Trie_Node> children = new HashMap<>(); //the children can be just a list, use map for search convenience
	boolean isEnd; // "test" is parent of "testment", so we have to tag "test" as a word, then we know along this path there are 2 words
	
	public Trie_Node() {} // this is for initializing root node
	
	public Trie_Node(char c) {
		this.c = c;
	}
}
