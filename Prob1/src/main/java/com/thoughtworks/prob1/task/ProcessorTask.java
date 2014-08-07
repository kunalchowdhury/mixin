package com.thoughtworks.prob1.task;

import com.thoughtworks.prob1.base.ExpressionType;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * The abstract notion of a task. A task is comparable which enables us to
 * prioritize among various task instances.
 *
 * A task is executed via the execute method which is implemented by all
 * concrete implementations of this class.
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public abstract class ProcessorTask implements Comparable<ProcessorTask> {
        private Map<ExpressionType, Set<String>> expressionToInputMap ;
        enum Priority{
            FIRST(1), SECOND(2);
            private int priority ;

            Priority(int priority) {
                this.priority = priority;
            }
            public  int getPriority(){
                return priority ;
            }
        }

        protected ProcessorTask(Map<ExpressionType, Set<String>> expressionToInputMap) {
            this.expressionToInputMap = expressionToInputMap;
        }

        public abstract void execute();

        public abstract Priority getCurrentPriority();

        public Map<ExpressionType, Set<String>> getExpressionToInputMap() {
            return Collections.unmodifiableMap(expressionToInputMap);
        }


        public int compareTo(ProcessorTask o) {
            int thisPriority = this.getCurrentPriority().getPriority() ;
            int otherPriority = o.getCurrentPriority().getPriority();
            return (thisPriority < otherPriority ) ? -1 :(thisPriority > otherPriority) ? 1 : 0 ;
        }
}
