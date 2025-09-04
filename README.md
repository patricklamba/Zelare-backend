# Zelare

A premium home cleaning service platform for Angola, connecting verified homeowners with trusted cleaning professionals through a secure and elegant mobile experience.

## Overview

Zelare is a full-stack application consisting of a React Native mobile app and a robust Spring Boot backend, designed for the Angolan market with a focus on privacy, security, and premium service delivery.

### Target Market
- **High-end clientele**: Politicians, executives, and affluent individuals
- **Verified cleaners**: Professional cleaning staff vetted through agency registration
- **Agency-managed**: Central office handles cleaner onboarding for quality assurance

## Architecture

### Frontend (React Native + Expo)
- **Mobile App**: iOS and Android native experience
- **Web Dashboard**: Admin interface for agency management (planned)
- **Authentication**: OTP-based phone verification
- **Real-time**: Messaging and notifications

### Backend (Spring Boot + PostgreSQL)
- **RESTful API**: Comprehensive endpoints for all operations
- **JWT Authentication**: Secure token-based auth
- **SMS Integration**: OTP delivery via Twilio
- **Database**: PostgreSQL with JPA/Hibernate
- **Testing**: Unit and integration tests with 85%+ coverage
- **Security**: SQL injection protection, XSS prevention, CORS configured

## Features

### For Employers (Homeowners)
- **Verified Account Registration**: Manual approval process for privacy
- **Premium Cleaner Browsing**: View profiles, ratings, and availability
- **Flexible Booking System**: Schedule cleaning services with preferences
- **Secure Communication**: In-app messaging with cleaners
- **Credit-based Payments**: Pre-loaded credits for seamless transactions
- **VIP Tiers**: Standard, Gold, Platinum, Diamond service levels
- **Service History**: Complete tracking of past cleanings

### For Cleaners
- **Agency Registration**: Must register through central office
- **Professional Profiles**: Showcase experience, skills, and availability
- **Service Management**: Accept/decline booking requests
- **Real-time Availability**: Update working status instantly
- **Earnings Tracking**: Monitor completed jobs and payments
- **Rating System**: Build reputation through client feedback

### For Agency (Web Dashboard - Planned)
- **Cleaner Onboarding**: Register and verify cleaning professionals
- **Client Management**: Handle VIP account approvals
- **Quality Control**: Monitor ratings, reviews, and service quality
- **Analytics Dashboard**: Business metrics and performance tracking
- **Dispute Resolution**: Handle conflicts between parties

## Tech Stack

### Frontend
- **Framework**: React Native with Expo
- **Navigation**: Expo Router
- **State Management**: React Context + Hooks
- **Styling**: React Native StyleSheet
- **Icons**: Lucide React Native
- **HTTP Client**: Custom API service layer
- **Platform Support**: iOS, Android, Web

### Backend
- **Framework**: Spring Boot 3.5.5
- **Security**: Spring Security 6 + JWT
- **Database**: PostgreSQL 15+
- **ORM**: JPA/Hibernate
- **SMS Service**: Twilio integration
- **Testing**: JUnit 5, Mockito, Testcontainers
- **Build Tool**: Maven
- **Java Version**: 17+

### Infrastructure
- **Database**: PostgreSQL (local development, cloud production)
- **SMS Provider**: Twilio
- **File Storage**: Local/Cloud storage for profile pictures
- **Monitoring**: Spring Boot Actuator

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL 12+
- Node.js 18+ (for mobile app)
- Twilio account (for SMS)

### Backend Setup

1. **Clone and setup database**:
```bash
git clone <repository-url>
cd zelare-backend

# Setup PostgreSQL
createdb zelare_db
createuser zelare_user --createdb
```

2. **Configure environment**:
```bash
cp .env.example .env
# Edit .env with your database and Twilio credentials
```

3. **Run the backend**:
```bash
mvn spring-boot:run
```

Backend will be available at `http://localhost:8080`

### Frontend Setup

1. **Setup React Native app**:
```bash
cd zelare-frontend
npm install
npx expo start
```

2. **Demo Credentials**:
- **Employer**: Use phone ending in even digit (e.g., `+244912345672`)
- **Cleaner**: Use phone ending in odd digit (e.g., `+244912345671`)
- **OTP**: Any 6-digit code works in development

## API Documentation

### Authentication Endpoints
```
POST /api/auth/register        # User registration
POST /api/auth/send-otp        # Send OTP code
POST /api/auth/verify-otp      # Verify OTP and login
POST /api/auth/logout          # User logout
```

### User Management
```
GET  /api/user/profile         # Get current user profile
PUT  /api/user/profile         # Update user profile
```

### Cleaner Endpoints
```
GET  /api/cleaner/available    # List available cleaners
GET  /api/cleaner/profile      # Get cleaner profile (auth required)
PUT  /api/cleaner/availability # Update availability status
```

