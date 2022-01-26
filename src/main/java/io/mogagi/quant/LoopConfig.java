package io.mogagi.quant;

import io.mogagi.quant.loop.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author mogagi
 * @since 2021/10/22 23:15
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoopConfig {

    final BarrierLoop barrierLoop;
    final SinaLoop sinaLoop;
    final IcAbsLoop icAbsLoop;
    final AlertLoop alertLoop;

    @Bean
    public Oscillator oscillator() {
        Oscillator Oscillator = new Oscillator(5, TimeUnit.SECONDS);
        Oscillator.notify(sinaLoop);
        barrierLoop.notify(sinaLoop);
        sinaLoop.notify(icAbsLoop);
        icAbsLoop.notify(alertLoop);
        Oscillator.submit();
        return Oscillator;
    }
}
