package beight.eksamenkea.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public String handleException(Exception e, Model model, HttpServletRequest request) {

        String exceptionName = e.getClass().getSimpleName();
        String exceptionMessage = e.getMessage();

        // Handle not logged in, see ProjectController.addAttributes
        if (exceptionName.equals("ServletRequestBindingException")
                && exceptionMessage.equals("Missing session attribute 'userProfile' of type UserProfile")) {
            // When attempting to reach endpoints in ProjectController while not logged in
            model.addAttribute("url", request.getRequestURI()); // Go to the requested page after login
            model.addAttribute("message", "Log in to access the requested page.");
            return "login";
        }

        // Print exception info to console unless 404
        boolean serverError = true;
        if (exceptionName.equals("NoResourceFoundException")) {
            serverError = false;
        } else {
            e.printStackTrace();
        }

        // Return html that shows exception info (for debugging problems specific to Azure)
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        model.addAttribute("serverError", serverError);
        model.addAttribute("name", exceptionName);
        model.addAttribute("message", exceptionMessage);
        model.addAttribute("stackTrace", sw.toString());
        return "exception";
    }

}
