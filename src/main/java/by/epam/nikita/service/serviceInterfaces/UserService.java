package by.epam.nikita.service.serviceInterfaces;

import by.epam.nikita.domain.interfaces_marker.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {

    /**
     * This method check is teacher with {@param username} already exists and load them.
     *
     * @param username - name of Teacher which we try to load.
     * @return Teacher with {@param username}
     * @throws UsernameNotFoundException - if Teacher with {@param username} doesn't exist
     */
    UserDetails loadUserByUsername(String username);


    /**
     * This method check is {@param user} exists as record in database and add {@param user} to database
     * if it's not
     *
     * @param user - student which must be added to database
     * @return false if {@param user} already exists and true if was added {@param user}
     */
    User setUserDetails(User user);

    /**
     * Method prepare {@param user} to activate and save him in database
     *
     * @param user - user for activation
     * @return true if {@param user} was activated
     */
    boolean prepareToActivateUser(User user);

    /**
     * Method search user by {@param code} in database and try activate his account
     *
     * @param code - code for activate Student from his email
     * @return - false if records in table hasn't {@param code} and {@return true} if Student was successfully activated
     */
    boolean activateUser(String code);

    /**
     * Method defines which class belongs {@param user} and save him in database
     *
     * @param user - Student or Teacher to save
     * @return user - user which was saved in database
     */
    User saveUser(User user);

    /**
     * Method check, are there difference between {@param user}'s parameters and other method's parameters
     * If they are different, method set {@param user} new parameters and save {@param user} in database,
     * if there are no difference between them, method save {@param user} with old parameters
     *
     * @param user     - User which profile must be updated
     * @param password - password for update
     * @param email    - email for update
     * @param filename - file's name for update
     */
    void updateProfile(User user, String password, String email, String filename);

    /**
     *
     * @param user - User, who load {@param file}
     * @param file - a representation of an uploaded file received in a multipart request
     * @param path - path for create new File
     * @return -
     */
    String generateFileName(User user, MultipartFile file, String path);

}
