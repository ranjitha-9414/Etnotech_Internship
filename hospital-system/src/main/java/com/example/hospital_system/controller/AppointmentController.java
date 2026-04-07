package com.example.hospital_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.hospital_system.entity.Appointment;
import com.example.hospital_system.entity.User;
import com.example.hospital_system.repository.UserRepository;
import com.example.hospital_system.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public Appointment book(@RequestBody Appointment appointment) {
        return appointmentService.bookAppointment(appointment);
    }

    @GetMapping("/my")
    public List<Appointment> myAppointments() {
        return appointmentService.getMyAppointments();
    }

    @GetMapping("/doctor")
    public List<Appointment> doctorAppointments() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return appointmentService.getDoctorAppointments(email);
    }

    @PutMapping("/{id}/complete")
    public Appointment complete(@PathVariable Long id) {
        return appointmentService.completeAppointment(id);
    }

    @DeleteMapping("/{id}")
    public String cancel(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return "Appointment cancelled successfully";
    }
}