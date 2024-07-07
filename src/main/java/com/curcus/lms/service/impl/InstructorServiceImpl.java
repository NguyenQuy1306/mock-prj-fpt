package com.curcus.lms.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.model.entity.Instructor;
import com.curcus.lms.model.mapper.UserMapper;
import com.curcus.lms.model.request.InstructorRequest;
import com.curcus.lms.model.response.InstructorResponse;
import com.curcus.lms.repository.InstructorRepository;
import com.curcus.lms.service.InstructorService;

@Service
public class InstructorServiceImpl implements InstructorService{
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<InstructorResponse> findAll(){
        try {
            return userMapper.toInstructorResponseList(instructorRepository.findAll());
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public List<InstructorResponse> findByName(String name){
        try {
            return userMapper.toInstructorResponseList(instructorRepository.findByName(name));
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public Optional<InstructorResponse> findById(Long instructorId){
        try {
            return instructorRepository.findById(instructorId).map(userMapper::toInstructorResponse);
        } catch (ApplicationException ex) {
            throw ex;
        }
    }

    @Override
    public InstructorResponse saveInstructor(InstructorRequest instructorRequest) {
        try {
            Instructor newInstructor = new Instructor();
            newInstructor.setName(instructorRequest.getName());
            if (instructorRepository.findByEmail(instructorRequest.getEmail())!=null) throw new ApplicationException("Email already exists");
            newInstructor.setEmail(instructorRequest.getEmail());
            newInstructor.setPassword(instructorRequest.getPassword());
            newInstructor.setFirstName(instructorRequest.getFirstName());
            newInstructor.setLastName(instructorRequest.getLastName());
            if (instructorRepository.findByEmail(instructorRequest.getPhoneNumber())!=null) throw new ApplicationException("PhoneNumber already exists");
            newInstructor.setPhoneNumber(instructorRequest.getPhoneNumber());
            return userMapper.toInstructorResponse(instructorRepository.save(newInstructor));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }

    @Override
    public InstructorResponse newUpdateInstructor(InstructorRequest instructorRequest, Long id) {
        try {
            if (instructorRepository.findById(id)==null) throw new ApplicationException("Unknown account"); 
            Instructor newInstructor = instructorRepository.findById(id).get();
            if (instructorRequest.getName()!=null) newInstructor.setName(instructorRequest.getName());
            if (instructorRequest.getEmail()!=null) newInstructor.setEmail(instructorRequest.getEmail());
            if (instructorRequest.getPassword()!=null) {
                if (instructorRepository.findByEmail(instructorRequest.getEmail())!=null) throw new ApplicationException("Email already exists");
                newInstructor.setPassword(instructorRequest.getPassword());
            }
            if (instructorRequest.getFirstName()!=null) newInstructor.setFirstName(instructorRequest.getFirstName());
            if (instructorRequest.getLastName()!=null) newInstructor.setLastName(instructorRequest.getLastName());
            if (instructorRequest.getPhoneNumber()!=null) {
                if (instructorRepository.findByEmail(instructorRequest.getPhoneNumber())!=null) throw new ApplicationException("PhoneNumber already exists");
                newInstructor.setPhoneNumber(instructorRequest.getPhoneNumber());
            }
            return userMapper.toInstructorResponse(instructorRepository.save(newInstructor));
        } catch (ApplicationException ex) {
            throw ex;
        }

    }


    @Override
    public void deleteInstructor(Long instructorId){
        try {
            if (instructorRepository.findById(instructorId)==null) throw new ApplicationException("Unknown account");
            instructorRepository.deleteById(instructorId);
        } catch (ApplicationException ex) {
            throw ex;
        }

    }
    
}