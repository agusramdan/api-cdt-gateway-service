package agus.ramdan.cdt.core.gateway.service.account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAccountInquiryResponseDTO {

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("data")
    private AccountData data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountData {
        private String accountNo;
        private String accountName;
        private String bankCode;
        private String bankName;
    }
}
