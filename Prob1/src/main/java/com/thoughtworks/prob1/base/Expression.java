package com.thoughtworks.prob1.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the common class encapsulating the type of an expression
 * and the corresponding regular expression pattern
 * Any new expression type can be added to this enum with the corresponding
 * functionality as how to process the input expression
 *
 * Created by Kunal Chowdhury on 6/17/14.
 */
public enum Expression {
    CONVERSION_INPUT(ExpressionType.BASIC_CONVERSION, "(\\w+\\s+)+(is)\\s+[IVXLCDM]+") {
        @Override
        public String generateKey(String targetString) {
             return process(0, 2, targetString, CONVERSION_INPUT.pattern, 0);
        }

        @Override
        public String generateValue(String targetString) {
             return process(2, -1, targetString, CONVERSION_INPUT.pattern, 2);
        }
    },
    COST_INPUT(ExpressionType.COST, "(\\w+\\s+)+(is)\\s+\\d+(\\s+\\w+)+") {
        @Override
        public String generateKey(String targetString) {
            return  process(0, 2, targetString, COST_INPUT.pattern, 0);

        }

        @Override
        public String generateValue(String targetString) {
            return process(2, -1, targetString, COST_INPUT.pattern, 2);

        }
    },
    CONVESRION_QUERY(ExpressionType.BASIC_CONVERSION_QUERY, "(how)(\\s+)(much)(\\s+)(is)(\\s+\\w+)+\\s+['?']") {
        @Override
        public String generateKey(String targetString) {
            return process(5, -1, targetString, CONVESRION_QUERY.pattern, 2).replace("?","").trim();
        }

        @Override
        public String generateValue(String targetString) {
            return null;
        }
    },
    COST_QUERY(ExpressionType.COST_QUERY, "(how)(\\s+)(many)(\\s+\\w+\\s+)+(is)(\\s+\\w+)+\\s+['?']") {
        @Override
        public String generateKey(String targetString) {
            return process(3, 5, targetString, COST_QUERY.pattern, 4);
        }

        @Override
        public String generateValue(String targetString) {
            return process(5, -1, targetString, COST_QUERY.pattern, 2).replace("?","").trim();
        }
    };

    private ExpressionType  expressionType;
    private Pattern pattern ;

    Expression(ExpressionType expressionType, String capturePattern){
            this.expressionType = expressionType ;
            this.pattern = Pattern.compile(capturePattern);
    }

    public boolean matches(String targetString){
        Matcher m = pattern.matcher(targetString);
        return m.matches();
    }

    public abstract String generateKey(String targetString);
    public abstract String generateValue(String targetString);

    public ExpressionType getExpressionType() {
        return expressionType;
    }

    String process(int start, int end, String str, Pattern currentPattern, int deltaStart){
        Matcher m = currentPattern.matcher(str);
        boolean matches = m.matches();
        String value = null;
        if(matches){
            int first = m.toMatchResult().start(start) + deltaStart;
            int second = (end == -1) ? str.length() : m.toMatchResult().start(end);
            value = str.substring(first , second ).trim();
        }

        return value;
    }

}
