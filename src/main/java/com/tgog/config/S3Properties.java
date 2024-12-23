package com.tgog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aws.s3")
public class S3Properties {

    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String imagesBaseUrl;
}