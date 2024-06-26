package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.AppointmentsDTO;
import com.example.nutriCare.Entities.Appointments;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Mappers.DoctorLinkMapper;
import com.example.nutriCare.Repositories.AppointmentRepository;
import com.example.nutriCare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    private final UserRepository userRepository;

    private final DoctorLinkMapper doctorLinkMapper;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository,DoctorLinkMapper doctorLinkMapper) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorLinkMapper = doctorLinkMapper;
    }

    public List<AppointmentsDTO> getAppointmentsByUser(Long userId) {
        List<Appointments> appointments = appointmentRepository.findByUserId(userId);
        List<Appointments> sortedAppointments = appointments.stream()
                .sorted(Comparator.comparing(Appointments::getId))
                .toList();
        return sortedAppointments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private AppointmentsDTO convertToDTO(Appointments appointment) {

        AppointmentsDTO dto = new AppointmentsDTO();
        dto.setId(appointment.getId());
        dto.setUserId(appointment.getUser().getId());
        dto.setUserNume(appointment.getUser().getNume() + " " + appointment.getUser().getPrenume());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorNume(appointment.getDoctor().getNume());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStatus(appointment.getStatus());
        dto.setSummary(appointment.getSummary());
        dto.setLink(appointment.getLink());
        return dto;
    }

    public List<AppointmentsDTO> getAppointmentsByDoctor(Long doctorId) {
        List<Appointments> appointments = appointmentRepository.findByDoctorId(doctorId);
        List<Appointments> sortedAppointments = appointments.stream()
                .sorted(Comparator.comparing(Appointments::getId))
                .toList();
        return sortedAppointments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AppointmentsDTO createAppointment(AppointmentsDTO appointmentDTO) {


        Optional<User> user = userRepository.findById(appointmentDTO.getUserId());
        Optional<User> doctor = userRepository.findById(appointmentDTO.getDoctorId());

            Appointments appointment = new Appointments();
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appointment.setStatus(appointmentDTO.getStatus());
            user.ifPresent(appointment::setUser);
            doctor.ifPresent(appointment::setDoctor);
            doctor.ifPresent(d -> appointment.setLink(doctorLinkMapper.getLinkForDoctor(d.getId())));
           appointmentRepository.save(appointment);

            return appointmentDTO;

    }

    public AppointmentsDTO updateStatus(Long appointmentId, String status) {
        Appointments appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id " + appointmentId));
        appointment.setStatus(status);
        Appointments savedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(savedAppointment);
    }

    public AppointmentsDTO updateSummary(Long appointmentId, String summary) {
        Appointments appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id " + appointmentId));
        appointment.setSummary(summary);
        Appointments savedAppointment = appointmentRepository.save(appointment);
        return convertToDTO(savedAppointment);
    }

    public List<String> getAvailableAppointmentTimes(Long doctorId, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<Appointments> existingAppointments = appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId, startOfDay, endOfDay);
        List<String> availableSlots = new ArrayList<>();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(17, 0);

        while (startTime.isBefore(endTime)) {
            final LocalTime slotStart = startTime;
            boolean isBooked = existingAppointments.stream()
                    .anyMatch(appointment -> appointment.getAppointmentDate().toLocalTime().equals(slotStart));

            if (!isBooked) {
                availableSlots.add(slotStart.format(timeFormatter));
            }

            startTime = startTime.plusHours(1);
        }

        return availableSlots;
    }
}

