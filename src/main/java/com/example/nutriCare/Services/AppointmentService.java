package com.example.nutriCare.Services;

import com.example.nutriCare.Dtos.AppointmentsDTO;
import com.example.nutriCare.Entities.Appointments;
import com.example.nutriCare.Entities.User;
import com.example.nutriCare.Repositories.AppointmentRepository;
import com.example.nutriCare.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final UserRepository userRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
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
        dto.setDataProgramare(appointment.getDataProgramare());
        dto.setStatus(appointment.getStatus());
        dto.setSumar(appointment.getSumar());
        return dto;
    }

    public List<Appointments> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public Appointments createAppointment(AppointmentsDTO appointmentDTO) {


        Optional<User> user = userRepository.findById(appointmentDTO.getUserId());
        Optional<User> doctor = userRepository.findById(appointmentDTO.getDoctorId());


            Appointments appointment = new Appointments();
            appointment.setDataProgramare(appointmentDTO.getDataProgramare());
            appointment.setStatus(appointmentDTO.getStatus());
            user.ifPresent(appointment::setUser);
            doctor.ifPresent(appointment::setDoctor);
            return appointmentRepository.save(appointment);


    }
}

