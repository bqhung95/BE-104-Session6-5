package com.example.appointmentservice.controller;


import com.example.appointmentservice.entity.Appointment;
import com.example.appointmentservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // Lưu ý: Đề bài ghi /appointment (số ít), mình để đúng theo yêu cầu
    @PostMapping("/appointment")
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment saved = appointmentService.createAppointment(appointment);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
