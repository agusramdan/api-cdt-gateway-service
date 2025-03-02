package agus.ramdan.cdt.core.gateway.utils;

import agus.ramdan.base.exception.BadRequestException;
import agus.ramdan.base.exception.Propagation3xxException;
import agus.ramdan.base.exception.Propagation4xxException;
import agus.ramdan.base.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;
@Log4j2
public class ResponseUtils {
    public static <T> Mono<T> responseCheck(String responseCode, String responseMessage, T r){
        if (responseCode != null && responseCode.length()>=3){

             int status = Integer.parseInt(responseCode.substring(0, 3));
             Throwable throwable = null;
             switch (status){
                 case 400 : throwable= new BadRequestException(responseMessage); break;
                 case 401 : throwable= new UnauthorizedException(responseMessage); break;
                 default:
                     switch (status/100){
                         case 3: throwable = new Propagation3xxException(responseMessage,status,null); break;
                         case 4: throwable = new Propagation4xxException(responseMessage,status,null); break;
                         case 5: throwable = new Propagation3xxException(responseMessage,status,null); break;
                     }
             }
            if (throwable!=null){
                log.error(responseMessage);
                return Mono.error(throwable);
            }
        }

        return Mono.just(r);
    }
}
