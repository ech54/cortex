package lu.cortex.registry.container.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lu.cortex.registry.container.service.ServiceRegistry;
import lu.cortex.model.DomainDefinition;

@RestController
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ServiceRegistry serviceRegistry;

    @RequestMapping(value = "/api/domains/all", method = RequestMethod.GET)
    public List<DomainDefinition> getDomains() {
        LOGGER.info("invoke /api/domains/all");
        final List<DomainDefinition> domains = serviceRegistry.getAllDomains();
        LOGGER.info("retrieve domains: " + domains);
        return domains;
    }
}
