package agus.ramdan.cdt.core.gateway.controller.query;

import agus.ramdan.cdt.core.gateway.controller.dto.account.AccountStatusDTO;
import agus.ramdan.cdt.core.gateway.service.account.CheckAccountInquiryService;
import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cdt/core/gateway/account/check/query")
@RequiredArgsConstructor
public class CheckAccountInquiryController {

    private final CheckAccountInquiryService checkAccountInquiryService;

    @PostMapping("")
    public Mono<ResponseEntity<BeneficiaryAccountDTO>> checkAccount(@RequestBody BeneficiaryAccountDTO requestDTO) {
        return checkAccountInquiryService.checkAccount(requestDTO)
                .map(ResponseEntity::ok);
    }
}
