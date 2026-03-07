# 📺 TVING Clone Backend (Spring Boot 3.x)

> **Spring Boot와 Spring Security를 활용한 고가용성 OTT 서비스 API 서버**
> 
> 🔗 **Frontend Repo**: [tving_front 바로가기](https://github.com/helloyoogs/tving_front)

---

## 🚀 Key Engineering Achievements
- **Stateless Authentication Architecture**: **Spring Security와 JWT**를 결합하여 서버 부하를 줄이고 확장성을 확보한 인증 시스템 설계.
- **Advanced Data Modeling**: JPA를 활용하여 사용자, 구독 등급, 콘텐츠 간의 **다대다(N:M) 연관 관계**를 객체 지향적으로 매핑 및 데이터 무결성 확보.
- **Global Exception Handling**: `@RestControllerAdvice`를 도입하여 모든 API의 응답 규격을 통일하고, 서비스 전역에서 발생하는 예외를 체계적으로 관리.
- **CORS & Secure Communication**: 분리된 프론트엔드 환경(Cross-Origin)에서의 안전한 데이터 통신을 위해 보안 필터 체인 및 CORS 정책 최적화.

## 🛠 Tech Stack
- **Framework**: `Spring Boot 3.x`
- **Language**: `Java 17`
- **Security**: `Spring Security`, `JWT`
- **Database**: `MySQL`
- **ORM**: `Spring Data JPA`
- **Build**: `Gradle`

## 🏗 System Architecture & Design
1. **JWT Custom Filter**: `OncePerRequestFilter`를 상속받아 모든 요청의 헤더에서 토큰을 검증하는 커스텀 필터 로직 구현.
2. **Layered Architecture**: Controller - Service - Repository 계층을 엄격히 분리하여 비즈니스 로직의 응집도를 높이고 유지보수성 향상.
3. **DTO Pattern**: 내부 Entity를 외부로 직접 노출하지 않고, API 스펙에 맞춘 DTO(Data Transfer Object)를 통해 데이터 보안 및 유연성 확보.

## 📈 Troubleshooting
- **이슈**: 프론트엔드에서 API 요청 시 JWT 토큰이 유실되거나 CORS 에러가 발생하는 현상.
- **해결**: Security Filter Chain에서 특정 허용 도메인을 명시하고, `ExposeHeaders` 설정을 통해 클라이언트가 Authorization 헤더를 읽을 수 있도록 조치하여 인증 흐름 정상화.
