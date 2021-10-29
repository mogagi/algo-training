package io.mogagi.quant.supports;

import io.mogagi.quant.data.Future;
import io.mogagi.quant.data.Spif;
import io.mogagi.quant.data.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Client that access sina
 *
 * @author mougg
 */
@Component
public class SinaClient {

    @Value("${sina.api.url:http://hq.sinajs.cn/}")
    String SINA_API_URL;
    @Autowired
    HttpClient httpClient;

    public Stock sZ399905() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SINA_API_URL + "list=sz399905")).build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return Stock.valueOf(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("query Z500 fail", e);
        }
    }

    public Map<String, Future> cffContracts() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SINA_API_URL + "list=CFF_LIST")).build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String respStr = response.body();
            return Arrays.stream(StringUtils.trim(respStr).split(","))
                    .filter(code -> code.matches("^.*\\d{4}$"))
                    .map(Future::valueOf)
                    .collect(Collectors.toMap(Future::getCode, fc -> fc));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("query contract fail", e);
        }
    }

    public List<Spif> cffTicks(List<String> contracts) {
        if (contracts == null || contracts.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(SINA_API_URL + "list=" + String.join(",", contracts))).build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String respStr = response.body();
            return Arrays.stream(respStr.split("\n"))
                    .map(Spif::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("query SPIF fail", e);
        }
    }
}