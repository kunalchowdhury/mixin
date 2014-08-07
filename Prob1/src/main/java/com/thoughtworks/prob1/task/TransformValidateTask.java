package com.thoughtworks.prob1.task;

import com.thoughtworks.prob1.base.ExpressionType;
import com.thoughtworks.prob1.transformer.ValidatorAndTransformer;
import com.thoughtworks.prob1.util.ExprParserUtil;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

import static com.thoughtworks.prob1.base.ExpressionType.*;

/**
 * The class is responsible for triggering the validation and transformation
 * processing of the input data.
 *
 * Priority wise this is the first task to be executed
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class TransformValidateTask extends ProcessorTask {

        private ConcurrentHashMap<String, String> resultMap ;

        private static final Map<ExpressionType, ValidatorAndTransformer> map =
                new EnumMap<ExpressionType, ValidatorAndTransformer>(ExpressionType.class){{
                    put(BASIC_CONVERSION, new ValidatorAndTransformer.ConversionInputValidatorAndTransformer());
                    put(COST,new ValidatorAndTransformer.CostInputValidatorAndTransformer());
                    put(BASIC_CONVERSION_QUERY, new ValidatorAndTransformer.ConversionQueryValidatorAndTransformer());
                    put(COST_QUERY, new ValidatorAndTransformer.CostQueryValidatorAndTransformer());
                }};

        /* *
        *  This array controls the sequence of the validators .
        *  Rather than depending on the ordinal values of the
        *  enums which is risky due to possible further changes
        *  such an approach handles the behaviour effectively
        *
        * */
        private static final ExpressionType[] processingSequence = new ExpressionType[]{BASIC_CONVERSION , COST , BASIC_CONVERSION_QUERY, COST_QUERY };

        public TransformValidateTask(Map<ExpressionType, Set<String>> expressionToInputMap, ConcurrentHashMap<String, String> resultMap) {
            super(expressionToInputMap);
            this.resultMap = resultMap ;
        }

    /**
     * Triggers the validation tasks for the given set of strings
     * The iteration over the processingSequence array holding the {@code ExpressionType} instances
     * would ensure the ordering and  hence the basic conversion and cost input validation
     * and transformation are always the first ones to be executed which prepares the datasets for the
     * query related strings e.g. the cost query and conversion query
     *
     * {@code ExecutorService} is used here as the validation and transformation is/may be CPU intensive.
     *
     *
     */
        public void execute() {
            int concurrentThread = Runtime.getRuntime().availableProcessors();
            Map<ExpressionType, Set<String>> expressionToInputMap = getExpressionToInputMap();
            for(ExpressionType e : processingSequence){
                internalProcess(e, Executors.newFixedThreadPool(concurrentThread), expressionToInputMap);
            }
        }

    public void internalProcess(final ExpressionType expressionType, ExecutorService executor, Map<ExpressionType, Set<String>> expressionToInputMap){
            Map<String, Future<Boolean>> futureMap = new LinkedHashMap<String, Future<Boolean>>();

            if(expressionToInputMap.get(expressionType) == null){
                return;
            }

            for(final String s : expressionToInputMap.get(expressionType)){
                futureMap.put(s,executor.submit(new Callable<Boolean>() {
                    public Boolean call() throws Exception {
                        return map.get(expressionType).process(s);
                    }
                }));
            }
            for(String s : futureMap.keySet()){
                try {
                    boolean valid = futureMap.get(s).get();
                    if(!valid){
                        resultMap.putIfAbsent(s, ExprParserUtil.ERROR);
                    }

                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                }
            }
            executor.shutdown();
        }


        public Priority getCurrentPriority() {
            return Priority.FIRST ;
        }
}
