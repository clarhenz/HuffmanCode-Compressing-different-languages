/*
* Clara Henzinger
* 30.11.2022
* Huffman Encoder and Decoder
*/

import java.util.*; // needed for Scanner
import java.io.*; // needed for File
import java.io.ObjectInputStream.GetField;

public class Huffman extends Huffnodes{

    //conducter
    public Huffman(String abc) {
        super(' ',0,null,null,null);

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

    /**
     * xLine - takes File return the xth line of text
     * @param int x
     * @param String filename
     * @return String text 
     */

    public static String xLine(int x, String fileName) throws FileNotFoundException{
        Scanner input = new Scanner(new File(fileName));
        String xLine;
        for(int i = 1; i < x; i++){
            input.nextLine();
        }
        xLine = input.nextLine();
        return xLine;

    }
    /**
     * returns an array containing the frequency of all letters
     * */

    public static int[] freqLine(String text){
        
        int[] freqA = new int[10000];
        for(int i = 0; i < 10000;i++){
            freqA[i] = freq(text, (char)i);
        }
        return freqA;
    }

    /** addArrays: takes two int arrays and adds their content in a thrid array
     * 
     * @param int[] arr1
     * @param int[] arr2
     * @return int[] freq
     */
    public static int[] addArrays(int[] arr1, int[] arr2){
        int[] shArr;
        int[] loArr;
        if(arr1.length > arr2.length){
            loArr = arr1;
            shArr = arr2;
        } else {
            loArr = arr2;
            shArr = arr1;
        }
        int[] freq = new int[loArr.length];

        for(int i = 0; i < shArr.length; i++){
            freq[i] = arr1[i] + arr2[i];
        }

        for(int i = shArr.length; i < loArr.length; i++){
            freq[i] = loArr[i];
        }

        return freq;
    }
    /**freqText -goes through the entire text line by line 
     * and returns an array w the frequencies of all texts
     * @param fileName
     * @return freqA
     * @throws FileNotFoundException
     */
    public static int[] freqText(String fileName) throws FileNotFoundException{
        int[] freqA = new int[10001];
        Scanner input = new Scanner(new File(fileName));
        while (input.hasNextLine()) {
            String line = input.nextLine();
            freqA = addArrays(freqA,freqLine(line));
        }

        return freqA;
    }

    public static String finalCode(String filename) throws FileNotFoundException{
        Scanner console = new Scanner(new File(filename));

        int[] freqABC = new int[10001];
        freqABC = freqText(filename);
        System.out.println();
        String[] code = charConvert(freqABC);
        String f = "";
        int count = 0;
        while (console.hasNextLine()) {
            String line = console.nextLine();
            for(int i = 0; i < line.length(); i++){
                if(((int)line.charAt(i)) < code.length){
                    f = f + code[(int)line.charAt(i)];
                    
                } else {
                    count++;
                }
                
            }
        } 
        System.out.println("number of skipped ch: " + count);
        console.close();
        return f;
    }
   
    public static void main(String[] args) throws FileNotFoundException{
        Scanner console = new Scanner(System.in);
        
        System.out.print("File Name: ");
        String filename = console.nextLine();
        File original = new File(filename);
        System.out.println("Size of original file: " + original.length() + " bytes");

        String c =  finalCode(filename);
        
        //create a File
        String filename2 = filename.substring(0,(filename.length() - 4)) + "-compressed.txt";
        File compressed = new File(filename2);
        try{
            if(compressed.createNewFile()){
                System.out.println("File created: " + compressed.getName());
            }else{
                System.out.println("File " + compressed.getName() + " already exists");
            }
            
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }

        //write in file
        try{
            FileWriter myWriter = new FileWriter(filename2);
            myWriter.write(c);
            myWriter.close();
            System.out.println("Success");
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }

        
        System.out.println("Size of new file: " + compressed.length()/8.0 + " bytes");

        console.close();
    }
}
