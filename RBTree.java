import java.util.Arrays;

/**
*
* RBTree in Java
*By Guy Yom Tov
*/

public class RBTree {
	 // the 4 fields of red black tree are: root, minimun mode, maximun node, the size of the tree.
	 
	public RBNode root;
	public RBNode minNode;
	public RBNode maxNode;
	public int size;
    
	public RBTree() { // the constructor of red black tree
		this.root=null;
		this.minNode=null;
		this.maxNode=null;
		this.size=0;
		
	}
	
	public static int getNodeColor(RBNode n){// o(1)
 // gives the node the color black=1 or red=0. if the node isnt a leaf, call the method getcolor().
		if (n==null) return 1; // leaves are getting the color black=1.
		else return n.getColor();
	}

/**
  * public RBNode getRoot()// o(1)
  *
  * returns the root of the red black tree
  *
  */
 public RBNode getRoot() {
   return this.root;
 }
 
 /**
  * public boolean empty()// o(1)
  *
  * returns true if and only if the tree is empty
  *
  */
 public boolean empty() {
   if (this.root==null){
	   return true;
   }
   else{
	   return false;
   }
  }

/**
  * public String search(int k)// o(logn)
  *
  * returns the value of an item with key k if it exists in the tree
  * otherwise, returns null
  */
 public String search(int k)
 {
	  RBNode node = this.root;
	    while (node != null) {
	 
	        if (k == node.getKey()) {
	            return node.getValue(); // if the current node's key is the key we are searching, return this node's value.
	        } else if (node.getKey() > k) {
	            node = node.getLeft(); // if the current node's key is bigger then the key we are searching, we should search in the left.
	        } else {
	            node = node.getRight();// if the current node's key is smaller then the key we are searching, we should search in the right.
	        }
	    }
	    return null; // if we exit the while loop, we reached a leaf which hasn't the same key as we wished, it means the key doesn't exist in the RBTree.
	}
 
/**
  * public RBNode search_node(int k)// o(logn)
  *
  * returns the node with key k if it exists in the tree (instead of the value in the method search).
  * otherwise, returns null
  * the method works the same as search besides the returned type.
  */
  
public RBNode search_node(int k)
{
	  RBNode node = this.root;
	    while (node != null) {
	 
	        if (k == node.getKey()) {
	            return node; // returning the node instead of his value
	        } else if (node.getKey() > k) {
	            node = node.getLeft();
	        } else {
	            node = node.getRight();
	        }
	    }
	    return node; // returning the node instead of his value
	}
 /**
  * public int insert(int k, String v)// o(logn)
  *
  * inserts an item with key k and value v to the red black tree.
  * the tree must remain valid (keep its invariants).
  * returns the number of color switches, or 0 if no color switches were necessary.
  * returns -1 if an item with key k already exists in the tree.
  * insert includes 3 cases, and 1 more case for initializing (Case 0).
  */
  public int insert(int k, String v) {// o(logn)
	// initializing a new RBNode, which has the desired key and value, the color is red (we always insert a red node), and his children are leaves.
	  RBNode insertedN=new RBNode(k,v,0,null,null); 
	  this.size++; // after inserting a RBNode to the tree, the number of nodes is increasing by one.
	 
	    if (this.root == null) { // in case we inserted the root.
	        this.root = insertedN; // we set the root to be the inserted node.
	        this.maxNode=insertedN; // we set the maximum (and only) node to be the inserted node.
	        this.minNode=insertedN; // we set the minimum (and only) node to be the inserted node.
	    } 
	    else { // in case we inserted a nod whice isnt the root.
	    	
	    	if(insertedN.getKey()<this.minNode.getKey()){ // maintaining the field minNode.
	    		this.minNode=insertedN;
	    	}
	    	else if(insertedN.getKey()>this.maxNode.getKey()){ // maintaining the field maxNode.
	    		this.maxNode=insertedN;
	    	}
	    	
	    	
	    	RBNode n = this.root;
	        while (true) {
	           
	            if (n.getKey() == k) { // returns -1 if an item with key k already exists in the tree.
	            	this.size--; // we earlier increased the size by one, but because the key already exists in the tree, we should return the size to his original size.
	                return -1;
	            }
	            else if (k< n.getKey()) {
	                if (n.getLeft() == null) { // if the key is smaller then the current node's key and his left child is a leaf, we set his left child to be the inserted node.
	                    n.setLeft(insertedN);;
	                    break; // after setting the inserted node instead of the left child of the current node, we found the right inserting place for him, and finished.
	                }
	                else {
	                    n = n.getLeft(); // continue searching smaller keys.
	                }
	            }
	            else {
	                 if (n.getRight() == null) { // if the key is bigger then the current node's key and his right child is a leaf, we set his right child to be the inserted node.
	                    n.setRight(insertedN);
	                    break;// after setting the inserted node instead of the right child of the current node, we found the right inserting place for him, and finished.
	                } 
	                 else {
	                    n = n.getRight(); // continue searching bigger keys.
	                }
	            }
	        }
	        insertedN.setFather(n); // setting the father of the inserted node (the father is the current node).
	    }
	    return insert_case_0(insertedN); 
  }
  /** insert_case_0// o(1)
   * includes 2 cases: 
   * 1)The current node the root of the tree
   * 2) the current node's father is black.
   * if none of 2 cases happen, we return insert_case_1.
   */
  private int insert_case_0(RBNode node){
	  if (node.getFather() == null){ // the root must be black, and the inserted node is red.
	        node.setColor(1);
	  		return 1; // one color switch.
	  }
	    else if (getNodeColor(node.getFather()) == 1) //if the node's father is black, the inserted node should be red as inserted.
	    {
	    	return 0; // no color switch.
	    }
	    else return insert_case_1(node); // if none of the 2 cases happened, move to inser_case_1. 		
  }
  /** insert_case_1:// o(1)
   * both the father and the uncle are red. then:
   *  both of them switch color to black.
   *  the grandfather switch color to red.
   */
  
