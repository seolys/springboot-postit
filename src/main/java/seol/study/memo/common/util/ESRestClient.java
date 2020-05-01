package seol.study.memo.common.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ESRestClient {
    @Value("${elasticSearch.host}")
    private String host;
    @Value("${elasticSearch.port}")
    private int port;
    @Value("${elasticSearch.scheme}")
    private String scheme;

    public RestHighLevelClient newInstance() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(host, port, scheme)));
        return client;
    }
}
