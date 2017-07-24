package com.bk.bestfind.utils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by mrt on 24/06/2017.
 */

public class FormatUtil {
    public static String formatCurrency(Double value, String code){
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(Currency.getInstance(code));
        return format.format(value);
    }
}
