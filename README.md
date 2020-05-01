# 포스트잇(Post-it)

- 포스트잇 느낌으로 css를 구성함.
- 메모, 키워드(태그)를 입력하여 포스트잇을 추가할 수 있음.
- 검색키워드를 입력 후 검색하면 메모의 단어매칭(한글/영문 단어 색인), 키워드매칭을 판단하여 검색할 수 있음.

## 사용기술

- ElasticSearch 6.4.3
  - nori 한글형태소 분석기
- Kibana(DevTool)
- SpringBoot 2.2.6
  - org.elasticsearch.client:elasticsearch-rest-high-level-client

※ spring-boot-starter-data-elasticsearch를 사용하려고 했으나 SpringBoot(2.2.6)와 ElasticSearch(6.4.3)가 호환되지 않아서 공식 클라이언트 모듈을 사용하였음.  
※ 당연한 말이지만 spring-boot-starter-data-elasticsearch을 사용하면 POJO로 Index구성이 가능하며 Repository interface에 만들어진 기능을 활용할 수 있음.  
(하지만 Spring이 만든 ES버전 호환정보가 부실하다고 생각함.)

## Script

[ElasticSearch Index생성 스크립트](./script/ElisticSearch.script)

## Screenshot

<img src="./screenshot/2.png" width="576" height="360">
<img src="./screenshot/3.png" width="576" height="360">
<img src="./screenshot/6.png" width="576" height="360">
<img src="./screenshot/7.png" width="576" height="360">
