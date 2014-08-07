package com.thoughtworks.prob1.evaluator;

import com.thoughtworks.prob1.base.Expression;
import com.thoughtworks.prob1.base.ExpressionType;
import com.thoughtworks.prob1.base.ParserResultVO;
import com.thoughtworks.prob1.task.EvaluatorTask;
import com.thoughtworks.prob1.task.ProcessorTask;
import com.thoughtworks.prob1.task.TransformValidateTask;
import com.thoughtworks.prob1.util.DataWarmer;
import com.thoughtworks.prob1.util.ExprParserUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The entry point of the query parsing application.
 * Delegates responsibilites using tasks to various processor tasks
 * e.g. {@code EvaluatorTask} , {@code TransformValidateTask} etc.
 *
 * Creates the map of the Exression and collection of string which
 * makes the processing easier and the corresponding processor
 * for each expression type is known.
 *
 * Uses the DataWarmer to cache the initial parsing using regular
 * expression which can be effectively used later.
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class QueryProcessor {
       private final Collection<String> queryToProcess ;

       private final Map<ExpressionType, Set<String>> expressionToInputMap ;

       private final ConcurrentHashMap<String, String> resultMap ;

       private final PriorityQueue<ProcessorTask> queue = new PriorityQueue<ProcessorTask>();

    /**
     * Allocates a new {@code QueryProcessor} to the collection of string
     * Internally initializes the expressionToInputMap and caches the
     * regular expression parsing data
     *
     * The {@code PriorityQueue} is used for prioritizing across the tasks
     * e.g. currently the valdation tasks should be triggered prior to the
     * evaluation so the priorities are set accordingly and thereby the queue
     * handles this feature.
     *
     * Note that except the validation done in the corresponding processors
     * there is an entry level validation also being done wherein if none of
     * a string does not match any of the parsers then we store an exception
     * for it.
     *
     * @param  queryToProcess
     *         Collection of strings
     */
       public QueryProcessor(Collection<String> queryToProcess) {
            this.queryToProcess = queryToProcess;
            this.expressionToInputMap = new EnumMap<ExpressionType, Set<String>>(ExpressionType.class);
            this.resultMap = new ConcurrentHashMap<String, String>();
            queue.add(new TransformValidateTask(expressionToInputMap, resultMap));
            queue.add(new EvaluatorTask(expressionToInputMap, resultMap));
            init();
       }

       private void init(){
            DataWarmer<String, ParserResultVO> warmer = DataWarmer.INSTANCE ;
            for(final String s : queryToProcess){
                boolean isProcessedAtLeastOnce = false;
                for(Expression expression : Expression.values()){
                       if(expression.matches(s)){
                          warmer.store(s, new ParserResultVO(expression.generateKey(s), expression.generateValue(s)));
                          isProcessedAtLeastOnce = true;
                          ExpressionType expressionType = expression.getExpressionType();
                          if(expressionToInputMap.containsKey(expressionType)){
                              expressionToInputMap.get(expressionType).add(s);
                          } else{
                              expressionToInputMap.put(expressionType, new HashSet<String>(){{add(s);}});
                          }
                          break;
                      }
                }
                if(!isProcessedAtLeastOnce){
                    resultMap.put(s, ExprParserUtil.ERROR);
                }
            }
       }

    /**
     * Entry point for processing the query and printing the results
     *
     */
       public void processQuery(){
              while (!queue.isEmpty()){
                  queue.remove().execute();
              }
              print();
       }

    public Map<String, String> getResultMap() {
        return Collections.unmodifiableMap(resultMap);
    }

    public void print(){
        for(String s : queryToProcess){
            if(resultMap.get(s) != null){
                System.out.println(resultMap.get(s));
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<String> set = new LinkedHashSet<String>();

        while (true){
           String str = scanner.nextLine();
           if(str.isEmpty()){
               break ;
           }
           set.add(str);
        }
        QueryProcessor queryProcessor = new QueryProcessor(set);
        queryProcessor.processQuery();
    }

}
