# 소개
## 이 프로젝트는 jWT 학습을 위한 프로젝트입니다.

<hr />


# 학습내용
## JPA DDL 설정 
- JPA를 사용하여 데이터베이스 테이블을 자동으로 생성하고 관리하는 방법에는 여러 가지가 있습니다. 이 글에서는 각 설정의 특징과 추천 설정 방법에 대해 설명하겠습니다.


### 기본 설정
- spring.jpa.hibernate.ddl-auto=create
    - 이 설정을 사용하면 서버가 시작될 때마다 데이터베이스 테이블을 새로 생성합니다. 주의할 점은 매번 테이블을 새로 생성하기 때문에 기존 데이터가 모두 삭제된다는 점입니다.
      주의사항: 서버를 다시 시작할 때 이 설정을 그대로 두면 테이블이 계속해서 새로 생성되어 기존 데이터가 모두 지워집니다. 따라서, 서버 재시작 시에는 spring.jpa.hibernate.ddl-auto=none으로 변경하는 것이 좋습니다.


### 문제점과 해결 방안
- 위의 설정은 개발 초기 단계에서는 유용할 수 있지만, 실제 운영 환경에서는 매우 위험할 수 있습니다. 데이터를 계속 유지하면서 스키마 변경 사항만 반영하고 싶다면, 더 나은 방법이 있습니다.


### 해결방안:
- ```spring.jpa.hibernate.ddl-auto=update```
- 이 설정을 사용하면 JPA가 기존 데이터베이스 테이블을 유지하면서 변경 사항만 데이터베이스에 반영합니다. 데이터가 삭제되지 않고, 새로운 필드나 테이블이 추가될 때만 업데이트됩니다.
  이 방법은 개발 중간 단계 및 운영 환경에서도 안전하게 사용할 수 있습니다.


### 정리
- 따라서, 테이블을 처음 생성할 때는 spring.jpa.hibernate.ddl-auto=create를 사용하고, 이후에는 spring.jpa.hibernate.ddl-auto=update로 설정하여 데이터베이스 변경 사항만 반영하는 것이 좋습니다. 이렇게 하면 데이터 손실을 방지하고, 스키마 변경을 안전하게 관리할 수 있습니다.


### properties파일
### - 초기 테이블 생성 시
```spring.jpa.hibernate.ddl-auto=create```

### - 서버 재시작 또는 운영 시
```spring.jpa.hibernate.ddl-auto=update```


<hr />


## Form 로그인 비활성화

SecurityConfig.java
```html
// Form 로그인 비활성화
    http
            .formLogin((auth) -> auth.disable());
```

Form 로그인을 비활성화한 이유는 다음과 같습니다:

### 1. 필터 순서 문제
- 스프링 시큐리티는 로그인 요청을 처리할 때 여러 필터를 거칩니다. Form 로그인이 활성화되면 이 필터가 JWT 필터보다 먼저 실행되어 오류가 발생할 수 있습니다.

### 2. JWT 로그인 방식 사용
- JWT 로그인 방식을 사용하기 위해서는 Form 로그인 필터를 비활성화해야 합니다. 그렇지 않으면 JWT 필터가 정상적으로 작동하지 않을 수 있습니다.


### 3. 정리
- 따라서, http.formLogin((auth) -> auth.disable()); 코드를 통해 Form 로그인 방식을 비활성화합니다. 이를 통해 JWT 필터가 올바르게 작동하도록 설정합니다.
- 또한 이 필터를 커스텀하여 등록해야합니다.

<hr />


## Spring Security의 User와 CustomUser
- Spring Security를 사용하여 사용자 인증을 받을 때, 기본적으로 User 타입으로 인증을 받는다.
- 인증된 사용자 정보(auth 변수)에 내가 원하는 값을 추가하려면 CustomUser 클래스를 생성하여 User 클래스를 상속 받아야 한다.
- 이 프로젝트에서는 코드의 단순성과 유지보수성을 높이기 위해 CustomUser 대신 기본 제공되는 User 객체를 사용한다.

<hr />

## 전체적인 흐름
- [MyUserDetailsService.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FspringJWT%2Fservice%2FMyUserDetailsService.java) 
  - 여기에서 뭘하고


- [AuthenticationService.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FspringJWT%2Fservice%2FAuthenticationService.java) 갔
  - 여기에서는 뭘하고


- [JwtUtil.java](src%2Fmain%2Fjava%2Fcom%2Fhanul%2FspringJWT%2Fjwt%2FJwtUtil.java)
  - 여기에서 뭘 해서 만들고 
  

- 이후 수정해야함.