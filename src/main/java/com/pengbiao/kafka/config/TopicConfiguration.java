package com.pengbiao.kafka.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class TopicConfiguration {
    private List<Topic> topics;
    @Autowired
    private GenericWebApplicationContext genericContext;
    @PostConstruct
    public void init(){
        initializeBeans(topics);
    }

    private void initializeBeans(List<Topic> topics) {
        topics.forEach(t -> genericContext.registerBean(t.name, NewTopic.class, t::toNewTopic));
    }

    @Setter
    @Getter
    @ToString
    static class Topic {
        String name;
        Integer numPartitions = 3;
        Short replicationFactor = 1;

        NewTopic toNewTopic() {
            return new NewTopic(this.name, this.numPartitions, this.replicationFactor);
        }

    }

}
