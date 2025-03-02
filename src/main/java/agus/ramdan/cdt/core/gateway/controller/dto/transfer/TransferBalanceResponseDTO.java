package agus.ramdan.cdt.core.gateway.controller.dto.transfer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferBalanceResponseDTO {
    @JsonProperty("destination_account")
    private String destinationAccount;

    @JsonProperty("destination_account_name")
    private String destinationAccountName;

    @JsonProperty("destination_bank_code")
    private String destinationBankCode;

    /**
     * Destination Region Code (Optional)
     */
    @JsonProperty("destination_region_code")
    private String destinationRegionCode;

    /**
     * Destination Country Code
     */
    @JsonProperty("destination_country_code")
    private String destinationCountryCode;

    @JsonProperty("transaction_no")
    private String transactionNo;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("transfer_date")
    private String transferDate;

    private String description;

    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("transaction_reff")
    private String transactionReff;

    /**
     * List of status transaction:
     * 1 = On Process
     * 2 = Success
     * 4 = Failed
     * 5 = Reverse
     */
    @JsonProperty("status")
    private String status = "1";

    /**
     * Bank response code
     */
    @JsonProperty("bank_response_code")
    private String bankResponseCode;

    /**
     * Bank transaction id
     */
    @JsonProperty("bank_transaction_id")
    private String bankTransactionId;

    /**
     * Transaction Fee (Optional)
     */
    @JsonProperty("transaction_fee")
    private BigDecimal transactionFee;

    /**
     * Message transaction (Optional)
     */
    @JsonProperty("message")
    private String message;

    /**
     * Beneficiary Name Check Score:
     * 100% = Identical
     * 0% = Completely different
     */
    @JsonProperty("score")
    private String score;
}
