package agus.ramdan.cdt.core.gateway.mapper;

import agus.ramdan.cdt.core.gateway.controller.dto.account.AccountStatusDTO;
import agus.ramdan.cdt.core.gateway.service.account.CheckAccountInquiryResponseDTO;
import agus.ramdan.cdt.core.master.controller.dto.BeneficiaryAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CheckAccountInquiryMapper {

    CheckAccountInquiryMapper INSTANCE = Mappers.getMapper(CheckAccountInquiryMapper.class);
    @Mapping(target = "account_number", source = "data.accountNo")
    @Mapping(target = "account_name", source = "data.accountName")
    @Mapping(target = "bank.code", source = "data.bankCode")
    @Mapping(target = "bank.name", source = "data.bankName")
    BeneficiaryAccountDTO toBeneficiaryAccountDTO(CheckAccountInquiryResponseDTO response);
}
