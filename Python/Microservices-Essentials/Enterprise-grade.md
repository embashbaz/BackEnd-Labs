# **Enterprise-grade URL shortener microservice**

**Modules covered:** 5, 6, 7, 8, and 9 | **Framework:** Django 5.0+ with DRF | **Timeline:** 15 working days

---

## **Project overview**

You will build a production-grade URL shortening service similar to Bitly or TinyURL, designed as a highly scalable microservice. Starting from a clean Dockerized foundation, you will progressively evolve it into a complex system handling relational data, advanced security, high-performance background tasks, and distributed service communication.

**Core objectives by module:**

| **Module** | **Focus area** |
| --- | --- |
| 5 | Foundational architecture — Django project structure, Docker, RESTful API standards |
| 6 | Data engineering — complex schemas, query optimization, Django ORM mastery |
| 7 | Security and access control — JWT authentication, Role-Based Access Control (RBAC) |
| 8 | Performance optimization — Redis caching, Celery async background processing |
| 9 | Microservices patterns — API Gateway patterns, resilient HTTP communication, service decoupling |

---

## **Database schema (Module 6 focus)**

#### **A. User model**

Extends Django's AbstractUser.

| **Field** | **Type** | **Notes** |
| --- | --- | --- |
| email | EmailField | Unique, required |
| is_premium | BooleanField | Default: False — triggers premium logic |
| tier | CharField | Choices: Free, Premium, Admin |

#### **B. URL model**

Stores the mapping between short codes and original long URLs.

| **Field** | **Type** | **Notes** |
| --- | --- | --- |
| original_url | URLField | Must validate standard URL formats |
| short_code | CharField | Unique, indexed, max length 10 — crucial for lookup performance |
| custom_alias | CharField | Nullable, unique — allows premium users to set vanity URLs |
| owner | ForeignKey (User) | ON DELETE CASCADE |
| is_active | BooleanField | Default: True |
| expires_at | DateTimeField | Nullable |
| title | CharField | Nullable, unique |
| description | CharField | Nullable, unique |
| favicon | CharField | Nullable, unique |
| click_count | PositiveIntegerField | Default: 0 — denormalized for quick read access |

#### **C. Click (analytics) model**

Logs every visit to a short link for analytics.

| **Field** | **Type** | **Notes** |
| --- | --- | --- |
| url | ForeignKey (URL) | ON DELETE CASCADE |
| clicked_at | DateTimeField | Auto Now Add |
| ip_address | GenericIPAddressField |  |
| city | CharField | Nullable |
| country | CharField | Nullable |
| user_agent | TextField | Stores browser and OS info |
| referrer | URLField | Nullable — tracks where the click came from |

#### **D. Tag model**

Categorization for URLs. Field: name (CharField, unique). Relationship: Many-to-Many with the URL model.

---

## **Technical implementation by module**


## **Module 5: Fundamentals, architecture, and containerization**

---

**Goal:** Establish the project foundation, Docker environment, and a Minimum Viable Product (MVP).

In this phase the focus is purely on infrastructure and core logic — no users or analytics yet. You will create a clean application that accepts a long URL, generates a unique short code, and redirects users, then containerize it so it runs identically on every machine.

### **Project structure:**

- Initialize a Django project using a modular structure (separate apps for core, shortener, and api)

- Use python-decouple or django-environ to separate settings (SECRET_KEY, DEBUG, DATABASE_URL) from code

**Core logic (MVP):**

- Create a basic URL model with original_url and short_code

- Implement a helper function to generate random 6-character alphanumeric strings for short_code

**REST API (DRF):**

- POST /api/urls/ — accept a URL and return the short code

- GET /<short_code>/ — redirect to the original URL (HTTP 302)

- Use DRF serializers for strict data validation

**Containerization:**

- Write a multi-stage Dockerfile to build a lightweight Python image

- Create a docker-compose.yml that spins up the Django web service and a PostgreSQL database

**Documentation:** Integrate drf-yasg or drf-spectacular to auto-generate Swagger/OpenAPI docs.


## **Module 6: ORM and data access layer**

---

**Goal:** Expand the data model to support users, relationships, and deep analytics.

Now that the core works, you will make it smart by adding user ownership, tagging, and analytics. The focus is on fetching data efficiently without overloading the database.

 **Advanced modeling:**

- Add is_premium and tier to User; add owner, click_count, is_active, and expires_at to URL

- Create the Click model to log every visit with ip_address, user_agent, country, city, and referrer

- Implement Many-to-Many Tag relationships (e.g., "Marketing", "Social")

 **Database migrations:**

- Manage schema changes using Django migrations

- Create a data migration to seed initial default tags

**Custom managers and QuerySets:** Implement URLManager with methods active_urls(), expired_urls(), and popular_urls().

 **Query optimization:**

- N+1 problem: use select_related for URLs with owners; use prefetch_related for URLs with tags

- Add database indexes to short_code and created_at

- Use annotate() for complex stats (e.g., total clicks per country) — calculate in the database, not Python


## **Module 7: Authentication and authorization**

---

**Goal:** Secure the application and implement business rules based on user roles.

You will lock down the API so only registered users can create links, and implement a tiered business model.

**JWT authentication:**

- Install djangorestframework-simplejwt

