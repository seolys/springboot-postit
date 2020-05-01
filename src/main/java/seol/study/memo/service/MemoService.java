package seol.study.memo.service;

import seol.study.memo.vo.MemoVO;

import java.io.IOException;
import java.util.List;

public interface MemoService {
    List<MemoVO> findAll() throws IOException;

    MemoVO findById(String id) throws IOException;

    MemoVO saveMemo(MemoVO memoVO) throws IOException;

    List<MemoVO> findByContentLike(MemoVO memoVO) throws IOException;
}
