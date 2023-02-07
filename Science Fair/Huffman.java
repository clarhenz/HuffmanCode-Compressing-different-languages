/*
* Clara Henzinger
* 30.11.2022
* Huffman Encoder and Decoder
*/

import java.util.*; // needed for Scanner
import java.io.*; // needed for File

public class Huffman extends Huffnodes{
    
    private String abc = "abcdefghijklmnopqrstuvwxyz";


    

    /* what to do next: 
     * import the frequency analysis methods
     * 
     * read file in
     * 
     * write a method that takes a x long string
     *  breaks it up into characters and runs charToCode on it 
     * 
    */

    //conducter
    public Huffman(String abc) {
        super(' ',0,null,null,null);
        this.abc = abc;
    }

    
   
        
    //returns the frequency of letters

    public static int freq(String text, char n){
        if(text == ""){
            return 0;
        } else {
            if (n == text.charAt(0)){
                return 1 + freq(text.substring(1),n);
            } else {
                return freq(text.substring(1),n);
            }
        }
    }

    public int[] frequency(String text){
        int[] freqA = new int[this.abc.length()];
        for(int i = 0; i < this.abc.length();i++){
            freqA[i] = freq(text, this.abc.charAt(i));
        }
        return freqA;
    }

    public static void main(String[] args){
        Scanner console = new Scanner(System.in);
        System.out.print("Name of file: ");
        String fileName = console.next();
        
        console.close();
    }
}
