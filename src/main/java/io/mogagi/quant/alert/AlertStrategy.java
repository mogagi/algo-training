package io.mogagi.quant.alert;

/**
 * @author mougg
 */
public interface AlertStrategy {

    boolean match(String loopName);

    boolean trigger(String loopName, String title);
}