package org.example.deal.tests;

import org.example.deal.service.impl.ImplKafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Tests {

    @Autowired
    private ImplKafkaProducerService  implKafkaProducerService;

    @Test
    void test(){
        implKafkaProducerService.finishRegistration(1l);
    }

}
