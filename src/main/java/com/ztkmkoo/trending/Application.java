package com.ztkmkoo.trending;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ztkmkoo.trending.github.SearchResponse;
import com.ztkmkoo.trending.github.deserializer.SearchResponseDeserializer;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Ztkmkoo on 2019-09-16
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws Exception {

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()){

            final String url = "https://api.github.com/search/repositories";

            final HttpGet httpGet = new HttpGet("https://api.github.com/search/repositories?page=1&per_page=10&q=language%3Ajava+stars%3A%3E%3D1000&sort=stars&order=desc");
            LOGGER.info("Executing request " + httpGet.getMethod() + " " + httpGet.getUri());

            final CloseableHttpResponse response = httpclient.execute(httpGet);
            final int code = response.getCode();
            final HttpEntity entity = response.getEntity();

            final String body = EntityUtils.toString(entity, "UTF-8");

            final Object[] param1 = new Object[3];
            param1[0] = url;
            param1[1] = response.getCode();
            param1[2] = body;
            LOGGER.log(Level.INFO, "{0} -> {1} : {2}", param1);

            if (code == 200) {

                final ObjectMapper objectMapper = customObjectMapper();
                final SearchResponse searchResponse = objectMapper.readValue(body, new TypeReference<SearchResponse>(){});
                if (searchResponse != null) {
                    LOGGER.log(Level.INFO, "SearchResponse: {0}", searchResponse);
                }
            } else {
                // do nothing
            }

            Thread.sleep(1000);
            LOGGER.info("Shutting down");

        } finally {
            // do nothing
        }
    }

    private static ObjectMapper customObjectMapper() {

        final ObjectMapper objectMapper = new ObjectMapper();
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(SearchResponse.class, new SearchResponseDeserializer());
        objectMapper.registerModule(simpleModule);

        return objectMapper;
    }
}
