package com.example.nutriCare.Controllers;

import com.example.nutriCare.Dtos.AppointmentsDTO;
import com.example.nutriCare.Entities.Appointments;
import com.example.nutriCare.Services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/user/{userId}")
    public List<AppointmentsDTO> getUserAppointments(@PathVariable Long userId) {
        return appointmentService.getAppointmentsByUser(userId);
    }


    @GetMapping("/doctor/{doctorId}")
    public List<Appointments> getDoctorAppointments(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }


    @PostMapping("/add-appointment")
    public ResponseEntity<AppointmentsDTO> createAppointment(@RequestBody AppointmentsDTO appointmentDTO) {
        AppointmentsDTO newAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.ok(newAppointment);
    }

    @GetMapping("/available-times")
    public List<String> getAvailableAppointmentTimes(@RequestParam Long doctorId,
                                                     @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);
        return appointmentService.getAvailableAppointmentTimes(doctorId, startOfDay, endOfDay);
    }


}