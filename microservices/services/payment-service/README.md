# Payment Service - Mock Gateway Implementation

## Overview
The Payment Service is a dedicated microservice for processing flight ticket payments using a mock payment gateway for testing purposes. It provides real-time payment processing, transaction tracking, and refund management.

## Features

### Payment Processing
- **Real-time payment initiation** - Process payments immediately
- **Transaction tracking** - Store and retrieve payment records
- **Multiple payment methods** - Credit Card, Debit Card, UPI, Net Banking, Wallet, EMI
- **Mock payment gateway** - Test different scenarios with test cards

### Payment Management
- **Payment status tracking** - PENDING, SUCCESS, FAILED, CANCELLED, REFUNDED
- **Refund processing** - Full or partial refunds
- **Payment history** - User and booking-level transaction history
- **Error handling** - Comprehensive error messages and validation

## Project Structure

```
payment-service/
├── pom.xml                          # Maven configuration
├── src/main/java/com/nara/
│   ├── PaymentServiceApplication.java
│   ├── controller/
│   │   └── PaymentController.java   # REST endpoints
│   ├── service/
│   │   └── PaymentService.java      # Business logic
│   ├── model/
│   │   └── Payment.java             # JPA entity
│   ├── repository/
│   │   └── PaymentRepository.java   # Data access
│   ├── gateway/
│   │   ├── PaymentGateway.java      # Gateway interface
│   │   ├── PaymentGatewayResponse.java
│   │   └── MockPaymentGateway.java  # Mock implementation
│   └── exception/
│       └── GlobalExceptionHandler.java
├── src/main/resources/
│   └── application.yml              # Configuration
└── src/test/java/com/nara/
    ├── PaymentServiceTest.java
    └── MockPaymentGatewayTest.java
```

## REST API Endpoints

### 1. Initiate Payment
**POST** `/api/v1/payments/initiate`

Request:
```json
{
  "bookingId": 1,
  "userId": 1,
  "amount": 5000.00,
  "currency": "INR",
  "paymentMethod": "CREDIT_CARD",
  "cardNumber": "4111111111111111",
  "cardholderName": "John Doe",
  "expiryDate": "12/25",
  "cvv": "123",
  "description": "Flight booking payment"
}
```

Response (Success):
```json
{
  "paymentId": 1,
  "bookingId": 1,
  "userId": 1,
  "amount": 5000.00,
  "currency": "INR",
  "status": "SUCCESS",
  "paymentMethod": "CREDIT_CARD",
  "transactionId": "txn-xxxxx",
  "maskedCardNumber": "**** **** **** 1111",
  "createdAt": "2026-06-07T20:30:00",
  "updatedAt": "2026-06-07T20:30:00",
  "message": "Payment processed successfully"
}
```

### 2. Get Payment Details
**GET** `/api/v1/payments/{id}`
- Returns payment details by payment ID

### 3. Get Payment by Transaction ID
**GET** `/api/v1/payments/transaction/{transactionId}`
- Returns payment details by transaction ID

### 4. Get Payments for Booking
**GET** `/api/v1/payments/booking/{bookingId}`
- Returns all payments associated with a booking

### 5. Get User Payment History
**GET** `/api/v1/payments/user/{userId}`
- Returns all payments made by a user (newest first)

### 6. Process Refund
**POST** `/api/v1/payments/{id}/refund?amount=2500.00`
- Processes full or partial refund
- Only works on SUCCESS payments

### 7. Get Payments by Status
**GET** `/api/v1/payments/status/{status}`
- Returns all payments with specific status
- Status: PENDING, SUCCESS, FAILED, CANCELLED, REFUNDED

### 8. Health Check
**GET** `/api/v1/payments/health`
- Service health check endpoint

## Test Cards for Mock Gateway

| Card Number | Description | Behavior |
|-------------|-------------|----------|
| 4111111111111111 | VISA Test Success | Always succeeds (100%) |
| 4000000000000002 | VISA Test Failure | Always fails (0%) |
| 5555555555554444 | MasterCard Test | 70% success rate |
| 378282246310005 | AMEX Test | Random success/failure (50%) |
| Any other valid | Generic Test | 80% success rate |

## Database Schema

### Payments Table
```sql
CREATE TABLE payments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  booking_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  status VARCHAR(20) NOT NULL,
  payment_method VARCHAR(20) NOT NULL,
  transaction_id VARCHAR(255) UNIQUE NOT NULL,
  masked_card_number VARCHAR(19),
  description VARCHAR(255),
  failure_reason VARCHAR(500),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## Configuration (application.yml)

```yaml
spring:
  application:
    name: payment-service
  datasource:
    url: jdbc:mysql://localhost:3306/airline_payment_db
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false

