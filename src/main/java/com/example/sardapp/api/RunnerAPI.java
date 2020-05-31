package com.example.sardapp.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.sardapp.entities")
public class RunnerAPI
{
    public static void main(String[] args) {
        SpringApplication.run(RunnerAPI.class, args);
    }
}