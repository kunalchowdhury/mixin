package com.thoughtworks.prob1.util;

import com.thoughtworks.prob1.base.RomanNumerals;

import java.util.*;

import static com.thoughtworks.prob1.base.RomanNumerals.*;

/**
 * A general purpose non-instantiable utility class to assist various operations ,
 * define various constants etc.
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class ExprParserUtil {

    private ExprParserUtil(){}

    public static final String ERROR = "I have no idea what you are talking about" ;

    /**
     * Returns the corresponding int value of a Roman numeral string
     * Assumption - the input string is a valid roman numeral string
     *
     * @param romanString the valid roman numeral string for which
     *                    the int value is returned
     * @return the int value corresponding to the input
     */
    public static int convertRomanToDec(String romanString){
        int sum = 0;
        for (int i = 0; i < romanString.length(); i++) {
            if((i <= romanString.length() -2)){
                int curr = RomanNumerals.getVal(romanString.charAt(i));
                int next = RomanNumerals.getVal(romanString.charAt(i+1));
                if(curr < next){
                    sum += (next - curr);
                    i++;
                }else{
                    sum += curr;
                }
            } else{
                    sum += RomanNumerals.getVal(romanString.charAt(i));
            }

        }

        return sum ;
    }
    /**
     * Returns a boolean denoting whether a given Roman numeral
     * string is valid or not based on the following conditions
     *
     * i. The symbols "I", "X", "C", and "M" can be repeated three
     * times in succession, but no more. (They may appear four times
     * if the third and fourth are separated by a smaller value,
     * such as XXXIX.) "D", "L", and "V" can never be repeated
     *
     * ii. "I" can be subtracted from "V" and "X" only. "X" can be
     * subtracted from "L" and "C" only. "C" can be subtracted from
     * "D" and "M" only. "V", "L", and "D" can never be subtracted.
     *
     * iii. Only one small-value symbol may be subtracted from any
     * large-value symbol
     *
     * The calculated value should not result in the constituent digit
     *
     * @param roman the valid roman numeral string which is to be
     *                    validated
     * @return the boolean value denoting the validation result
     */

    public static boolean isValidRomanNumeral(String roman){
             if((roman.length() == 1) && validCharacters().contains(roman.charAt(0))){
                 return true ;
             }
             boolean isValid = validRepetions(roman) &&
                              subtractionRule(roman) &&
                              consecutiveGreater(roman) ;

             if(isValid){
                 int val = convertRomanToDec(roman);
                 if(getRomanFromInt(val) != null){
                     isValid = false ;
                 }
             }

             return isValid ;

    }
    private static boolean consecutiveGreater(String roman){
        boolean retVal = true;
        for(int i=2 ; i < roman.length() ; i++){
            int curr = RomanNumerals.getVal(roman.charAt(i));
            int prev = RomanNumerals.getVal(roman.charAt(i-1));
            int nextToPrev = RomanNumerals.getVal(roman.charAt(i-2));
            if((curr > prev) && (curr > nextToPrev)){
                     retVal = false ;
                     break;

            }


        }
        return retVal ;
    }

    private static boolean subtractionRule(String roman){
        boolean retVal = true ;
        EnumSet<RomanNumerals> never = EnumSet.of(D ,L, V) ;
        EnumMap<RomanNumerals, EnumSet<RomanNumerals>> restriction =
                new EnumMap<RomanNumerals, EnumSet<RomanNumerals>>(RomanNumerals.class);
        restriction.put(I, EnumSet.of(V, X));
        restriction.put(X, EnumSet.of(L, C));
        restriction.put(C, EnumSet.of(D, M));
        for(int i=0 ; i < roman.length() ; i++){
            if((i <= roman.length() -2)){
                char currChar = roman.charAt(i);
                char nextChar = roman.charAt(i+1);
                int curr = RomanNumerals.getVal(currChar);
                int next = RomanNumerals.getVal(nextChar);
                RomanNumerals currRomanNumeral = getRomanNumeral(currChar);
                RomanNumerals nextRomanNumeral = getRomanNumeral(nextChar);

                if(curr < next){
                      if(never.contains(currRomanNumeral)){
                          retVal = false;
                          break;
                      } else if(!restriction.get(currRomanNumeral).contains(nextRomanNumeral)){
                           retVal = false ;
                           break;
                      }
                      i++;
                }
            }
        }
        return retVal ;

    }

    private static boolean validRepetions(String roman){
        boolean retVal = true;
        int[] restrictedValues = new int[RomanNumerals.values().length];
        EnumSet<RomanNumerals> singles = EnumSet.of(D ,L, V);
        EnumSet<RomanNumerals> duplicates = EnumSet.complementOf(singles);
        for(int i=0 ; i < roman.length() ; i++){
            char c = roman.charAt(i);
            RomanNumerals romanNumeral = getRomanNumeral(c);
            if(romanNumeral == null){
                retVal = false ;
                break;
            }

            int currPos = romanNumeral.getOrdinalSpecifier();
            if(i == 0){
                restrictedValues[currPos] ++ ;
            }else{
                char prev = roman.charAt(i -1);
                if(singles.contains(romanNumeral) ){
                    if(restrictedValues[currPos] ==1){
                        retVal = false;
                        break;
                    }
                }
                if((c == prev) && duplicates.contains(romanNumeral)){
                    if(restrictedValues[currPos] == 3){
                        retVal = false;
                        break;
                    }

                } else if(duplicates.contains(romanNumeral) && (restrictedValues[currPos] == 3) ){
                    int currVal = RomanNumerals.getVal(c);
                    int prevVal = RomanNumerals.getVal(prev) ;
                    if(currVal < prevVal){
                        retVal = false;
                        break;
                    }
                }
                restrictedValues[currPos] ++ ;

            }
        }
        return retVal ;

    }

}
