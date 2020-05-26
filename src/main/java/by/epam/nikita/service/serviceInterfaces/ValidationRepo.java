package by.epam.nikita.service.serviceInterfaces;

import by.epam.nikita.DTO.CaptchaResponseDto;
import by.epam.nikita.domain.interfaces_marker.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface ValidationRepo {

    /**
     * Check is {@param user} instance of Student.class
     *
     * @param user - user for check
     * @return true if user is instance of Student.class
     */
    boolean isStudent(User user);

    /**
     * Check is current User contains Roles.STUDENT
     *
     * @param authentication - represents the token for an authentication request or for an authenticated principal
     *                       once the request has been processed by the method
     * @return true if current User contains Roles.STUDENT
     */
    boolean isStudent(Authentication authentication);

    /**
     * Check is {@param user} instance of Teacher.class
     *
     * @param user - user for check
     * @return true if user is instance of Teacher.class
     */
    boolean isTeacher(User user);

    /**
     * Check is current User contains Roles.TEACHER
     *
     * @param authentication - represents the token for an authentication request or for an authenticated principal
     *                       once the request has been processed by the method
     * @return true if current User contains Roles.TEACHER
     */
    boolean isTeacher(Authentication authentication);

    /**
     * Check by {@param id} is User present in database like Teacher
     *
     * @param id - id of User which must be checked
     * @return true if he present in database
     */
    boolean isTeacherPresentInDB(Long id);

    /**
     * Check by {@param name} is User present in database like Teacher
     *
     * @param name - name of User which must be checked
     * @return true if he present in database
     */
    boolean isTeacherPresentInDB(String name);

    /**
     * Check by {@param id} is User present in database like Student
     *
     * @param id - id of User which must be checked
     * @return true if he present in database
     */
    boolean isStudentPresentInDB(Long id);

    /**
     * Check by {@param name} is User present in database like Student
     *
     * @param name - name of User which must be checked
     * @return true if he present in database
     */
    boolean isStudentPresentInDB(String name);

    /**
     * Check is User with {@param id} exist in database like Student or Teacher and return him like User
     *
     * @param id - id of User which must be checked
     * @return User from database or else null
     */
    User isUserExistById(Long id);

    /**
     * Check by {@param id} is Course present in database
     *
     * @param id - id of Course which must be checked
     * @return true if Course present in database
     */
    boolean isNewCourse(Long id);

    /**
     * Check by {@param name} is Course present in database
     *
     * @param name - name of Course which must be checked
     * @return true if Course present in database
     */
    boolean isCoursePresentInDB(String name);

    /**
     * Check is {@param fail} valid
     *
     * @param file - data to check
     * @return true if is valid, otherwise @return false
     */
    boolean isFailValid(MultipartFile file);

    /**
     * Method check input params and fill {@param model}  in accordance with them
     *
     * @param model              - definition of holder for model attributes. Using for adding attributes to the model.
     * @param captchaResponseDto - parameter for check captcha
     * @param passwordConfirm    - parameter for confirm password
     * @param user               - user which must be saved in database
     * @return - filled Model
     */
    Model validateState(Model model,
                        CaptchaResponseDto captchaResponseDto,
                        String passwordConfirm,
                        User user);
}


