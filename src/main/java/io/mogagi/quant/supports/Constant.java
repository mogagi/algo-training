package io.mogagi.quant.supports;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author mogagi
 */
public interface Constant {
    Double AVG_DAYS_IN_MONTH = (365 * 3 + 364.0) / (12 * 4);
    LocalTime MORNING_OPEN = LocalTime.of(9, 30),
            MORNING_CLOSE = LocalTime.of(11, 30),
            AFTERNOON_OPEN = LocalTime.of(13, 0),
            AFTERNOON_CLOSE = LocalTime.of(15, 0);

    DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyyMMdd");

    /* contract code */
    String SZ399905 = "399905";

    /* cache key */
    String LAST_PUSH_LDT = "lastPushLdt";
    String IS_MARKET_DAY = "isMarketDay";
}
