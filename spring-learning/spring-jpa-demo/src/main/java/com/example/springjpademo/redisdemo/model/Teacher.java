package com.example.springjpademo.redisdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value="user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
private String name;
private int age;
private String courseName;
}
