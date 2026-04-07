package com.example.hospital_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.hospital_system.entity.Appointment;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    int countByDoctorIdAndAppointmentDate(Long doctorId, java.time.LocalDate date);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
}