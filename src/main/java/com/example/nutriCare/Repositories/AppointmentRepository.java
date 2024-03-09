package com.example.nutriCare.Repositories;

import com.example.nutriCare.Entities.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
    List<Appointments> findByUserId(Long userId);

    List<Appointments> findByDoctorId(Long doctorId);

    List<Appointments> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime start, LocalDateTime end);
}