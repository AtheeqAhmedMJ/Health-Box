# Production Deployment Checklist

Complete this checklist before going live.

## Security

- [ ] **Secrets Management**
  - [ ] All credentials in environment variables (not in code)
  - [ ] `.env` file not committed to git
  - [ ] Generate new JWT_SECRET: `openssl rand -base64 32`
  - [ ] Change default superadmin credentials
  - [ ] Rotate SMTP password to app-specific password
  - [ ] Razorpay keys marked as production

- [ ] **Database Security**
  - [ ] PostgreSQL password is strong (20+ chars, mixed case, symbols)
  - [ ] Database user is read-only except for app user
  - [ ] SSL/TLS enabled for database connections
  - [ ] Backup encryption enabled
  - [ ] No test data in production DB

- [ ] **Application Security**
  - [ ] HTTPS enforced (automatic on Render/Railway)
  - [ ] HSTS headers enabled
  - [ ] CORS restricted to your frontend domain only
  - [ ] Rate limiting enabled on auth endpoints
  - [ ] Input validation enabled (@Valid on all DTOs)
  - [ ] SQL injection protection (JPA parameterized queries)
  - [ ] No debug logs in production
  - [ ] Error messages don't expose internals

- [ ] **API Security**
  - [ ] JWT tokens short-lived (15 min)
  - [ ] Refresh tokens rotated
  - [ ] Token revocation on logout implemented
  - [ ] API endpoints require authentication
  - [ ] Tenant isolation verified with cross-tenant tests

## Infrastructure

- [ ] **Database**
  - [ ] Managed PostgreSQL (Render/Railway/Neon/AWS RDS)
  - [ ] Automated daily backups enabled
  - [ ] Backup retention: minimum 30 days
  - [ ] Point-in-time restore tested
  - [ ] Database monitoring/alerts configured
  - [ ] Connection limits tuned for production load
  - [ ] Indexes created on frequently queried columns

- [ ] **Application Server**
  - [ ] Spring Boot memory limits: -Xms256m -Xmx1g
  - [ ] Tomcat thread pool: 200 max connections
  - [ ] Gzip compression enabled
  - [ ] Keep-alive connections configured
  - [ ] Request timeout: 30 seconds
  - [ ] Max request size: 10MB

- [ ] **Frontend CDN**
  - [ ] Static assets cached (1 year expiry)
  - [ ] Gzip compression enabled
  - [ ] Cache-busting on CSS/JS via hash names
  - [ ] Security headers set (CSP, X-Frame-Options)

## Monitoring & Logging

- [ ] **Application Logs**
  - [ ] Centralized logging service configured (Sentry/Datadog)
  - [ ] Error alerts setup (Slack/email)
  - [ ] Log retention: 30+ days
  - [ ] Sensitive data excluded from logs (passwords, tokens)
  - [ ] Production log level: INFO (not DEBUG)

- [ ] **Performance Monitoring**
  - [ ] APM service configured (New Relic/DataDog)
  - [ ] Database query performance monitored
  - [ ] API endpoint latency tracked
  - [ ] Error rate alerts (>1% threshold)
  - [ ] Memory/CPU alerts configured

- [ ] **Uptime Monitoring**
  - [ ] Uptime monitoring service active (UptimeRobot/Pingdom)
  - [ ] Health check endpoint: /actuator/health
  - [ ] Alert on downtime >5 minutes
  - [ ] SLA tracking enabled

- [ ] **Backup Monitoring**
  - [ ] Backup completion alerts configured
  - [ ] Restore tests scheduled (monthly)
  - [ ] Backup size tracking

## Database

- [ ] **Schema**
  - [ ] All Flyway migrations applied successfully
  - [ ] Database version matches application version
  - [ ] Indexes on tenant_id, patient.phone, appointments.status
  - [ ] Foreign key constraints enabled
  - [ ] No orphaned data

- [ ] **Data**
  - [ ] Test data removed from production
  - [ ] Super admin account secured and not used for daily ops
  - [ ] First backup completed and verified

## Application Deployment

- [ ] **Backend**
  - [ ] Dockerfile builds without errors
  - [ ] JAR size reasonable (<200MB)
  - [ ] Application starts in <30 seconds
  - [ ] Readiness probe responds within 5s
  - [ ] Liveness probe responds within 3s
  - [ ] Graceful shutdown configured (30s timeout)
  - [ ] No hardcoded config paths

- [ ] **Frontend**
  - [ ] Build completes with no warnings
  - [ ] Bundle size <500KB (gzipped)
  - [ ] All environment variables injected at runtime
  - [ ] Single Page App routing configured (SPA fallback)
  - [ ] Robots.txt and sitemap.xml present