### Booking System (Planned)
```
POST /api/bookings             # Create new booking
GET  /api/bookings             # List user bookings
PUT  /api/bookings/{id}        # Update booking status
```

## Project Structure

### Backend Structure
```
zelare-backend/
├── src/main/java/com/zelare/backend/
│   ├── controller/           # REST controllers
│   ├── service/             # Business logic
│   ├── entity/              # JPA entities
│   ├── repository/          # Data access layer
│   ├── dto/                 # Data transfer objects
│   ├── config/              # Configuration classes
│   └── exception/           # Exception handlers
├── src/test/java/           # Unit and integration tests
├── src/main/resources/
│   ├── application.yml      # Main configuration
│   └── application-*.yml    # Environment configs
└── pom.xml                  # Maven dependencies
```

### Frontend Structure
```
zelare-frontend/
├── app/
│   ├── (cleaner)/          # Cleaner-specific screens
│   ├── (employer)/         # Employer-specific screens
│   ├── auth/               # Authentication screens
│   ├── _layout.tsx         # Root layout
│   └── index.tsx           # Landing page
├── contexts/               # React contexts (Auth, etc.)
├── lib/                    # API client and utilities
├── components/             # Reusable UI components
├── data/                   # Mock data and types
└── types/                  # TypeScript definitions
```

## Testing

### Backend Tests
```bash
# Run unit tests only
mvn test -Punit-tests

# Run integration tests with Testcontainers
mvn verify -Pintegration-tests

# Run all tests with coverage report
mvn verify -Pcoverage
```

### Test Coverage
- **Services**: 95% line coverage
- **Controllers**: 85% line coverage
- **Repositories**: 80% line coverage
- **Overall**: 85%+ target coverage

### Frontend Tests
```bash
# Run React Native tests
npm test

# Run with coverage
npm run test:coverage
```

## Security Features

- **JWT Authentication**: Stateless token-based security
- **OTP Verification**: Phone-based two-factor authentication
- **Role-based Access**: Cleaner/Employer/Admin permissions
- **SQL Injection Protection**: Parameterized queries
- **XSS Prevention**: Input validation and sanitization
- **CORS Configuration**: Proper cross-origin setup
- **Rate Limiting**: API request throttling
- **Data Encryption**: Sensitive data protection

## Deployment

### Development
- Backend runs on `localhost:8080`
- PostgreSQL on `localhost:5432`
- React Native on Expo development server

### Production (Planned)
- **Backend**: Docker containerization
- **Database**: Managed PostgreSQL service
- **Mobile**: App Store/Play Store distribution
- **Web Dashboard**: Static hosting with API integration

## Environment Configuration

### Required Environment Variables
```env
# Database
DB_USERNAME=zelare_user
DB_PASSWORD=your_password
DB_HOST=localhost
DB_PORT=5432

# JWT
JWT_SECRET=your-super-secret-key

# SMS (Twilio)
TWILIO_ACCOUNT_SID=your_account_sid
TWILIO_AUTH_TOKEN=your_auth_token
TWILIO_PHONE_NUMBER=your_twilio_number

# App
SPRING_PROFILES_ACTIVE=dev
```

## Contributing

### Development Workflow
1. Fork the repository
2. Create feature branch: `git checkout -b feature/amazing-feature`
3. Run tests: `mvn verify -Pcoverage`
4. Commit changes: `git commit -m 'Add amazing feature'`
5. Push branch: `git push origin feature/amazing-feature`
6. Create Pull Request

### Code Standards
- **Java**: Follow Google Java Style Guide
- **TypeScript**: ESLint + Prettier configuration
- **Tests**: Maintain 85%+ coverage
- **Documentation**: Update README for new features

## Business Model

### Revenue Streams
- **Commission**: Percentage of each booking
- **Premium Subscriptions**: Enhanced features for cleaners
- **VIP Services**: Premium support for high-end clients
- **Agency Fees**: Registration and verification services

### Market Positioning
- **Premium Quality**: Vetted professionals only
- **Privacy First**: Secure handling of sensitive client data
- **Local Focus**: Tailored for Angolan market needs
- **Agency Supported**: Professional onboarding and support

## License

This project is proprietary software. All rights reserved.

## Support

For technical issues or business inquiries:
- **Email**: support@zelare.ao
- **Phone**: +244 XXX XXX XXX
- **Office**: Luanda, Angola

## Version History

- **v0.1.0** (Current): MVP with basic authentication and cleaner browsing
- **v0.2.0** (Planned): Full booking system and payments
- **v0.3.0** (Planned): Web dashboard and advanced features
- **v1.0.0** (Planned): Production release

---

**Zelare** - Premium Home Cleaning Services for Angola
