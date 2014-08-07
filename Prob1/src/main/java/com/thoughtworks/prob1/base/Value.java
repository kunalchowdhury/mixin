package com.thoughtworks.prob1.base;

/**
 * Value associated with given key for each type of input data
 *
 * Created by Kunal Chowdhury on 6/17/14.
 */
public interface Value<T> {
    public T getVal();

    /*
     * This Value is used for type of input as - XX is I where I
     * is regarded as the value
     */
    public static class ConversionValue implements Value<String>{
        private String val ;

        public ConversionValue(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }

        @Override
        public String toString() {
            return "ConversionValue{" +
                    "val='" + val + '\'' +
                    '}';
        }
    }

    /*
     * This value is associated with input types denoting the type cost
     * of an item in a unit.
     * e.g. XX Silver is 32 Credit, where val = 32
     */
    public static class CostValue implements Value<Double>{
        private Double val;

        public CostValue(Double val) {
            this.val = val;
        }

        public Double getVal() {
            return val;
        }

        @Override
        public String toString() {
            return "CostValue{" +
                    "val=" + val +
                    '}';
        }
    }



}
