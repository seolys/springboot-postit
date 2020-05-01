package seol.study.memo.service;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import seol.study.memo.vo.MemoVO;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemoServiceImplTest {

    @Autowired
    MemoService memoService;

    String id = null;
    String content = "포스트잇 컨텐츠";
    String tag = "postit,memo,test";

    @Before
    public void before() throws IOException {
        saveMemo();
    }

    @After
    public void after() throws IOException {
        delete();
    }

    public void saveMemo() throws IOException {
        MemoVO memoVO = new MemoVO();
        memoVO.setContent(content);
        memoVO.setTag(tag);

        this.id = memoService.saveMemo(memoVO);
        MemoVO findMemoVO = memoService.findById(this.id);

        assertThat(findMemoVO.getContent()).isEqualTo(content);
        assertThat(findMemoVO.getTags().size()).isEqualTo(this.tag.split(",").length);
    }

    private void delete() throws IOException {
        memoService.delete(this.id);
    }

    @Test
    public void findAll() throws IOException {
        final List<MemoVO> memoList = memoService.findAll();
        assertThat(memoList.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void findByContentLike() throws IOException {
        String findKeyword = "test";

        MemoVO memoVO = new MemoVO();
        memoVO.setContent(findKeyword);
        final List<MemoVO> memoList = memoService.findByContentLike(memoVO);

        boolean check = false;
        for (MemoVO vo : memoList) {
            if(this.content.equals(vo.getContent())) {
                check = true;
            }
        }

        assertThat(check).isTrue();
    }
}