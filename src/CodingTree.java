/*
 * Zachariah Wingo
 * TCSS 342
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


/**
 * Creates a Huffman encoding tree.
 * @author Zachariah Wingo
 *
 */
public class CodingTree {

	/** Maps character codes. */
	final private Map<Character, String> codes;
	
	/** Stores frequency of each character. */
	final private Map<Character, Integer> myFrequency;

	/** Stores the bits to be written. **/
	final List<Byte> bits;
	
	/** Priority queue. */
	final private PriorityQueue<Node> myPriorityQueue;
	
	/** Root node. */
	private Node myRoot;

	/**
	 * Constructor.
	 */
	CodingTree(final String message) {
		codes = new HashMap<Character, String>(); 
		myFrequency = new HashMap<Character, Integer>();
		bits = new ArrayList<Byte>();
		myPriorityQueue = new PriorityQueue<Node>((Comparator)new Node());
		myRoot = null;
		countCharacters(message);
		populatePriorityQueue();
		buildHuffmanTree();
		encodeCharacters(myRoot, "");
		encodeMessage(message);
	}
	
	
	/**
	 * Counts the number of each character type.
	 * @param message the message to type.
	 */
	private void countCharacters(final String message) {
		int size = message.length();
		
		for(int i=0; i < size; i++) {
			final Character c = message.charAt(i);
			Integer count = myFrequency.get(c);
			count = (count == null) ? 1 : (++count);
			myFrequency.put(c, count);
		}

	}
	
	
	/**
	 * Populates priority queue.
	 */
	private void populatePriorityQueue() {
		
		// Simply asks for set of keys and loops through the set.
		for(Character c : myFrequency.keySet()) {
			myPriorityQueue.offer(new Node(myFrequency.get(c), c));
		}
	}
	
	
	/**
	 * Build Huffman Tree by taking the two lowest value nodes and combining them
	 * into a single tree. Each new tree is put back into the priority queue so
	 * the root of this tree can be paired with other nodes/trees. We stop when we have
	 * a single node left representing the tree root.
	 */
	private void buildHuffmanTree() {
		while(myPriorityQueue.size() > 1) {
			Node left = myPriorityQueue.poll();
			Node right = myPriorityQueue.poll();
			myPriorityQueue.offer(new Node(left, right));
		}

		// Priority Queue should only have a single node which is the root of our tree.
		myRoot = myPriorityQueue.poll();
	}
	
	/**
	 * Encodes the characters in a recursive manner. The theCode parameter
	 * is a string that starts out empty and gets appended with a 0 or 1 
	 * depending on the direction of the current node.
	 * @param theNode the Node to encode.
	 * @param theCode the current encoded code.
	 */
	private void encodeCharacters(final Node theNode, String theCode) {
		

		
		if (theNode.getRight() != null) {
			encodeCharacters(theNode.getLeft(), theCode + '0');
		}
		if (theNode.getRight() != null) {
			encodeCharacters(theNode.getRight(), theCode + '1');
		}
		if (theNode.isLeaf()) {
			codes.put(theNode.getCharacter(), theCode);
		}

	}
	
	/**
	 * Private message accepts a string to encode and stores the ecoded message bytes
	 * @param theMessage the message to encode.
	 */
	private void encodeMessage(final String theMessage) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < theMessage.length(); i++) {
			sb.append(codes.get(theMessage.charAt(i)));
				
				while(sb.length() >= 8) {
					bits.add(Integer.valueOf(sb.substring(0,8), 2).byteValue());
					sb.delete(0, 8);
				}
		}
		if(sb.length() > 0) {
			bits.add(Integer.valueOf(sb.toString()).byteValue());
		}
	}
	/**
	 * Returns the codes.
	 * @return returns a map of codes.
	 */
	public Map<Character, String> getCodes() {
		return codes;
	}
	
	public List<Byte> getBytes() {
		return bits;
	}
	
	/**
	 * To String.
	 */
	@Override
	public String toString() {
		return codes.toString();
	}
}
