package io.mogagi.quant.loop;

import io.mogagi.quant.alert.AlertChannel;
import io.mogagi.quant.alert.AlertMsg;
import io.mogagi.quant.alert.AlertStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Send alert message
 *
 * @author mougg
 */
@Slf4j
@Component
public class AlertLoop extends AbstractLoop {

    private List<AlertChannel> alertChannels;
    private List<AlertStrategy> alertStrategies;

    @Autowired
    public AlertLoop(@Value("${loop.alert.nThreads:1}") int nThreads, List<AlertChannel> alertChannels, List<AlertStrategy> alertStrategies) {
        super(nThreads);
        this.alertChannels = alertChannels;
        this.alertStrategies = alertStrategies;
    }

    @Override
    public LoopResult work(String loopName, Object data) {
        AlertMsg alertMsg = (AlertMsg) data;
        for (AlertStrategy strategy : alertStrategies) {
            if (strategy.match(loopName)) {
                if (!strategy.trigger(loopName, alertMsg.getTitle())) {
                    return LoopResult.stop();
                }
            }
        }
        log.info("send alert message, loopName={}, title={}", loopName, alertMsg.getTitle());
        alertChannels.forEach(channel -> channel.send(alertMsg.getTitle(), alertMsg.getContent()));
        return LoopResult.stop();
    }
}