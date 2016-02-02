package lu.cortex.registry.container.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping(value = "/echo", method = RequestMethod.GET)
    public String echo() {
        return "echo";
    }
}
