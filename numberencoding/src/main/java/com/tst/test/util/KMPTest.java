package com.tst.test.util;

import java.util.Arrays;

/**
 * Created by Kunal Chowdhury on 9/26/2015.
 */
public class KMPTest {

    public static void failureFunction(String str){
        char[] b = new char[str.length()+1] ;
        System.arraycopy(str.toCharArray(), 0, b, 1,
                str.toCharArray().length);
        int ffunction[] = new int[b.length];
        int t = 0;
        ffunction[0] = 0;
        for (int s = 1; s < str.length(); s++) {
             while (t > 0 && b[s+1] != b[t+1]) t= ffunction[t];
             if(b[s+1] == b[t+1]){
                 t = t+1;
                 ffunction[s+1] = t;
             }else{
                 ffunction[s+1] = 0;
             }
        }
        System.out.println(Arrays.toString(ffunction));
    }

    public static void main(String[] args) {
        failureFunction("ababaa");
    }


}
