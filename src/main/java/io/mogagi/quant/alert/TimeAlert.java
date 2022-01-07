package io.mogagi.quant.alert;

import io.mogagi.quant.supports.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mogagi
 * @since 2021/10/27 7:43
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TimeAlert implements AlertStrategy {

    @Value("${alert.time.loop}")
    Set<String> matchLoops;
    @Value("${alert.time.threshold:6}")
    Integer alertThreshold;
    @Value("${alert.time.second:60}")
    Integer alertSecond;
    final Cache cache;

    @Override
    public String toString() {
        return "time alert";
    }

    @Override
    public boolean match(String loopName) {
        return matchLoops.contains(loopName);
    }

    @Override
    public boolean trigger(String loopName, String title) {
        String timeKey = String.format("timeAlert:%s:%s", loopName, title);
        AtomicInteger counter = cache.get(timeKey);
        if (counter == null) { // init counter
            cache.put(timeKey, new AtomicInteger(0), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(alertSecond));
            return false;
        } // counter++
        return counter.addAndGet(1) >= alertThreshold;
    }
}