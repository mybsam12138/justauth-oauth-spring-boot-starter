# justauth-oauth-spring-boot-starter

A **Spring Boot starter** that adds **first-class Spring Boot configuration and framework support** on top of **JustAuth**.

> **Primary Motivation**  
> JustAuth is a powerful OAuth library, but **it does not natively support Spring Boot configuration, auto-configuration, or framework-level extension**.  
>  
> This project exists to **bridge that gap**.  
> 
> Test it: Choose gitee provider in www.niudiantask.cn
---

## 🎯 Why This Starter Exists (The Real Reason)

**JustAuth focuses on OAuth capability**, but:

- ❌ No Spring Boot `application.yml` configuration binding
- ❌ No auto-configuration
- ❌ No provider lifecycle managed by Spring
- ❌ No framework-level extension model
- ❌ OAuth1 / OAuth2 logic mixed at usage level

As a result, in Spring Boot projects, developers often end up with:

- Manually constructed `AuthRequest` objects
- Provider logic scattered across services
- Conditional logic (`if / switch`) per provider
- Poor extensibility when adding new providers

### This starter solves that problem.

> **It turns JustAuth from a library into a Spring Boot–friendly framework.**

---

## 🧠 Design Philosophy

This project is designed as a **framework**, not a utility.

Key goals:

- Make JustAuth **configuration-driven**
- Integrate cleanly with **Spring Boot auto-configuration**
- Enforce correct OAuth flow by design
- Allow new providers to be added **without modifying existing code**

---

## 🏗️ Core Architecture

```
Spring Boot Configuration (application.yml)
        ↓
@ConfigurationProperties (OAuthProperties)
        ↓
Auto-Configuration
        ↓
OAuthStrategyFactory
        ↓
OAuthCommonService   ← Strategy abstraction
        ↑
AbstractOauthService        ← Template Method
        ↑
AbstractOauth1Service      AbstractOauth2Service
        ↑
Concrete Provider Services (Google / GitHub / ...)
```

---

## ⚙️ Spring Boot Configuration Support (Key Feature)

This starter introduces **native Spring Boot configuration support** for JustAuth.

```yaml
justauth:
  oauth:
    google:
      client-id: xxx
      client-secret: xxx
      redirect-uri: /oauth/callback/google
    github:
      client-id: xxx
      client-secret: xxx
      redirect-uri: /oauth/callback/github
```

### What this enables

- No manual `AuthRequest` construction
- No hard-coded provider configuration
- Environment-based configuration (dev / prod)
- Providers enabled or disabled via config

---

## 🔌 How Configuration Becomes Behavior

1. Configuration is bound via `@ConfigurationProperties`
2. Auto-configuration creates provider services
3. Each provider service receives **only its own config**
4. All providers are registered as Spring beans
5. `OAuthStrategyFactory` resolves the correct strategy at runtime

> **Configuration decides what exists,  
> Factory decides what is used.**

---

## 🧩 Strategy Pattern — Provider as Strategy

Each OAuth provider is implemented as a **strategy service**.

All providers implement:

```
OAuthCommonService
```

This allows:
- Uniform invocation
- Provider-specific behavior
- Runtime substitution

No controller or business code knows provider details.

---

## 🧬 Template Method — Enforcing OAuth Flow

OAuth flow structure is defined once in:

```
AbstractOauthService
```

The algorithm skeleton is fixed:

```
validateState
→ obtainAccessToken
→ obtainUserInfo
→ convertUser
```

Subclasses only implement **variable steps**.

This prevents incorrect OAuth implementations.

---

## 🧪 OAuth1 / OAuth2 Separation

Protocol differences are isolated into:

- `AbstractOauth1Service`
- `AbstractOauth2Service`

Concrete providers simply extend the correct template.

This avoids:
- Duplicated logic
- Protocol leakage
- Complex inheritance chains

---

## 🔐 OAuthStrategyFactory — Strategy Resolution

Provider selection is centralized in:

```
OAuthStrategyFactory
```

Responsibilities:
- Map provider → OAuth service
- Eliminate conditional logic
- Enforce Open/Closed Principle

---

## ♻️ Open/Closed Principle (OCP)

To add a new OAuth provider:

1. Add configuration
2. Create a new service
3. Extend the appropriate abstract template
4. Register as Spring bean

❌ No changes to:
- Controllers
- Factory
- Existing providers
- Framework core

---

## 🎯 Single Responsibility Principle (SRP)

| Component | Responsibility |
|--------|----------------|
Configuration Properties | Config binding |
Auto-Configuration | Bean wiring |
Factory | Strategy resolution |
Template | OAuth flow |
Protocol Template | OAuth1 / OAuth2 logic |
Concrete Service | Provider specifics |

Each class has **one reason to change**.

---

## 🚀 What This Starter Is Good For

- Spring Boot applications using JustAuth
- Multi-provider OAuth authentication
- Internal authentication frameworks
- Learning reference for framework design

---

## 🧠 What This Starter Is NOT

- ❌ A simple OAuth demo
- ❌ A JustAuth replacement
- ❌ A Spring Security alternative

It **extends JustAuth**, it does not replace it.

---

## 👤 Author

**Sam**  
Java / Spring Boot Engineer  
Focus: Framework Design · Spring Boot Starters · Clean Architecture

---

## 📄 License

MIT License
