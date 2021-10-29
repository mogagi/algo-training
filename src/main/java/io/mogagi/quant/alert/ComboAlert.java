package io.mogagi.quant.alert;

import io.mogagi.quant.supports.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static io.mogagi.quant.supports.Constant.AFTERNOON_CLOSE;

/**
 * @author mogagi
 * @date 2021/10/27 7:43
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ComboAlert implements AlertStrategy {

    @Value("${alert.combo.loop}")
    final Set<String> matchLoops;
    @Value("${alert.combo.threshold:5}")
    private Integer threshold;
    final Cache cache;

    @Override
    public String toString() {
        return "combo alert";
    }

    @Override
    public boolean match(String loopName) {
        return matchLoops.contains(loopName);
    }

    @Override
    public boolean trigger(String loopName, String title) {
        String comboKey = "comboAlert:" + loopName;
        Combo lastCombo = cache.get(comboKey);
        if (lastCombo == null) { // init combo
            cache.put(comboKey, Combo.valueOf(title), LocalDateTime.of(LocalDate.now(), AFTERNOON_CLOSE));
            return false;
        } else if (title == null || !title.equals(lastCombo.title)) { // combo interrupted
            cache.remove(comboKey);
            return false;
        } // combo continue
        return lastCombo.count.addAndGet(1) >= threshold;
    }

    static class Combo {
        private String title;
        private final AtomicInteger count = new AtomicInteger(0);

        public static Combo valueOf(String title) {
            Combo combo = new Combo();
            combo.title = title;
            return combo;
        }
    }
}