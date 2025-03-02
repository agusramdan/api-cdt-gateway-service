package agus.ramdan.cdt.core.gateway.service.notifikasi;

import agus.ramdan.cdt.core.gateway.controller.dto.notification.PaymentNotificationRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.notification.PaymentNotificationResponseDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentNotificationService {

    public Mono<PaymentNotificationResponseDTO> processNotification(PaymentNotificationRequestDTO request) {
        // Validasi Signature (SHA1)
        if (!validateSignature(request)) {
            return Mono.error(new RuntimeException("Invalid Signature"));
        }

        // Simpan data ke database jika diperlukan
        // Example: paymentNotificationRepository.save(request)

        // Buat response ke BayarGW
        PaymentNotificationResponseDTO response = new PaymentNotificationResponseDTO(
                request.getMerchantID(),
                request.getOrderID(),
                request.getTrxID(),
                request.getCurrency(),
                request.getAmount(),
                request.getChannelID(),
                request.getAccount(),
                request.getStan(),
                request.getRefCode(),
                "00", // Berhasil
                "Success Payment",
                "1.0"
        );

        return Mono.just(response);
    }

    private boolean validateSignature(PaymentNotificationRequestDTO request) {
        String rawData = request.getMerchantID() + "##" +
                request.getChannelID() + "##" +
                "YOUR_TRANSACTION_KEY" + "##" +
                request.getOrderID() + "##" +
                request.getAmount();
        String expectedSignature = org.apache.commons.codec.digest.DigestUtils.sha1Hex(rawData);
        return expectedSignature.equals(request.getSignature());
    }
}
