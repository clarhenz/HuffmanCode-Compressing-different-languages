/**
 *  Clara Henzinger
 *  07.12.2022
 *  Linked List of Huffman Tree Nodes
 *  First Node in the list has index 0
*/

//import java.io.*;
//import java.util.*;

public class Huffnodes {
    private char ch;
    private int freq;
    private Huffnodes next;
    private Huffnodes left;
    private Huffnodes right;

    /**
     * Constructor
     */
    public Huffnodes(char c, int f, Huffnodes n, Huffnodes l, Huffnodes r) {
        this.ch = c;
        this.freq = f;
        this.next = n;
        this.left = l;
        this.right = r;
    }

    /**
     * private method to find the i-th Huffnode in the list
     */
    private static Huffnodes getNode(Huffnodes huff, int i) {
        if (i < 0 || huff == null) {    // base case 1: not found
            return null;
        } else if (i == 0) {           // base case 2: just found
            return huff;
        } else {
            return getNode(huff.next, i - 1);
        }
    }
    //public methods

     /**
     * charAt - returns the character at the specified index of the
     * specified linked-list string, where the first character has
     * index 0.  If the index i is < 0 or i > length - 1, the method
     * will end up throwing an IllegalArgumentException.
     */
    public static char charAt(Huffnodes huff, int i) {
        if (huff == null) {
            throw new IllegalArgumentException("the string is empty");
        } 
          
        Huffnodes node = getNode(huff, i);

        if (node != null) {
            return node.ch;     
        } else {
            throw new IllegalArgumentException("invalid index: " + i);
        }
    }

      /**
     * freqAt - returns the frequency at the specified index of the
     * specified linked-list string, where the first character has
     * index 0.  If the index i is < 0 or i > length - 1, the method
     * will end up throwing an IllegalArgumentException.
     */
    public static int freqAt(Huffnodes huff, int i) {
        if (huff == null) {
            throw new IllegalArgumentException("the string is empty");
        } 
          
        Huffnodes node = getNode(huff, i);

        if (node != null) {
            return node.freq;     
        } else {
            throw new IllegalArgumentException("invalid index: " + i);
        }
    }

    /**
     * deleteNode - deletes a node i in the given linked-list and
     * returns a reference to the resulting linked-list string
     */
    public static Huffnodes deleteNode(Huffnodes huff, int i) {
        if (huff == null) {
            throw new IllegalArgumentException("string is empty");
        } else if (i < 0) { 
            throw new IllegalArgumentException("invalid index: " + i);
        } else if (i == 0) { 
           huff = huff.next;
        } else {
            Huffnodes prevNode = getNode(huff, i-1);
            if (prevNode != null && prevNode.next != null) {
                prevNode.next = prevNode.next.next;
            } else {
                throw new IllegalArgumentException("invalid index: " + i);
            }
        }

        return huff;
    }

        /**
     * insertNode - inserts the node before the node
     * currently in position i of the specified linked-list.
     * Returns a reference to the resulting linked-list.
     */
    public static Huffnodes insertNode(Huffnodes huff, int i, char ch, int freq, Huffnodes left, Huffnodes right) {
        Huffnodes newNode, prevNode;

        if (i < 0) { 
            throw new IllegalArgumentException("invalid index: " + i);
        } else if (i == 0) {
            newNode = new Huffnodes(ch, freq, huff,left,right);
            huff = newNode;
        } else {
            prevNode = getNode(huff, i - 1);
            if (prevNode != null) {
                newNode = new Huffnodes(ch, freq, prevNode.next,left,right);
                prevNode.next = newNode;
            } else {
                throw new IllegalArgumentException("invalid index: " + i);
            }
        }

        return huff;
    }

    /**
     * length - recursively determines the number of nodes in the
     * linked-list to which huff refers
     */
    public static int length(Huffnodes huff) {
        if (huff == null) {
            return  0;
        } else {
            return 1 + length(huff.next);
        }
    }

     /**
     * print - recursively writes the specified linked-list 
     * System.out
     */
    public static void print(Huffnodes huff) {
        if (huff == null) {
            System.out.println();
            return;
        } else {
            System.out.print(huff.ch + " " + huff.freq + " ");     
            print(huff.next);
        }
    }

