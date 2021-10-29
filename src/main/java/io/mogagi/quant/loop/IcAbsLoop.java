package io.mogagi.quant.loop;

import io.mogagi.quant.alert.AlertMsg;
import io.mogagi.quant.data.Future;
import io.mogagi.quant.data.Spif;
import io.mogagi.quant.data.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.mogagi.quant.supports.Constant.AVG_DAYS_IN_MONTH;
import static io.mogagi.quant.supports.Constant.SZ399905;

/**
 * Analysis IC relative spread
 *
 * @author mougg
 */
@Slf4j
@Component
public class IcAbsLoop extends AbstractLoop {
    private static final String LW = "LW", HW = "HW", FORMAT = "\n%s %.2f %.2f %.2f\n%s %.2f %.2f %s\n%s %.2f %.2f %s\n%s %.2f %.2f %s\n%s %.2f %.2f %s";

    private Double lwThreshold, hwThreshold;

    @Autowired
    public IcAbsLoop(@Value("${loop.icAbs.nThreads}") int nThreads,// @Value("${loop.icAbs.content}") String content,
                     @Value("${loop.icAbs.lw}") Double lwThreshold, @Value("${loop.icAbs.hw}") Double hwThreshold) {
        super(nThreads);
        this.lwThreshold = lwThreshold;
        this.hwThreshold = hwThreshold;
    }

    @Override
    public LoopResult work(String loopName, Object data) {
        if ("SinaLoop".equals(loopName)) {
            return sina((SinaLoop.Result) data);
        }
        log.error("{} miss {}", name, loopName);
        return LoopResult.stop();
    }

    private LoopResult sina(SinaLoop.Result data) {
        Stock sZ399905 = data.getSZ399905();
        Double indexPrice = sZ399905.getPrice(), lw = indexPrice * lwThreshold, hw = indexPrice * hwThreshold;
        List<Object> result = new LinkedList<>(Arrays.asList(SZ399905, indexPrice, lw, hw));
        Map<String, Future> codeMap = data.getCodeFutureMap();
        String alertTitle = null;
        for (Spif spif : data.getIcList()) {
            Long dayLeft = LocalDate.now().until(codeMap.get(spif.getCode()).getDueDate(), ChronoUnit.DAYS);
            Double avgSpread = (indexPrice - spif.getPrice()) / dayLeft * AVG_DAYS_IN_MONTH;
            String mark = avgSpread <= lw ? LW : avgSpread >= hw ? HW : "";
            result.addAll(Arrays.asList(spif.getCode(), spif.getPrice(), avgSpread, mark));
            alertTitle = LW.equals(mark) ? spif.getCode() + "处于低水位" : HW.equals(mark) ? spif.getCode() + "处于高水位" : alertTitle;
        }
        String alertContent = String.format(FORMAT, result.toArray());
        log.info(alertContent);
        return alertTitle == null ? LoopResult.stop() : LoopResult.pass(new AlertMsg(alertTitle, alertContent));
    }
}