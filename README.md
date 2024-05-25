## 🙌 Spring boot 회원 게시판 API 기능

- **JPA**

```
**JPA Gradle**
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
```

- **QueryDsl**

```

**QueryDSL Gradle**

//plugins
id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"

implementation "com.querydsl:querydsl-jpa:5.0.0:jakarta"
annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
annotationProcessor "jakarta.annotation:jakarta.annotation-api"
annotationProcessor "jakarta.persistence:jakarta.persistence-api"

// Querydsl 설정부
def generated = 'src/main/generated'
querydsl {
	jpa = true
	querydslSourcesDir = generated
}
sourceSets {
	main.java.srcDir generated
}
compileQuerydsl{
	options.annotationProcessorPath = configurations.querydsl
}
configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
	querydsl.extendsFrom compileClasspath
}
```


```
** QueryDsl Config **
QueryDslConfig
```


- **Spring Securtiy JWT 토큰 인증 방식**

```
**JWT token Gradle**
implementation group: 'io.jsonwebtoken', name: 'jjwt-api', version: '0.11.5'
runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-impl', version: '0.11.5'
runtimeOnly group: 'io.jsonwebtoken', name: 'jjwt-jackson', version: '0.11.5'
```


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
**Spring Securtiy Gradle**
implementation 'org.springframework.boot:spring-boot-starter-security'
```

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