- Implement Login, Register, and Token Refresh endpoints

**Role-Based Access Control (RBAC):**

- Custom permission class IsOwnerOrReadOnly — users cannot edit or delete other people's links

- Tiered logic:

| **Tier** | **Capabilities** |
| --- | --- |
| Free | Max 10 active URLs, no custom aliases |
| Premium | Unlimited URLs, custom aliases, detailed analytics access |

**Security best practices:**

- Rate limiting on the Login endpoint (e.g., 5 attempts per minute)

- Secure password storage (Django defaults) and sanitized URL inputs
- 

## **Module 8: Advanced optimization and production readiness**

---

**Goal:** Prepare the system for high traffic using caching and background workers.

In a real URL shortener, writing to the database on every click is too slow. This module introduces async tasks for background analytics and caching for instant redirects.

**Redis caching:**

- Redirect strategy: GET /<short_code>/ checks Redis first; if found, redirect immediately; if not, fetch from DB and cache it

- Cache invalidation: delete the cache key immediately when a URL is updated

**Asynchronous tasks (Celery):**

- Write-behind pattern: when a link is clicked, trigger track_click_task.delay(url_id, ip_address, user_agent) instead of writing to the Click model in the view

- Periodic tasks (Celery Beat): configure a nightly task to clean up or archive expired URLs

**Logging and monitoring:**

- Structured logging (JSON format preferred) for all 500 errors and security warnings

- /health endpoint to check if DB and Redis are reachable
  

## **Module 9: Microservices essentials**

---

**Goal:** Simulate a distributed architecture and external service integration.

You will implement a URL Preview feature that fetches the title, description, and favicon of the destination website.

**External service integration:**

- When a user submits a URL to shorten, fetch the title, description, and favicon of the destination page and store them in the URL model

- Create a separate minimal Django app representing a "URL Preview" external service

- Inter-service communication: the main app sends an HTTP request (using httpx or requests) to the Preview Service, then stores the result

**Resiliency patterns:**

- Retries: if the target website is down, fail gracefully or retry with exponential backoff

- Circuit breaker (bonus): if a domain keeps failing, stop retrying for a period of time

**API Gateway concepts:**

- Standardize API versioning at /api/v1/...

- Document CORS header setup for frontend interaction (e.g., React)

---

## **API endpoint specifications**

#### **Authentication**

| **Method** | **Endpoint** | **Description** |
| --- | --- | --- |
| POST | /api/v1/auth/register/ | Create new account |
| POST | /api/v1/auth/login/ | Returns Access and Refresh JWT |
| POST | /api/v1/auth/refresh/ | Get new access token |

#### **URL operations**

| **Method** | **Endpoint** | **Description** |
| --- | --- | --- |
| POST | /api/v1/urls/ | Create a short link (check rate limits and quotas) |
| GET | /api/v1/urls/ | List my URLs (supports pagination, search by tag) |
| GET | /api/v1/urls/{short_code}/ | Retrieve details of a specific URL |
| PUT | /api/v1/urls/{short_code}/ | Update target URL (reset click count optional) |
| DELETE | /api/v1/urls/{short_code}/ | Deactivate or delete URL |

#### **Public interface**

| **Method** | **Endpoint** | **Description** |
| --- | --- | --- |
| GET | /{short_code}/ | Redirection endpoint — returns 302 Found or 301 Moved Permanently; triggers async analytics task and Redis cache lookup |

#### **Analytics**

| **Method** | **Endpoint** | **Description** |
| --- | --- | --- |
| GET | /api/v1/analytics/{short_code}/ | Detailed stats for one URL — Premium only; returns time-series data and geo-location breakdown |

---

## **Project roadmap and milestones**

| **Phase** | **Days** | **Module** | **Key deliverables** |
| --- | --- | --- | --- |
| Phase 1: Foundation | Days 1–3 | Module 5 | Docker setup, Django project init, basic create and redirect endpoints, Swagger docs |
| Phase 2: Data logic | Days 4–6 | Module 6 | User and Click models, relationships, migrations, custom managers, N+1 optimization |
| Phase 3: Security | Days 7–9 | Module 7 | JWT setup, custom permissions, Free vs. Premium logic, rate limiting |
| Phase 4: Scale | Days 10–12 | Module 8 | Redis cache for redirects, Celery setup, async click tracking |
| Phase 5: Integration | Days 13–15 | Module 9 | External HTTP calls (title fetch), final QA, documentation, demo video |

---

## **Technical stack requirements**

| **Category** | **Technology** |
| --- | --- |
| Language | Python 3.11+ |
| Framework | Django 5.0+ and Django REST Framework |
| Database | PostgreSQL 15+ (psycopg2-binary) |
| Auth | djangorestframework-simplejwt |
| Caching | Redis 7+ (django-redis) |
| Async tasks | Celery 5+ (with Redis as broker) |
| Containerization | Docker and Docker Compose |
| Documentation | drf-yasg or drf-spectacular |
| WSGI server | Gunicorn |

---

## **Submission guidelines**

- **GitHub repository** — must include a clear .gitignore, requirements.txt, and atomic commits

- **README.md** — must include:

  - How to run the project (Docker commands)

  - Architecture diagram

  - List of all endpoints
