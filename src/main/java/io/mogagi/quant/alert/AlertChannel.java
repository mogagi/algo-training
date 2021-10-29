package io.mogagi.quant.alert;

/**
 * @author mougg
 */
public interface AlertChannel {
    void send(String title, String content);
}