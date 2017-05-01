package de.quicklp.skywars.utils;

public class MathUtil {

    public static int getBase(int percent, int percentage) {
        return Math.round(percentage * 100 / percent);
    }

    public static int getPercentage(int percentage, int base) {
        return Math.round(percentage / 100 * base);
    }

    public static int getPercent(int base, int percentage) {
        return Math.round(percentage * 100 / base);
    }

}
