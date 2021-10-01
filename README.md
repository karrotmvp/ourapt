# Introduction
> Product of Karrot MVP Internship team2


# Requirements
- docker, docker-compose: 3.9^
- Unix shell


# References
### 프로필 환경에 따른 서버 실행 방법
<img width="600" alt="image" src="https://user-images.githubusercontent.com/54184118/135190068-3d3f308d-953f-4fe9-be3b-0fae744e950f.png">

# Branch(profile) rule
- production: 실제 서비스가 운영되는 브랜치에요. 이 브랜치에 올라온 코드는 실제로 유저에게 딜리버돼요.
- main: production 브랜치로 가기전에 버그가 없다고 생각되는, 테스트(가 있다면)를 통과한 코드만 존재하는 브랜치에요.
- staging: 팀 외부이자, 당근마켓 내부에 노출될 브랜치에요. 버그는 존재할 수 있으나, 실행가능한 상태의 코드만 존재해야해요.
- dev: 개발자 개인의 로컬에서 작동될 환경을 위한 브랜치에요. 개발자 개인의 로컬에서 작동될 환경을 위한 브랜치에요. 버그는 존재할 수 있으나, 실행 가능한 상태의 코드만 존재해야해요.

