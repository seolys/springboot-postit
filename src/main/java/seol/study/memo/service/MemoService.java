package seol.study.memo.service;

import org.elasticsearch.action.delete.DeleteResponse;
import seol.study.memo.vo.MemoVO;

import java.io.IOException;
import java.util.List;

public interface MemoService {
    List<MemoVO> findAll() throws IOException;

    MemoVO findById(String id) throws IOException;

    String saveMemo(MemoVO memoVO) throws IOException;

    List<MemoVO> findByContentLike(MemoVO memoVO) throws IOException;

    DeleteResponse delete(String _id) throws IOException;
}
