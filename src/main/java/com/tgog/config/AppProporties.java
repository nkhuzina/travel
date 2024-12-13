package com.tgog.config;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Component("projectProps")
@Data
@ConfigurationProperties(prefix = "tgog")
@Validated
public class AppProporties {
    @Min(value=5, message="must be between 5 and 25")
    @Max(value=25, message="must be between 5 and 25")
    private int pageSize;

//    private String uploadDirectory = "./src/main/resources/static/assets/uimages/";
//    private String pathDirectory = "assets/uimages/";

}
