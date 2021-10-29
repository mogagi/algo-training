package io.mogagi.quant.data;

import io.mogagi.quant.supports.StringUtils;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Share Price Indexes Future
 *
 * @author mougg
 */
@Data
public class Spif {
    public static final String IC_NAME = "中证500指数期货";
    public static final String IC = "IC";
    /**
     * 今开 7012.600
     */
    private Double open;
    /**
     * 今高 7091.600
     */
    private Double high;
    /**
     * 今低 6998.800
     */
    private Double low;
    /**
     * 现价
     */
    private Double price;
    /**
     * 成交量
     */
    private Integer volume;
    /**
     * 成交额 万元
     */
    private Double turnover;
    /**
     * 持仓量
     */
    private Double position;
    /**
     * 今收盘
     */
    private Double close;
    /**
     * 涨停价
     */
    private Double highLimit;
    /**
     * 跌停价
     */
    private Double lowLimit;
    /**
     * 昨收盘
     */
    private Double prevClose;
    /**
     * 昨结算
     */
    private Double prevClear;
    /**
     * 昨持仓
     */
    private Double prevPosition;
    /**
     * ticket 时间
     */
    private LocalDateTime dateTime;
    /**
     * 均价 7050.500
     */
    private Double avg;
    /**
     * 合约名 中证500指数期货2012
     */
    private String name;
    /**
     * 合约代码 IC2012
     */
    private String code;

    public static Spif valueOf(String raw) {
        Spif spif = new Spif();
        String[] segments = StringUtils.trim(raw).split(",");
        spif.setOpen(Double.valueOf(segments[0]));
        spif.setHigh(Double.valueOf(segments[1]));
        spif.setLow(Double.valueOf(segments[2]));
        spif.setPrice(Double.valueOf(segments[3]));
        spif.setVolume(Integer.valueOf(segments[4]));
        spif.setTurnover(Double.valueOf(segments[5]));
        spif.setPosition(Double.valueOf(segments[6]));
        spif.setClose(Double.valueOf(segments[7]));
        spif.setHighLimit(Double.valueOf(segments[9]));
        spif.setLowLimit(Double.valueOf(segments[10]));
        spif.setPrevClose(Double.valueOf(segments[13]));
        spif.setPrevClear(Double.valueOf(segments[14]));
        spif.setPrevPosition(Double.valueOf(segments[15]));
        spif.setDateTime(LocalDateTime.parse(segments[36] + "T" + segments[37]));
        spif.setAvg(Double.valueOf(segments[48]));
        spif.setName(segments[49]);
        spif.setCode(segments[49].replace(IC_NAME, IC));
        return spif;
    }
}
