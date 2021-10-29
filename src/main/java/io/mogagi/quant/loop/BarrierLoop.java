package io.mogagi.quant.loop;

import io.mogagi.quant.supports.Cache;
import io.mogagi.quant.supports.SinaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static io.mogagi.quant.supports.Constant.*;

/**
 * Fetch data from Sina API
 *
 * @author mougg
 */
@Slf4j
@Component
public class BarrierLoop extends AbstractLoop {

    final SinaClient sinaClient;
    final Cache cache;

    @Autowired
    public BarrierLoop(@Value("${loop.barrier.nThreads:1}") int nThreads, SinaClient sinaClient, Cache cache) {
        super(nThreads);
        this.sinaClient = sinaClient;
        this.cache = cache;
    }

    @Override
    protected LoopResult work(String loopName, Object data) {
        LocalTime now = LocalTime.now();
        return now.isBefore(MORNING_OPEN) ? LoopResult.stop()
                : now.isBefore(MORNING_CLOSE) ? isMarketDay()
                : now.isBefore(AFTERNOON_OPEN) ? LoopResult.stop()
                : now.isBefore(AFTERNOON_CLOSE) ? isMarketDay()
                : LoopResult.stop();
    }

    private LoopResult isMarketDay() {
        Boolean isMarketDay = cache.get(IS_MARKET_DAY);
        if (isMarketDay == null) {
            isMarketDay = sinaClient.sZ399905().getDateTime().toLocalDate().isEqual(LocalDate.now());
            cache.put(IS_MARKET_DAY, isMarketDay, LocalDateTime.of(LocalDate.now(), AFTERNOON_CLOSE));
        }
        return isMarketDay ? LoopResult.pass() : LoopResult.stop();
    }
}