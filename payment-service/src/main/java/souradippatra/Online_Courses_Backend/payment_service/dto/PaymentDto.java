package souradippatra.Online_Courses_Backend.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    private Long userId;
    private Long courseId;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime createdAt;
}
