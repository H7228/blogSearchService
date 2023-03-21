##### GITHUB: https://github.com/H7228/blogSearchService
##### BUILD.jar: https://github.com/H7228/blogSearchService/tree/main/bebs/build/libs
##### 실행위치(bebs): java -jar build/libs/bebs-1.0-SNAPSHOT.jar


# blogSearchService
> KAKAO API를 이용한 BLOG SEARCH CONTROLLER


## 사용한 OPEN SOURCE
1. Spring Boot: 자바 기반의 웹 어플리케이션 개발을 위한 프레임워크
2. Spring Data JPA: 데이터베이스 연동을 위한 ORM 라이브러리
3. PostgreSQL: 관계형 데이터베이스 관리 시스템(RDBMS)
4. Spring Security: 데이터베이스에 저장되는 사용자 인증(Authentication)을 받음
5. Jackson: JSON 데이터 처리
    - API의 값 paser
6. Lombok: Getter, Setter 등의 메서드를 자동 생성
7. ModelMapper: 객체 간 변환을 위한 라이브러리
    - entity to dto, dto to dto
8. Apache Commons Lang: 문자열, 배열, 숫자 등의 자바 기본 자료형을 처리
9. H2: 인메모리 데이터베이스
10. Caffeine: 메모리 캐시 라이브러리, 캐싱을 위해 사용
11. Springdoc: OpenAPI 3(Swagger) 문서 자동 생성
12. JUnit: 자바 단위 테스트 라이브러리, junit5


## API 명세
http://localhost:8080/bebs-ui.html

### 키워드로 블로그를 검색: Blog Search API
> URL: GET /blog/search/keyword

##### Parameter
- keyword: 검색할 키워드
- sort: 정렬 방식 (accuracy, recency)
- page: 검색 할 페이지
- size: 한 페이지에 나타낼 수

##### Responses
``` json
{
  "items": [
    {
      "title": "제목",
      "link": "링크",
      "description": "설명",
      "bloggerName": "블로거 이름",
      "bloggerLink": "블로거 링크",
      "postDate": "게시일"
    }
  ]
}
```
- items: 블로그 검색 결과 리스트
- title: 글 제목
- link: 글 링크
- description: 글 요약
- bloggerName: 블로거 이름
- bloggerLink: 블로거 링크
- postDate: 게시일


### 가장 많이 검색된 상위 10개의 키워드를 조회:  Keyword API
> URL: GET /keyword/popular

##### Request
N/A

##### Response

HTTP Status: 200 OK

``` json
[
  {
    "id": "키워드 ID",
    "keyword": "키워드",
    "count": "검색 수"
  }
]
```
- id: 키워드 ID
- keyword: 검색할 키워드
- count: 검색 수
