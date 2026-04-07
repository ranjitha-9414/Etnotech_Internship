package com.example.hospital_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.hospital_system.entity.Appointment;
import com.example.hospital_system.entity.User;
import com.example.hospital_system.repository.AppointmentRepository;
import com.example.hospital_system.repository.UserRepository;

@Service
public class AppointmentService {
    

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Appointment bookAppointment(Appointment appointment) {
System.out.println("AUTH: " + SecurityContextHolder.getContext().getAuthentication());
    // ✅ get authentication
    var auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || auth.getPrincipal() == null) {
        throw new RuntimeException("User not authenticated");
    }

    String email = auth.getPrincipal().toString();
    System.out.println("EMAIL FROM TOKEN: " + email);

    // ✅ find user
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

    // ✅ set patientId
    appointment.setPatientId(user.getId());

    // ✅ queue logic
    int count = appointmentRepository.countByDoctorIdAndAppointmentDate(
            appointment.getDoctorId(),
            appointment.getAppointmentDate()
    );

    appointment.setQueueNumber(count + 1);

    return appointmentRepository.save(appointment);
}
public List<Appointment> getMyAppointments() {

    String email = SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal()
            .toString();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    return appointmentRepository.findByPatientId(user.getId());
}
public void cancelAppointment(Long appointmentId) {

    var auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getPrincipal().toString();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

    // ✅ check if this appointment belongs to logged-in user
    if (!appointment.getPatientId().equals(user.getId())) {
        throw new RuntimeException("You cannot cancel this appointment ❌");
    }

    appointmentRepository.deleteById(appointmentId);
}
public List<Appointment> getMyAppointments(Long patientId) {
    return appointmentRepository.findByPatientId(patientId);
}
public List<Appointment> getDoctorAppointments(String email) {
System.out.println("EMAIL FROM TOKEN: " + email);
    User doctor = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    return appointmentRepository.findByDoctorId(doctor.getId());
}

// mark completed
public Appointment completeAppointment(Long id) {

    Appointment app = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));
    return app;

}
}