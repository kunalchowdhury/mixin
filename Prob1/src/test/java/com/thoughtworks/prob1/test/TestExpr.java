package com.thoughtworks.prob1.test;

import com.thoughtworks.prob1.base.Expression;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by Kunal Chowdhury on 6/17/14.
 */
public class TestExpr  {

    @Test
    public void testBaseExpression1(){
        String s = "glob dabra is IVC";
        Expression targetExp = null;
        int  count = 0;
        String key = null;
        String value = null;
        for (Expression t : Expression.values()) {
           if(t.matches(s)){
                count++;
                targetExp = t;
                key = t.generateKey(s);
                value = t.generateValue(s);
           }

        }

        assertEquals(targetExp, Expression.CONVERSION_INPUT);
        assertEquals(count, 1);
        assertEquals(key, "glob dabra");
        assertEquals(value, "IVC");

    }

    @Test
    public void testBaseExpression2(){
        String s = "glob prok Gold is 57800 Credits";
        Expression targetExp = null;
        int  count = 0;
        String key = null;
        String value = null;
        for (Expression t : Expression.values()) {
            if(t.matches(s)){
                count++;
                targetExp = t;
                key = t.generateKey(s);
                value = t.generateValue(s);
            }

        }

        assertEquals(targetExp, Expression.COST_INPUT);
        assertEquals(count, 1);
        assertEquals(key, "glob prok Gold");
        assertEquals(value, "57800 Credits");

    }

    @Test
    public void testBaseExpression3(){
        String s = "how much is pish tegj glob glob ?";
        Expression targetExp = null;
        int  count = 0;
        String key = null;
        String value = null;
        for (Expression t : Expression.values()) {
            if(t.matches(s)){
                count++;
                targetExp = t;
                key = t.generateKey(s);
                value = t.generateValue(s);
            }

        }

        assertEquals(targetExp, Expression.CONVESRION_QUERY);
        assertEquals(count, 1);
        assertEquals(key, "pish tegj glob glob");
        assertNull(value);

    }

    @Test
    public void testBaseExpression4(){
        String s = "how many Credits is glob prok Iron ?";
        Expression targetExp = null;
        int  count = 0;
        String key = null;
        String value = null;
        for (Expression t : Expression.values()) {
            if(t.matches(s)){
                count++;
                targetExp = t;
                key = t.generateKey(s);
                value = t.generateValue(s);
            }

        }

        assertEquals(targetExp, Expression.COST_QUERY);
        assertEquals(count, 1);
        assertEquals(key, "Credits");
        assertEquals(value,"glob prok Iron");

    }





}
