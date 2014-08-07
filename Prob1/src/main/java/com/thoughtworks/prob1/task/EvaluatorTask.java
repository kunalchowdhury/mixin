package com.thoughtworks.prob1.task;

import com.thoughtworks.prob1.base.ExpressionType;
import com.thoughtworks.prob1.evaluator.InputExpression;
import com.thoughtworks.prob1.transformer.ValidatorAndTransformer;
import com.thoughtworks.prob1.util.ExprParserUtil;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.thoughtworks.prob1.base.ExpressionType.*;

/**
 * This class is responsible for evaluating the query related input data
 * e.g. the conversion and cost queries
 *
 * Priority wise this is the second task to be executed
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class EvaluatorTask extends ProcessorTask {
    private ConcurrentHashMap<String, String> resultMap ;

    private static final Map<ExpressionType, InputExpression> exprToEvaluatorMap
            = new EnumMap<ExpressionType, InputExpression>(ExpressionType.class){{
                     put(BASIC_CONVERSION_QUERY, new InputExpression.ConversionQueryEvaluator());
                     put(COST_QUERY, new InputExpression.CostQueryEvaluator());
                    }};

    public EvaluatorTask(Map<ExpressionType, Set<String>> expressionToInputMap, ConcurrentHashMap<String, String> resultMap){
        super(expressionToInputMap);
        this.resultMap = resultMap ;
    }
    /**
     * Triggers the evaluation tasks for the given set of query strings
     * This uses the various data sets that have been populated by the
     * validation and transformer component {@code ValidatorAndTransformer}
     *
     * This also populates the output strings to be printed alongwith the
     * calculated data.
     *
     */

    public void execute() {
        Map<ExpressionType, Set<String>> expressionToInputMap = getExpressionToInputMap();
        for(ExpressionType e : exprToEvaluatorMap.keySet()){
            if(expressionToInputMap.get(e) != null){
            for(String s : expressionToInputMap.get(e)){
                if(ExprParserUtil.ERROR.equals(resultMap.get(s))){
                    continue;
                }
                switch(e){
                    case BASIC_CONVERSION_QUERY :
                        String resultString = ValidatorAndTransformer.getConversionQuery().get(s);
                        BigDecimal conVal = exprToEvaluatorMap.get(e).evaluate(resultString);
                        resultMap.putIfAbsent(s, resultString + " is "+ conVal.toPlainString());
                        break ;
                    case COST_QUERY:
                        String[] resultStrings = ValidatorAndTransformer.getCostQueryMap().get(s);
                        BigDecimal costVal =exprToEvaluatorMap.get(e).evaluate(resultStrings);
                        resultMap.putIfAbsent(s, resultStrings[0]+" "+resultStrings[1]+" is "+costVal.toPlainString()+" "+resultStrings[2]);
                        break ;
                    }
                }
            }
        }


    }

    @Override
    public Priority getCurrentPriority() {
        return Priority.SECOND;
    }
}