  private int insert_case_1(RBNode node){
	  int getVal=0; // the return number of color switches.
	// if the uncle and father are red, color switch to black the color of the father and uncle.
	  if ((node.getUncle()!=null) && (getNodeColor(node.getUncle()) == 0)) {
		  if (getNodeColor(node.getFather())==0){
			  getVal++; // one more color switch: father became black instead of red.
		  }
	        node.getFather().setColor(1);
	        node.getUncle().setColor(1);
	        if (getNodeColor(node.getGrandFather())==1){ //if the greandfather color was black, it changed to red.
				  getVal++; // one more color switch: grandfather became red instead of black.
			  }
	        node.getGrandFather().setColor(0);
	        getVal++; // one more color switch: uncle became black instead of red.
	        
	     // return the num of color swithces+ moving to insert_case_0 (the father is black now).
	        return getVal+insert_case_0(node.getGrandFather());
	    }
	  else {
	        return insert_case_2(node); // if insert_case_1 doesn't fit the situation. move to insert_case_2.
	    }
  }
  
  /** insert_case_2:// o(1)
   * The parent is red and the uncle is black. 
   * includes two mirror cases:
   * 1) The current node is the right child of his father and
   * the node's father is the left child of its father (node's grandfather), we use left_rotation.
   * 2) The current node is the left child of his father and
   *  the node's father is the right child of its father (node's grandfather), we use right_rotation.
   */
  private int insert_case_2(RBNode node){
	 RBNode myGrandFather = node.getGrandFather();
// The current node is the right child of his father and the node's father is the left child of its father.
	 if ((node == node.getFather().getRight()) && node.getFather()== myGrandFather.getLeft()) {
		 left_rotation(node.getFather()); //switches the roles of the current node and its father.
		 node=node.getLeft(); // after the rotation we shouid update the node.
	 }
	 
	// The current node is the left child of his father and the node's father is the right child of its father.
	 else if ((node == node.getFather().getLeft()) && (node.getFather() == myGrandFather.getRight())) {
		 right_rotation(node.getFather()); //switches the roles of the current node and its father.
		 node= node.getRight(); // after the rotation we shouid update the node.
	 }
	 return insert_case_3(node);
	  
  }
 
