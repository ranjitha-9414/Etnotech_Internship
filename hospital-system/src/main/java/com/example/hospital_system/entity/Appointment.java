package com.example.hospital_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long doctorId;

    private LocalDate appointmentDate;

    private int queueNumber;

    private String status; // BOOKED, CANCELLED, COMPLETED

    public Appointment() {
        this.status = "BOOKED"; // ✅ default
    }

    public Long getId() { return id; }

    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }

    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public int getQueueNumber() { return queueNumber; }
    public void setQueueNumber(int queueNumber) { this.queueNumber = queueNumber; }

    // ✅ ADD HERE
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
}