package com.tst.test.collection;

import java.util.Arrays;

/**
 * Created by Kunal Chowdhury on 9/25/2015.
 */
public class StringSearchTree {

    private static final int RADIX = 256;

    private SearchNode root = new SearchNode();

    private int currentState = 0;

    private int failureFunction[];

    private int count = 0;

    private SearchNode[] states = new SearchNode[1000000];

    private static class SearchNode{
        private int value;
        private SearchNode[] next = new SearchNode[RADIX] ;
    }

    public int get(String key){
        SearchNode x = get(root, key, 0);
        if (x == null) return -1;
        return x.value;
    }

    private SearchNode get(SearchNode x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void put(String key, int val) {
        root = put(root, key, val, 0);
        count++;
    }

    private SearchNode put(SearchNode x, String key, int val, int d) {
        boolean isNew =(count == 0 || x== null);
        if (x == null) {
            x = new SearchNode();
        }
        if (d == key.length()) {
            x.value = val;
            return x;
        }
        char c = key.charAt(d);
        //System.out.println("c = "+c);
        if(c == 'r'){
            System.out.println("state = "+currentState+" "+isNew);
        }
        if(isNew) {
            states[currentState++] = x;
        }
        x.next[c] = put(x.next[c], key, val, d+1);
        if(d == 0 ){
            //SearchNode dummy = new SearchNode();
            states[currentState++] = null;

      //    System.out.println("completed "+key);
        }
        return x;
    }
    private void print(SearchNode n){
        if(n != null) {
            for (int i = 0; i < n.next.length-1; i++) {
                if (n.next[i] != null) {
                    System.out.print((char) i + ",");
                }
            }
        }
        System.out.println();
    }
    public void evaluateFailureFunction(){
        failureFunction = new int[currentState];
        int t = 0;
        failureFunction[0] = 0;
        for (int s = 1; s < currentState -1 ; s++) {
            SearchNode sNode = states[s+1];
            SearchNode tNode ;
            while (t > 0){
                tNode = states[t+1];
                if(!equals(sNode, tNode)){
                   t = failureFunction[t];
                }
            }
            tNode = states[t+1];
            if(atLeastEquals(sNode, tNode)){
                t = t+1;
                failureFunction[s+1] = t;
            }else{
                failureFunction[s+1] = 0;
            }

        }
        System.out.println(Arrays.toString(failureFunction));

    }

    private boolean atLeastEquals(SearchNode sNode, SearchNode tNode) {
        if(sNode == null || tNode == null){
            return false;
        }
        for (int i = 0; i < RADIX; i++) {
            if(sNode.next[i] != null && tNode.next[i] != null){
                return true;
            }
        }
        return false;
    }

    private boolean equals(SearchNode sNode, SearchNode tNode) {
        if(sNode == null || tNode == null){
            return false;
        }
        for (int i = 0; i < RADIX; i++) {
            if((sNode.next[i] != null && tNode.next[i] == null)
                    ||(sNode.next[i] == null && tNode.next[i] != null)){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String strs[] = new String[]{"he", "she", "his", "hers"};
        StringSearchTree st = new StringSearchTree();
        for (int i = 0; i < strs.length; i++) {
            st.put(strs[i], i);
        }
        /*for (int i = 0; i < strs.length; i++) {
            System.out.println(st.get(strs[i]));
        }
*/
      /*  for (int i = 0; i < st.currentState-1; i++) {
            SearchNode state = st.states[i];
            System.out.println(Arrays.toString(state.next));
            *//*for (int j = 0; j < RADIX; j++) {
                System.out.println(state.value);
            }*//*
        }
*/
        for (int i = 0; i < st.currentState  ; i++) {
            System.out.print(i);
            st.print(st.states[i]);

        }

        st.evaluateFailureFunction();
        /*int length = 6;
        int pos = 0;
        int cur = 0;
        */
        //System.out.println(null != null);
        //for (int i = pos; i < length; i++) {
            //getDigitAt(i,6);
            /*int d = (int)(123456 / Math.pow(10,
                    length - i -1));*/
            //System.out.println("-- >> "+getDigitAt(i,6,123456));
            /*cur = d*10;*/

        //}

    }

}
