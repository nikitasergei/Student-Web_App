package by.epam.nikita.service.serviceInterfaces;

import by.epam.nikita.domain.models.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherService {

    /**
     * Method try to get all Teachers from database
     *
     * @param pageable - parameter for pagination information
     * @return list of teachers as a page(s)
     */
    Page<Teacher> getAllTeachers(Pageable pageable);

    /**
     * Method try to get Teacher from database by {@param teacherName}
     *
     * @param teacherName - teacher's name
     * @return teacher with {@param teacherName)
     */
    Teacher getTeacherByUsername(String teacherName);

    /**
     * Method try to get Teacher from database by {@param id}
     *
     * @param teacherId - teacher's id
     * @return teacher with {@param id} or else {null}
     */
    Teacher getTeacherById(Long teacherId);

    /**
     * This method check is {@param teacher} exists as record in database and add {@param teacher} to database
     * if it's not
     *
     * @param teacher - student which must be added to database
     * @return false if {@param teacher} already exists and true if was added {@param teacher}
     */
    boolean addUser(Teacher teacher);
}
