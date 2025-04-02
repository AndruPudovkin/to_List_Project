package com.list.service.tolistservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ToListServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
