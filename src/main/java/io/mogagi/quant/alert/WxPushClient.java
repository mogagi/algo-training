package io.mogagi.quant.alert;

import io.mogagi.quant.supports.Cache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static io.mogagi.quant.supports.Constant.AFTERNOON_CLOSE;
import static io.mogagi.quant.supports.Constant.LAST_PUSH_LDT;

/**
 * Push to wechat
 *
 * @author mougg
 */
@Slf4j
//@Component TODO
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WxPushClient implements AlertChannel {

    @Value("${xtuis.url:http://wx.xtuis.cn/")
    String xtuisUrl;
    @Value("xtuis.token:U2yI2DmnrVbYC4MWmrnSCN6D0")
    String xtuisToken;
    @Value("${xtuis.coolSec:300}")
    Integer xtuisCoolSec;

    final Cache cache;
    final HttpClient httpClient;

    @Override
    public synchronized void send(String title, String content) {
        LocalDateTime lastAlertLdt = cache.get(LAST_PUSH_LDT, LocalDateTime.MIN);
        if (lastAlertLdt.until(LocalDateTime.now(), ChronoUnit.SECONDS) < xtuisCoolSec) { // still cooling
            log.debug("wxPush is cooling down, title={}", title);
        } else {
            push(title, content);
            cache.put(LAST_PUSH_LDT, LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), AFTERNOON_CLOSE));
        }
    }

    private void push(String title, String content) {
        String url = String.format("%s%s.send?text=%s&desp=%s", xtuisUrl, xtuisToken, title, content);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("push wx fail, statusCode={}, title={}", response.statusCode(), title);
            }
        } catch (IOException | InterruptedException e) {
            log.error("push wx fail, title={}", title, e);
        }
    }
}