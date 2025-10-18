package souradippatra.Online_Courses_Backend.payment_service.service;

import org.springframework.stereotype.Service;
import souradippatra.Online_Courses_Backend.payment_service.dto.PaymentDto;
import souradippatra.Online_Courses_Backend.payment_service.exception.ResourceNotFoundException;
import souradippatra.Online_Courses_Backend.payment_service.model.Payment;
import souradippatra.Online_Courses_Backend.payment_service.repository.PaymentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentDto initiatePayment(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setUserId(dto.getUserId());
        payment.setCourseId(dto.getCourseId());
        payment.setAmount(dto.getAmount());
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setStatus(Payment.PaymentStatus.PENDING);

        payment = paymentRepository.save(payment);
        return mapToDto(payment);
    }

    public PaymentDto updatePaymentStatus(Long id, String status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));

        payment.setStatus(Payment.PaymentStatus.valueOf(status.toUpperCase()));
        paymentRepository.save(payment);

        // Later: produce Kafka event (PAYMENT_SUCCESS)
        return mapToDto(payment);
    }

    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id " + id));
        return mapToDto(payment);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    private PaymentDto mapToDto(Payment p) {
        return new PaymentDto(
                p.getId(),
                p.getUserId(),
                p.getCourseId(),
                p.getAmount(),
                p.getStatus().name(),
                p.getTransactionId(),
                p.getCreatedAt()
        );
    }
}
