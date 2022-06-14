/**
 *  This class implements a BST.
 *
 *  @param <T> the type of the key.
 *
 *  @author W. Masri and YOUR_NAME_HERE
 */
class BST<T extends Comparable<T>> {
	// **************//
	// DO NO CHANGE

	/**
	 *  Node class.
	 *  @param <T> the type of the key.
	 */
	class Node<T extends Comparable<T>>
	{
		/**
		*  key that uniquely identifies the node.
		*/
		T key;
		/**
		*  references to the left and right nodes.
		*/
		Node<T> left, right;

		/**
		 * Constructor for class Node.
		 * @param item key of a node
		 */
		public Node(T item){
			key = item;
			left = right = null;
		}

		/**
		 * A method that returns the value of a node.
		 *
		 * @return the value inside the node.
		 */
		public String toString(){
			return "" + key;
		}
	}

	/**
	 *  The root of the BST.
	 */
	Node<T> root;

	/**
	 * Constructor for BST, sets root to null.
	 */
	public BST(){
		root = null;
	}

	/**
	 * Returns all the nodes in the BST by calling a function that returns
	 * all nodes in an in order traversal.
	 *
	 * @return in order list of all nodes in the BST
	 */
	public String toString(){
		return inorderToString();
	}
	// DO NO CHANGE
	// **************//

	/**
	 * A recursive helper method to print out all of the nodes in an in order traversal.
	 * @param root the root of BST to be printed
	 * @return nodes in the tree in an inorder traversal
	 */
	private String inorderPrint(Node<T> root) {
		String s = "";
		if (root == null){
			return "";
		}
		s += inorderPrint(root.left);
	    s += "\"" + root.toString() + "\" ";
	    s += inorderPrint(root.right);
    	return s;
	}

	/**
	 *  This method returns a string in which the elements are listed in an inorder fashion.
	 *  Your implementation must be recursive.
	 *  Note: you can create private helper methods
	 *  @return string in which the elements are listed in an inorder fashion
	 */
	public String inorderToString() {
		String treeInOrder = inorderPrint(root);
		treeInOrder = treeInOrder.substring(0, treeInOrder.length()-1); //remove the trailing space
		return treeInOrder;
	}

	/**
	 * A private helper method to insert a key into a node into the BST.
	 * @param key the key to insert
	 * @param node node to insert the key in
	 * @return the node with the key inserted.
	 */
	private Node<T> insert(T key, Node<T> node){

		if(node == null)
			return new Node<>(key);

		if(key.compareTo(node.key) < 0)
			node.left = insert(key,node.left);
		else if (key.compareTo(node.key) > 0)
			node.right = insert(key,node.right);

		return node;
	}

	/**
	 *  This method inserts a node in the BST. You can implement it iteratively or recursively.
	 *  Note: you can create private helper methods
	 *  @param key to insert
	 */
	public void insert(T key) {
		root = insert(key, root);
	}

	/**
	 *  This method finds and returns a node in the BST. You can implement it iteratively or recursively.
	 *  It should return null if not match is found.
	 *  Note: you can create private helper methods
	 *  @param key to find
	 *  @return the node associated with the key.
	 */
	public Node<T> find(T key){
		return findNode(root,key);
	}

	/**
	 * A helper method to recursively find a node with a specific key. If it is not in the BST, it returns null.
	 * @param node the node of which its key will be checked
	 * @param key key to be checked
	 * @return the node with the matching key, or null if not found.
	 */
	private Node<T> findNode(Node<T> node, T key){

		if(node == null)
			return null;

		if (node.key == key)
			return node;

		else if(key.compareTo(node.key) < 0){ //if key is less than root
			return findNode(node.left,key);
		}
		else if(key.compareTo(node.key) > 0){ //if key is more than root
			return findNode(node.right,key);
		}
		return null;
	}

	/**
	 *  Main Method For Your Testing -- Edit all you want.
	 *
	 *  @param args not used
	 */
	public static void main(String[] args) {
		/*
							 50
						  /	      \
						30    	  70
	                 /     \    /     \
	                20     40  60     80
		*/


		BST<Integer> tree1 = new BST<>();
		tree1.insert(50); tree1.insert(30); tree1.insert(20); tree1.insert(40);
		tree1.insert(70); tree1.insert(60); tree1.insert(80);

		if (tree1.find(70) != null) {
			System.out.println("Yay1");
		}
		if (tree1.find(90) == null) {
			System.out.println("Yay2");
		}
		if (tree1.toString().equals("\"20\" \"30\" \"40\" \"50\" \"60\" \"70\" \"80\"") == true) {
			System.out.println("Yay3");
		}

		BST<String> tree2 = new BST<>();
		tree2.insert("50"); tree2.insert("30"); tree2.insert("20"); tree2.insert("40");
		tree2.insert("70"); tree2.insert("60"); tree2.insert("80");

		if (tree2.find("70") != null) {
			System.out.println("Yay4");
		}
		if (tree2.find("90") == null) {
			System.out.println("Yay5");
		}
		if (tree2.toString().equals("\"20\" \"30\" \"40\" \"50\" \"60\" \"70\" \"80\"") == true) {
			System.out.println("Yay6");
		}

		BST<String> trees = new BST<>();
		trees.insert("aest");
		trees.insert("is");
		trees.insert("aaaa");
		trees.insert("dont");
		trees.insert("know");
		trees.insert("test");
		trees.insert("yowi");

		System.out.println(trees.toString());
	}

}
