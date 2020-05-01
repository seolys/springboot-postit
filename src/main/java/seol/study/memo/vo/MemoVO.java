package seol.study.memo.vo;

import lombok.Builder;
import lombok.Data;
import org.elasticsearch.search.SearchHit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class MemoVO {
    private String id;
    private String content;
    private String tag;
    private List<String> tags;
    private String regDateTime;
    private String uptDateTime;

    @Builder
    public MemoVO(String content, String tag) {
        this.content = content;
        this.tag = tag;
    }

    public MemoVO(Map<String, Object> map) {
        this.setDataFromMap(map);
    }

    public MemoVO(SearchHit hit) {
        this.id = hit.getId();
        this.setDataFromMap(hit.getSourceAsMap());
    }

    public void setDataFromMap(Map<String, Object> map) {
        this.content = (String) map.get("content");
        this.tags = (List<String>) map.get("tags");
        this.regDateTime = (String) map.get("regDateTime");
        this.uptDateTime = (String) map.get("uptDateTime");
    }

    public List<String> getParsingTags() {
        if (tag==null || "".equals(tag)) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.stream(tag.split(",")).map(String::trim).collect(Collectors.toList());
    }

}
