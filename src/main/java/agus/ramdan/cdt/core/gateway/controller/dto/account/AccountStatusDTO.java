package agus.ramdan.cdt.core.gateway.controller.dto.account;

import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountStatusDTO {
    private BeneficiaryAccountDTO beneficiary;
    @JsonProperty("customer_status")
    private String customerStatus;
    @JsonProperty("customer_type")
    private String customerType;
}
