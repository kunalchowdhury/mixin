package com.thoughtworks.prob1.test;

import com.thoughtworks.prob1.base.ExpressionType;
import com.thoughtworks.prob1.evaluator.QueryProcessor;
import com.thoughtworks.prob1.task.TransformValidateTask;
import com.thoughtworks.prob1.util.ExprParserUtil;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.testng.Assert.assertEquals;

/**
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class TestTaskProcessor {
    @Test
    public void testTransformValidate(){
        Map<ExpressionType, Set<String>> expressionToInputMap = new HashMap<ExpressionType, Set<String>>();
        expressionToInputMap.put(ExpressionType.BASIC_CONVERSION, new HashSet<String>(){{
            add("glob is I");
            add("prok is V");
            add("pish is X");
            add("tegj is L");
        }});

        expressionToInputMap.put(ExpressionType.COST, new HashSet<String>(){{
            add("glob1 glob Silver is 34 Credits");
            add("glob prok Gold is 57800 Credits");
            add("pish1 pish Iron is 3910 Credits");
        }});
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
        TransformValidateTask task = new TransformValidateTask(expressionToInputMap,map );
        task.execute();
        assertEquals(map.size(), 2);
        assertEquals(map.get("glob1 glob Silver is 34 Credits"), ExprParserUtil.ERROR);
        assertEquals(map.get("pish1 pish Iron is 3910 Credits"), ExprParserUtil.ERROR);
    }

    @Test
    public void testQueryProcessor(){
        QueryProcessor queryProcessor = new QueryProcessor(new HashSet<String>(){{
            add("glob is I");
            add("prok is V");
            add("pish is X");
            add("tegj is L");
            add("glob glob Silver is 34 Credits");
            add("glob prok Gold is 57800 Credits");
            add("pish pish Iron is 3910 Credits");
            add("how much is pish tegj glob glob ?");
            add("how many Credits is glob prok Silver ?");
            add("how many Credits is glob prok Gold ?");
            add("how many Credits is glob prok Iron ?");
            add("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        }});

        queryProcessor.processQuery();
        Map<String, String> map = queryProcessor.getResultMap();
        assertEquals(map.size(), 5);
        assertEquals(map.get("how much is pish tegj glob glob ?"),"pish tegj glob glob is 42");
        assertEquals(map.get("how many Credits is glob prok Silver ?"),"glob prok Silver is 68 Credits");
        assertEquals(map.get("how many Credits is glob prok Gold ?"), "glob prok Gold is 57800 Credits");
        assertEquals(map.get("how many Credits is glob prok Iron ?"),"glob prok Iron is 782 Credits");
        assertEquals(map.get("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"), "I have no idea what you are talking about");

    }
}
