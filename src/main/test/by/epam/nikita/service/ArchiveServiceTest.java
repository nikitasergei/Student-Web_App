package by.epam.nikita.service;

import by.epam.nikita.domain.models.Archive;
import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.repository.ArchiveRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArchiveServiceTest {

    @Autowired
    private ArchiveService archiveService;

    @MockBean
    private ArchiveRepo archiveRepo;

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private TeacherService teacherService;

    @Test
    public void shouldReturnNotNullArchive() {
        Archive archive = new Archive();
        archive.setId(1L);

        Mockito.doReturn(Optional.of(archive)).when(archiveRepo).findById(1L);

        Assert.assertNotNull(archiveService.getById(1L));


    }

    @Test
    public void shouldReturnNull() {
        Archive archive = new Archive();
        archive.setId(1L);

        Mockito.doReturn(Optional.empty()).when(archiveRepo).findById(2L);

        Assert.assertNull(archiveService.getById(2L));
    }


    @Test
    public void shouldReturnPageOfArchivesByCourseId() {
        Course course = new Course();
        Archive archive = new Archive();
        Archive archive1 = new Archive();
        List<Archive> archives = new ArrayList<>();
        archives.add(archive);
        archives.add(archive1);
        course.setId(1L);
        course.setArchiveNotes(archives);

        Mockito.doReturn(new PageImpl<>(archives)).when(archiveRepo).findAllByCourseId(1L, Pageable.unpaged());

        Assert.assertEquals(new PageImpl<>(archives), archiveService.getCourseArchive(1L, Pageable.unpaged()));

    }


    @Test
    public void shouldReturnTrue() {
        Archive archive = new Archive();
        archive.setId(null);

        Mockito.doReturn(null).when(archiveRepo).findById(archive.getId());

        Assert.assertTrue(archiveService.saveArchive(archive));
    }

    @Test
    public void testSetArchiveAttributes() {
        Course course = new Course();
        String courseName = "course1";
        course.setCourseName(courseName);

        Teacher teacher = new Teacher();
        String teacherName = "teacher1";
        teacher.setUsername(teacherName);

        Student student = new Student();
        String studentName = "student1";
        student.setUsername(studentName);

        Integer rating = 1;

        Mockito.doReturn(course).when(courseService).getByCourseName(courseName);
        Mockito.doReturn(teacher).when(teacherService).getTeacherByUsername(teacherName);
        Mockito.doReturn(student).when(studentService).getStudentByUsername(studentName);
        Archive testArchive = archiveService.setArchiveAttributes(courseName, teacherName, studentName, rating);

        Assert.assertNotNull(testArchive);
        Assert.assertEquals(testArchive.getCourse().getCourseName(), courseName);
        Assert.assertEquals(testArchive.getTeacher().getUsername(), teacherName);
        Assert.assertEquals(testArchive.getStudent().getUsername(), studentName);

    }
}