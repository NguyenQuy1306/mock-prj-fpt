    // InstructorServiceImpl.java
    package com.curcus.lms.service.impl;

    import com.curcus.lms.model.dto.InstructorDTO;
    import com.curcus.lms.model.entity.Instructor;
    import com.curcus.lms.model.request.InstructorCreateRequest;
    import com.curcus.lms.repository.InstructorRepository;
    import com.curcus.lms.service.InstructorService;
    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class InstructorServiceImpl implements InstructorService {

        @Autowired
        private InstructorRepository instructorRepository;

        @Override
        public InstructorDTO createInstructor(InstructorCreateRequest request) {
            Instructor instructor = new Instructor();
            BeanUtils.copyProperties(request, instructor);
            this.instructorRepository.save(instructor);
            
            InstructorDTO responseDTO = new InstructorDTO();
            BeanUtils.copyProperties(instructor, responseDTO);
            return responseDTO;
        }

        @Override
        public InstructorDTO getInstructor(Long id) {
            Instructor instructor = instructorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            InstructorDTO instructorDTO = new InstructorDTO();
            BeanUtils.copyProperties(instructor, instructorDTO);
            return instructorDTO;
        }

        @Override
        public List<InstructorDTO> getAllInstructors() {
            List<Instructor> instructors = instructorRepository.findAll();
            return instructors.stream()
                    .map(instructor -> {
                        InstructorDTO dto = new InstructorDTO();
                        BeanUtils.copyProperties(instructor, dto);
                        return dto;
                    })
                    .collect(Collectors.toList());
        }

        @Override
        public InstructorDTO updateInstructor(Long id, InstructorCreateRequest request) {
            Instructor instructor = instructorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
            BeanUtils.copyProperties(request, instructor);
            instructor = instructorRepository.saveAndFlush(instructor);
            InstructorDTO responseDTO = new InstructorDTO();
            BeanUtils.copyProperties(instructor, responseDTO);
            return responseDTO;
        }

        @Override
        public void deleteInstructor(Long id) {
            instructorRepository.deleteById(id);
        }
    }