## Testing

- [ ] **Functional Testing**
  - [ ] Hospital registration → Admin login → Appointment → Payment flow works
  - [ ] Cross-tenant isolation verified (tenant A cannot see tenant B data)
  - [ ] Role-based access control tested (doctor/admin/patient permissions)
  - [ ] All API endpoints respond with correct status codes

- [ ] **Security Testing**
  - [ ] SQL injection test attempted (parameterized queries block it)
  - [ ] XSS prevention verified (no inline scripts)
  - [ ] CSRF protection enabled
  - [ ] Unauthenticated requests rejected (401)
  - [ ] Token expiration forces re-login

- [ ] **Performance Testing**
  - [ ] Load test: 100 concurrent users
  - [ ] Response time: <2s for 95th percentile
  - [ ] Database connection pool never exhausted
  - [ ] Memory leak test: 24-hour soak test passes

## Configuration

- [ ] **Environment Variables**
  - [ ] DATABASE_URL set correctly
  - [ ] DATABASE_USERNAME/PASSWORD strong
  - [ ] JWT_SECRET set (not default)
  - [ ] FRONTEND_URL matches exact origin (http/https, domain, port)
  - [ ] SMTP credentials configured (or disabled)
  - [ ] RAZORPAY keys set to production (or mock mode)
  - [ ] PORT not conflicting (8080 for backend, 80/443 for frontend)

- [ ] **CORS**
  - [ ] Allowed origins list reviewed
  - [ ] Credentials included if needed
  - [ ] Preflight requests cached (1 hour)

- [ ] **SSL/TLS**
  - [ ] HTTPS enforced (redirect HTTP → HTTPS)
  - [ ] Certificate valid and not self-signed
  - [ ] Certificate renewal automated (Let's Encrypt)
  - [ ] TLS 1.2+ enforced

## Documentation

- [ ] **Runbooks**
  - [ ] How to deploy new version
  - [ ] How to rollback on failure
  - [ ] How to scale horizontally
  - [ ] How to migrate database
  - [ ] How to backup/restore

- [ ] **Architecture**
  - [ ] System design document current
  - [ ] API documentation (Swagger/OpenAPI)
  - [ ] Database schema documented
  - [ ] Deployment topology documented

- [ ] **Team Knowledge**
  - [ ] On-call runbook available
  - [ ] Team trained on alert response
  - [ ] Incident response procedure documented

## Compliance & Privacy

- [ ] **Data Protection**
  - [ ] GDPR readiness (if EU users)
  - [ ] Data classification completed (PII, PHI)
  - [ ] Encryption at rest configured (database)
  - [ ] Encryption in transit configured (TLS)
  - [ ] Data retention policy set

- [ ] **Audit**
  - [ ] Charge edit audit trail logged
  - [ ] Payment verification logged
  - [ ] User login/logout logged
  - [ ] Sensitive field changes logged
  - [ ] Audit logs encrypted and immutable

- [ ] **Privacy Policy**
  - [ ] Published and accessible
  - [ ] Terms of service agreed by users
  - [ ] Cookie consent implemented (if applicable)

## Post-Deployment

- [ ] **Smoke Tests** (First 1 hour)
  - [ ] Login endpoint working
  - [ ] Dashboard loading
  - [ ] API responding to requests
  - [ ] Logs showing no errors

- [ ] **Monitoring** (First 24 hours)
  - [ ] Error rate within baseline
  - [ ] Response times acceptable
  - [ ] Database performance normal
  - [ ] No unusual traffic patterns

- [ ] **Stakeholder Communication**
  - [ ] Users notified of deployment
  - [ ] Support team briefed
  - [ ] Incident contact provided
  - [ ] Known issues documented

## Maintenance

- [ ] **Regular Tasks**
  - [ ] Weekly: Review error logs
  - [ ] Weekly: Check backup completion
  - [ ] Monthly: Performance review
  - [ ] Monthly: Security patch assessment
  - [ ] Quarterly: Disaster recovery drill

- [ ] **Updates**
  - [ ] Spring Boot security updates subscribed
  - [ ] PostgreSQL updates monitored
  - [ ] Dependencies updated quarterly
  - [ ] CVE scanning enabled

---

**Sign-off:**

- **Deployed by:** ___________________  **Date:** ______________
- **Reviewed by:** ___________________  **Date:** ______________
- **Approved by:** ___________________  **Date:** ______________

---

Use this checklist for every production deployment. Fix any failures before going live.
