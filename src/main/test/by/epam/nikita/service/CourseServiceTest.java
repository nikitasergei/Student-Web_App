package by.epam.nikita.service;

import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.repository.CourseRepo;
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

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @MockBean
    private CourseRepo courseRepo;

    @MockBean
    private ValidationService validationService;

    @Test
    public void shouldReturnNotNullCourseById() {
        Course course = new Course();
        course.setId(1L);

        Mockito.doReturn(Optional.of(course)).when(courseRepo).findById(1L);

        Assert.assertNotNull(courseService.getById(1L));
    }

    @Test
    public void shouldReturnNullCourseById() {
        Mockito.doReturn(Optional.empty()).when(courseRepo).findById(1L);

        Assert.assertNull(courseService.getById(1L));
    }

    @Test
    public void shouldReturnPageOfTeachersCoursesByTeacherId() {
        Teacher teacher = new Teacher();
        Course course = new Course();
        Course course1 = new Course();
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        courses.add(course1);
        teacher.setId(1L);
        teacher.setCourseSet(courses);

        Mockito.doReturn(new PageImpl<>(courses)).when(courseRepo).findByTeacherId(1L, Pageable.unpaged());

        Assert.assertEquals(courseService.getByTeacherId(1L, Pageable.unpaged()), new PageImpl<>(courses));
    }

    @Test
    public void shouldReturnNotNullCourseByName() {
        Course course = new Course();
        course.setCourseName("Math");

        Mockito.doReturn(course).when(courseRepo).findByCourseName("Math");

        Assert.assertNotNull(courseService.getByCourseName("Math"));

    }

    @Test
    public void shouldReturnNullByCourseName() {

        Mockito.doReturn(null).when(courseRepo).findByCourseName("Math");

        Assert.assertNull(courseService.getByCourseName("Math"));

    }

    @Test
    public void shouldReturnTrue() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Math");

        given(validationService.isNewCourse(1L)).willReturn(true);
        given(validationService.isCoursePresentInDB("Math")).willReturn(true);

        Assert.assertTrue(courseService.saveCourse(course));
    }

    @Test
    public void shouldReturnFalseBecauseCourseAlreadyExist() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Math");

        Mockito.doReturn(true).when(validationService).isNewCourse(1L);
        Mockito.doReturn(false).when(validationService).isCoursePresentInDB("Math");

        Assert.assertFalse(courseService.saveCourse(course));
    }
}