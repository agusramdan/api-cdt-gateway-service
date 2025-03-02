package agus.ramdan.cdt.core.gateway.controller.dto.notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentNotificationResponseDTO {
    private String merchantID;
    private String orderID;
    private String trxID;
    private String currency;
    private String amount;
    private String channelID;
    private String account;
    private String stan;
    private String referenceNumber;
    private String status; // "00" = Success, "01" = Failed
    private String description;
    private String version;
}
