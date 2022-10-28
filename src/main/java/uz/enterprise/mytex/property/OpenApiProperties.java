package uz.enterprise.mytex.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author - 'Zuhriddin Shamsiddionov' at 1:52 PM 10/26/22 on Wednesday in October
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api.info")
public class OpenApiProperties {
    private String title;
    private String version;
    private String description;
    private String contactName;
    private String contactEmail;
    private String contactUrl;
}
