package com.example.springbootstudent.service.redis;

import com.example.springbootstudent.model.Student;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StudentRedisService {

    private final RedisTemplate<String, Student> redisTemplate;

    public StudentRedisService(RedisTemplate<String, Student> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(Student student) {
        String redisKey = "student:" + student.getId();
        Student studentRedis=new Student(student.getId(),student.getName(),student.getSurname(),
                                    student.getAge(),student.getScore(),student.getGroupName(),student.getImagePath());
        redisTemplate.opsForValue().set(redisKey, studentRedis);
    }

    public void update(Student updateStudent) {
        String redisKey = "student:" + updateStudent.getId();
        redisTemplate.opsForValue().set(redisKey, updateStudent);
    }

    public void delete(Long id) {
        String redisKey = "student:" + id;
        redisTemplate.delete(redisKey);
    }

    public Student getStudent(Long id) {
        String redisKey = "student:" + id;
        Student student = redisTemplate.opsForValue().get(redisKey);
        return student;
    }

    public List<Student> getAll() {
        Set<String> keys = redisTemplate.keys("student:*");
        List<Student> students = new ArrayList<>();

        if(keys!=null && !keys.isEmpty()) {
            for (String key:keys) {
                Student student = redisTemplate.opsForValue().get(key);
                if (student != null) {
                    students.add(student);
                }
            }
        }
        return students;
    }
    }
