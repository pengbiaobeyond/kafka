package com.pengbiao.kafka.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public HttpUtil() {
    }

    public static Map<String, Object> getAllParams(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder builder = new StringBuilder();
        HashMap paramMap = new HashMap();

        try {
            Enumeration headers = request.getHeaderNames();

            String line;
            while(headers.hasMoreElements()) {
                String headName = (String)headers.nextElement();
                line = request.getHeader(headName);
                paramMap.put(headName, line);
            }

            Map<String, String[]> requestParms = request.getParameterMap();
            if (requestParms != null) {
                Map<String, Object> tmpParamMap = new HashMap();
                requestParms.forEach((k, v) -> {
                    try {
                        tmpParamMap.put(k, v[0]);
                    } catch (Exception var4) {
                    }

                });
                paramMap.putAll(tmpParamMap);
            }

            while((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String bodyString = builder.toString();
            log.info("bodyString内容为:{}", bodyString);
            log.info("转换为Map为:{}", JSONObject.parseObject(bodyString,Map.class));
            paramMap.putAll(JSONObject.parseObject(bodyString,Map.class));
            HashMap var8 = paramMap;
            return var8;
        } catch (Exception var18) {
            log.error("获取验证参数失败:{}", var18);
        } finally {
            try {
                reader.close();
            } catch (IOException var17) {
                log.error("关闭读取body流失败:{}", var17);
            }

        }

        return paramMap;
    }
}
