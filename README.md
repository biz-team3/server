# server

biz-team3 백엔드 서버 기본 베이스입니다.

## Stack

- Java 21
- Spring Boot 4.0.5
- Oracle DB
- MyBatis
- Gradle
- YAML 기반 설정

## Environment

로컬 실행은 별도 `.env` 없이 `application-local.yml` 기본값을 사용합니다.

```yaml
url: jdbc:oracle:thin:@localhost:1521/XEPDB1
username: biz_team3
password: biz_team3
```

`.env`는 운영 또는 개인별 override가 필요할 때만 사용합니다. 실제 `.env`는 git에 올리지 않습니다.

## Docker

로컬 Oracle 컨테이너는 `application-local.yml`과 같은 조건으로 실행합니다.

```bash
docker compose up -d
```

Windows PowerShell:

```powershell
docker compose up -d
```

```text
JDBC URL : jdbc:oracle:thin:@localhost:1521/XEPDB1
Username : biz_team3
Password : biz_team3
```

## DB 초기화

Spring Boot가 `src/main/resources/db/*.sql`을 자동 실행하지 않는다. 로컬 DB를 초기화할 때는 테이블 생성 SQL을 먼저 적용한 뒤 `src/main/resources/db/mock/init.sql`을 실행한다.

알림 기능까지 테스트하려면 `notifications_init_1.sql`도 테이블 생성 순서에 포함되어야 하며, mock seed는 최신 user/post/media seed가 먼저 적용된 상태를 기준으로 한다.

## Run

```bash
./gradlew bootRun
```

Windows PowerShell:

```powershell
.\gradlew.bat bootRun
```

Prod profile:

```bash
SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun
```

Windows PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE="prod"; .\gradlew.bat bootRun
```

## Config

- 기본 설정: `src/main/resources/application.yml`
- 로컬 프로필: `src/main/resources/application-local.yml`
- 운영 프로필: `src/main/resources/application-prod.yml`
- MyBatis config 위치: `src/main/resources/mybatis/config/mybatis-config.xml`
- MyBatis mapper XML 위치: `src/main/resources/mybatis/mapper/*.xml`
- Mapper interface 패키지 기준: `com.bizteam3.server.**.mapper`
