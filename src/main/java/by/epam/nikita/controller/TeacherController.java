package by.epam.nikita.controller;

import by.epam.nikita.DTO.models.CourseDTO;
import by.epam.nikita.DTO.models.TeacherDTO;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.service.serviceInterfaces.Convertor;
import by.epam.nikita.service.serviceInterfaces.CourseService;
import by.epam.nikita.service.serviceInterfaces.TeacherService;
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
public class TeacherController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private Convertor converter;

    /**
     * Method get all Teachers from database and {@return teachers} view with them
     *
     * @param model    - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable - parameter for pagination information
     * @return teachers view
     */
    @GetMapping("teachers")
    public String getTeachersList(Model model,
                                  @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<TeacherDTO> page = teacherService.getAllTeachers(pageable)
                .map(teacher -> converter.convertToDto(teacher));

        model.addAttribute("page", page);
        model.addAttribute("url", "/teachers");

        return "teachers";
    }

    /**
     * Using for get editTeacher view
     *
     * @return "editTeacher" by method GET
     */
    @GetMapping("editTeacher")
    public String addTeacher() {
        return "editTeacher";
    }

    /**
     * Method try to get Teacher from database by {@param id} end {@return} view for update with his parameters
     * or {@return teachers} view with all teachers as a page(s) if Teacher with {@param id} doesn't exist in database
     *
     * @param id       - id of Teacher for update
     * @param model    - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable - parameter for pagination information
     * @return - "editPage" or "teachers"
     */
    @GetMapping("editTeacher/{id}")
    public String updateTeacher(Model model,
                                @PathVariable Long id,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Teacher editTeacher = teacherService.getTeacherById(id);
        TeacherDTO teacher = converter.convertToDto(editTeacher);
        if (editTeacher != null) {
            model.addAttribute("teacher", teacher);
            return "editTeacher";
        } else {
            Page<TeacherDTO> page = teacherService.getAllTeachers(pageable)
                    .map(teacher1 -> converter.convertToDto(teacher1));
            model.addAttribute("page", page);
            model.addAttribute("url", "/teachers");
            return "teachers";
        }
    }

    /**
     * Method {@return courses} view with page(s) of teacher's courses with {@param id}
     *
     * @param id       - id of Teacher which courses must be showed
     * @param model    - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable - parameter for pagination information
     * @return "courses" view with information about courses of a teacher with {@param id}
     */
    @GetMapping("teacherCourses/{id}")
    public String showTeachersCourses(Model model,
                                      @PathVariable Long id,
                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<CourseDTO> page = courseService.getByTeacherId(id, pageable)
                .map(course -> converter.convertToDto(course));

        model.addAttribute("page", page);
        model.addAttribute("url", "/courses");
        return "courses";
    }
}