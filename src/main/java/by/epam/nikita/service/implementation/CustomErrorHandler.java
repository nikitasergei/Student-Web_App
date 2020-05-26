package by.epam.nikita.service.implementation;

import by.epam.nikita.service.serviceInterfaces.ErrorHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CustomErrorHandler implements ErrorHandler {

    public Model withError(Model model, String message) {
        model.addAttribute("message", message);
        model.addAttribute("messageType", "warning");
        return model;
    }

    public Model sendInfo(Model model, String message) {
        model.addAttribute("message", message);
        model.addAttribute("messageType", "info");
        return model;
    }

    public Model withSuccess(Model model, String message) {
        model.addAttribute("message", message);
        model.addAttribute("messageType", "success");
        return model;
    }
}
