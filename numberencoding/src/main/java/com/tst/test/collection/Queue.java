package com.tst.test.collection;

import java.util.Iterator;

/**
 * Created by Kunal Chowdhury on 9/27/2015.
 */
public class Queue implements Iterator<String>{
    private Node<String> head, tail;
    private int size;

    public int size(){
        return size;
    }

    public void add(String s){
        final Node<String> node = new Node<String>(s);
        node.setNext(null);
        if(size == 0){
            head = tail = node;
            head.setNext(tail);
            tail.setNext(null);
        }else{
            tail.setNext(node);
            tail = node ;
        }
        size++;
    }
    private String dequeue(){
        String s = head.getElement();
        head = head.getNext();
        size-- ;
        return s;
    }

    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.add("A");
        queue.add("B");
        queue.add("C");
        /*System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());*/
        while(queue.hasNext()){
            System.out.println(queue.next());
        }
    }

    public boolean hasNext() {
        return (head != null);
    }

    public String next() {
        String element = head.getElement();
        head = head.getNext();
        return element;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}
