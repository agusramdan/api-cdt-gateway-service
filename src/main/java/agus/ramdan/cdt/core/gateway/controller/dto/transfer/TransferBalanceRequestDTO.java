package agus.ramdan.cdt.core.gateway.controller.dto.transfer;
import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferBalanceRequestDTO {

    /**
     * 0 = Transfer Online
     * 1 = BI Fast Transfer
     */
    @JsonProperty("transferType")
    private String transferType = "0";

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

    /**
     * 01 = Resident
     * 02 = Non-Resident
     */
    @JsonProperty("destination_customer_status")
    private String destinationCustomerStatus="01";

    /**
     * 01 = Individual
     * 02 = Corporate
     * 03 = Government
     * 04 = Remittance
     * 99 = Others
     */
    @JsonProperty("destination_customer_type")
    private String destinationCustomerType="01";

    /**
     * CACC = Current Account
     * SVGS = Savings Account
     * LOAN =Loan
     * CCRD =Credit Card
     * UESB =E-Money
     * OTHR =None of the above
     */
    @JsonProperty("destination_account_type")
    private String destinationAccountType ="SVGS";

    /**
     * purpose_of_transaction String(2) M
     * 01=Investment
     * 02=Transfer of Wealth
     * 03=Purchase
     * 99=Others (for various
     * purposes
     */
    @JsonProperty("purpose_of_transaction")
    private String purposeOfTransaction;

    /**
     * transaction_no String(20) M Transaction Number Merchant
     */
    @JsonProperty("transaction_no")
    private String transactionNo;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("transfer_date")
    private String transferDate;

    private String description;
    /**
     *
     * checkName String(1) O “0” = No Need Check name with
     * Bank (going to bypass name
     * checking)
     * “1” = Check Name Validation
     * with bank
     * Not sent = Identical Name
     * Checking (score 100%)
     * * For Wallet (Dana):
     * • There is NO checkName
     * for Wallet transfer
     * destination. Different
     * name in request and
     * bank name
     */
    private String checkName="0";

}