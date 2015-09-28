package com.tst.test.util;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kunal Chowdhury on 9/26/2015.
 */
public class SearchUtil {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder(10);
        //sb.insert(0, "abc");
        sb.insert(3, "xyz");
        System.out.println(sb);
    }

    public static void main3(String[] args) {
        List<StringBuilder> l1 = new ArrayList<StringBuilder>();
        l1.add(new StringBuilder("abc"));
        l1.add(new StringBuilder("def"));
        l1.add(new StringBuilder("ghi"));
        l1.add(new StringBuilder("xyz"));

        List<String> l2 = new ArrayList<String>();
        l2.add("nme");
        l2.add("pol");
        l2.add("kun");

        //List<String> temp = l1.subList(0, l1.size() - 1);
        List<StringBuilder> atemp = new ArrayList<StringBuilder>();
        for (StringBuilder s : l1){
            atemp.add(s);
        }

        int tempSz = atemp.size();
        int targetSize = l1.size()*l2.size();
        int pos = l1.size();

        while (l1.size() < targetSize) {
            l1.addAll(pos, atemp);
            pos+= tempSz;
        }
        System.out.println(l1);
        int k = 0;
        for (int i = 0; i < l2.size(); i++) {
            for (int j = 0; j < tempSz; j++) {
                StringBuilder stringBuilder = new StringBuilder(l1.get(k));
                stringBuilder.append(l2.get(i)) ;
                l1.set(k++, stringBuilder);
            }
        }

        System.out.println(l1);
    }

    public static void main1(String args[]) {

        ArrayList<int[]> input = new ArrayList<int[]>();
        input.add(new int[] { 1, 2, 3 });
        input.add(new int[] { 4, 5 });
        input.add(new int[] { 6, 7 });

        combine(input, new int[input.size()], 0);
    }

    private static void combine(ArrayList<int[]> input, int[] current, int k) {

        if(k == input.size()) {
            for(int i = 0; i < k; i++) {
                System.out.print(current[i] + " ");
            }
            System.out.println();
        } else {
            for(int j = 0; j < input.get(k).length; j++) {
                current[k] = input.get(k)[j];
                combine(input, current, k + 1);
            }
        }
    }
}
