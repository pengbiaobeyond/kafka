package com.pengbiao.kafka.domain;

import lombok.Data;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
public class Topic {
    public String name;
    public String numPartitions;
    public String replicationFactor;
}
