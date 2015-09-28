package com.tst.test.collection;

/**
 * Created by Kunal Chowdhury on 9/27/2015.
 */
public class Node<T> {

    private T element ;

    private Node next;

    public Node(T element) {
        this.element = element;
    }

    public Node(T element, Node next) {
        this.element = element;
        this.next = next;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "element=" + element +
                '}';
    }
}
