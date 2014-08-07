package com.thoughtworks.prob1.test;

import com.thoughtworks.prob1.base.Expression;
import com.thoughtworks.prob1.base.Key;
import com.thoughtworks.prob1.base.ParserResultVO;
import com.thoughtworks.prob1.base.Value;
import com.thoughtworks.prob1.evaluator.InputExpression;
import com.thoughtworks.prob1.transformer.ValidatorAndTransformer;
import com.thoughtworks.prob1.util.DataWarmer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.testng.Assert.assertEquals;

/**
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class TestValidatorsAndTransformers {
    @BeforeClass
    public void prepareData(){
        String[] strings = new String[]{"glob is I",
                "prok is V",
                "pish is X",
                "tegj is L",
                "glob glob Silver is 34 Credits",
                "glob prok Gold is 57800 Credits",
                "pish pish Iron is 3910 Credits",
        };
        DataWarmer<String, ParserResultVO> warmer = DataWarmer.INSTANCE ;
        for(final String s : strings){
            for(Expression expression : Expression.values()){
                if(expression.matches(s)){
                    warmer.store(s, new ParserResultVO(expression.generateKey(s), expression.generateValue(s)));
                    break;
                }
            }
        }

        ValidatorAndTransformer.ConversionInputValidatorAndTransformer v1 = new ValidatorAndTransformer.ConversionInputValidatorAndTransformer();
        ValidatorAndTransformer.CostInputValidatorAndTransformer v2 = new ValidatorAndTransformer.CostInputValidatorAndTransformer();

        for (int i = 0; i < 4; i++) {
            v1.process(strings[i]);
        }

        for (int i = 4; i < strings.length; i++) {
             v2.process(strings[i]);
        }

    }


    @Test
    public void testInputAll(){

        Map<Key,Value> conversionMap = ValidatorAndTransformer.getConversionMap();
        assertEquals(conversionMap.size(),4);
        Map<Key, Value> costMap = ValidatorAndTransformer.getCostMap();
        assertEquals(costMap.size(),3);
        assertEquals(conversionMap.get(new Key.ConversionKey("glob")).getVal(),"I");
        assertEquals(conversionMap.get(new Key.ConversionKey("prok")).getVal(),"V");
        assertEquals(conversionMap.get(new Key.ConversionKey("pish")).getVal(),"X");
        assertEquals(conversionMap.get(new Key.ConversionKey("tegj")).getVal(),"L");
        Key.CostKey key1 = new Key.CostKey("Silver","Credits");
        Key.CostKey key2 = new Key.CostKey("Gold","Credits");
        Key.CostKey key3 = new Key.CostKey("Iron","Credits");
        assertEquals(((Value.CostValue)costMap.get(key1)).getVal(), 17.0);
        assertEquals(((Value.CostValue)costMap.get(key2)).getVal(), 14450.0);
        assertEquals(((Value.CostValue)costMap.get(key3)).getVal(), 195.5);
    }

    @Test
    public void testExpression(){
        InputExpression.ConversionQueryEvaluator evaluator = new InputExpression.ConversionQueryEvaluator();
        assertEquals(evaluator.evaluate("pish tegj glob glob").intValue(), 42);

        InputExpression.CostQueryEvaluator costQueryEvaluator = new InputExpression.CostQueryEvaluator();
        assertEquals(costQueryEvaluator.evaluate("glob prok", "Silver","Credits").intValue(), 68);

        costQueryEvaluator = new InputExpression.CostQueryEvaluator();
        assertEquals(costQueryEvaluator.evaluate("glob prok", "Gold","Credits").intValue(), 57800);

        costQueryEvaluator = new InputExpression.CostQueryEvaluator();
        assertEquals(costQueryEvaluator.evaluate("glob prok", "Iron","Credits").intValue(), 782);
    }

}
