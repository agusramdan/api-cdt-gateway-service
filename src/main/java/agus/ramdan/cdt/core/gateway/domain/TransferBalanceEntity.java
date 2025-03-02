package agus.ramdan.cdt.core.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "transfer_balance_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferBalanceEntity {

    @Id
    private String id; // MongoDB ID

    private String merchantId;
    private String transferType;
    private String destinationAccount;
    private String destinationAccountName;
    private String destinationBankCode;
    private String destinationRegionCode;
    private String destinationCountryCode;
    private String destinationCustomerStatus;
    private String destinationCustomerType;
    private String destinationAccountType;
    private String amount;
    private String transferDate;
    private String description;
    private String signature;

    private LocalDateTime createdAt; // Waktu saat request dikirim

    // Response API
    private String responseCode;
    private String responseMessage;

    private String transactionId;
    private String transactionReff;
    private String status;
    private String bankResponseCode;
    private String bankTransactionId;
    private BigDecimal transactionFee;
    private String message;
    private String score;
}
