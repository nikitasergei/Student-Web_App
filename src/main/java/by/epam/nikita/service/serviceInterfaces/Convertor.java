package by.epam.nikita.service.serviceInterfaces;

import by.epam.nikita.DTO.models.ArchiveDTO;
import by.epam.nikita.DTO.models.CourseDTO;
import by.epam.nikita.DTO.models.StudentDTO;
import by.epam.nikita.DTO.models.TeacherDTO;
import by.epam.nikita.domain.models.Archive;
import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;


public interface Convertor {

    CourseDTO convertToDto(Course course);

    StudentDTO convertToDto(Student student);

    TeacherDTO convertToDto(Teacher teacher);

    ArchiveDTO convertToDto(Archive archive);

}
