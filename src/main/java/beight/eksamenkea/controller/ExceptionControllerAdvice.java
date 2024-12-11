package beight.eksamenkea.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public String handleException(Exception e, Model model) {
        if (e.getMessage().equals("Not logged in.")) return "redirect:/";
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        model.addAttribute("message", sw.toString());
        e.printStackTrace();
        return "exception";
    }

}
