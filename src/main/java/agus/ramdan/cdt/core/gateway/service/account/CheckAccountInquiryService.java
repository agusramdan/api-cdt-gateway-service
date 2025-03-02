package agus.ramdan.cdt.core.gateway.service.account;

import agus.ramdan.cdt.core.gateway.config.BayarGWConfig;
import agus.ramdan.cdt.core.gateway.controller.dto.account.AccountStatusDTO;
import agus.ramdan.cdt.core.gateway.mapper.CheckAccountInquiryMapper;
import agus.ramdan.cdt.core.gateway.utils.ResponseUtils;
import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CheckAccountInquiryService {

    private final WebClient webClient;
    private final BayarGWConfig bayarGWConfig;
    private final CheckAccountInquiryMapper checkAccountInquiryMapper;

    public CheckAccountInquiryService(WebClient.Builder webClientBuilder, BayarGWConfig bayarGWConfig, CheckAccountInquiryMapper checkAccountInquiryMapper) {
        this.webClient = webClientBuilder.baseUrl(bayarGWConfig.getService().getCheckAccountInquiry().getApiUrl()).build();
        this.bayarGWConfig = bayarGWConfig;
        this.checkAccountInquiryMapper = checkAccountInquiryMapper;
    }

    /**
     * SHA1(bankCode + “##” +
     * accountNumber + “##” +
     * TransactionKey)
     *
     * @param destinationAccount
     * @param destinationBankCode
     * @return
     */

    private String generateSignature(String destinationBankCode, String destinationAccount) {
        String rawData = destinationBankCode + "##" + destinationAccount + "##" + bayarGWConfig.getTransactionKey();
        //String md5Hash = DigestUtils.md5Hex(rawData);
        return DigestUtils.sha1Hex(rawData);
    }

    public Mono<BeneficiaryAccountDTO> checkAccount(BeneficiaryAccountDTO requestDTO) {
        CheckAccountInquiryRequestDTO inquiryRequest = new CheckAccountInquiryRequestDTO();
        inquiryRequest.setMerchantId(bayarGWConfig.getMerchantId());
        inquiryRequest.setAccountNumber(requestDTO.getAccount_number());
        inquiryRequest.setBankCode(requestDTO.getBank().getCode());
        inquiryRequest.setSignature(generateSignature(inquiryRequest.getBankCode(), inquiryRequest.getAccountNumber()));
        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(inquiryRequest)
                .retrieve()
                .bodyToMono(CheckAccountInquiryResponseDTO.class)
                .flatMap(r ->ResponseUtils.responseCheck(r.getResponseCode(),r.getResponseMessage(),r))
                .map(checkAccountInquiryMapper::toBeneficiaryAccountDTO);
    }
}

