package axing.cloud.gateway.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "axing.cloud")
public class FetchGatewayProperties {

    /**
     * 是否只能通过网关获取资源
     * 默认为True
     */
    private Boolean mustFetchGateway = Boolean.TRUE;

    /**
     * 经过网关秘钥key
     */
    private String fetchGatewayKey = "fetchGateway";

    /**
     * 经过网关秘钥value
     */
    private String fetchGatewayValue = "fetchGateway";
}

