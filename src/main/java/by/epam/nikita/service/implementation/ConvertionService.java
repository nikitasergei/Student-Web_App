package by.epam.nikita.service.implementation;

import by.epam.nikita.DTO.models.ArchiveDTO;
import by.epam.nikita.DTO.models.CourseDTO;
import by.epam.nikita.DTO.models.StudentDTO;
import by.epam.nikita.DTO.models.TeacherDTO;
import by.epam.nikita.domain.models.Archive;
import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.service.serviceInterfaces.Convertor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertionService implements Convertor {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourseDTO convertToDto(Course course) {
        return modelMapper.map(course, CourseDTO.class);
    }

    @Override
    public StudentDTO convertToDto(Student student) {
        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public TeacherDTO convertToDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDTO.class);
    }

    @Override
    public ArchiveDTO convertToDto(Archive archive) {
        return modelMapper.map(archive, ArchiveDTO.class);
    }

}
