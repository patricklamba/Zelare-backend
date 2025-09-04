package com.zelare.backend.repository;

import com.zelare.backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.zelare.backend.entity.Enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByEmployerId(UUID employerId);

    List<Booking> findByCleanerId(UUID cleanerId);

    List<Booking> findByStatus(BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.employer.id = :employerId AND b.status = :status")
    List<Booking> findByEmployerIdAndStatus(@Param("employerId") UUID employerId, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.cleaner.id = :cleanerId AND b.status = :status")
    List<Booking> findByCleanerIdAndStatus(@Param("cleanerId") UUID cleanerId, @Param("status") BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.scheduledDate BETWEEN :startDate AND :endDate")
    List<Booking> findByScheduledDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.cleaner.id = :cleanerId AND b.status = 'COMPLETED'")
    Long countCompletedBookingsByCleaner(@Param("cleanerId") UUID cleanerId);
}