 /** insert_case_3:// o(1)
 * The parent is red, the uncle is black.
 * includes 2 mirror cases:
 * 1) the current node is the left child of his fathr and the father is the left child of his father (the grandfather).
 * 2) the current node is the right child of his fathr and the father is the right child of his father (the grandfather).
 * we are using right/left rotations to convert to insert_case_3.
 */
 private int insert_case_3(RBNode node){
	int getVal=0;
	RBNode myGrandFather = node.getGrandFather() ;
	 if (getNodeColor(node.getFather())==0){
		  getVal++; // one more color switch, the father changed from red to black.
	  }
	 node.getFather().setColor(1); 
	 
	 if (getNodeColor(myGrandFather)==1){
		  getVal++;// one more color switch, the grandfather changed from black to red.
	  }
	 myGrandFather.setColor(0);
	 
	 //the current node is the left child of his fathr and the father is the left child of his father.
	 if (node == node.getFather().getLeft() && node.getFather()==myGrandFather.getLeft())
		 right_rotation(myGrandFather);
	 //the current node is the right child of his fathr and the father is the right child of his father.
	 else if(node == node.getFather().getRight() && node.getFather()==myGrandFather.getRight())
		 left_rotation(myGrandFather);
	 
	 return getVal;
 }
/** left_rotation(RBNode node)// o(1)
 * the father=F and his right child= C are changing places,
 *  now C is the father of his left child F.
 */
 private void left_rotation(RBNode node) {
	 RBNode right = node.getRight();
	    transplant(node, right);
	    node.setRight(right.getLeft());
	    if (right.getLeft() != null) {
	    	right.getLeft().setFather(node);
	    }
	    right.setLeft(node);
	    node.setFather(right);
}
/** transplant(RBNode x, RBNode y)// o(1)
 * Replace the subtree of x by the subtree of y
 */
private void transplant(RBNode x, RBNode y) {
	 if (x.getFather() == null) {
	        this.root = y;
	    } 
	 else {
	        if (x == x.getFather().getLeft()){
	        	x.getFather().setLeft(y);
	        }
	        else
	            x.getFather().setRight(y);
	    }
	    if (y != null) {
	        y.setFather(x.getFather()); 
	    }	
}
/** right_rotation(RBNode node)// o(1)
 * the father=F and his left child= C are changing places,
 *  now C is the father of his right child F.
 */
private void right_rotation(RBNode node) {
	
		RBNode left = node.getLeft();
		transplant(node, left);
	    node.setLeft(left.getRight()); 
	    if (left.getRight() != null) {
	        left.getRight().setFather(node);	
	        }
	    left.setRight(node); 
	    node.setFather(left);	
}
 
 
 /**
  * delete(int k)// o(logn)
  * deletes an item with key k from the binary tree, if it is there;
  * the tree must remain valid (keep its invariants).
  * returns the number of color switches, or 0 if no color switches were needed.
  * returns -1 if an item with key k was not found in the tree.
  * delete includes 4 cases, and 1 more case for initializing (Case 0).
  */ 
  public int delete(int k)// o(logn)
  {
	  this.size--; // after deleting one node, we should decrease the size by one.
	  int retVal=0; // retVal is trhe number of color swiches, which initialized to 0 at first.
	  RBNode n = search_node(k);
	    if (n == null){ // Key not found, return -1.
	    	this.size++; // returning the size to it's actual size.
	    	return -1; 
	    }
	   
	    else if(k==this.root.getKey()&& this.size==0){ // k is the key of the root which is the only root in the tree.
	    	this.root=null;
	    	this.maxNode=null;
	    	this.minNode=null;
	    	this.size=0;
	    	return 0;
	    }
	    if (n.getLeft() != null && n.getRight() != null) {
	        // Copy key and value from predecessor,then delete it.
	        RBNode minRightChild = GetminNode(n.getRight());
	        n.setKey(minRightChild.getKey());
	        n.setValue(minRightChild.getValue());
	        n = minRightChild;
	    }

	    if (n.getLeft() == null || n.getRight() == null){ // if at least one of the node's children are leaves.
	    	RBNode child;
	    	if(n.getRight()==null){ // if node's right child is a leaf, the child is the left child of the node.
	    		child=n.getLeft();
	    	}
	    	else { // if node's left child is a leaf, the child is the right child of the node.
	    		child=n.getRight();
	    		}
	    	
	    	// if the node's color is black, we switch color to red and increase retVal by one.
		    if (getNodeColor(n) == 1) {
		    	if(getNodeColor(child)==0){
		    		n.setColor(0);
		    		retVal=1+delete_case_1(n); // move to delete_case_0 and increase retVal by one.
		    	}
		    	else{
		    		retVal=delete_case_1(n); // move to delete_case_0.
		    	}
		    }
		    transplant(n, child);
		   
	    }
	    
	    if(k==this.minNode.getKey()&&(this.root!=null)){ //updating the field minNode.
	    	this.minNode= GetminNode(this.root);
	    	
	    }
	    else if(k==this.maxNode.getKey()&&(this.root!=null)){//updating the field naxNode.
	    	this.maxNode= GetmaxNode(this.root);
	    	
	    }
	    
	    return retVal;
	   
  }


/** delete_case_1(RBNode n)// o(1)
 * node's siblig (=S) is red. 
 * we switch the colors of node's father (=F) and S,
 * and then rotate_left(F)/rotate_right(F) depanding on the mirror case:
 * if n is a left child, we use rotate_left(F), otherwise, rotate_right(F).
 * turning S into nodes's grandfather. 
 * Now node has a black sibling and a red parent.
 */
private int delete_case_1(RBNode n) {
	int retVal=0;
	if (n.getFather()==null) return 0;
	
	// if node's sibling's color is red, and node's father's color is black
	 if (getNodeColor(n.getSibling()) == 0) { 
		 if (getNodeColor(n.getFather())==1){
			 retVal++;
		 }
		 // switch colors to the father and sibling of node. (and increasing by 2 retVal).
	        n.getFather().setColor(0);
	        n.getSibling().setColor(1);
	        retVal++;
	        // if node is a left child, f=do a left_rotation(node's father).
	        if (n == n.getFather().getLeft())
	           left_rotation(n.getFather());
	        // if node is a right child, f=do a right_rotation(node's father).
	        else
	        	 right_rotation(n.getFather());
	    }
	 
	    return retVal+ delete_case_2(n); // return the number of color switches and move to delete_case_2.
	}

/** delete_case_2(RBNode n)// o(1)
 * includes 2 cases:
 * 1) node's father, node's sibling(=S), and S's children are black.
 * we switch color S from black to red.
 * 2) node's sibling(=S), and S's children are black. and node's father(=F) is red.
 * we switch colors between S and P. (now S is red and F is black).
 */
private int delete_case_2(RBNode n) {
    int retVal=0;
    // if node's sibling(=S) and S's children are black
    	if( getNodeColor(n.getSibling()) == 1 &&
    		getNodeColor(n.getSibling().getLeft()) == 1 &&
    		getNodeColor(n.getSibling().getRight()) == 1)
    	{
    		// if node's father is black
    		if (getNodeColor(n.getFather()) == 1){
    			n.getSibling().setColor(0);
    			retVal++; // one more color switches.
    			return retVal+delete_case_1(n.getFather());
    		}
    	
    		// if node's father is red.
        else if (getNodeColor(n.getFather()) == 0){
        	
        	// F and P switching colors
        	n.getFather().setColor(1);
        	n.getSibling().setColor(0);
        	retVal=retVal+2; // two more color switches.
        	return retVal;
        }
    	}
        else{
        	return retVal+delete_case_3(n); // moving to delete_case_3.
        }	

          return retVal;  
    }

/** delete_case_3(RBNode n)// o(1)
 * node's sibling (=S) is black, S's left child is red, S's right child is black.
 * the node is the left child of his father. we use right_rotation(S)
 * S's left child becomes S's father and N's sibling.
 * in the other case, we use left_rotation(S) instead of right_rotation. 
 * now we switch the colors of S and its new father.
 */
private int delete_case_3(RBNode n) {
	int retVal=0;
	// if node is a left child and node's sibling is black, S's left child is red, S's right child is black.
	   if (n == n.getFather().getLeft() &&
			   getNodeColor(n.getSibling()) == 1 &&
			   getNodeColor(n.getSibling().getLeft()) == 0 &&
			   getNodeColor(n.getSibling().getRight()) == 1)
		    {
		   // S and S's left child are switching colors
		        n.getSibling().setColor(0);   
		        n.getSibling().getLeft().setColor(1);
		        retVal+=2; // retVal increased by 2.
		        right_rotation(n.getSibling());
		    }
	// if node is a right child and node's sibling is black, S's right child is red, S's left child is black.
		    else if (n == n.getFather().getRight() &&
		    		getNodeColor(n.getSibling()) == 1 &&
		    		getNodeColor(n.getSibling().getRight()) == 0 &&
		    		getNodeColor(n.getSibling().getLeft()) == 1)
		    {
		    	// S and S's right child are switching colors
		        n.getSibling().setColor(0);
		        n.getSibling().getRight().setColor(1);
		        retVal+=2; // retVal increased by 2.
		        left_rotation(n.getSibling());
		    }
	   return retVal+delete_case_4(n); // moving to delete_case_4.
}

/** node's sibling(=S) is black, S's right child is red, and the node is the left child of his father(=F).
 * if node is a right child, we use left_rotation(F).
 * if node is a left child, we use right_rotation(F).
 * S becomes the father of F and S's right child.
 *  We switch the colors of F and S.
 *  We make S's right child colored in black.
 */
private int delete_case_4(RBNode n) { //o(1)
	int retVal=0;
	
	// if S's color is different from F's color
	// we switch S's color to be the color of his father, and increase retVal by one.
		if (getNodeColor(n.getSibling())!=getNodeColor(n.getFather())) retVal++;
	    n.getSibling().setColor(getNodeColor(n.getFather()));
	    
	    // if F's color is red, we change it to black, and increase retVal by one.
	    if ( getNodeColor(n.getFather())==0) retVal++;
	    n.getFather().setColor(1);
	    
	 // if node's sibling's right child(=B) is red.
	    if (n == n.getFather().getLeft() &&
	    	getNodeColor(n.getSibling().getRight()) == 0 )
	    {
	    	// B's color switched from red to black ,and increase retVal by one.
	        n.getSibling().getRight().setColor(1);
	        retVal++;
	        left_rotation(n.getFather());
	    }
	    
	    // if node's sibling's left child(=A) is red.
	    else if (getNodeColor(n.getSibling().getLeft()) == 0)
	    {
	    	// A's color switched from red to black ,and increase retVal by one.
	        n.getSibling().getLeft().setColor(1);
	        retVal++;
	        right_rotation(n.getFather());
	    }
	    return retVal;
}

/** GetminNode(RBNode root)//o(logn)
 * return the minimim node in the tree.
 */
private RBNode GetminNode(RBNode n) {// we go down as left as we can.
		while (n.getLeft()!=null){
			n=n.getLeft();
		}
		return n;
}

/** GetmaxNode(RBNode root)//o(logn)
 * return the maximum node in the subtree.
 */
private RBNode GetmaxNode(RBNode n) {
		while (n.getRight()!=null){ // we go down as right as we can.
			n=n.getRight();
		}
		return n;
}

/**
   * public String min()//o(1)
   * Returns the value of the item with the smallest key in the tree,
   * or null if the tree is empty
   */
  public String min()
  {
	  if (this.minNode==null){
		  return null;
	  }
	  else{
		// returns the value of the field minMode which is maintained all the time.
		  return this.minNode.getValue(); 
	  }
	 
  }

