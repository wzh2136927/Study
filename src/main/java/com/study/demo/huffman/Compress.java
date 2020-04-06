package com.study.demo.huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Compress {
    static int count = 7;
    static int buffer = 0;
    static int length=0;
    ArrayList<Node> nodes  = new ArrayList<>();
    private File compressFile;
    public Compress(String path) throws IOException {
        String out;
        compressFile = new File(path);
        //此处因该只能有一个流
        if(compressFile.isDirectory()){
            out=path+".zip";
        }else {
            String prefix = path.substring(0, path.lastIndexOf("."));//
            out= prefix+".zip";
        }
        BufferedOutputStream outputStream= new BufferedOutputStream(new FileOutputStream(out));
        compressFile(compressFile,outputStream);
        outputStream.close();
    }
    public void compressFile(File ptah,BufferedOutputStream bufferedOutputStream) throws IOException {
        if(ptah.isDirectory()){
            String directoryName = ptah.getPath();
            bufferedOutputStream.write(directoryName.length());
            int type=1;
            bufferedOutputStream.write(type);
            for(int a=0;a<directoryName.length();a++){
                char ch = directoryName.charAt(a);
                bufferedOutputStream.write(ch);
                //把文件名的长度和文件名写入
            }
            compress_file(ptah,bufferedOutputStream);
        }else {
            int type=0;
            String directoryName = ptah.getPath();
            // System.out.println(directoryName+"  "+ directoryName.length());
            bufferedOutputStream.write(directoryName.length());
            bufferedOutputStream.write(type);//类型
            for(int a=0;a<directoryName.length();a++){
                char ch = directoryName.charAt(a);
                bufferedOutputStream.write(ch);
                //把文件名的长度写入
            }
            // System.out.println(directoryName.length() + "   type   "+ type +"   "+ directoryName );
            compress_f(ptah,bufferedOutputStream);
        }
    }

    private void compress_file(File ptah, BufferedOutputStream bufferedOutputStream) throws IOException {
        if (!ptah.exists())
            return;
        File[] files = ptah.listFiles();
        for (int i = 0; i < files.length; i++) {
            compressFile(files[i],bufferedOutputStream);
        }
    }
    private void compress_f(File file, BufferedOutputStream bufferedOutputStream) throws IOException {
        if(!file.exists())
            return;
        int[] characterAndWeight =getInts(file);
        for(int i= 0;i<256;i++){
            if(characterAndWeight[i]!=0){
                nodes.add(new Node(characterAndWeight[i],i));
            }
        }//得到哈夫曼树的权重和节点
        HashMap<Integer,String> map= new HashMap<>();
        HuffmanTree huffmanTree =new HuffmanTree(nodes);
        huffmanTree.print(huffmanTree.root,"",  map);//create the hashMap
        writeFile(file,bufferedOutputStream,map);//compress the file
        nodes.clear();
        map.clear();
    }

    public static void writeFile(File path, BufferedOutputStream bufferedOutputStream, HashMap<Integer,String> map) throws IOException {
        BufferedInputStream fis = new BufferedInputStream(new FileInputStream(path));
        BufferedOutputStream out = bufferedOutputStream;
        String theCodeOfLength="";
        for(int i=0x80000000;i!=0;i>>>=1){
            theCodeOfLength+=(length&i)==0?'0':'1';
        }//转换成32位二进制
        for (int j = 0; j<32 ; j++){
            char ch = theCodeOfLength.charAt(j);
            writeBit(ch-'0',out);
        }
        //System.out.println(length+"  32位编码 "+ yy);
        length=0;
//write the length of byte into the compressed file
        for(int i =0;i<=255;i++){
            if(map.containsKey(i)){
                String character= map.get(i);
                int a = character.length();
                out.write((byte)a);
            }else {
                out.write((byte)0);
            }
        }
        //写入哈夫曼编码长度
        for(int i= 0;i<=255;i++){
            if(map.containsKey(i)){
                String character = map.get(i);
                for (int j = 0; j< character.length(); j++) {
                    char ch = character.charAt(j);
                    writeBit(ch-'0',out);
                }
            }
        }//写入哈夫曼编码

        //System.out.println(buffer+" the last  code    "+count);
        int  value= fis.read();
        while(value!=-1){
            String str= map.get(value);
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                writeBit(ch-'0',out);
            }
            value = fis.read();
        }
        System.out.println(buffer);
        if(buffer!=0)
            out.write(buffer);//写入文件的末尾
        buffer=0;
        count=7;
        fis.close();
    }


    private static void writeBit(int ch,BufferedOutputStream outputStream) throws IOException {
        int a= ch<<count;
        buffer=buffer|a;
        count--;
        if (count==-1){
            outputStream.write(buffer);
            count=7;
            buffer=0;
        }
    }



    public  static int[] getInts(File path) throws IOException {
        int [] times = new int[256];
        BufferedInputStream fis = new BufferedInputStream(new  FileInputStream(path));
        int value = fis.read();
        while (value!=-1){
            length++;
            times[value]++;
            value=fis.read();
        }
        fis.close();
        return times;
    }
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        Compress compress =new Compress("G://huffman.txt");//这里更改自己的文件路径就好了
        long end = System.currentTimeMillis();
        System.out.println("execute time:"+(end - start)+"ms");

    }
}
