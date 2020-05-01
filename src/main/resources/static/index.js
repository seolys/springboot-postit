function memoRender() {
    $("div.memo_data").remove();
    memoInqr().then(makeMemoHtml);
}

function makeMemoHtml(res){
    console.log("makeMemoHtml", res);

    var $dom = $("div.reg_form");
    for(data of res.dataList) {
        memoHtmlAppend($dom, data);
    }
}

function memoHtmlAppend($dom, data) {
    var html =
        `
        <div id="${data.id}" class="memo_area memo_data">
            <div class="memo_header">
                <div class="memo_header_left">
                    <input type="checkbox">
                </div>
                <div class="memo_header_center">
                    ${data.uptDateTime}
                </div>
                <div class="memo_header_right">
                    <button>삭제</button><!--구현필요-->
                </div>
            </div>
            <div class="memo_body">
                <textarea class="memo-content">${data.content}</textarea>
            </div>
            <div class="memo_footer">
                ${memoTagsRender(data.tags)}
            </div>
        </div>
        `;

    $dom.after(html);
}

function memoTagsRender(tags = []) {
    return tags.map(tag => `<a href="#" class="memo-tag" onclick="tagSearch('${tag}')">#${tag}</a>`).join(" ");
}

function tagSearch(keyword) {
    if(!keyword){
        return;
    }
    $("input#searchKeyword").val(keyword);
    memoSearch();
}

function memoInqr() {
    return $.ajax({
        type: 'GET',
        url: '/api/v1/memo',
        dataType: 'json',
        contentType:'application/json; charset=utf-8'
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function memoRegist() {
    var data = {
        content: $("textarea#memoContent").val(),
        tag: $("input#memoTag").val()
    };

    if(!data.content) {
        alert("메모를 작성해주세요.")
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/api/v1/memo',
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function(res) {
        alert('등록되었습니다.');

        var $dom = $("div.reg_form");
        memoHtmlAppend($dom, res.data);

        $("textarea#memoContent").val("");
        $("input#memoTag").val("");

    }).fail(function (error) {
        console.error(error);
        alert(JSON.stringify(error));
    });
}

function memoSearch() {
    var keyword = $("input#searchKeyword").val();
    if(!keyword) {
        memoRender();
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/api/v1/memo/search',
        dataType: 'json',
        contentType:'application/json; charset=utf-8',
        data: JSON.stringify({
            content: keyword
        })
    }).done(function(res) {
        $("div.memo_data").remove();

        var $dom = $("div.reg_form");
        for(data of res.dataList) {
            memoHtmlAppend($dom, data);
        }

    }).fail(function (error) {
        console.error(error);
        alert(JSON.stringify(error));
    });
}