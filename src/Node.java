/*
 * Zachariah Wingo
 * TCSS 342
 */
import java.util.Comparator;

public class Node implements Comparator<Node> {

	/** The weight equal to character count. */
	private int myWeight;
	 
	/** The character. */
	private char myCharacter;
	
	/** The left node. */
	private Node left;
	
	/** The right node. */
	private Node right;
	
	
	/** Constructor. */
	Node(final int theWeight, final char theCharacter) {
		super();
		myWeight = theWeight;
		myCharacter = theCharacter;
	}
	
	
	/**
	 * Creates new node with two other nodes as children.
	 * @param theLeft reference to the left node.
	 * @param theRight reference the right node.
	 */
	Node(final Node theLeft, final Node theRight) {
		super();
		left = theLeft;
		right = theRight;
		myWeight = (theLeft.myWeight + theRight.myWeight);
	}
	
	/**
	 * Creates empty node.
	 */
	Node() {
		super();
	}
	
	/**
	 * Determines if this node is a leaf.
	 */
	public boolean isLeaf() {
		return (left == null && right == null);
	}
	
	/**
	 * Returns weight.
	 * @return returns the weight of the character as an integer.
	 */
	public int getWeight() {
		return myWeight;
	}
	
	/**
	 * Returns the character type.
	 * @return returns type of character as a character.
	 */
	public char getCharacter() {
		return myCharacter;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
	
	/**
	 * To string.
	 */
	@Override
	public String toString() {
		return "'" + myCharacter + "'=" + myWeight + ", ";
	}
	
	/**
	 * Compares and returns the difference between the weights.
	 * @param theLeft reference to left node.
	 * @param theRight reference to right node.
	 */
	@Override
	public int compare(final Node theLeft, final Node theRight) {
		return theLeft.myWeight - theRight.myWeight;
	}
}
