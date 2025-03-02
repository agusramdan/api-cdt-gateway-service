package agus.ramdan.cdt.core.gateway.controller.command;

import agus.ramdan.cdt.core.gateway.controller.dto.notification.PaymentNotificationRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.notification.PaymentNotificationResponseDTO;
import agus.ramdan.cdt.core.gateway.service.notifikasi.PaymentNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cdt/core/gateway/notification/command")
public class PaymentNotificationController {

    private final PaymentNotificationService paymentNotificationService;

    public PaymentNotificationController(PaymentNotificationService paymentNotificationService) {
        this.paymentNotificationService = paymentNotificationService;
    }

    @GetMapping
    public Mono<ResponseEntity<PaymentNotificationResponseDTO>> handleNotification(PaymentNotificationRequestDTO request) {
        return paymentNotificationService.processNotification(request)
                .map(ResponseEntity::ok);
    }
}
