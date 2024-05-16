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
        int ct = 0;
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) == n){
                ct++;
            }
        }
        return ct;
    }
    //    if(text == ""){
    //        return 0;
    //    } else {
    //        if (n == text.charAt(0)){
    //            return 1 + freq(text.substring(1),n);
    //        } else {
    //            return freq(text.substring(1),n);
    //        }
    //    }
    //}

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

    public static int freqLine(String text, int[] freqA){
        
        //int[] freqA = new int[10000];
        int skipped = 0;
        for(int i = 0; i < text.length(); i++){
            int j = (int)text.charAt(i);
            if ((j>= 0) && (j<10000)){
                freqA[j]++;
            } else {
                skipped++;
            }
        }
        //for(int i = 0; i < 10000;i++){
        //    freqA[i] = freq(text, (char)i);
        //}
        return skipped;
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
        int skipped = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
            skipped = skipped + freqLine(line, freqA); 
            //addArrays(freqA,freqLine(line));
        }
        if (skipped > 0) {
            System.out.println("skipped chars:" + skipped);
        }
        return freqA;
    }

    public static String finalCode(String filename) throws FileNotFoundException{
        Scanner console = new Scanner(new File(filename));
        // System.out.println("printing " + filename);
        int[] freqABC = new int[10001];
        freqABC = freqText(filename);
        String[] code = charConvert(freqABC);
        String f = "";
        int count = 0;
        int c = 0;
        int flength = 0;
        while (console.hasNextLine()) {
            // System.out.println("in while loop ");
            try{
                String line = console.nextLine();
                c++;
                //System.out.println(line);
                for(int i = 0; i < line.length(); i++){
                    int j = (int)line.charAt(i);
                    if((j  >= 0) && (j < code.length)) {
                        String cur = code[j];
                        flength = flength + cur.length();
                        f = f + cur;  
                    } else {
                        count++;
                        
                    }
                }
            } catch(java.lang.Exception e){
                System.out.println("An error occured");
                e.printStackTrace();
            }
            
            
            
        } 
        System.out.println("number of processed lines: " + c);
        System.out.println("total number of skipped ch: " + count);
        console.close();
        //System.out.println("new file length:" + flength/8.0 + " bytes = " + 1.0* flength/(8.0*1024) + " kbytes = " + 1.0* flength/(8.0*1024*1024) + " MB");
        return f;
    }
   

    public static void writeResult(File summaryF, String filename, double olength, double l2) throws FileNotFoundException{
        //Write in summary File
        double ratio = l2/olength;
        try{
            FileWriter myWriter = new FileWriter(summaryF,true);
            myWriter.write("\n");
            myWriter.write(filename + ":");
            myWriter.write("\nSize of original file: " + olength + " bytes = " + olength/(1024) + " kbytes = " + olength/(1024*1024) + " MB");
            myWriter.write("\nSize of new file: " + l2 + " bytes = " + l2/(1024) + " kbytes = " + l2/(1024*1024) + " MB");
            myWriter.write("\nCompression ratio:" + ratio);
            myWriter.write("\n");
            myWriter.close();
            // System.out.println("Success");
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }    

    public static void writeCodeToFile(String code, String filename) throws FileNotFoundException {
        //create a File
        String filename2 = filename.substring(0,(filename.length() - 4)) + "-compressed.txt";
        File compressed = new File(filename2);
        try{
            if(compressed.createNewFile()){
                System.out.println("File created: " + compressed.getName());
            }else{
                ;
                // System.out.println("File " + compressed.getName() + " already exists");
            }      
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }   
        //write in file
        try{
            FileWriter myWriter = new FileWriter(filename2);
            myWriter.write(code);
            myWriter.close();
            // System.out.println("Success");
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    public static void processFile(File summaryF, String filename) throws FileNotFoundException {
        File original = new File(filename);
        double l1 = 1.0*original.length();
        System.out.println(filename +": "+ "Size of original file: " + l1 + " bytes = " + l1/(1024) + " kbytes = " + l1/(1024*1024) + " MB");

        String code =  finalCode(filename); 
        writeCodeToFile(code, filename);

        double l2 = (code.length())/8.0;          
        System.out.println("Size of new file: " + l2 + " bytes = " + l2/(1024) + " kbytes = " + l2/(1024*1024) + " MB");
        double ratio = l2/l1;
        System.out.println("Compression ratio:" + ratio);
    
        writeResult(summaryF, filename, l1, l2);

    }

    public static void convertAndWrite() throws FileNotFoundException{
         
        //Create Summary File
        String summary = "STPWC/ChatGBTvsAuthors.txt";
        File summaryF = new File(summary);
        try{
            if(summaryF.createNewFile()){
                System.out.println("File created: " + summaryF.getName());
            }else{
                System.out.println("File " + summaryF.getName() + " already exists");
            }
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }

        Scanner console = new Scanner(System.in);
        System.out.print("File Name/ Author: ");
        String filename = console.nextLine();
        System.out.print("ChatGBT (yes: 1 /no: 2): ");
        int ChatGBTyn = Integer.valueOf(console.nextLine());
        console.close();
        System.out.println(ChatGBTyn == 1);

        if (filename.contains(".txt")) {
            processFile(summaryF, filename);
        } else {
            String author = filename;
            for (int i = 1; i<11; i++){
                if(ChatGBTyn == 1){
                    filename = "STPWC/ChatGBT" + "/" + author + i + "_ChatGBT.txt";
                } else {
                    String[] nameAuth = author.split(" ");
                    String dirAuth = nameAuth[1];
                    String filesAuthor = nameAuth[0] + nameAuth[1];
                    filename = "STPWC/" + dirAuth + "/" + filesAuthor + i + ".txt";
                }
                processFile(summaryF, filename);
                
            }   
        }
    }

    public static double fileRatio(String filename) throws FileNotFoundException {
        File original = new File(filename);
        double l1 = 1.0*original.length();
        String code =  finalCode(filename); 

        double l2 = (code.length())/8.0;          
        double ratio = l2/l1;
        return ratio;

    }

    public static void writeAllRatios(String summary, String filename) throws FileNotFoundException{
        String part = filename;
        try{
            
            for (int i = 1; i<11; i++){
                FileWriter myWriter = new FileWriter(summary,true);
                filename = "ASCII TEXTS\\EU Parlament\\T" + i +"_" + part + ".txt"; 
                myWriter.write(i + ": " + fileRatio(filename));
                myWriter.write("\n");
                myWriter.close();
            }
            
                
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }

    public static double lsum(String summary, String filename) throws FileNotFoundException {       
        String part = filename;
        double mySum = 0;
        
        for (int i = 1; i<11; i++){
            filename = "ASCII TEXTS\\EU Parlament\\T" + i +"_" + part + ".txt";
            mySum = mySum + fileRatio(filename);
            
        }
        
        return mySum;
    }

    public static double lmean(String summary,String filename) throws FileNotFoundException {       
        double mySum = lsum(summary, filename);
        double mean = mySum/10.0;
        return mean;
    }

    public static double ldeviantion(String summary, String filename) throws FileNotFoundException{ 
        String part = filename;
        double mean = lmean(summary, filename);
        System.out.println("mean: " + mean);
        double deviation = 0.0;

        for (int i = 1; i<11; i++){
            filename = "ASCII TEXTS\\EU Parlament\\T" + i +"_" + part + ".txt";
            deviation = deviation + ((fileRatio(filename) - mean)*(fileRatio(filename) - mean));
        }
        System.out.println(deviation);
        deviation = deviation/(10.0-1.0);
        deviation = Math.sqrt(deviation);
        try{
            FileWriter myWriter = new FileWriter(summary,true);
            myWriter.write("\n");
            myWriter.write("mean: " + mean);
            myWriter.write("\n");
            myWriter.write("deviation: " + deviation);
            myWriter.write("\n");
            myWriter.close();
            
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
        return deviation;
    }

    public static void fStats(String summary) throws FileNotFoundException{
        
        Scanner console = new Scanner(System.in);
        System.out.print("Language: ");
        String filename = console.nextLine();
        console.close();
        try{
            FileWriter myWriter = new FileWriter(summary,true);
            myWriter.write("\n");
            myWriter.write("\n");
            myWriter.write("Language: " + filename);
            myWriter.write("\n");
            myWriter.close();
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }
        writeAllRatios(summary, filename);
        System.out.println("deviation: " + ldeviantion(summary, filename));
        
    }
    public static void demo() throws FileNotFoundException{
        //Create Summary File
        String summary = "demoResult.txt";
        File summaryF = new File(summary);
        try{
            if(summaryF.createNewFile()){
                System.out.println("File created: " + summaryF.getName());
            }else{
                System.out.println("File " + summaryF.getName() + " already exists");
            }
        }catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }

        String filename = "demoWrite.txt";
        processFile(summaryF, filename);
        
    }

    public static void main(String[] args) throws FileNotFoundException{
        convertAndWrite();
        //fStats("deviationASCII.txt");
        //demo();
    }
}