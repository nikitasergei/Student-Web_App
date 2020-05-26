package by.epam.nikita.controller;

import by.epam.nikita.DTO.models.CourseDTO;
import by.epam.nikita.DTO.models.StudentDTO;
import by.epam.nikita.service.serviceInterfaces.Convertor;
import by.epam.nikita.service.serviceInterfaces.ErrorHandler;
import by.epam.nikita.service.serviceInterfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private Convertor converter;

    @Autowired
    private ErrorHandler errorHandler;

    /**
     * Method for get list of Teachers from database as a page(s) in view "students"
     *
     * @param model    - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable - parameter for pagination information
     * @return - all teachers from database as a page(s)
     */
    @GetMapping("/students")
    public String getTeachersList(Model model,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<StudentDTO> page = studentService.getAllStudents(pageable)
                .map(student -> converter.convertToDto(student));

        model.addAttribute("page", page);
        model.addAttribute("url", "/students");

        return "/students";
    }

    /**
     * Method {@return courses} view of student with {@param id}
     *
     * @param model - definition of holder for model attributes. Using for adding attributes to the model.
     * @param id    - student's id for search
     **/
    @GetMapping("studentCourses/{id}")
    public String showStudentsCourses(Model model,
                                      @PathVariable Long id) {
        Page<CourseDTO> page = studentService.getByStudentId(id)
                .map(course -> converter.convertToDto(course));

        model.addAttribute("page", page);
        model.addAttribute("url", "/courses");
        return "courses";
    }

    /**
     * Method try to write current Student with {@param studentId} on a Course with {@param courseId}
     *
     * @param model     - definition of holder for model attributes. Using for adding attributes to the model.
     * @param studentId - current Student id
     * @param courseId  - Course's id which Student choose for study
     * @param pageable  - parameter for pagination information
     * @return page with all Students or page with error message
     */
    @GetMapping("{studentId}/addStudentAtCourse/{courseId}")
    public String addStudentInCourse(Model model,
                                     @PathVariable Long studentId,
                                     @PathVariable Long courseId,
                                     @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        if (studentService.isStudentStartLearnCourse(studentId, courseId)) {
            Page<StudentDTO> page = studentService.getAllStudents(pageable)
                    .map(student -> converter.convertToDto(student));
            model.addAttribute("page", page);
            model.addAttribute("url", "/students");
            return "/students";
        } else {
            errorHandler.withError(model, "Something is wrong! Try again!" );
            return "coursesForEnroll";
        }
    }
}


