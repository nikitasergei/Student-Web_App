package by.epam.nikita.service.serviceInterfaces;

import org.springframework.ui.Model;

public interface ErrorHandler {

    Model withError(Model model, String message);

    Model sendInfo(Model model, String message);

    Model withSuccess(Model model, String message);

}
