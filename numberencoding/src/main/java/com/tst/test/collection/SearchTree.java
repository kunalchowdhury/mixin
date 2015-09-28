package com.tst.test.collection;

/**
 * Created by Kunal Chowdhury on 9/25/2015.
 */
public class SearchTree {

    private static final int RADIX = 10; // 10 digits + 1 slash + 1 double quote

    private SearchNode root = new SearchNode();

    private static class SearchNode{
        private String[] value ;
        private SearchNode[] next = new SearchNode[RADIX] ;
        private int currentIdx;
    }


    public void put(int key, String val, int keyLength){
        root = put(root, key, val,0, keyLength);
    }

    public void find(int key){
        SearchNode x = get(root, key, 0);

    }

    private SearchNode get(SearchNode root, int key, int i) {
        return null;
    }

    private SearchNode put(SearchNode node,
                           int key,
                           String val,
                           int pos, int keyLength){
        if(node == null) {
            node = new SearchNode();
        }
        if(pos == keyLength ){
            ensureCapacity(node);
            node.value[node.currentIdx] = val ;
            node.currentIdx++;
            return node;
        }
        int digit = getDigitAt(pos, keyLength, key);
        node.next[digit] = put(node.next[digit], key, val, pos+1, keyLength);
        return node;
    }

    private void ensureCapacity(SearchNode node) {
        if(node.currentIdx == node.value.length -1){
                 String[] s = new String[2*node.value.length];
                 System.arraycopy(node.value, 0, s, 0, node.value.length);
        }
    }

    private int getDigitAt(int idx, int length, int key){
        int prev =0;
        int d = (int)(key / Math.pow(10,
                length - idx -1));
        if(idx != 0){
            prev = (int)(key / Math.pow(10,
                    length - idx ));
        }
        return (d - prev*10);

    }

    public static void main(String[] args) {
        int length = 6;
        int pos = 0;
        int cur = 0;
        for (int i = pos; i < length; i++) {
            //getDigitAt(i,6);
            /*int d = (int)(123456 / Math.pow(10,
                    length - i -1));*/
           //System.out.println("-- >> "+getDigitAt(i,6,123456));
            /*cur = d*10;*/

        }

    }

}
