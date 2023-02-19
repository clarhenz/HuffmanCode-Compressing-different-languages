import java.io.FileNotFoundException;

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
            System.out.print("ch: " + huff.ch + " freq: " + huff.freq + " ");
        }     
        
    }

    /** 
     * sortList(): sort nodes of the list in ascending
     * order
     */ 
    public Huffnodes sortList()
    {
 
        // Node current will point to head

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

    public static Huffnodes bCleanLL(Huffnodes huff){
        while(huff.freq == 0){
            huff = huff.next;
        }
        Huffnodes prev = huff;
        Huffnodes curr = prev.next;
        while(curr.next != null){
            if(curr.freq == 0){
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = prev.next;
        }

        if(curr.freq == 0){
            prev.next = curr.next;
        } else {
            prev = curr;
        }

        return huff;
    }

    /**
     * buildLL - builds the Linked List using an array
     * @param abcFreq
     * @return
     */
    public static Huffnodes buildLL(int[] abcFreq){
        Huffnodes huff = new Huffnodes((char)0, abcFreq[0], null, null, null);
        Huffnodes prev = huff;
        Huffnodes cur = null;
        for(int i = 1; i < abcFreq.length; i++){
            cur = new Huffnodes((char)i, abcFreq[i],null,null,null);
            prev.next = cur;
            prev = cur;
        }
        huff = bCleanLL(huff);
        return huff;
    }

    public static void printLL(Huffnodes huff){
        Huffnodes curr = huff;
        while(curr.next != null){
            print(curr);
            curr = curr.next;
        }
        print(curr);
    }

    /**takes two nodes and creates a merged node in the huffmantree which it returns*/
    public Huffnodes mergeNode(Huffnodes next, Huffnodes node1, Huffnodes node2, int ct) {
        int newF = node1.freq + node2.freq;
        char newC = (char)ct;
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
        int ct = 1;
        if(l < 2){
            return huff;
        } else if (l==2){
            Huffnodes merge = mergeNode(null,getNode(huff,0),getNode(huff,1), ct);
            ct++;
            return merge;
        } else {
            Huffnodes merge = mergeNode(null,getNode(huff,0),getNode(huff,1), ct);
            ct++;
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
            // System.out.println("\nList: ");
            // printLL(huff);
            return buildHuffT(huff);
        }
    }

    public Huffnodes buildT(){
        Huffnodes huff = this;
        huff = huff.sortList();
        // System.out.println("\nList after sorting: ");
        // printLL(huff);
        Huffnodes rootN = buildHuffT(huff);
        return rootN;
    }

    // public void buildEverything(int[] abcFreq){
        
    //     // System.out.println("Tree: ");
    //     // printT(rootN);
        
    // }
    /**
     * Recursively performs a preorder traversal of the tree/subtree
     * whose root is specified, printing the keys of the visited nodes.
     * Note that the parameter is *not* necessarily the root of the 
     * entire tree. 
     */
    private static void findBitC(Huffnodes root, String bitStr, String[] arr) {
        // System.out.println(root.ch);
        if ((root.left == null) && (root.right == null)){
            int asc = (int)root.ch;
            // System.out.println(root.ch);
            if((asc >= 0) && (asc <= 1000)){
                arr[asc] = bitStr;
            }
        }
        if (root.left != null) {
            // System.out.println(bitStr);
            findBitC(root.left,bitStr + "0",arr);
        }
        if (root.right != null) {
            findBitC(root.right, bitStr + "1",arr);
        }

        
    }
    

    /**
     * charToCode: turns character into ascii, find it in the array
     * @param char caracter
     * @param Huffnodes root node
     * @param String[] array containing codes
     * @return code as String
    */

    public static void charsToCode(Huffnodes root, String[] arr){
        findBitC(root, "", arr);
    }

    public static void printCode(String[] code){
        for(int i = 0; i < code.length; i++){
            if(code[i] != null){
                System.out.print(" " + (char)i + " freq: " + code[i] + " ");
            }
        }
    }
    

    /**charConvert: final method used by Huffmann.java
     */
    public static String[] charConvert(int[] abcFreq){
        Huffnodes huff = buildLL(abcFreq);
        Huffnodes rootN = huff.buildT();
        String[] code = new String[10001];
        charsToCode(rootN, code);
        //printCode(code);
        return code;

    }
    /***
     * printT: prints out all nodes of the tree
     * @param root
     */
    public static void printT(Huffnodes root){
        print(root);
        if(root.left != null){
            System.out.println();
            System.out.println("L: ");
            printT(root.left);
        }
        if(root.right != null){
            System.out.println();
            System.out.println("R: ");
            printT(root.right);
    
        }
    }

    //testing
    public static void cTest(int[] abcFreq){
        

    }

    public static void main(String[] args) throws FileNotFoundException{
       

    }

}
