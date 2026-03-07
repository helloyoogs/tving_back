# ⚙️ TVING Clone Project (Backend)
> **Spring Boot 3.x 기반의 고가용성 OTT 서비스 API 서버 구축 및 보안 아키텍처 설계**

[![Spring Boot](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)](https://spring.io/projects/spring-security)

---

## 📖 1. 프로젝트 소개
*   **개요**: OTT 서비스의 핵심인 대규모 콘텐츠 관리와 안전한 사용자 인증을 제공하는 RESTful API 서버입니다.
*   **핵심 목표**: 확장 가능한 Stateless 인증 시스템 구축, 복잡한 데이터 관계 매핑, 전역 예외 처리를 통한 시스템 안정성 확보.

## 🛠 2. Tech Stack & Decision Making (The Why)
*   **Java 17 & Spring Boot 3.x**: 최신 자바 문법을 활용하여 코드 가독성을 높이고 스프링의 자동 설정을 통해 비즈니스 로직에 집중했습니다.
*   **Spring Security & JWT**: 서버의 세션 유지 비용을 없애기 위해 **Stateless 인증 구조**를 설계하여 향후 서버 수평 확장(Scale-out)이 용이하도록 했습니다.
*   **Spring Data JPA**: 사용자, 구독 등급, 콘텐츠 간의 복잡한 **N:M(다대다) 연관 관계**를 객체 지향적으로 매핑하여 데이터 무결성을 보장했습니다.

## 🏗 3. System Architecture & Design
*   **Layered Architecture**: Controller-Service-Repository 계층을 엄격히 분리하여 관심사를 분리하고 유지보수성을 높였습니다.
*   **DTO Pattern**: 내부 Entity를 직접 노출하지 않고 API 스펙에 맞춘 DTO를 사용하여 데이터 보안과 유연성을 확보했습니다.
*   **Global Exception Handling**: `@RestControllerAdvice`를 활용하여 서비스 전역에서 발생하는 예외를 체계적으로 관리하고 응답 규격을 통일했습니다.

## 🔥 4. Troubleshooting (의도적 고군분투)
**이슈: 프론트엔드 환경(Cross-Origin)에서 API 요청 시 JWT 토큰 인식 불가 및 CORS 에러**
*   **원인 분석**: Security Filter Chain에서 외부 도메인의 접근 정책이 엄격하게 설정되어 있었으며, 클라이언트가 보안 헤더(`Authorization`)에 접근하지 못하도록 막혀 있음을 확인.
*   **해결 방안**: 
    1. 보안 필터 체인에 CORS 정책을 최적화하여 특정 도메인 허용.
    2. `ExposeHeaders` 설정을 통해 클라이언트가 헤더를 읽을 수 있도록 조치.
*   **결과**: 프론트엔드-백엔드 간 안정적인 인증 흐름 구축 완료.

## 🗃 5. 데이터베이스 설계 (ERD)
> `dbdiagram.io`를 활용하여 테이블 간 관계를 시각화했습니다.
*   **설계 원칙**: 데이터 무결성을 위한 정규화 준수 및 효율적인 데이터 접근을 고려한 인덱스 설계.

## 🏃 6. 시작 가이드 (Quick Start)
```bash
git clone https://github.com/helloyoogs/tving_back.git
# application.yml 설정을 확인하세요 (DB 연결 정보 등)
./gradlew bootRun
