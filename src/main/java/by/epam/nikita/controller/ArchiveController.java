package by.epam.nikita.controller;

import by.epam.nikita.domain.interfaces.User;
import by.epam.nikita.domain.models.Archive;
import by.epam.nikita.domain.models.Course;
import by.epam.nikita.domain.models.Student;
import by.epam.nikita.domain.models.Teacher;
import by.epam.nikita.service.ArchiveService;
import by.epam.nikita.service.CourseService;
import by.epam.nikita.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class ArchiveController {
    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ValidationService validationService;

    /**
     * Method {@return editArchive} view with model contains course with {@param id},
     * all students which study this course and teacher of this course from database. This data are using for choose
     * parameters for archive creating
     *
     * @param model    - definition of holder for model attributes. Using for adding attributes to the model.
     * @param id       - selected course's id
     * @param pageable - parameter for pagination information
     * @return
     */
    @GetMapping("editArchive/{id}")
    public String addArchiveNote(Model model,
                                 @PathVariable Long id,
                                 @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Course course = courseService.getById(id);
        Teacher teacher = course.getTeacher();
        Set<Student> students = course.getStudents();
        model.addAttribute("course", course);
        model.addAttribute("teacher", teacher);
        model.addAttribute("students", students);
        return "editArchive";
    }


    /**
     * Method POST for create new Archive by method's parameters and save this Archive in database
     *
     * @param courseName  - course's name to create an archive note
     * @param teacherName - teacher's name to create an archive note
     * @param studentName - student's name to create an archive note
     * @param rating      - rating of student from this archive note
     * @param model       - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable    - parameter for pagination information
     * @return - try to save new created archive and show "archive" view.
     */
    @PostMapping("editArchive")
    public String addArchive(@RequestParam String courseName,
                             @RequestParam String teacherName,
                             @RequestParam String studentName,
                             @RequestParam Integer rating,
                             Model model,
                             @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Archive archive = archiveService.setArchiveAttributes(courseName, teacherName, studentName, rating);
        return saveArchive(archive, model, pageable);
    }

    /**
     * Method try to save {@param archive} and {@return archive} view.
     * If archive was successfully saved method {@return page(s) of archives}
     * If archive wasn't saved method {@return view with error message}
     *
     * @param archive  - archive to save
     * @param model    - definition of holder for model attributes. Using for adding attributes to the model.
     * @param pageable - parameter for pagination information
     * @return - archive view
     */
    private String saveArchive(@ModelAttribute Archive archive,
                               Model model,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Archive> page = archiveService.getAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/archive");
        if (archiveService.saveArchive(archive)) {
            return "redirect:/archive";
        } else {
            model.addAttribute("savingReport", "Something go wrong. Try later!");
            model.addAttribute("usingHistory", archive);
            return "archive";
        }
    }

    /**
     * Method try to get and show view with all archive note for course with {@param id}
     *
     * @param model          - definition of holder for model attributes. Using for adding attributes to the model.
     * @param courseId       - course's id from request
     * @param pageable       - parameter for pagination information
     * @param authentication - represents the token for an authentication request or for an authenticated principal
     *                       once the request has been processed by the method
     * @return - page(s) with Courses's Archives
     */
    @GetMapping("archive/{courseId}")
    public String showCourseArchive(Model model,
                                    @PathVariable Long courseId,
                                    Authentication authentication,
                                    @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        model.addAttribute("page", archiveService.getCourseArchive(courseId, pageable));
        model.addAttribute("course", courseService.getById(courseId));
        model.addAttribute("url", "/archive");
        if (validationService.isTeacher(authentication)) {
            model.addAttribute("isTeacher", true);
        }
        return "archive";
    }

    /**
     * Method return page(s) of User's with {@param id} Archives
     *
     * @param model          - definition of holder for model attributes. Using for adding attributes to the model.
     * @param id             - User's id which Archives we looking for
     * @param pageable       - parameter for pagination information
     * @param authentication - represents the token for an authentication request or for an authenticated principal
     *                       once the request has been processed by the method
     * @return - archive
     */
    @GetMapping("/{id}/my-archives")
    public String showAllArchivesByUserId(Model model,
                                          @PathVariable Long id,
                                          Authentication authentication,
                                          @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Archive> page = null;
        User existedUser = validationService.isUserExistById(id);
        if (existedUser == null) {
            model.addAttribute("message", "You haven't any archives notes");
            model.addAttribute("messageType", "warning");
            return "archive";
        } else if (validationService.isStudent(authentication))
            page = archiveService.getAllByStudentId(id, pageable);
        else if (validationService.isTeacher(authentication))
            page = archiveService.getAllByTeacherId(id, pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "archive");
        return "archive";
    }

    /**
     * Method POST which take parameters to create new Archive
     *
     * @param model       - definition of holder for model attributes. Using for adding attributes to the model.
     * @param courseId    - new Archive course's id
     * @param teacherName - new Archive teacher's name
     * @param studentName - new Archive student's name
     * @param rating      - new Archive student's rating
     * @param pageable    - parameter for pagination information
     * @return archive page
     */
    @PostMapping("editArchive/{courseId}")
    public String addNewArchive(Model model,
                                @PathVariable Long courseId,
                                @RequestParam String teacherName,
                                @RequestParam String studentName,
                                @RequestParam String rating,
                                @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Course courseFromDB = courseService.getById(courseId);
        if (courseFromDB != null && validationService.isStudentPresentInDB(studentName)) {
            Archive newArchive = archiveService.setArchiveAttributes(courseFromDB.getCourseName(), teacherName,
                    studentName, Integer.parseInt(rating));
            if (archiveService.saveArchive(newArchive)) {
                Page<Archive> page = archiveService.getCourseArchive(courseId, pageable);
                model.addAttribute("page", page);
                model.addAttribute("url", "archive");
            }
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Wrong data input! There aren't students which study this course! ");
        }
        return "archive";
    }
}
