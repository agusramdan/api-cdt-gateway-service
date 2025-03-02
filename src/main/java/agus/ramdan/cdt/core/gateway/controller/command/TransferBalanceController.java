package agus.ramdan.cdt.core.gateway.controller.command;

import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.core.gateway.service.transfer.TransferBalanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/cdt/core/gateway/transfer/command")
public class TransferBalanceController {

    private final TransferBalanceService transferBalanceService;

    public TransferBalanceController(TransferBalanceService transferBalanceService) {
        this.transferBalanceService = transferBalanceService;
    }

    @PostMapping()
    public Mono<ResponseEntity<TransferBalanceResponseDTO>> transferBalance(@RequestBody TransferBalanceRequestDTO requestDTO) {
        return transferBalanceService.transferBalance(requestDTO)
                .map(ResponseEntity::ok);
    }
}

