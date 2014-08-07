package com.thoughtworks.prob1.evaluator;

import com.thoughtworks.prob1.base.Key;
import com.thoughtworks.prob1.base.Value;
import com.thoughtworks.prob1.transformer.ValidatorAndTransformer;
import com.thoughtworks.prob1.util.ExprParserUtil;

import java.math.BigDecimal;
import java.util.Map;

/**
 * An interpreter skeleton and simple implementations for the input data type.
 *
 * Return type is BigDecimal to set the scale and precision for non-integer values
 *
 * As of now it has not been set as these parameters are not known
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public interface InputExpression {

    BigDecimal evaluate(String... input);

    /**
     * This class acts as a converter between the custom currency to
     * decimal via the Roman numerals route
     */

    public class ConversionQueryEvaluator implements InputExpression{
        /**
         * Returns the integer value of the custom input. The input is converted
         * to the corresponding roman numeral and thereafter the value is
         * calculated
         *
         * @param input the input string denoting the custom digits
         *
         * @return the calculated value as per the rules of conversion from Roman to decimal number.
         *
         */

        public BigDecimal evaluate(String... input) {
            String inputDigits = input[0];
            Map<Key, Value> map =  ValidatorAndTransformer.getConversionMap();
            StringBuilder sb = new StringBuilder();
            for(String s : inputDigits.split("\\s+")){
                sb.append((String)map.get(new Key.ConversionKey(s)).getVal());
            }

            return new BigDecimal(ExprParserUtil.convertRomanToDec(sb.toString()));
        }
    }



    public class CostQueryEvaluator implements InputExpression{
        /**
         * Returns the cost of the specified amount of items in the desired
         * units. The custom target units is converted to integer values
         * first and thereafter the cost is calculated.
         *
         *
         * @param input the input string denoting the item type, no of units and the target units
         *        for which the value is required.
         *
         * @return the calculated value as per the rules of conversion from Roman to decimal number
         *        and the unit cost
         *
         */

        public BigDecimal evaluate(String... input) {

            String item = input[1];
            String units = input[2];
            String targetUnits = input[0];

            Map<Key, Value> map =  ValidatorAndTransformer.getCostMap();
            Map<Key, Value> convMap =  ValidatorAndTransformer.getConversionMap();
            double v = (Double)(map.get(new Key.CostKey(item, units)).getVal());
            StringBuilder sb = new StringBuilder();
            for(String s : targetUnits.split("\\s+")){
                sb.append((String)convMap.get(new Key.ConversionKey(s)).getVal());
            }
            int target = ExprParserUtil.convertRomanToDec(sb.toString());
            return new BigDecimal(v * target);

        }
    }

}
