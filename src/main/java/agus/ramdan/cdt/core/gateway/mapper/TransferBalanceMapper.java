package agus.ramdan.cdt.core.gateway.mapper;

import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceRequestDTO;
import agus.ramdan.cdt.core.gateway.controller.dto.transfer.TransferBalanceResponseDTO;
import agus.ramdan.cdt.core.gateway.domain.TransferBalanceEntity;
import agus.ramdan.cdt.core.gateway.service.transfer.TransferBalanceServiceRequestDTO;
import agus.ramdan.cdt.core.gateway.service.transfer.TransferBalanceServiceResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface TransferBalanceMapper {

    TransferBalanceMapper INSTANCE = Mappers.getMapper(TransferBalanceMapper.class);

//    @Mapping(target = "merchantId", source = "merchantId")
//    @Mapping(target = "transactionNo", expression = "java(java.util.UUID.randomUUID().toString())")
//    @Mapping(target = "transferDate", expression = "java(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern(\"yyyy-MM-dd'T'HH:mm:ss\")))")
//    @Mapping(target = "signature", ignore = true) // Signature akan diisi secara manual
//    TransferBalanceServiceRequestDTO toServiceRequest(TransferBalanceRequestDTO requestDTO, String merchantId);
    @Named("formatAmount")
    static String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        return amount.setScale(2, BigDecimal.ROUND_DOWN).toPlainString(); // 2 desimal, tanpa koma
    }
    @Mapping(target = "amount", source = "amount", qualifiedByName = "formatAmount") // Format amount
    @Mapping(target = "transferDate", expression = "java(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern(\"yyyy-MM-dd'T'HH:mm:ss\")))")
    TransferBalanceServiceRequestDTO toServiceRequest(TransferBalanceRequestDTO requestDTO);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    TransferBalanceEntity toEntity(TransferBalanceServiceRequestDTO requestDTO);
    TransferBalanceResponseDTO toResponseDTO(TransferBalanceServiceResponseDTO.TransferBalanceData response);
//    @Mapping(target = "transactionId", source = "transactionId")
//    @Mapping(target = "transactionReff", source = "transactionReff")
//    @Mapping(target = "status", source = "status")
//    @Mapping(target = "bankResponseCode", source = "bankResponseCode")
//    @Mapping(target = "bankTransactionId", source = "bankTransactionId")
//    @Mapping(target = "transactionFee", source = "transactionFee")
//    TransferBalanceEntity updateEntityWithResponse(TransferBalanceEntity entity, TransferBalanceServiceResponseDTO.TransferBalanceData data);
}
