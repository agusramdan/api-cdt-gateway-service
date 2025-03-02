package agus.ramdan.cdt.core.gateway.controller.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentNotificationRequestDTO {
    private String merchantID;
    private String orderID;
    private String currency;
    private String amount;
    private String channelID;
    private String account;
    private String stan;
    private String trxID;
    private String token;
    private String paymentdate;
    private String refCode;
    private String bankFee;
    private String pgFee;
    private String desc;
    private String status;
    private String signature;
}
