package by.epam.nikita.controller;

import by.epam.nikita.domain.interfaces.User;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class ProfileController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private UserService userService;

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
            model.addAttribute("role", "student");
            model.addAttribute("user", student);
            if (student.getFilename() != null)
                model.addAttribute("file", student.getFilename());
            return "profile";
        } else if (validationService.isTeacher(authentication)) {
            Teacher teacher = teacherService.getTeacherById(id);
            model.addAttribute("user", teacher);
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
        model.addAttribute("page", courseService.getAll(pageable));
        model.addAttribute("url", "/courses");
        model.addAttribute("student", studentService.getStudentById(studentId));
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
     * @throws IOException - in case of reading or writing errors
     */
    @PostMapping("profile/{id}")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam("file") MultipartFile file) {
        String resultFilename = user.getFilename();
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            resultFilename = uuidFile + "." + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        userService.updateProfile(user, password, email, resultFilename);
        return "redirect:/profile/{id}";
    }
}
