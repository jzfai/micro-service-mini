package top.kuanghua.gateway.filter;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Configuration
@Component
public class AuthorizeFilterBefore implements GlobalFilter, Ordered {

    private static final String AUTHORIZE_TOKEN = "AUTHORIZE_TOKEN";
    @Value("#{'${filter.allowPaths:}'.empty ? null : '${filter.allowPaths:}'.split(',')}")
    private List<String> allowPaths;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        //白名单放行
        for (String allowPath : allowPaths) {
            if (StringUtils.contains(path, allowPath)) {
                return chain.filter(exchange);
            }
        }
        //解析jwt token放行
        String jwtToken = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if (ObjectUtils.isEmpty(jwtToken)) {
            JSONObject message = new JSONObject();
            message.put("code", 403);
            message.put("msg", "token为空");
            DataBuffer buffer = getDataBuffer(response, message);
            return response.writeWith(Mono.just(buffer));
        }
        //将信息设置到头部
        ServerHttpRequest httpRequest = exchange.getRequest().mutate().headers(httpHeaders -> {
//            String decode = "";
//            try {
//                decode = URLEncoder.encode(JSON.toJSONString(tokenInfo), "utf-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            httpHeaders.add("TOKEN_INFO", "tokenInfoData");
        }).build();

        ServerWebExchange build = exchange.mutate().request(httpRequest).build();
        return chain.filter(build);
    }

    private DataBuffer getDataBuffer(ServerHttpResponse response, JSONObject message) {
        byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //response.setStatusCode(HttpStatus.UNAUTHORIZED);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return buffer;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
