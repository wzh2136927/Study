package com.study.demo.huffman;

public class Node {
    public  Node rightNode;
    public  Node leftNode;
    public int weight;
    public int character;

    public Node(int weight){
        this.weight = weight;
    }
    public Node(int weight,int character){
        this.weight=weight;
        this.character =character;
    }
    public  Node (int weight,Node leftNode,Node rightNode){
        this.weight= weight;
        this.leftNode= leftNode;
        this.rightNode= rightNode;
    }

    public void setRightNode(Node rightNode){
        this.rightNode = rightNode;
    }
    public void setLeftNode(Node leftNode){
        this.leftNode= leftNode;
    }
    public void setWeight(int weight){
        this.weight =weight;
    }
    public void setCharacter(char character){
        this.character= character;
    }
    public Node getRightNode(){
        return this.rightNode;
    }
    public Node getLeftNode(){
        return this.leftNode;
    }
    public int  getCharacter(){
        return this.character;
    }
    public int getWeight(){
        return this.weight ;
    }
    public int  compareTo(Node N){
        if(this.weight>N.weight)return 1;
        else if(this.weight<N.weight)
            return -1;
        else return 0;
    }
}