  /**
   * public String max()//o(1)
   * Returns the value of the item with the largest key in the tree,
   * or null if the tree is empty
   */
  public String max()
  {
	  if (this.maxNode==null){
		  return null;
	  }
	  else{
		// returns the value of the field minMode which is maintained all the time.
		  return this.maxNode.getValue();
	  }
  }
 
 /**
  * public RBNode[] sortNode(RBNode node)//o(logn)
  * a recursive method gets a node, making 2 different arrays, leftArray and rightArray.
  * puts inside the leftArray the nodes which are smaller then the node.
  * (if there are no smaller nodes, leftArray has no nodes inside).
  * puts inside the rightArray the nodes which are bigger then the node.
  * (if there are no smaller nodes, rightArray has no nodes inside).
  * then, we make another array of one node which is the current node.
  * now, we join the three arrays
  * we return the joined array, the nodes inside are already sorted recursivly.
  */
	public RBNode[] sortNode(RBNode node){
		if (node==null){
			return new RBNode[0];
		}
		RBNode[] leftArray;
		RBNode[] rightArray;
		
		if (node.getLeft()!=null){
			leftArray= sortNode(node.getLeft()); // leftArray has the nodes which are smaller then the node.
		}
		else{
			leftArray=new RBNode[0]; //if there are no smaller nodes, leftArray has no nodes inside
		}
		
		if (node.getRight()!=null){ // leftArray has the nodes which are smaller then the node.
			rightArray= sortNode(node.getRight());
		}
		else{
			rightArray=new RBNode[0];//if there are no smaller nodes, rightArray has no nodes inside
		}
		
		RBNode[] thisNode={node}; // making a new node's array which contains only the current node.
		RBNode[] joined = new RBNode[leftArray.length+rightArray.length+1];
		
		// making the joined array which consist the three arrays ( leftArray, rightArray, thisNode).
		System.arraycopy( leftArray, 0, joined, 0, leftArray.length );
		System.arraycopy( thisNode , 0, joined, leftArray.length, 1 );
		System.arraycopy( rightArray , 0, joined, leftArray.length+1, rightArray.length );
		
		return joined;
 	}
	
