package com.list.service.tolistservice;

import org.springframework.boot.SpringApplication;

public class TestToListServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ToListServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
