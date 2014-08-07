package com.thoughtworks.prob1.transformer;

import com.thoughtworks.prob1.base.Key;
import com.thoughtworks.prob1.base.ParserResultVO;
import com.thoughtworks.prob1.base.RomanNumerals;
import com.thoughtworks.prob1.base.Value;
import com.thoughtworks.prob1.util.DataWarmer;
import com.thoughtworks.prob1.util.ExprParserUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import static com.thoughtworks.prob1.base.RomanNumerals.*;

/**
 * This class is responsible for validating the input and properly transforming
 * the necessary information.
 * e.g. cost per item in custom unit, custom digit to Roman numeral
 *
 * Created by Kunal Chowdhury on 6/17/14.
 */
public abstract class ValidatorAndTransformer {

    private static final Set<String> validDigits, validItems, validUnits ;
    private static final ConcurrentHashMap<Key, Value> conversionMap, costMap ;
    private static final ConcurrentHashMap<String, String>  conversionQuery;
    private static final ConcurrentHashMap<String, String[]> costQueryMap ;



    static {
        validDigits =  new ConcurrentSkipListSet<String>();
        validItems  =  new ConcurrentSkipListSet<String>();
        validUnits  =  new ConcurrentSkipListSet<String>();
        conversionMap = new ConcurrentHashMap<Key, Value>();
        costMap = new ConcurrentHashMap<Key, Value>();
        conversionQuery = new ConcurrentHashMap<String, String>();
        costQueryMap = new ConcurrentHashMap<String, String[]>();
    }

    public static Map<Key, Value> getConversionMap() {
        return Collections.unmodifiableMap(conversionMap);
    }

    public static Map<Key, Value> getCostMap() {
        return Collections.unmodifiableMap(costMap);
    }

    public static Map<String, String> getConversionQuery() {
        return Collections.unmodifiableMap(conversionQuery);
    }

    public static Map<String, String[]> getCostQueryMap() {
        return Collections.unmodifiableMap(costQueryMap);
    }

    public abstract boolean process(String input);

    public static class ConversionInputValidatorAndTransformer extends ValidatorAndTransformer {
        /**
         * Returns a boolean denoting whether the validation was successful
         * This stores the conversion map which can transform values from
         * custom digits to Roman numeral
         *
         * @param input the input String for a conversion input
         *
         * @return boolean to denote whether the processing is successful
         *
         */

        @Override
        public boolean process(String input) {
            DataWarmer<String, ParserResultVO> warmer = DataWarmer.INSTANCE;
            ParserResultVO parserResultVO = warmer.get(input);
            boolean valid = (parserResultVO != null);
            if(valid){
                String key = parserResultVO.getKey();
                String value = parserResultVO.getValue();
                if(!ExprParserUtil.isValidRomanNumeral(value)){
                     valid = false ;
                } else {
                    conversionMap.putIfAbsent(new Key.ConversionKey(key), new Value.ConversionValue(value)) ;
                    validDigits.add(key);
                }
            }
            return valid;
        }
    }

    public static class CostInputValidatorAndTransformer extends ValidatorAndTransformer {
        /**
         * Returns a boolean denoting whether the validation was successful
         * This populates the cost conversion map which store the cost per item
         * in custom units
         *
         * @param input the input String for a cost per item input
         *
         * @return boolean to denote whether the processing is successful
         *
         */
        @Override
        public boolean process(String input) {
            DataWarmer<String, ParserResultVO> warmer = DataWarmer.INSTANCE;
            ParserResultVO parserResultVO = warmer.get(input);
            boolean valid = (parserResultVO != null);
            boolean foundDigit = false;
            if(valid){
                String key = parserResultVO.getKey();
                String[] parts = key.split("\\s+");
                foundDigit = validDigits.contains(parts[0]) ;    // thread-safe because first validator is already over
                if(foundDigit){
                    StringBuilder item = new StringBuilder();
                    StringBuilder noOfItems = new StringBuilder();
                    boolean itemStarted = false;
                    for(String s : parts){
                        if(itemStarted){
                            item.append(s);
                        }else if(!validDigits.contains(s)){
                            itemStarted = true ;
                            item.append(s).append(" ");
                        }else {
                            Value obj = conversionMap.get(new Key.ConversionKey(s));
                            noOfItems.append((String)obj.getVal());
                        }
                    }
                    validItems.add(item.toString().trim());
                    String[] valParts = parserResultVO.getValue().split("\\s+");
                    int value = Integer.valueOf(valParts[0]);
                    StringBuilder units = new StringBuilder();
                    for (int i = 1; i < valParts.length; i++) {
                           units.append(valParts[i]);
                    }
                    validUnits.add(units.toString().trim());
                    String romanString = noOfItems.toString();
                    if(!ExprParserUtil.isValidRomanNumeral(romanString)){
                        valid = false;
                    } else{
                        int itemNos = ExprParserUtil.convertRomanToDec(romanString);
                        double perItemUnitVal =  (double)value/itemNos ;
                        costMap.putIfAbsent(new Key.CostKey(item.toString().trim(),
                                units.toString().trim()),
                                new Value.CostValue(perItemUnitVal));
                    }
                }

            }
            return valid && foundDigit;
        }
    }

