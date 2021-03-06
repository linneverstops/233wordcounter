/** 
 * EECS233 HW6 Programming Project 3
 * Tung Ho Lin
 */

import java.math.BigDecimal;
import java.math.MathContext;

public class MyHashTable {
  
  private MyNode[] data;
  
  private int maxSize;
  
  private int curSize;
  
  public MyHashTable() {
    data = new MyNode[64];  //starts with an initial length of 64
    maxSize = 64;
    curSize = 0;
  }        
          
  public MyNode[] getData() {
    return data;
  }
  
  public int getMaxSize() {
    return maxSize;
  }
  
  //a method to calculate the loadfactor and round it to 3 sigfig
  public double loadfactor() {
    double d = (double) curSize/maxSize;
    BigDecimal bd = new BigDecimal(d);
    bd = bd.round(new MathContext(3));
    double rounded = bd.doubleValue();
    return rounded;
  }
  
  public int hash(String word) {
    int num = word.hashCode();
    if(num < 0)
      num = num * -1;
    return num % maxSize;
  }
  
  public void rehash() {
    int oldSize = maxSize;
    MyNode[] oldData = data;
    maxSize = oldSize * 2;
    data = new MyNode[maxSize];
    for(int i=0; i<oldSize; i++) {
      if(oldData[i] != null) {
        MyNode curNode = oldData[i];
        while(curNode != null) {
          put(curNode.data);
          curNode = curNode.next;
        }
      }
    }
  }
  
  public void put(String word) {
    if(contains(word))
      getNode(word).increment();  //increment the occur if the word already exists
    else {
      int index = hash(word);
      if(data[index] != null)
        data[index].getLast().next = new MyNode(word, null);
      else
        data[index] = new MyNode(word, null);
      curSize++;
    }
  }
  
  //get the Node that contains the input word
  public MyNode getNode(String word) {
    int index = hash(word);
    if(data[index] == null)   //the word has not been hashed into the table before, return null
      return null;
    else {
      MyNode curNode = data[index];
      while(curNode!= null){
        if(curNode.data.equals(word))
          return curNode;   //find the word 
        curNode = curNode.next;
      }
      return null;   //return null if the word doesn't exist
    }
  }
  
  public boolean contains(String word) {
    if(getNode(word) == null)
      return false;
    else
      return true;
  }
  
  //inner class MyNode
  public class MyNode {
    
    private String data;
    
    private MyNode next;
    
    private int occur;  //the number of times this word occur
    
    public MyNode(String data, MyNode next) {
      this.data = data;
      this.next = next;
      this.occur = 1;
    }
    
    public MyNode getNext() {
      return next;
    }
    
    public String getData() {
      return data;
    }
    
    public void setData(String data) {
      this.data = data;
    }
    
    public int getOccur() {
      return occur;
    }
    
    //increment the number of occurences of the word
    public void increment() {
      occur++;
    }
   
    //to get the last node directly/indirectly connected to this node
    public MyNode getLast() {
      MyNode curNode = this;
      while(curNode.next != null)
        curNode = curNode.next;
      return curNode;
    }
    
  }
}