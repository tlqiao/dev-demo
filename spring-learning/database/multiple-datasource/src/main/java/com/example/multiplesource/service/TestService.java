package com.example.multiplesource.service;

import com.example.multiplesource.config.SwitchDataSource;
import com.example.multiplesource.model.College;
import com.example.multiplesource.model.Student;
import com.example.multiplesource.repository.CollegeRepository;
import com.example.multiplesource.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TestService {
    @Autowired
    CollegeRepository collegeRepository;
    @Autowired
    StudentRepository studentRepository;
    @SwitchDataSource(value = "college")
    //使用定义的注解
    public List<College> getAllColleges(){
        return collegeRepository.findAll();
    }
    @SwitchDataSource(value = "student")
    //使用定义的注解
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

}