    public static class ConversionQueryValidatorAndTransformer extends ValidatorAndTransformer{
        /**
         * Returns a boolean denoting whether the conversion query has valid digits
         * This populates the conversionQuery map which is later used to frame the output
         * This map stores the custom digit information
         *
         * @param input the input String for a conversion query input
         *
         * @return boolean to denote whether the processing is successful
         *
         */
        @Override
        public boolean process(String input) {
            DataWarmer<String, ParserResultVO> warmer = DataWarmer.INSTANCE;
            ParserResultVO parserResultVO = warmer.get(input);
            boolean valid = (parserResultVO != null);
            boolean validDigit = true;
            String key = null;
            if(valid){
                key = parserResultVO.getKey() ;
                for(String str : key.split("\\s+")){
                    if(!validDigits.contains(str)) {
                        validDigit = false;
                        break;
                    }
                }
            }
            boolean retVal = valid && validDigit ;
            if(retVal){
                StringBuilder sb = new StringBuilder();
                for(String s : key.split("\\s+")){
                    sb.append(conversionMap.get(new Key.ConversionKey(s)).getVal());
                }
                if(!ExprParserUtil.isValidRomanNumeral(sb.toString())){
                    retVal = false;
                } else {
                    conversionQuery.putIfAbsent(input, key);
                }

            }

            return retVal ;
        }
    }

    public static class CostQueryValidatorAndTransformer extends ValidatorAndTransformer{
        /**
         * Returns a boolean denoting whether the validation was successful for the
         * cost query map. i.e. queries that use the per item cost information in
         * custom units.
         * This populates the costQueryMap which stores the output string that is
         * is later used to print the calculated values.
         *
         * @param input the input String for evaluating the custom unit cost for some
         *              items.
         *
         * @return boolean to denote whether the processing is successful
         *
         */
        @Override
        public boolean process(String input) {
            DataWarmer<String, ParserResultVO> warmer = DataWarmer.INSTANCE;
            ParserResultVO parserResultVO = warmer.get(input);
            boolean valid = (parserResultVO != null);
            boolean validDigit, validItem, validUnit;
            String key , value ;
            StringBuilder item , digits ;
            key =  null ;
            validDigit =  validItem = validUnit = false;
            item = digits =null;
            if(valid){
                key = parserResultVO.getKey();
                validUnit =  validUnits.contains(key);
                value = parserResultVO.getValue();
                String[] values =  value.split("\\s+");
                digits = new StringBuilder();
                item = new StringBuilder();
                for(String val : values){
                    if(validDigits.contains(val) && !validItem){
                         validDigit = true;
                         digits.append(val).append(" ");
                    } else {
                        item.append(val).append(" ");
                    }
                }
                valid = validItems.contains(item.toString().trim());
            }
            boolean retVal = valid && validDigit && validUnit && costMap.containsKey(new Key.CostKey(item.toString().trim(), key));

            if(retVal){
                StringBuilder sb = new StringBuilder();
                String customDigit = digits.toString().trim();
                for(String s : customDigit.split("\\s+")){
                    sb.append(conversionMap.get(new Key.ConversionKey(s)).getVal());
                }
                if(!ExprParserUtil.isValidRomanNumeral(sb.toString())){
                    retVal = false;
                } else{
                    costQueryMap.putIfAbsent(input, new String[]{customDigit, item.toString().trim(), key} );
                }
            }

            return retVal;
        }
    }

}
