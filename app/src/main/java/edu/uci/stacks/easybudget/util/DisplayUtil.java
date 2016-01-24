package edu.uci.stacks.easybudget.util;

public class DisplayUtil {
    public static String formatToCurrencyFromCents(int amount) {
        return String.format("$%.2f", amount/100.0);
    }
}
