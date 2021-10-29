package io.mogagi.quant.supports;

/**
 * @author mougg
 */
public class StringUtils {

    private StringUtils() {
    }

    public static String trim(String originStr) {
        String startToken = "=\"", endToken = "\";";
        return originStr.substring(originStr.indexOf(startToken) + startToken.length(), originStr.lastIndexOf(endToken));
    }
}
