package by.epam.nikita.controller;

import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.service.CourseService;
import by.epam.nikita.service.TeacherService;
import by.epam.nikita.service.UserService;
import by.epam.nikita.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ValidationService validationService;

    /**
     * Method {@return all courses} from database as a page(s)
     *
     * @param model          - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable       - parameter for pagination information
     * @param authentication -represents the token for an authentication request or for an authenticated principal
     *                       once the request has been processed by the method
     * @return - all courses from database as a page(s)
     */
    @GetMapping("courses")
    public String listCourses(Model model,
                              Authentication authentication,
                              @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Course> page = courseService.getAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/courses");
        if (validationService.isTeacher(authentication))
            model.addAttribute("isTeacher", true);
        return "courses";
    }

    /**
     * Method just return view "editCourse" by GET method
     *
     * @param model    - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable - parameter for pagination information
     * @return
     */
    @GetMapping("course/add")
    public String addCourse(Model model,
                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Teacher> page = teacherService.getAllTeachers(pageable);
        model.addAttribute("page", page);
        return "editCourse";
    }

    /**
     * Method try get Course for update by {@param id} from database and {@return course} in view
     *
     * @param model    - definition of holder for model attributes. Using for adding attributes to the model.
     * @param id       - id of a course for update
     * @param pageable - parameter for pagination information
     * @return view "editCourse" with course for update
     */
    @GetMapping("editCourse/{id}")
    public String updateCourse(Model model,
                               @PathVariable Long id,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Course editCourse = courseService.getById(id);
        Page<Teacher> page = teacherService.getAllTeachers(pageable);
        if (editCourse != null) {
            model.addAttribute("course", editCourse);
            model.addAttribute("page", page);
        }
        return "editCourse";
    }

    /**
     * Method check validity of course, and teacher with {@param username} persistent in database and
     * set teacher with {@param username} as a parameter of {@param course}. If {@param course) or {@param username}
     * is not valid {@return editCourse} view with model contains errors.
     *
     * @param course        - valid course from view for add
     * @param username      - teacher's name for add to course attributes
     * @param bindingResult - instance of BindingResult interface for binding errors
     * @param model         - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable      - parameter for pagination information
     * @return view "editCourse" with errors or page(s) of courses
     **/
    @PostMapping("course/add")
    public String addCourse(@Valid Course course,
                            @RequestParam String username,
                            BindingResult bindingResult,
                            Model model,
                            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

        if (validationService.isCoursePresentInDB(course.getCourseName())) {
            errorsMap.put("courseNameError", "Course with name " + course.getCourseName() + " already exist!");
            if (!validationService.isTeacherPresentInDB(username)) {
                errorsMap.put("teacherError", "User with name " + username + " not found in database");
            }
        } else if (!validationService.isTeacherPresentInDB(username))
            errorsMap.put("teacherError", "User with name " + username + " not found in database");
        else {
            course.setTeacher(teacherService.getTeacherByUsername(username));
            return getCourse(course, bindingResult, model, pageable);
        }
        model.mergeAttributes(errorsMap);

        return "editCourse";
    }

    /**
     * Method try to update {@param course} with other parameters
     *
     * @param course        - valid course from view for update
     * @param username      - teacher's name for update course's attributes
     * @param bindingResult - instance of BindingResult interface for binding errors
     * @param model         - definition of holder for model attributes. Using for adding attributes to the model.
     * @param id            - course's id for update
     * @param pageable      - parameter for pagination information
     * @return "editCourse" view if parameters contains errors, or try to save {@param course} with new parameters
     */
    @PostMapping("editCourse/{id}")
    public String updateCourse(@Valid Course course,
                               @RequestParam String username,
                               BindingResult bindingResult,
                               Model model,
                               @PathVariable Long id,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        if (validationService.isTeacherPresentInDB(id)) {
            course.setTeacher(teacherService.getTeacherByUsername(username));
            return getCourse(course, bindingResult, model, pageable);
        } else {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            errorsMap.put("teacherError", "Teacher with name " + username + " not found in database");
            model.mergeAttributes(errorsMap);
            return "editCourse";
        }
    }

    /**
     * Method check validity of {@param course} and redirect on view "courses" if is valid or {@return editCourse} view
     * with errors if it's not.
     *
     * @param course        - valid course from view for get
     * @param bindingResult - instance of BindingResult interface for binding errors
     * @param model         - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable      - parameter for pagination information
     * @return "editCourse" view with errors or redirect on "courses" view
     */
    private String getCourse(@Valid Course course, BindingResult bindingResult, Model model,
                             @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Course> page = courseService.getAll(pageable);
        model.addAttribute("url", "/courses");
        model.addAttribute("page", page);
        if (bindingResult.hasErrors()) {
            model.addAttribute("page", page);
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "editCourse";
        } else {
            if (courseService.saveCourse(course)) {
                return "redirect:/courses";
            } else {
                model.addAttribute("page", page);
                model.addAttribute("savingReport", "Course with name like this already exist!");
                return "editCourse";
            }
        }
    }
}
