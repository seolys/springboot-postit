package seol.study.memo.common.constant;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("0000")
    ;
    private final String code;
    ResponseCode(String code) {
        this.code = code;
    }

}