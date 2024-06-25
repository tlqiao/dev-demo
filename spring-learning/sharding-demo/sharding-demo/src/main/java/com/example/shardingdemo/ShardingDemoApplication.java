package com.example.shardingdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.shardingdemo.mapper")
public class ShardingDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShardingDemoApplication.class, args);
	}

}
