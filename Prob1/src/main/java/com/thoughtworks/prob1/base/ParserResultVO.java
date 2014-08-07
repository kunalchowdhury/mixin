package com.thoughtworks.prob1.base;

/**
 * The intermediate result of parsing an input string with the first raw key-value
 * pair is stored here.
 * Subsequent processing may bypass the parsing all-together thereafter as the
 * components can retrieve the information from {@code ParserResultVO}
 *
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class ParserResultVO {

    private String key ;

    private String value ;

    public ParserResultVO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
