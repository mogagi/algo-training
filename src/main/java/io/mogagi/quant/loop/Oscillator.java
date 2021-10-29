package io.mogagi.quant.loop;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 振荡器
 *
 * @author mogagi
 */
public class Oscillator extends AbstractLoop {

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private Long period;
    private TimeUnit timeUnit;

    public Oscillator(long period, TimeUnit timeUnit) {
        this.period = period;
        this.timeUnit = timeUnit;
    }

    @Override
    public void submit() {
        executor.scheduleAtFixedRate(() -> observers.forEach(Loop::submit), 0, period, timeUnit);
    }
}