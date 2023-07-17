package com.targetindia.stationarymanagementsystem.services;

import com.targetindia.stationarymanagementsystem.dto.StudentDTO;
import com.targetindia.stationarymanagementsystem.dto.StudentLoginDTO;
import com.targetindia.stationarymanagementsystem.entities.Student;
import com.targetindia.stationarymanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //HANDLE Registration of Student
    public void studentRegistration(StudentDTO studentDTO){
        Student student = new Student();
        student.setStudentEmail(studentDTO.getStudentEmail());
        student.setStudentName(studentDTO.getStudentName());
        student.setStudentPassword(passwordEncoder.encode(studentDTO.getStudentPassword()));
        try{
            repository.save(student);
        }catch (Exception e){
            throw e;
        }
    }


    //Handle Login of Student
    public Student studentLogin(StudentLoginDTO loginDTO){
        try{
            Student fetchedStudentByEmail = repository.findByStudentEmail(loginDTO.getStudentEmail());
            if(fetchedStudentByEmail != null){
                String password = loginDTO.getStudentPassword();
                String encodedPassword = fetchedStudentByEmail.getStudentPassword();
                if(passwordEncoder.matches(password, encodedPassword)){
                    Optional<Student> res = repository.findOneByStudentEmailAndStudentPassword(loginDTO.getStudentEmail(), encodedPassword);
                    if(res.isPresent()) return res.get();
                    else return null;
                }
                throw new Exception("Incorrect Password");
            }
            else throw new Exception("Incorrect Email");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Student> getAllStudent(){
        try {
            return repository.findAll();
        }catch (Exception e){
            throw e;
        }
    }

    public Student findStudentById(Integer studentId) {
        try {
            Optional<Student> result = repository.findById(studentId);
            if(result.isPresent()) return result.get();
            return null;
        }catch (Exception e){
            throw e;
        }
    }
}