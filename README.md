# 포스트잇(Post-it)
- 포스트잇 느낌으로 css를 구성했습니다.
- 메모, 키워드(태그)를 입력하여 포스트잇을 추가할 수 있습니다.
- 검색키워드를 입력 후 검색하면 메모의 단어매칭(한글/영문 단어 색인), 키워드매칭을 판단하여 검색할 수 있습니다.

## 사용기술

- ElasticSearch 6.4.3
  - nori 한글형태소 분석기
- Kibana(DevTool)
- SpringBoot 2.2.6
  - org.elasticsearch.client:elasticsearch-rest-high-level-client

※ spring-boot-starter-data-elasticsearch를 사용하려고 했으나 SpringBoot와 ElasticSearch 버전호환이 되지않음.  
2.1.x버전으로 돌아가자니 변경사항이 많아보이고, API연동간에 자동으로 달라붙는 파라메터만 조금 손보면 가능할것같은데..  
구글링해도 버전을 낮춰야 한다는 답변 뿐이라서 Elastic에서 제공하는 client를 활용하여 연동하였다.

## Script

[ElasticSearch Index생성 스크립트](./script/ElisticSearch.script)

## Screenshot

<img src="./screenshot/2.png" width="576" height="360">
<img src="./screenshot/3.png" width="576" height="360">
<img src="./screenshot/6.png" width="576" height="360">
<img src="./screenshot/7.png" width="576" height="360">