	 /**
	  * public int[] keysToArray()//o(n)
	  * Returns a sorted array which contains all keys in the tree,
	  * or an empty array if the tree is empty.
	  */
	 public int[] keysToArray()
	 {
		 if (this.root==null){
			 return new int[0];
		 }
		 RBNode[] sortedNodes= sortNode(this.getRoot());
		 int[] keys=new int[sortedNodes.length]; // initializing the array of keys that will be returned.
		 
		 for (int i=0;i<sortedNodes.length;i++){ // for every node in the sortedNode array, we put his key in the array keys.
			 keys[i]= sortedNodes[i].getKey();
		 }
		 return keys;
	 }
	 
 /**
  * public String[] valuesToArray()//o(n)
  * Returns an array which contains all values in the tree,
  * sorted by their respective keys,
  * or an empty array if the tree is empty.
  */
 public String[] valuesToArray()
 {
	 if (this.root==null){
		 return new String[0]; // in case the tree is empty
	 }
	 RBNode[] sortedNodes= sortNode(this.getRoot());
	 String[] vals= new String[sortedNodes.length];  // initializing the array of values that will be returned.
	 
	 for (int i=0;i<sortedNodes.length;i++){ // for every node in the sortedNode array, we put his value in the array vals.
		 vals[i]= sortedNodes[i].getValue();
	 }
	 return vals;                  
 }

