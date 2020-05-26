package by.epam.nikita.service.serviceInterfaces;

import by.epam.nikita.domain.models.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArchiveService {

    /**
     * Method try to get archive note by {@param id}
     *
     * @param id - archive note id
     * @return - archive from database or @null if archive is not present ib database
     */
    Archive getById(Long id);

    /**
     * Method return all archives from database as a page(s)
     *
     * @param pageable - parameter for pagination information
     * @return all archives from database as a page(s)
     */
    Page<Archive> getAll(Pageable pageable);

    /**
     * Method return page(s) of Archives of Student with {@param id}
     *
     * @param id       - id of student which archives we are looking for
     * @param pageable - parameter for pagination information
     * @return page(s) of Student's Archives
     */
    Page<Archive> getAllByStudentId(Long id, Pageable pageable);

    /**
     * Method return page(s) of Archives of Teacher with {@param id}
     *
     * @param id       - id of teacher which archives we are looking for
     * @param pageable - parameter for pagination information
     * @return page(s) of Teacher's Archives
     */
    Page<Archive> getAllByTeacherId(Long id, Pageable pageable);

    /**
     * Method try to get list archives of course with {@param id} as a page(s)
     *
     * @param courseId - id of course
     * @param pageable - parameter for pagination information
     * @return list of archive notes as a page(s)
     */
    Page<Archive> getCourseArchive(Long courseId, Pageable pageable);

    /**
     * Method saves {@param archive}
     *
     * @param archive - Archive which must be saved
     * @return - true if {@param archive} was successfully saved and false otherwise
     */
    boolean saveArchive(Archive archive);

    /**
     * Method create new instance of Archive.class from method parameters
     *
     * @param courseName  - course's name of archive note
     * @param teacherName - teacher's name of archive note
     * @param studentName - student's name of archive note
     * @param rating      - student's rating in this student note
     * @return - Archive with set attributes
     */
    Archive setArchiveAttributes(String courseName,
                                 String teacherName,
                                 String studentName,
                                 Integer rating);
}