server:
  port: 8084

payment:
  gateway:
    name: mock
    timeout: 30000
    retry-attempts: 3
```

## Build & Run

### Build
```bash
cd microservices/services/payment-service
mvn clean package
```

### Run
```bash
# Using Maven
mvn spring-boot:run

# Using JAR
java -jar target/payment-service-0.0.1-SNAPSHOT.jar
```

### Run Tests
```bash
mvn test
```

## Integration with Other Services

### Common Library Dependencies
Payment service uses these DTOs from `common-lib`:
- `PaymentStatus` (enum)
- `PaymentMethod` (enum)
- `PaymentRequestDTO`
- `PaymentResponseDTO`
- `PaymentDTO`

### Flight Booking Integration (Future)
When a flight booking is completed, the booking service should:
1. Call `POST /api/v1/payments/initiate` with booking details
2. Check payment status
3. Update booking status based on payment result
4. Store payment transaction ID with booking

Example Integration:
```java
// In FlightBookingService
PaymentResponseDTO paymentResponse = restTemplate.postForObject(
    "http://payment-service/api/v1/payments/initiate",
    paymentRequest,
    PaymentResponseDTO.class
);

if (paymentResponse.getStatus() == PaymentStatus.SUCCESS) {
    booking.setPaymentId(paymentResponse.getPaymentId());
    booking.setStatus(BookingStatus.CONFIRMED);
} else {
    booking.setStatus(BookingStatus.FAILED);
}
```

## Error Handling

### Common Error Scenarios

**Invalid Card Details**
```json
{
  "error": "Payment processing failed",
  "message": "Invalid card details provided"
}
```

**Insufficient Amount**
```json
{
  "error": "Refund processing failed",
  "message": "Refund amount cannot exceed original payment amount"
}
```

**Payment Not Found**
```json
{
  "error": "Payment not found",
  "message": "Payment not found with ID: 999"
}
```

## Payment Status Lifecycle

```
PENDING → SUCCESS/FAILED
  ↓
SUCCESS → REFUNDED (via refund endpoint)
  ↓
CANCELLED (if user cancels before processing)
```

## Testing with cURL

### Initiate Payment
```bash
curl -X POST http://localhost:8084/api/v1/payments/initiate \
  -H "Content-Type: application/json" \
  -d '{
    "bookingId": 1,
    "userId": 1,
    "amount": 5000.00,
    "currency": "INR",
    "paymentMethod": "CREDIT_CARD",
    "cardNumber": "4111111111111111",
    "cardholderName": "John Doe",
    "expiryDate": "12/25",
    "cvv": "123",
    "description": "Flight booking"
  }'
```

### Get Payment
```bash
curl http://localhost:8084/api/v1/payments/1
```

### Process Refund
```bash
curl -X POST "http://localhost:8084/api/v1/payments/1/refund?amount=2500.00"
```

## Logging

Service logs to console with DEBUG level for `com.nara` package:
- Payment initiation requests and results
- Payment retrievals
- Refund processing
- Error conditions

## Security Considerations

- ✓ Card details masked in responses
- ✓ Input validation on all DTOs
- ✓ Transaction ID uniqueness
- ✓ Status-based access control (refunds only on SUCCESS)
- ⚠️ TODO: Add JWT authentication
- ⚠️ TODO: Add role-based authorization
- ⚠️ TODO: Add rate limiting
- ⚠️ TODO: Add encryption for sensitive data

## Future Enhancements

1. **Real Payment Gateways** - Integrate with Stripe, Razorpay, PayPal
2. **Webhook Support** - Handle async payment confirmations
3. **Idempotency** - Prevent duplicate transactions
4. **Reconciliation** - Match payments with bank deposits
5. **Analytics** - Payment trends and metrics
6. **PCI Compliance** - Proper card data handling
7. **Payment Schedules** - Installment payments
8. **Multi-currency** - Currency conversion support
9. **Settlement** - Payout to merchant accounts
10. **Dispute Management** - Chargeback handling

## Troubleshooting

**Issue**: Database connection error
```
Solution: Ensure MySQL is running and credentials in application.yml are correct
```

**Issue**: Port 8084 already in use
```
Solution: Change port in application.yml or kill existing process
```

**Issue**: Payment always fails
```
Solution: Check card number format and expiry date. Use test card 4111111111111111
```

## Support

For issues or questions about the payment service, refer to:
- Mock Gateway Implementation: `gateway/MockPaymentGateway.java`
- Service Logic: `service/PaymentService.java`
- API Endpoints: `controller/PaymentController.java`