  /**
   * public int size()//o(1)
   *
   * Returns the number of nodes in the tree.
   *
   * precondition: none
   * postcondition: none
   */
  public int size()
  {
	   return this.size;
  }
  
  
  
  
  

/**
  * public class RBNode
  */
 public static class RBNode{
   
	 // the 6 fields of RBNode: node's value, node's key, node's left and right child, node's father and node's color.
		public String value;
		public int key;
		public RBNode left;
		public RBNode right;
		public RBNode father;
		public int color;
	 	

	 	public RBNode( int key, String value ){ // extra the constructor of RBNode. in case the tester bulid this way
	 		this.value=value;
	 		this.key=key;
	 		this.left=null;
	 		this.right=null;
	 		this.father=null;
	 		this.color=0; // 1=black,  0 = red
		 	}
	 	
	 	public RBNode( int key, String value, int color,RBNode left ,RBNode right ){ // the constructor of RBNode.
	 		this.value=value;
	 		this.key=key;
	 		this.left=left;
	 		this.right=right;
	 		this.father=null;
	 		this.color=color; // 1=black,  0 = red
	 		if (left != null)  left.father = this; // if the left child of the node isnt a leaf, the father of it is the node.
	 	    if (right != null) right.father = this; // if the right child of the node isnt a leaf, the father of it is the node.	
	 	}
	 	
