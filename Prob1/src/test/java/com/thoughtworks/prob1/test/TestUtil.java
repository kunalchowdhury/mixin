package com.thoughtworks.prob1.test;

import static com.thoughtworks.prob1.util.ExprParserUtil.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by Kunal Chowdhury on 6/18/14.
 */
public class TestUtil {

    @Test
    public void testCalc(){
        assertEquals(convertRomanToDec("MMVI"), 2006);
        assertEquals(convertRomanToDec("MCMIII"), 1903);
        assertEquals(convertRomanToDec("MCMXLIV"), 1944);
        assertEquals(convertRomanToDec("LXIX"), 69);
    }

    @Test
    public void testInvalidString(){

         assertFalse(isValidRomanNumeral("IVI"));
         assertFalse(isValidRomanNumeral("IXI"));
         assertTrue(isValidRomanNumeral("VI"));
         assertTrue(isValidRomanNumeral("XXXIX"));
         assertFalse(isValidRomanNumeral("XXXX"));
         assertFalse(isValidRomanNumeral("DID"));
         assertTrue(isValidRomanNumeral("LXIX"));
         assertFalse(isValidRomanNumeral("LLXIX"));
         assertFalse(isValidRomanNumeral("IDL"));
         assertFalse(isValidRomanNumeral("IIV"));
         assertTrue(isValidRomanNumeral("I"));
         assertTrue(isValidRomanNumeral("V"));
         assertTrue(isValidRomanNumeral("X"));
         assertTrue(isValidRomanNumeral("L"));
         assertTrue(isValidRomanNumeral("C"));
         assertTrue(isValidRomanNumeral("D"));
         assertTrue(isValidRomanNumeral("M"));



    }
}
