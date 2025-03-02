package agus.ramdan.cdt.core.gateway.service.transfer;

import agus.ramdan.cdt.core.gateway.config.BayarGWConfig;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.core.gateway.domain.TransferBalanceEntity;
import agus.ramdan.cdt.core.gateway.mapper.TransferBalanceMapper;
import agus.ramdan.cdt.core.gateway.repository.TransferBalanceRepository;
import agus.ramdan.cdt.core.gateway.utils.ResponseUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Log4j2
public class TransferBalanceService {

    private final WebClient webClient;
    private final BayarGWConfig bayarGWConfig;
    private final TransferBalanceMapper transferBalanceMapper;
    private final TransferBalanceRepository transferBalanceRepository;


    public TransferBalanceService(WebClient.Builder webClientBuilder, BayarGWConfig bayarGWConfig,
                                  TransferBalanceMapper transferBalanceMapper, TransferBalanceRepository transferBalanceRepository) {
        this.webClient = webClientBuilder.baseUrl(bayarGWConfig.getService().getTransferBalance().getApiUrl()).build();
        this.bayarGWConfig = bayarGWConfig;
        this.transferBalanceMapper = transferBalanceMapper;
        this.transferBalanceRepository = transferBalanceRepository;
    }

    private String generateSignature(TransferBalanceServiceRequestDTO requestDTO) {
        String rawData = requestDTO.getMerchantId() + "##" +
                requestDTO.getDestinationAccount() + "##" +
                requestDTO.getDestinationAccountName() + "##" +
                requestDTO.getDestinationBankCode() + "##" +
                requestDTO.getTransactionNo() + "##" +
                requestDTO.getAmount() + "##" +
                bayarGWConfig.getTransactionKey();

        String md5Hash = DigestUtils.md5Hex(rawData);
        return DigestUtils.sha1Hex(md5Hash);
    }

    public Mono<TransferBalanceResponseDTO> transferBalance(TransferBalanceRequestDTO requestDTO) {
        // Konversi request DTO ke service DTO
        TransferBalanceServiceRequestDTO serviceRequest = transferBalanceMapper.toServiceRequest(requestDTO);
        serviceRequest.setMerchantId(bayarGWConfig.getMerchantId());
        serviceRequest.setSignature(generateSignature(serviceRequest));
        // Konversi ke entity dan simpan ke MongoDB sebelum dikirim ke API
        TransferBalanceEntity entity = transferBalanceMapper.toEntity(serviceRequest);

        return transferBalanceRepository.save(entity)
                .flatMap(savedEntity -> webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(serviceRequest)
                        .retrieve()
                        .bodyToMono(TransferBalanceServiceResponseDTO.class)
                        .flatMap(r ->{
                            savedEntity.setResponseCode(r.getResponseCode());
                            savedEntity.setResponseMessage(r.getResponseMessage());
                            transferBalanceRepository.save(savedEntity);
                            return  ResponseUtils.responseCheck(r.getResponseCode(),r.getResponseMessage(),r.getData());
                        })
                        .flatMap(response -> {
                            savedEntity.setBankTransactionId(response.getBankTransactionId());
                            savedEntity.setTransactionReff(response.getTransactionReff());
                            savedEntity.setStatus(response.getStatus());
                            savedEntity.setBankResponseCode(response.getBankResponseCode());
                            savedEntity.setBankTransactionId(response.getBankTransactionId());
                            savedEntity.setTransactionFee(response.getTransactionFee());
                            savedEntity.setMessage(response.getMessage());
                            savedEntity.setScore(response.getScore());
                            return transferBalanceRepository.save(savedEntity)
                                    .thenReturn(transferBalanceMapper.toResponseDTO(response));
                        }));
    }
}


