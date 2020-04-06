package com.study.demo.day01.entity;


import java.util.HashMap;
import java.util.Map;

/**
 * @author wxf
 * @createTime 20191120 13:07
 * @description 字典树(前缀树)
 */
public class Trie {

    public TrieNode root = new TrieNode('/'); // 存储无意义字符
    private class TrieNode {
        private char data;
        private boolean isEndingChar = false;
        private TrieNode [] children = new TrieNode[26];//这里会造成空间上的浪费

        public TrieNode(char data) {
            this.data = data;
        }
    }
    public void insert(char []text){
        TrieNode pointer = this.root;
        for(char c:text){
            if(pointer.children[c-'a']==null){
                pointer.children[c-'a']=new TrieNode(c);
            }
            pointer = pointer.children[c-'a'];
        }
        pointer.isEndingChar = true;
    }

    /**
     * 直接整个单词查找
     * word的每一个字符，挨个去字典树里面找，从root开始遍历，
     * 如果children数组中没有这个字符就直接返回false，没有找到这个单词
     */
    public boolean directSearch(String word){
        TrieNode pointer = this.root;
        for(char c : word.toCharArray()){
            if ( pointer.children[c-'a']==null){
                return false;
            }
            pointer = pointer.children[c-'a'];
        }
        return pointer.isEndingChar;
    }

    /**
     * 模糊查找  后缀*
     * @return
     */
    public boolean startWithSearch(String regix){
        TrieNode pointer = this.root;
        regix = regix.split("\\*")[0];
        for(char c : regix.toCharArray()){
            if ( pointer.children[c-'a']==null){
                return false;
            }
            pointer = pointer.children[c-'a'];
        }
        return true;
    }

    /**
     * 统计查找的个数  TODO
     * @return
     */
    public int count(){
       return 0;
    }

    public static void main(String[] args) {
        Trie test = new Trie();
        char[] str = "test".toCharArray();
        test.insert(str);
//
        System.out.println(test.directSearch("test"));
        System.out.println(test.startWithSearch("tes*"));
         Map map = new HashMap<>();
         map.put("test","222");
    }
} 