package agus.ramdan.cdt.core.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bayargw")
public class BayarGWConfig {
    private String merchantId;
    private String transactionKey;
    private ServiceConfig service;

    @Getter
    @Setter
    public static class ServiceConfig {
        private ApiUrlConfig checkAccountInquiry;
        private TransferBalanceConfig transferBalance;
    }
    @Getter
    @Setter
    public static class ApiUrlConfig {
        private String apiUrl;
    }

    @Getter
    @Setter
    public static class TransferBalanceConfig {
        private String apiUrl;
        /**
         * 0 = Transfer Online
         * 1 = BI Fast Transfer
         */
        private String transferType = "0";
    }
}