    /** 
     * sortList(): sort nodes of the list in ascending
     * order
     */ 
    public Huffnodes sortList()
    {
 
        // Node current will pointo head

        Huffnodes current = this;
        Huffnodes index = null;
        int temp; char temp2;
 
        while (current != null) {
            // Node index will point to node next to
            // current
            index = current.next;
 
            while (index != null) {
                // If current node's data is greater
                // than index's node data, swap the data
                // between them
                if (current.freq > index.freq) {
                    temp = current.freq;
                    temp2 = current.ch;
                    current.freq = index.freq;
                    current.ch = index.ch;
                    index.freq = temp;
                    index.ch = temp2;
                }
 
                index = index.next;
            }
            current = current.next;
        }

        return this;
    }
    /**equals: finds out if two Huffnodes are equivalent
     * @return true if == and false if !=
    */
    public boolean equals(Huffnodes node1){
        return (this.freq == node1.freq) && (this.ch == node1.ch);
    }
    /**
     *  getIndex: finds the index of a node
     * @param n
     * @param huff
     * @return index of node or -1 if node not in linked list
     */
    public int getIndex(Huffnodes n, Huffnodes huff){
        for(int i = 0; i<length(huff); i++){
            if(huff.equals(getNode(n,i))){
                return i;
            }  
        }
        return -1;
    }
    /**takes two nodes and creates a merged node in the huffmantree which it returns*/
    public Huffnodes mergeNode(Huffnodes next, Huffnodes node1, Huffnodes node2) {
        int newF = node1.freq + node2.freq;
        char newC = ' ';
        Huffnodes merged = new Huffnodes(newC, newF, next, node1,node2);
        return merged;

    }

    /**
     * Merges the two nodes with the lowest frequency
     * and adds the new merged node to the LList in it's correct place
     * 
     * @return pointer to first node in LList
     */

    public Huffnodes mergeLowestF(){
        Huffnodes huff = this;
        int l = length(huff);
        if(l < 2){
            return huff;
        } else if (l==2){
            Huffnodes merge = mergeNode(null,getNode(huff,0),getNode(huff,1));
            return merge;
        } else {
            Huffnodes merge = mergeNode(null,getNode(huff,0),getNode(huff,1));
            huff = getNode(huff,2);
        
            if(huff.freq >= merge.freq){ //make merge the first node 
                merge.next = huff;
                return merge;
            } else { 
                Huffnodes prev = huff; 
                Huffnodes current = huff.next; 
                while(current != null){ 
                    if(merge.freq <= current.freq){ 
                        prev.next = merge; 
                        merge.next = current; 
                        
                        return huff;
                    } else { 
                        prev = current; 
                        current = current.next; 
                    }
                } 
                prev.next = merge;
                return huff;
            }
        }  
    }
    
    /**
     * merges the nodes until it reaches the root node
     * @param huff
     * @return root node
     */
    public Huffnodes buildHuffT(Huffnodes huff){
        if(length(huff) <= 1){
            return huff;
        } else {
            huff = huff.mergeLowestF();
            return buildHuffT(huff);
        }
    }

    public Huffnodes buildT(){
        Huffnodes huff = this;
        huff = huff.sortList();
        Huffnodes rootN = buildHuffT(huff);
        return rootN;
    }

    /**
     * Recursively performs a preorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void findBitC(Huffnodes root, String bitStr, String[] arr) {
        //System.out.print(root.freq + " " + root.ch);
        if (root.left != null) {
            findBitC(root.left,bitStr + "0",arr);
        }
        if (root.right != null) {
            findBitC(root.right, bitStr + "1",arr);
        }

        if ((root.left == null) && (root.right == null)){
            int asc = root.ch;
            arr[asc] = bitStr;
            //System.out.println(" ch: "+ root.ch + " str: " + bitStr);
        }
    }
    
    /**
     * charToCode: turns character into ascii, find it in the array
     * @param char caracter
     * @param Huffnodes root node
     * @param String[] array containing codes
     * @return code as String
    */

    public String charToCode(char c, Huffnodes root, String[] arr){
        int aC = c;
        findBitC(root, null, arr);
        String code = arr[aC];
        return code;
    }
    

    public static void main(String[] args){
        Huffnodes huff = new Huffnodes('c', 45, null, null, null);
        huff = insertNode(huff, 1, 'l', 50,null,null);
        huff = insertNode(huff, 2, 'r', 3,null,null);
        huff = insertNode(huff, 3, 'a', 5,null,null);
        huff = insertNode(huff, 4, 'b', 10,null,null);
        
        /*huff = huff.sortList();
        print(huff);
        huff = huff.mergeLowestF();
        print(huff);
        huff = huff.mergeLowestF();
        print(huff);
        huff = huff.mergeLowestF();
        print(huff);
        huff = huff.mergeLowestF();
        print(huff);*/

        Huffnodes rootN = huff.buildT();
        String[] code = new String[10000];
        findBitC(rootN, "",code);
        for(int i = 0; i<code.length; i++){
            if(code[i]!=null){
                System.out.println((char)i +" " + code[i]);
            }
        }

    }

}
