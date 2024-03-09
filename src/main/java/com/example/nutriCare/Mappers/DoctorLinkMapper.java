package com.example.nutriCare.Mappers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DoctorLinkMapper {
    private Map<Long, String> linkForDoctors;

    public DoctorLinkMapper() {
        linkForDoctors = new HashMap<>();
        linkForDoctors.put(4L, "https://us04web.zoom.us/j/3654549949?pwd=dGMxM3pybkdnZ0M5T0E4MWpnOTdiZz09");

    }

    public String getLinkForDoctor(Long doctorId) {

        return linkForDoctors.get(doctorId);
    }

}
