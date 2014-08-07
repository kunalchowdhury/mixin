package com.thoughtworks.prob1.base;

/**
 * The Key associated with each type of data.
 *
 * Created by Kunal Chowdhury on 6/17/14.
 */
public interface Key {
    /**
     * This Key denotes type of input as - {@literal 'XX is I'} where XX
     * is regarded as the key
     */
     public class ConversionKey implements Key{
        private String digit ;
        public ConversionKey(String digit) {
            this.digit = digit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConversionKey)) return false;

            ConversionKey that = (ConversionKey) o;

            return digit.equals(that.digit);

        }

        @Override
        public int hashCode() {
            return digit.hashCode();
        }

        @Override
        public String toString() {
            return "ConversionKey{" +
                    "digit='" + digit + '\'' +
                    '}';
        }
    }

    /**
     * This key is associated with input types denoting the type cost
     * of an item in a unit.
     * e.g. {@literal 'XX Silver is 32 Credit'} , where item = Silver, unit = Credit
     */

    public class CostKey implements Key{

        private String item ;
        private String unit ;

        public CostKey(String item, String unit) {
            this.item = item;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CostKey)) return false;

            CostKey costKey = (CostKey) o;

            return item.equals(costKey.item) && unit.equals(costKey.unit);

        }

        @Override
        public int hashCode() {
            int result = item.hashCode();
            result = 31 * result + unit.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "CostKey{" +
                    "item='" + item + '\'' +
                    ", unit='" + unit + '\'' +
                    '}';
        }
    }
}
