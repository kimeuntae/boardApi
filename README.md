## 🙌 Spring boot 회원 게시판 API 기능

- **JPA**
- **QueryDsl**

```
** QueryDsl 설정**
QueryDslConfig
```
- **Spring Securtiy JWT 토큰 인증 방식**

```
**JWT 인증**
AuthController::authorize2
**사용자 확인**
CustomUserDetailsService::loadUserByUsername
**Token생성**
TokenProvider::createToken
**Token검증 및 유효검사
TokenProvider::getAuthentication,validateToken
```

- **Spring Securtiy Form Login 인증 방식**

```
**Form Login 인증**
CustomAuthenticationProvider::authenticate

**인증 및 인가 filter**
ReqeustVaildationBeforeFilter
AuthoritiesLoggingAfterFilter
AuthoritiesLoggingAtFilter

**인증전후**
loginProcessingUrl
defaultSuccessUrl

```

- **공통프레임워크 기능 추가예정** 
