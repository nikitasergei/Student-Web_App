package by.epam.nikita.controller;

import by.epam.nikita.DTO.models.CourseDTO;
import by.epam.nikita.DTO.models.StudentDTO;
import by.epam.nikita.DTO.models.TeacherDTO;
import by.epam.nikita.domain.interfaces_marker.User;
import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.service.implementation.ValidationService;
import by.epam.nikita.service.serviceInterfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProfileController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserService userService;

    @Autowired
    private Convertor converter;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Method show page with information about current User
     *
     * @param id             - current User 's id
     * @param model          - definition of holder for model attributes. Using for adding attributes to the model.
     * @param authentication - represents the token for an authentication request or for an authenticated principal
     *                       once the request has been processed by the method.
     * @return profile page or startPage if something goes wrong
     */
    @GetMapping("profile/{id}")
    public String getProfile(@PathVariable Long id,
                             Model model,
                             Authentication authentication) {
        if (validationService.isStudent(authentication)) {
            Student student = studentService.getStudentById(id);
            StudentDTO studentDTO = converter.convertToDto(student);
            model.addAttribute("role", "student");
            model.addAttribute("user", studentDTO);
            if (student.getFilename() != null)
                model.addAttribute("file", student.getFilename());
            return "profile";
        } else if (validationService.isTeacher(authentication)) {
            Teacher teacher = teacherService.getTeacherById(id);
            TeacherDTO teacherDTO = converter.convertToDto(teacher);
            model.addAttribute("user", teacherDTO);
            model.addAttribute("role", "teacher");
            if (teacher.getFilename() != null)
                model.addAttribute("file", teacher.getFilename());
            return "profile";
        } else return "startPage";
    }


    /**
     * Method show page(s) with courses which User with {@param studentId} can study
     *
     * @param model     - definition of holder for model attributes. Using for adding attributes to the model.
     * @param studentId - current Student's id
     * @param pageable  - parameter for pagination information
     * @return page(s) with list of courses for student choice
     */
    @GetMapping("/{studentId}/addStudentAtCourse")
    public String getCoursesForEnroll(Model model,
                                      @PathVariable Long studentId,
                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Course> courses = courseService.getAll(pageable);
        Page<CourseDTO> coursesDTO = courses.map(course -> converter.convertToDto(course));
        Student studentById = studentService.getStudentById(studentId);
        StudentDTO studentDTO = converter.convertToDto(studentById);
        model.addAttribute("page", coursesDTO);
        model.addAttribute("url", "/courses");
        model.addAttribute("student", studentDTO);
        return "coursesForEnroll";
    }

    /**
     * Method update profile of current {@param user} with information from request parameters
     * If some request params are empty, some fields will not  be update
     *
     * @param user     - current User
     * @param password - new password if it's not empty
     * @param email    - new email if not empty
     * @param file     - new file if not empty
     * @return profile page of current User with updated information
     */
    @PostMapping("profile/{id}")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam("file") MultipartFile file) {
        String resultFilename = userService.generateFileName(user, file, uploadPath);
        userService.updateProfile(user, password, email, resultFilename);
        return "redirect:/profile/{id}";
    }
}
