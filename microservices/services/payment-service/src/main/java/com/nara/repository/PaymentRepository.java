package com.nara.repository;

import com.nara.model.Payment;
import com.nara.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByBookingId(Long bookingId);

    List<Payment> findByUserId(Long userId);

    List<Payment> findByStatus(PaymentStatus status);

    @Query("SELECT p FROM Payment p WHERE p.bookingId = :bookingId AND p.status = :status")
    Optional<Payment> findLatestPaymentByBookingIdAndStatus(
            @Param("bookingId") Long bookingId,
            @Param("status") PaymentStatus status);

    @Query("SELECT p FROM Payment p WHERE p.userId = :userId ORDER BY p.createdAt DESC")
    List<Payment> findUserPaymentHistory(@Param("userId") Long userId);
}
