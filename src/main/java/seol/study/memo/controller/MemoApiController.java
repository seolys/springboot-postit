package seol.study.memo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import seol.study.memo.vo.MemoVO;
import seol.study.memo.vo.ResultVO;
import seol.study.memo.service.MemoService;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemoApiController {

    private final MemoService memoService;

    @GetMapping("/api/v1/memo")
    public ResultVO<MemoVO> findAllMemo() throws IOException {
        ResultVO<MemoVO> resultVO = new ResultVO<>();
        resultVO.setDataList(memoService.findAll());
        return resultVO;
    }

    @PostMapping("/api/v1/memo")
    public ResultVO<MemoVO> memoPost(@RequestBody MemoVO memoVO) throws IOException {
        log.debug("memoVO : " + memoVO);
        String saveId = memoService.saveMemo(memoVO);

        ResultVO<MemoVO> resultVO = new ResultVO<>();
        resultVO.setData(memoService.findById(saveId));
        return resultVO;
    }

    @GetMapping("/api/v1/memo/{id}")
    public ResultVO<MemoVO> findAllMemo(@PathVariable("id") String id) throws IOException {
        ResultVO<MemoVO> resultVO = new ResultVO<>();
        resultVO.setData(memoService.findById(id));
        return resultVO;
    }

    @PostMapping("/api/v1/memo/search")
    public ResultVO<MemoVO> findAllMemo(@RequestBody MemoVO memoVO) throws IOException {
        ResultVO<MemoVO> resultVO = new ResultVO<>();
        resultVO.setDataList(memoService.findByContentLike(memoVO));
        return resultVO;
    }

}
