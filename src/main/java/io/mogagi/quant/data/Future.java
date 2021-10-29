package io.mogagi.quant.data;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import static io.mogagi.quant.supports.Constant.YYYY_MM_DD;

@Data
public class Future {
    /**
     * 代码 IC2012
     */
    private String code;
    /**
     * 新浪代码
     */
    private String sinaCode;
    /**
     * 到期日
     */
    private LocalDate dueDate;

    public static Future valueOf(String code) {
        Future future = new Future();
        future.setCode(code);
        future.setSinaCode("CFF_RE_" + code);
        String ldStr = code.replaceFirst("[^\\d]{1,2}", "20") + "01";
        future.setDueDate(LocalDate.parse(ldStr, YYYY_MM_DD).with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.FRIDAY)));
        return future;
    }
}