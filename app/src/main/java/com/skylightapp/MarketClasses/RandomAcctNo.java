package com.skylightapp.MarketClasses;

import java.nio.charset.Charset;
import java.util.Random;

@SuppressWarnings("LoopStatementThatDoesntLoop")
public class RandomAcctNo {
    public static String getAcctNumeric(int n) {
        StringBuilder sb = new StringBuilder(n);
        String AlphaNumericString = "1111111" + "012345678" + "22222";

        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
            return sb.toString();
        }
        return AlphaNumericString;
    }
        public static void main(String[] args) {
            int n = 10;
        }
}

