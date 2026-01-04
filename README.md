# JustAuth OAuth Spring Boot Starter

A Spring Boot starter for OAuth authentication using JustAuth library. This starter provides a common Maven package that can be used as a dependency in other Java applications. It implements the Strategy design pattern to support multiple OAuth providers (Google OAuth 2.0 and Twitter OAuth 1.0) and uses Redis for token and state verification.

## Features

- ✅ **Strategy Design Pattern**: Clean architecture for OAuth provider implementations
- ✅ **Multiple OAuth Providers**: 
  - Google (OAuth 2.0)
  - Twitter (OAuth 1.0)
- ✅ **Redis Integration**: Token and state verification using Redis
- ✅ **Auto-Configuration**: Spring Boot auto-configuration for easy setup
- ✅ **RESTful API**: Ready-to-use REST endpoints for OAuth flow

## Requirements

- Java 8+
- Spring Boot 2.7.x
- Redis (for token/state storage)

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.github</groupId>
    <artifactId>justauth-oauth-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

Add the following configuration to your `application.yml` or `application.properties`:

### application.yml

```yaml
justauth:
  oauth:
    base-url: http://localhost:8080
    google:
      enabled: true
      client-id: YOUR_GOOGLE_CLIENT_ID
      client-secret: YOUR_GOOGLE_CLIENT_SECRET
      redirect-uri: http://localhost:8080/oauth/callback/google
    twitter:
      enabled: true
      client-id: YOUR_TWITTER_API_KEY
      client-secret: YOUR_TWITTER_API_SECRET
      redirect-uri: http://localhost:8080/oauth/callback/twitter

spring:
  redis:
    host: localhost
    port: 6379
    password: # Optional
    database: 0
```

### application.properties

```properties
justauth.oauth.base-url=http://localhost:8080
justauth.oauth.google.enabled=true
justauth.oauth.google.client-id=YOUR_GOOGLE_CLIENT_ID
justauth.oauth.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
justauth.oauth.google.redirect-uri=http://localhost:8080/oauth/callback/google

justauth.oauth.twitter.enabled=true
justauth.oauth.twitter.client-id=YOUR_TWITTER_API_KEY
justauth.oauth.twitter.client-secret=YOUR_TWITTER_API_SECRET
justauth.oauth.twitter.redirect-uri=http://localhost:8080/oauth/callback/twitter

spring.redis.host=localhost
spring.redis.port=6379
spring.redis.database=0
```

## Usage

### 1. Get Authorization URL

Initiate OAuth login by getting the authorization URL:

```bash
GET /oauth/authorize/{provider}
```

**Example:**
```bash
curl http://localhost:8080/oauth/authorize/google
```

**Response:**
```json
{
  "authorizationUrl": "https://accounts.google.com/o/oauth2/v2/auth?..."
}
```

### 2. Handle OAuth Callback

After user authorization, the OAuth provider will redirect to the callback URL:

```
GET /oauth/callback/{provider}?code=xxx&state=xxx
```

**Example:**
```bash
GET /oauth/callback/google?code=4/0AeanS...&state=abc123
```

**Response:**
```json
{
  "success": true,
  "user": {
    "uuid": "user-uuid",
    "username": "John Doe",
    "nickname": "johndoe",
    "avatar": "https://...",
    "email": "john@example.com",
    "token": {
      "accessToken": "ya29.a0AfH6SMC...",
      "refreshToken": "...",
      "expireIn": 3599
    }
  }
}
```

### 3. Verify Token

Verify an access token stored in Redis:

```bash
POST /oauth/verify/{provider}?userId={userId}&accessToken={token}
```

**Example:**
```bash
curl -X POST "http://localhost:8080/oauth/verify/google?userId=user-uuid&accessToken=ya29.a0AfH6SMC..."
```

**Response:**
```json
{
  "valid": true
}
```

### 4. Revoke Token

Revoke an access token:

```bash
POST /oauth/revoke/{provider}?userId={userId}&accessToken={token}
```

**Example:**
```bash
curl -X POST "http://localhost:8080/oauth/revoke/google?userId=user-uuid&accessToken=ya29.a0AfH6SMC..."
```

**Response:**
```json
{
  "revoked": true
}
```

## Architecture

### Strategy Pattern

The starter uses the Strategy design pattern to support multiple OAuth providers:

- `OAuthStrategy` - Interface defining OAuth operations
- `GoogleOAuthStrategy` - Google OAuth 2.0 implementation
- `TwitterOAuthStrategy` - Twitter OAuth 1.0 implementation

### Redis Integration

- **State Verification**: States are stored in Redis with 10-minute expiration to prevent CSRF attacks
- **Token Storage**: Access tokens are stored in Redis with 24-hour expiration
- **Token Verification**: Tokens can be verified against Redis storage

## Programmatic Usage

You can also use the `OAuthService` directly in your code:

```java
@Autowired
private OAuthService oAuthService;

// Get authorization URL
String authUrl = oAuthService.getAuthorizationUrl("google");

// Handle callback
AuthUser user = oAuthService.handleCallback("google", callback, state);

// Verify token
boolean isValid = oAuthService.verifyToken("google", userId, accessToken);
```

## Building from Source

```bash
mvn clean install
```

## License

This project is open source and available under the MIT License.
