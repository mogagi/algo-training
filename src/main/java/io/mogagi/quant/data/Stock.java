package io.mogagi.quant.data;

import io.mogagi.quant.supports.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * stock or index
 *
 * @author mougg
 */
@Data
public class Stock {
    /**
     * 指数名
     */
    private String name;
    /**
     * 今开
     */
    private Double open;
    /**
     * 昨收
     */
    private Double prevClose;
    /**
     * 现价
     */
    private Double price;
    /**
     * 今高
     */
    private Double high;
    /**
     * 今低
     */
    private Double low;
    /**
     * 成交量（张）
     */
    private Long volume;
    /**
     * 成交额（元）
     */
    private Double turnover;
    /**
     * 时间
     */
    private LocalDateTime dateTime;

    public static Stock valueOf(String raw) {
        Stock indexSina = new Stock();
        String[] segments = StringUtils.trim(raw).split(",");
        indexSina.setName(segments[0]);
        indexSina.setOpen(Double.valueOf(segments[1]));
        indexSina.setPrevClose(Double.valueOf(segments[2]));
        indexSina.setPrice(Double.valueOf(segments[3]));
        indexSina.setHigh(Double.valueOf(segments[4]));
        indexSina.setLow(Double.valueOf(segments[5]));
        indexSina.setVolume(Long.valueOf(segments[8]));
        indexSina.setTurnover(Double.valueOf(segments[9]));
        indexSina.setDateTime(LocalDateTime.parse(segments[30] + "T" + segments[31]));
        return indexSina;
    }
}
