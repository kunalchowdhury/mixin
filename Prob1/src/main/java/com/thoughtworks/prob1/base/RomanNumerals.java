package com.thoughtworks.prob1.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class is for easy switching between integer and Roman numerals
 * and maybe used by other components effectively
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public enum RomanNumerals {
    I(0, 1),
    V(1, 5),
    X(2, 10),
    L(3, 50),
    C(4, 100),
    D(5, 500),
    M(6, 1000);

    private int val ;
    private int ordinalSpecifier ;

    private static Map<Character, RomanNumerals> map = new HashMap<Character, RomanNumerals>(){{
        put('I', I);
        put('V', V);
        put('X', X);
        put('L', L);
        put('C', C);
        put('D', D);
        put('M', M);

    }};

    private static Map<Integer, RomanNumerals> invmap = new HashMap<Integer, RomanNumerals>(){{
        put(1, I);
        put(5, V);
        put(10, X);
        put(50, L);
        put(100, C);
        put(500, D);
        put(1000, M);

    }};

    RomanNumerals(int ordinalSpecifier , int val) {
        this.val = val;
        this.ordinalSpecifier = ordinalSpecifier;
    }

    public static int getVal(Character roman){
        return map.get(roman).val ;
    }
    public static RomanNumerals getRomanNumeral(Character roman){
        return map.get(roman);
    }

    public static RomanNumerals getRomanFromInt(int val){
         return invmap.get(val);
    }

    public int getOrdinalSpecifier() {
        return ordinalSpecifier;
    }

    public static Set<Character> validCharacters(){
        return map.keySet();
    }



}