	       /**
              * public String getValue()// o(1)
              *
              * returns the root of the value of the RBNode.
              *
              */
	 	public String getValue(){
	 		return this.value;
	 	}
	 	
	 	/**
              * public RBNode getSibling()// o(1)
              *
              * returning the sibling of the node
              *
              */
	 	public RBNode getSibling(){ //if the node is a left child, return the right chils. (and does the same for the opposite side).
	 		 if (this == this.father.getLeft())
	 			  return this.father.getRight();
	 		else
	 			  return this.father.getLeft();
	 	
	 	/**
              * public int getColor()// o(1)
              *
              * returning the the node's color.
              *
              */
	 	}
		public int getColor() {
			return this.color;
		}
		
		/**
              * public void setColor(int color)// o(1)
              *
              * setting the node's color. (1=black, 0=red)
              *
              */
		public void setColor(int color) {
			this.color = color;
		}

                /**
              * public void setValue(String value)// o(1)
              *
              * setting the node's value. (can be only a non-negative, distinct integer)
              *
              */
		public void setValue(String value) {
			this.value = value;
		}

                /**
              * public void setLeft(RBNode left)// o(1)
              *
              * setting the node's left child. 
              *
              */
		public void setLeft(RBNode left) {
			this.left = left;
		}

                 /**
              * public void setRight(RBNode right)// o(1)
              *
              * setting the node's right child. 
              *
              */
		public void setRight(RBNode right) {
			this.right = right;
		}

                 /**
              * public void setFather(RBNode father)// o(1)
              *
              * setting the node's father. 
              *
              */
		public void setFather(RBNode father) {
			this.father = father;
		}

              /**
              * public boolean isRed()// o(1)
              *
              * returns true if the RBNode is red, and false if its black. 
              *
              */
		public boolean isRed(){
			if (this.color==1){
				return false;
			}
			else{
				return true;
			}
		}
		
		  /**
              * public RBNode getLeft()// o(1)
              *
              * returns the left child of the RBNode. 
              *
              */
		public RBNode getLeft(){
			return this.left;
			
		}
		
		 /**
              * public RBNode getRight()// o(1)
              *
              * returns the right child of the RBNode. 
              *
              */
		public RBNode getRight(){
			return this.right;
		}
		
		 /**
              * public int getKey()// o(1)
              *
              * returns the key of the RBNode. 
              *
              */
		public int getKey(){
			return this.key;
		}
		
		/**
              * public void setKey(int key)// o(1)
              *
              * setting the key of the RBNode. 
              *
              */
		public void setKey(int key) {
		this.key = key;
	        }
	        
	         /**
              * public RBNode getFather()// o(1)
              *
              * returns the father of the RBNode. 
              *
              */
		public RBNode getFather(){
			return this.father;
		}
		
		 /**
              * public RBNode getGrandFather()// o(1)
              *
              * returns the grandfather of the RBNode. 
              *
              */
		public RBNode getGrandFather(){
			if (this.father != null)
				  return this.father.getFather(); // returns the father of the father's node.
				 else
				  return null;
		}
		
		 /**
              * public RBNode getUncle()// o(1)
              *
              * returns the uncle of the RBNode. 
              *
              */
		public RBNode getUncle(){
			RBNode myGrandFather=this.getGrandFather();
			RBNode myFather=this.father; 
			if(myGrandFather==null) // if grandfather is null, the father is the root of the RBTree and there is no uncle.
				{return null;}
			else if (myFather==myGrandFather.getLeft()) // if my father is a left child, the uncle is a right child.
				return myGrandFather.getRight();
			else return myGrandFather.getLeft(); // if my father is a right child, the uncle is a left child.
		}
	}
	
  

}
