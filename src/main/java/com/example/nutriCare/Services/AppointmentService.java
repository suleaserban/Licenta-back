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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final UserRepository userRepository;

    private DoctorLinkMapper doctorLinkMapper;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository,DoctorLinkMapper doctorLinkMapper) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorLinkMapper = doctorLinkMapper;
    }

    public List<AppointmentsDTO> getAppointmentsByUser(Long userId) {
        List<Appointments> appointments = appointmentRepository.findByUserId(userId);
        return appointments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private AppointmentsDTO convertToDTO(Appointments appointment) {

        AppointmentsDTO dto = new AppointmentsDTO();
        dto.setUserId(appointment.getUser().getId());
        dto.setUserNume(appointment.getUser().getNume());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setDoctorNume(appointment.getDoctor().getNume());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStatus(appointment.getStatus());
        dto.setSummary(appointment.getSummary());
        dto.setLink(appointment.getLink());
        return dto;
    }

    public List<Appointments> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
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

