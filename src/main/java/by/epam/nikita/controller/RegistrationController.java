package by.epam.nikita.controller;

import by.epam.nikita.DTO.CaptchaResponseDto;
import by.epam.nikita.domain.interfaces.User;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.service.StudentService;
import by.epam.nikita.service.TeacherService;
import by.epam.nikita.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Method for GET "home" view
     *
     * @return "home" view
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * Method redirect on "startPage" view from "/" path
     *
     * @return startPage view
     */
    @GetMapping("/")
    public String homePage() {
        return "redirect:startPage";
    }

    /**
     * Method for GET "startPage" view
     *
     * @return startPage view
     */
    @GetMapping("startPage")
    public String startPage() {
        return "startPage";
    }

    /**
     * Method for GET a "registration" view
     *
     * @return "registration" view
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    /**
     * Method check validity of incoming parameters and {@return registration} view with errors if it has errors
     * or {@return home} view if {@param user} was successfully added to database
     *
     * @param passwordConfirm - parameter for confirm password
     * @param captchaResponse - parameter for check captcha
     * @param userRole        - role of {@param user}
     * @param user            - user which must be saved in database
     * @param bindingResult   - instance of BindingResult interface for binding errors
     * @param model           - definition of holder for model attributes. Using for adding attributes to the model.
     * @return "registration" view with errors or "home" view
     */
    @PostMapping("registration")
    public String addUser(
            @RequestParam("passwordConfirm") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @RequestParam("userRole") String userRole,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto captchaResponseDto = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (!captchaResponseDto.isSuccess()) {
            model.addAttribute("captchaError", "Fill Captcha");
        }
        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);

        if (isConfirmEmpty) {
            model.addAttribute("passwordConfirmError", "Password confirmation can't be empty");
        }
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Passwords is different!");
        }
        if (isConfirmEmpty || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration";
        }
        if (!isAdded(userRole, user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        } else {
            model.addAttribute("message", "You must activate your account. Activation link sent to your email.");
            model.addAttribute("messageType", "info");
            return "home";
        }
    }

    /**
     * Method choose class of {@param user} depending of {@param userRole}, set attributes and add entity to database
     *
     * @param userRole - parameter role of {@param user}
     * @param user     - user to add to database
     * @return true if entity was successfully added and {@return false} otherwise
     */
    private boolean isAdded(@RequestParam("userRole") String userRole, @Valid User user) {
        switch (userRole) {
            case "student": {
                saveUserLikeAStudent(user);
                return true;
            }
            case "teacher": {
                saveUserLikeATeacher(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Method set {@param user} some attributes and save him like a Student
     *
     * @param user - user for save
     * @return true if user was successfully saved like a Student
     */
    private boolean saveUserLikeAStudent(User user) {
        Student student = new Student();
        student.setUsername(user.getUsername());
        student.setPassword(user.getPassword());
        student.setEmail(user.getEmail());
        return studentService.addStudent(student);
    }

    /**
     * Method set {@param user} some attributes and save him like a Teacher
     *
     * @param user - user for save
     * @return true if user was successfully saved like a Teacher
     */
    private boolean saveUserLikeATeacher(User user) {
        Teacher teacher = new Teacher();
        teacher.setUsername(user.getUsername());
        teacher.setPassword(user.getPassword());
        teacher.setEmail(user.getEmail());
        return teacherService.addUser(teacher);
    }

    /**
     * Method search user with {@param code} in database and activate him.
     *
     * @param model - definition of holder for model attributes. Using for adding attributes to the model.
     * @param code  - code for activate  user
     * @return "home" view with message about successful activation or {@return "registration"} view with error message
     */
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {

        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
            return "home";
        } else {
            model.addAttribute("messageType", "warning");
            model.addAttribute("message", "Activation code is not found!");
            return "registration";
        }
    }
}