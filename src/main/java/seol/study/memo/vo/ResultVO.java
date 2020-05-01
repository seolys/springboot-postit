package seol.study.memo.vo;

import lombok.Data;
import seol.study.memo.common.constant.ResponseCode;

import java.util.List;

@Data
public class ResultVO<T> {
    private String resultCd;
    private String resultMsg;
    private T data;
    private List<T> dataList;

    public ResultVO() {
        this.resultCd = ResponseCode.SUCCESS.getCode();
    }
}
