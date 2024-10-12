package com.example.springbootstudent.repository.impl;

import com.example.springbootstudent.model.Student;
import com.example.springbootstudent.repository.inter.StudentRepositoryInter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Repository
public class StudentRepositoryImpl implements StudentRepositoryInter {
    private final JdbcTemplate jdbcTemplate;

    private final DataSource dataSource;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String urlDb;

    @Value("${spring.datasource.username}")
    private String usernameDb;

    @Value("${spring.datasource.password}")
    private String passwordDb;

    Connection connection;

    public StudentRepositoryImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

//    @Override
//    public void save(Student student) {
//        try {
//            connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("insert into student(name,surname,age,score,group_name) values (?,?,?,?,?)");
//
//            preparedStatement.setLong(1, student.getId());
//            preparedStatement.setString(1, student.getName());
//            preparedStatement.setString(2, student.getSurname());
//            preparedStatement.setInt(3, student.getAge());
//            preparedStatement.setInt(4, student.getScore());
//            preparedStatement.setString(5, student.getGroupName());
//
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public int save(Student student) {
        Map<String,Object> map=new HashMap<>();
        map.put("name",student.getName());
        map.put("surname",student.getSurname());
        map.put("age",student.getAge());
        map.put("score",student.getScore());
        map.put("group_name",student.getGroupName());

        SimpleJdbcInsert simpleJdbcInsert=new SimpleJdbcInsert(dataSource)
                .withTableName("student")
                .usingGeneratedKeyColumns("id");

        return simpleJdbcInsert.execute(map);
    }

//    @Override
//    public List<Student> getAll() {
//        List<Student> students = null;
//        try {
//            connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
//
//            PreparedStatement preparedStatement = connection.prepareStatement("select * from student");
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            students = new ArrayList<>();
//
//            while (resultSet.next()) {
//                Student student = new Student();
//                student.setId(resultSet.getLong("id"));
//                student.setName(resultSet.getString("name"));
//                student.setSurname(resultSet.getString("surname"));
//                student.setAge(resultSet.getInt("age"));
//                student.setScore(resultSet.getInt("score"));
//                student.setGroupName(resultSet.getString("group_name"));
//                students.add(student);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return students;
//    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query("select * from student", BeanPropertyRowMapper.newInstance(Student.class));
    }

//    @Override
//    public Student getStudentById(Long id) {
//        Student student=new Student();
//        try{
//            connection=DriverManager.getConnection(urlDb,usernameDb,passwordDb);
//            PreparedStatement preparedStatement=connection.prepareStatement("select * from student where id=?");
//            preparedStatement.setLong(1,id);
//
//            ResultSet resultSet=preparedStatement.executeQuery();
//
//            if (resultSet.next()){
////                student.setId(resultSet.getLong("id"));
//                student.setName(resultSet.getString("name"));
//                student.setSurname(resultSet.getString("surname"));
//                student.setAge(resultSet.getInt("age"));
//                student.setScore(resultSet.getInt("score"));
//                student.setGroupName(resultSet.getString("group_name"));
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return student;
//    }

    @Override
    public Student getStudentById(Long id) {
        try{
            return jdbcTemplate.queryForObject("select * from student where id=?",BeanPropertyRowMapper.newInstance(Student.class),id);
        }catch (Exception e){
            return null;
        }
    }

//    @Override
//    public void deleteStudentById(Long id) {
//        try{
//            connection=DriverManager.getConnection(urlDb,usernameDb,passwordDb);
//            PreparedStatement preparedStatement=connection.prepareStatement("delete from student where id=?");
//            preparedStatement.setLong(1,id);
//
//            preparedStatement.execute();
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void deleteStudentById(Long id) {
      jdbcTemplate.update("delete from student where id=?",id);
    }

//    @Override
//    public void putById(Long id, String name, String surname, int age, int score, String groupName) {
//       try{
//           connection=DriverManager.getConnection(urlDb,usernameDb,passwordDb);
//           PreparedStatement preparedStatement=connection.prepareStatement("update student set name=?, surname=?, age=?, score=?, group_name=? where id=?");
//           preparedStatement.setString(1,name);
//           preparedStatement.setString(2,surname);
//           preparedStatement.setInt(3,age);
//           preparedStatement.setInt(4,score);
//           preparedStatement.setString(5,groupName);
//           preparedStatement.setLong(6,id);
//
//           preparedStatement.execute();
//
//       }catch (SQLException e){
//           e.printStackTrace();
//       }
//    }

    @Override
    public void putById(Long id, String name, String surname, int age, int score, String groupName) {
      jdbcTemplate.update("update student set name=?, surname=?, age=?, score=?, group_name=? where id=?",
              new Object[]{name,surname,age,score,groupName,id});
    }

//    @Override
//    public void updateName(Long id, String name) {
//        try {
//            connection=DriverManager.getConnection(urlDb,usernameDb,passwordDb);
//            PreparedStatement preparedStatement=connection.prepareStatement("update student set name=? where id=?");
//            preparedStatement.setString(1,name);
//            preparedStatement.setLong(2,id);
//
//            preparedStatement.execute();
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void updateName(Long id, String name) {
       jdbcTemplate.update("update student set name=? where id=?",name,id);
    }

//    @Override
//    public void updateSurname(Long id, String surname) {
//        try {
//            connection=DriverManager.getConnection(urlDb,usernameDb,passwordDb);
//            PreparedStatement preparedStatement=connection.prepareStatement("update student set surname=? where id=?");
//            preparedStatement.setString(1,surname);
//            preparedStatement.setLong(2,id);
//
//            preparedStatement.execute();
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void updateSurname(Long id, String surname) {
       jdbcTemplate.update("update student set surname=? where id=?",surname,id);
    }

//    @Override
//    public void updateAge(Long id, int age) {
//        try{
//            connection=DriverManager.getConnection(urlDb,usernameDb,passwordDb);
//            PreparedStatement preparedStatement=connection.prepareStatement("update student set age=? where id=?");
//            preparedStatement.setInt(1,age);
//            preparedStatement.setLong(2,id);
//
//            preparedStatement.execute();
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void updateAge(Long id, int age) {
      jdbcTemplate.update("update student set age=? where id=?",age,id);
    }

//    @Override
//    public void updateGroupName(Long id, String groupName) {
//        try{
//            connection=DriverManager.getConnection(urlDb,usernameDb,passwordDb);
//            PreparedStatement preparedStatement=connection.prepareStatement("update student set group_name=? where id=?");
//            preparedStatement.setString(1,groupName);
//            preparedStatement.setLong(2,id);
//
//            preparedStatement.execute();
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public void updateGroupName(Long id, String groupName) {
        jdbcTemplate.update("update student set group_name=? where id=?",groupName,id);
    }
}
