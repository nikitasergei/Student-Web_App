package by.epam.nikita.service.implementation;

import by.epam.nikita.domain.models.Archive;
import by.epam.nikita.repository.ArchiveRepo;
import by.epam.nikita.service.serviceInterfaces.ArchiveService;
import by.epam.nikita.service.serviceInterfaces.CourseService;
import by.epam.nikita.service.serviceInterfaces.StudentService;
import by.epam.nikita.service.serviceInterfaces.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArchiveServiceImpl implements ArchiveService {

    @Autowired
    private ArchiveRepo archiveRepo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    /**
     * Method try to get archive note by {@param id}
     *
     * @param id - archive note id
     * @return - archive from database or @null if archive is not present ib database
     */
    @Override
    public Archive getById(Long id) {
        return archiveRepo.findById(id).orElse(null);
    }

    /**
     * Method return all archives from database as a page(s)
     *
     * @param pageable - parameter for pagination information
     * @return all archives from database as a page(s)
     */
    @Override
    public Page<Archive> getAll(Pageable pageable) {
        return archiveRepo.findAll(pageable);
    }

    /**
     * Method return page(s) of Archives of Student with {@param id}
     *
     * @param id       - id of student which archives we are looking for
     * @param pageable - parameter for pagination information
     * @return page(s) of Student's Archives
     */
    @Override
    public Page<Archive> getAllByStudentId(Long id, Pageable pageable) {
        return archiveRepo.findAllByStudentId(id, pageable);
    }

    /**
     * Method return page(s) of Archives of Teacher with {@param id}
     *
     * @param id       - id of teacher which archives we are looking for
     * @param pageable - parameter for pagination information
     * @return page(s) of Teacher's Archives
     */
    @Override
    public Page<Archive> getAllByTeacherId(Long id, Pageable pageable) {
        return archiveRepo.findAllByTeacherId(id, pageable);
    }

    /**
     * Method try to get list archives of course with {@param id} as a page(s)
     *
     * @param courseId - id of course
     * @param pageable - parameter for pagination information
     * @return list of archive notes as a page(s)
     */
    @Override
    public Page<Archive> getCourseArchive(Long courseId, Pageable pageable) {
        return archiveRepo.findAllByCourseId(courseId, pageable);
    }

    /**
     * Method saves {@param archive}
     *
     * @param archive - Archive which must be saved
     * @return - true if {@param archive} was successfully saved and false otherwise
     */
    @Override
    public boolean saveArchive(Archive archive) {
        if (archive.getId() == null) {
            archiveRepo.save(archive);
            return true;
        } else return false;
    }

    /**
     * Method create new instance of Archive.class from method parameters
     *
     * @param courseName  - course's name of archive note
     * @param teacherName - teacher's name of archive note
     * @param studentName - student's name of archive note
     * @param rating      - student's rating in this student note
     * @return - Archive with set attributes
     */
    @Override
    public Archive setArchiveAttributes(String courseName, String teacherName, String studentName, Integer rating) {
        Archive archive = new Archive();
        archive.setCourse(courseService.getByCourseName(courseName));
        archive.setTeacher(teacherService.getTeacherByUsername(teacherName));
        archive.setStudent(studentService.getStudentByUsername(studentName));
        archive.setRating(rating);
        return archive;
    }
}
