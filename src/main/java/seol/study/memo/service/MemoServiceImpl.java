package seol.study.memo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;
import seol.study.memo.vo.MemoVO;
import seol.study.memo.common.util.ESRestClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemoServiceImpl implements MemoService {
    private final String INDEX_NAME = "memo";
    private final String TYPE_NAME = "_doc";

    private final ESRestClient esRestClient;

    @Override
    public List<MemoVO> findAll() throws IOException {
        // 검색 쿼리 설정
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.from(0); // 페이징 스킾
        searchSourceBuilder.size(20);
        searchSourceBuilder.sort(new FieldSortBuilder("uptDateTime").order(SortOrder.ASC));

        // 요청
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.types(TYPE_NAME);
        searchRequest.source(searchSourceBuilder);

        RestHighLevelClient client = esRestClient.newInstance();
        SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        client.close();

        final List<MemoVO> collect = Arrays.stream(searchResponse.getHits().getHits())
                                            .map(MemoVO::new)
                                            .collect(Collectors.toList());
        return collect;
    }

    @Override
    public MemoVO findById(String _id) throws IOException {
        // 요청
        GetRequest request = new GetRequest(INDEX_NAME, TYPE_NAME, _id);

        RestHighLevelClient client = esRestClient.newInstance();
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        client.close();

        MemoVO memoVO = new MemoVO(response.getSourceAsMap());
        memoVO.setId(_id);
        return memoVO;
    }

    @Override
    public MemoVO saveMemo(MemoVO memoVO) throws IOException {
        this.saveValidCheck(memoVO);

        IndexRequest request = new IndexRequest("memo","_doc");

        SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        String today = format.format(Calendar.getInstance().getTime());

        request.source(jsonBuilder()
                .startObject()
                .field("content", memoVO.getContent())
                .field("tags", memoVO.getParsingTags())
                .field("regDateTime", today)
                .field("uptDateTime", today)
                .endObject()
        );

        RestHighLevelClient client = esRestClient.newInstance();
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        client.close();

        return this.findById(response.getId());
    }

    private void saveValidCheck(MemoVO memoVO) {
        if (memoVO == null || memoVO.getContent() == null || "".equals(memoVO.getContent()))
            throw new NullPointerException("내용을 입력해주세요.");
    }

    @Override
    public List<MemoVO> findByContentLike(MemoVO memoVO) throws IOException {
//        QueryStringQueryBuilder query = QueryBuilders.queryStringQuery(memoVO.getContent());
        MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery(memoVO.getContent(), "content", "tags");


//        검색 쿼리 설정
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query);
        searchSourceBuilder.from(0); // 페이징 스킾
        searchSourceBuilder.size(20);
//        searchSourceBuilder.sort(new FieldSortBuilder(FIELD_NAME).order(SortOrder.DESC));

        // 요청
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.types(TYPE_NAME);
        searchRequest.source(searchSourceBuilder);

        RestHighLevelClient client = esRestClient.newInstance();
        SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        client.close();

        final List<MemoVO> collect = Arrays.stream(searchResponse.getHits().getHits())
                .map(hits -> hits.getSourceAsMap())
                .map(MemoVO::new)
                .collect(Collectors.toList());
        return collect;
    }

}
