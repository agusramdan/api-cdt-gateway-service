package agus.ramdan.cdt.core.gateway.service.account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckAccountInquiryRequestDTO {
    private String merchantId;
    private String transferType="0";
    private String accountNumber;
    private String bankCode;
    private String signature;


}
