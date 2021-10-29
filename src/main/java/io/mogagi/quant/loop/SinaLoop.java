package io.mogagi.quant.loop;

import io.mogagi.quant.data.Future;
import io.mogagi.quant.data.Spif;
import io.mogagi.quant.data.Stock;
import io.mogagi.quant.supports.Cache;
import io.mogagi.quant.supports.SinaClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.mogagi.quant.supports.Constant.AFTERNOON_CLOSE;

/**
 * Fetch data from Sina API
 *
 * @author mougg
 */
@Slf4j
@Component
public class SinaLoop extends AbstractLoop {
    public static final String CONTRACT_NAMES = "contractNames";

    final Cache cache;
    final SinaClient sinaClient;

    @Autowired
    public SinaLoop(@Value("${loop.sina.nThreads:2}") int nThreads, SinaClient sinaClient, Cache cache) {
        super(nThreads);
        this.cache = cache;
        this.sinaClient = sinaClient;
    }

    @Override
    public LoopResult work(String loopName, Object data) {
        Map<String, Future> codeFutureMap = cache.get(CONTRACT_NAMES);
        if (codeFutureMap == null) {
            codeFutureMap = sinaClient.cffContracts();
            cache.put(CONTRACT_NAMES, codeFutureMap, LocalDateTime.of(LocalDate.now(), AFTERNOON_CLOSE));
        }
        Stock sZ399905 = sinaClient.sZ399905();
        List<String> icCodes = codeFutureMap.values().stream()
                .filter(future -> future.getCode().startsWith("IC"))
                .map(Future::getSinaCode).collect(Collectors.toList());
        List<Spif> icList = sinaClient.cffTicks(icCodes);
        return LoopResult.pass(new Result(sZ399905, codeFutureMap, icList));
    }

    @Data
    @AllArgsConstructor
    static class Result {
        private Stock sZ399905;
        private Map<String, Future> codeFutureMap;
        private List<Spif> icList;
    }
}