package by.epam.nikita.service.serviceInterfaces;

public interface MailSender {

    /**
     * This method send mail with content {@param message} to {@param subject} on {@param emailTo}
     *
     * @param emailTo - address to send mail
     * @param subject - subject of mail
     * @param message - message of mail
     */
    void send(String emailTo, String subject, String message);
}
