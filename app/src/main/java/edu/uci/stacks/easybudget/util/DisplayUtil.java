package edu.uci.stacks.easybudget.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.uci.stacks.easybudget.data.BudgetDataContract;

public class DisplayUtil {
    public static String formatToCurrencyFromCents(int amount) {
        if (amount >= 0)
            return String.format("$%.2f", amount/100.0);
        return String.format("-$%.2f", Math.abs(amount)/100.0);
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

    public static int toCentsFromDollars(String dollars) {
        String[] parts = dollars.split("\\.");
        int result = Integer.parseInt(parts[0])*100;
        if (parts.length > 1) {
            if (parts[1].length() > 1) {
                result += (parts[1].charAt(0)-'0')*10;
                result += (parts[1].charAt(1)-'0');
            } else {
                result += Integer.parseInt(parts[1])*10;
            }
        }
        return result;
    }
}
