# 🦋 나만의 비서 NAVI
 > https://mysecnavi.store/ <br>
 > [API 문서](https://documenter.getpostman.com/view/26905233/2s93ecwqKW) <br>

[1. 프로젝트 소개](#프로젝트-소개) <br/>
[2. 프로젝트 목표](#프로젝트-목표) <br/>
[3. 프로젝트 전체 구조](#프로젝트-전체-구조) <br/>
[4. 프로젝트 주요기능](#프로젝트-주요기능) <br/>

## 프로젝트 소개
* 오늘 하루 필요한 여러가지 정보들을 안내하는 사이트입니다

## 프로젝트 목표
1. REST API 설계
2. 프론트엔드 프레임워크 사용
3. 모바일 친화적으로 만들어보기 
4. AWS 기반 배포

## 프로젝트 전체 구조
![structure drawio](https://github.com/mftqn555/NAVI/assets/93305133/597eb7f3-4f9c-45fe-b4c3-229f971ae18a)

## 프로젝트 주요기능
### 1. 인삿말
![image](https://github.com/mftqn555/NAVI/assets/93305133/fab61532-047f-4b7a-b32b-532e39a6b74e)
* **ChatGPT API**를 이용해 방문하시는 분들께  __아침, 점심, 저녁, 새벽__ 별로 현재 시간에 따른 인사메세지를 보여드립니다
---
### 2. 오늘의 핫 이슈
![trend](https://github.com/mftqn555/NAVI/assets/93305133/d688baf8-03c3-46cb-a969-c635100b37c6)
* **구글 트렌드**를 기반으로 오늘의 핫이슈 정보를 제공합니다.
---
### 3. 실시간 날씨
![image](https://github.com/mftqn555/NAVI/assets/93305133/eb61835e-a59c-4dd4-b3cc-62acf2a9b389)
* **HTML Geolocation API**를 이용해 사용자의 위치에 따른 날씨정보를 제공합니다. <br> <br>
![image](https://github.com/mftqn555/NAVI/assets/93305133/807321a0-6e3a-4403-9c93-02456583f7a2) <br>
1. 주소창 옆을 클릭하고 날씨정보 제공에 동의를 하면 현재 위치에 따른 날씨정보를 제공합니다
2. 동의하지 않을경우 기본값으로 서울의 날씨정보를 제공합니다
3. 업데이트 아이콘 클릭시 현재 시간에 따른 날씨정보를 다시 불러옵니다
---
### 4. 부산 버스도착정보
![bus](https://github.com/mftqn555/NAVI/assets/93305133/39dd9097-eb8d-47bb-a421-3936063d4144)
* 찾고자 하는 버스를 검색 후 선택하면, 실시간 도착정보를 제공합니다
---
### 5. 지하철 도착 정보
![subway](https://github.com/mftqn555/NAVI/assets/93305133/393d0016-c6d1-4606-87ca-d1426807fddb)
* 찾고자 하는 지하철 역을 검색 후 선택하면 실시간 도착정보를 제공합니다
### 6. 자유게시판
![image](https://github.com/mftqn555/NAVI/assets/93305133/571036cb-1085-4513-86de-59978b6ebd69)
![image](https://github.com/mftqn555/NAVI/assets/93305133/0be606d2-3fd8-4a27-9b3b-b3f8bf6fe8f0)
* 관리자가 작성한 공지사항 및 유저가 작성한 게시글을 볼 수 있습니다
* 댓글 및 답글을 작성할 수 있습니다
### 7. 기타
* 관리자 계정
 1. 회원 정보 조회 및 강퇴
 2. 모든 게시글, 댓글 삭제
 3. 공지사항 작성 
* 이메일 전송으로 회원정보 찾기 <br>
![password](https://github.com/mftqn555/NAVI/assets/93305133/d3a5e569-4d81-46b7-948e-e3e58fad488f)








