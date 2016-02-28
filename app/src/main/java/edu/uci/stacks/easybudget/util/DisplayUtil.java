package edu.uci.stacks.easybudget.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.uci.stacks.easybudget.data.BudgetDataContract;

public class DisplayUtil {
    public static String formatToCurrencyFromCents(int amount) {
        return String.format("$%.2f", amount/100.0);
    }

    public static String formatToMonthDay(String date) {
        String result = "";
        try {
            SimpleDateFormat inDateFormat  = new SimpleDateFormat(BudgetDataContract.MoneyTransaction.DATE_STRING);
            SimpleDateFormat outDateFormat = new SimpleDateFormat("MM/dd");
            result = outDateFormat.format(inDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
