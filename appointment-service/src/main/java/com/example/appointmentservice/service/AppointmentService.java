package com.example.appointmentservice.service;

import com.example.appointmentservice.entity.Appointment;
import com.example.appointmentservice.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;

    public Appointment createAppointment(Appointment appointment) {
        // 1. Kiểm tra Patient có tồn tại không
        // Giả sử patient-service có endpoint GET /api/v1/patients/{id}
        String patientUrl = "http://patient-service/api/v1/patients/" + appointment.getPatientId();

        try {
            // Gọi API. Nếu trả về 200 OK thì OK, nếu 404 sẽ ném lỗi
            restTemplate.getForObject(patientUrl, Object.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Bệnh nhân không tồn tại hoặc Patient-Service không phản hồi!");
        }

        // 2. Kiểm tra Doctor có tồn tại không
        // Giả sử doctor-service có endpoint GET /api/v1/doctors/{id}
        String doctorUrl = "http://doctor-service/api/v1/doctors/" + appointment.getDoctorId();

        try {
            restTemplate.getForObject(doctorUrl, Object.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Bác sĩ không tồn tại hoặc Doctor-Service không phản hồi!");
        }

        // 3. Nếu hợp lệ, lưu vào DB
        appointment.setStatus("PENDING"); // Mặc định trạng thái là PENDING
        return appointmentRepository.save(appointment);
    }
}
