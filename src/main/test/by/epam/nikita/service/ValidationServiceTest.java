package by.epam.nikita.service;

import by.epam.nikita.domain.interfaces.User;
import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationServiceTest {

    @Autowired
    private ValidationService validationService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private CourseService courseService;


    @Test
    public void shouldReturnTrueIfUserIsStudent() {
        User user = new Student();

        boolean result = validationService.isStudent(user);
        Assert.assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfUserIsNotStudent() {
        User user = new Teacher();
        boolean result = validationService.isStudent(user);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnTrueIfUserIsTeacher() {
        User user = new Teacher();
        boolean result = validationService.isTeacher(user);
        Assert.assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfUserIsNotTeacher() {
        User user = new Student();
        boolean result = validationService.isTeacher(user);
        Assert.assertFalse(result);
    }

    @Test
    public void shouldReturnTrueIfTeacherWithIdPresentInDB() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        given(teacherService.getTeacherById(1L))
                .willReturn(teacher);
        Assert.assertTrue(validationService.isTeacherPresentInDB(1L));
    }

    @Test
    public void shouldReturnFalseIfTeacherWithIdNotPresentInDB() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        given(teacherService.getTeacherById(1L))
                .willReturn(null);
        Assert.assertFalse(validationService.isTeacherPresentInDB(1L));
    }

    @Test
    public void shouldReturnTrueIfTeacherWithUsernamePresentInDB() {
        Teacher teacher = new Teacher();
        teacher.setUsername("Mike");

        given(teacherService.getTeacherByUsername("Mike"))
                .willReturn(teacher);
        Assert.assertTrue(validationService.isTeacherPresentInDB("Mike"));
    }

    @Test
    public void shouldReturnFalseIfTeacherWithUsernameNotPresentInDB() {
        Teacher teacher = new Teacher();
        teacher.setUsername("Mike");

        given(teacherService.getTeacherByUsername("Mike"))
                .willReturn(null);
        Assert.assertFalse(validationService.isTeacherPresentInDB("Mike"));
    }

    @Test
    public void shouldReturnFalseIfStudentWithUsernameNotPresentInDB() {
        Student student = new Student();
        student.setUsername("Mike");

        given(studentService.getStudentByUsername("Mike"))
                .willReturn(null);
        Assert.assertFalse(validationService.isStudentPresentInDB("Mike"));
    }

    @Test
    public void shouldReturnTrueIfStudentWithUsernamePresentInDB() {
        Student student = new Student();
        student.setUsername("Mike");

        given(studentService.getStudentByUsername("Mike"))
                .willReturn(student);
        Assert.assertTrue(validationService.isStudentPresentInDB("Mike"));
    }

    @Test
    public void shouldReturnNotNullUser_Teacher() {
        Teacher user = new Teacher();
        user.setId(1L);

        given(validationService.isTeacherPresentInDB(1L)).willReturn(null);
        given(teacherService.getTeacherById(1L)).willReturn(user);

        Assert.assertNotNull(validationService.isUserExistById(1L));

    }

    @Test
    public void shouldReturnNotNullUser_Student() {
        Student user = new Student();
        user.setId(1L);

        given(validationService.isStudentPresentInDB(1L)).willReturn(null);
        given(studentService.getStudentById(1L)).willReturn(user);

        Assert.assertNotNull(validationService.isUserExistById(1L));
    }

    @Test
    public void shouldReturnNull() {

        given(validationService.isStudentPresentInDB(1L)).willReturn(null);
        given(validationService.isTeacherPresentInDB(1L)).willReturn(null);
        Assert.assertNull(validationService.isUserExistById(1L));
    }


    @Test
    public void shouldReturnTrueIfCourseWithIdPresentInDB() {
        Course course = new Course();
        course.setId(1L);

        given(courseService.getById(1L)).willReturn(course);
        Assert.assertTrue(validationService.isNewCourse(1L));

    }

    @Test
    public void shouldReturnFalseIfCourseWithIdNotPresentInDB() {
        Course course = new Course();
        course.setId(1L);

        given(courseService.getById(1L)).willReturn(null);
        Assert.assertFalse(validationService.isNewCourse(1L));

    }

    @Test
    public void shouldReturnTrueIfCourseWithCourseNamePresentInDB() {
        Course course = new Course();
        course.setCourseName("Math");

        given(courseService.getByCourseName("Math")).willReturn(course);
        Assert.assertTrue(validationService.isCoursePresentInDB("Math"));
    }

    @Test
    public void shouldReturnFalseIfCourseWithCourseNameNotPresentInDB() {
        Course course = new Course();
        course.setCourseName("Math");

        given(courseService.getByCourseName("Math")).willReturn(null);
        Assert.assertFalse(validationService.isCoursePresentInDB("Math"));
    }
}