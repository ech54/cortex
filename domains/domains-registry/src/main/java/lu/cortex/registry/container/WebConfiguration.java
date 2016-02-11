package lu.cortex.registry.container;

import org.apache.commons.lang.CharEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import lu.cortex.DomainCommonConfiguration;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.endpoints.EndpointPath;

@Configuration
@Import(value = {DomainCommonConfiguration.class, ContainerConfiguration.class})
@ComponentScan("lu.cortex")
public class WebConfiguration {


    @Bean(name="registry.install.endpoint")
    public Endpoint registryInstallEndpoint() {
        return new EndpointDefault("registry-domain", EndpointPath.buildPath("registry-domain", "domain-definition", "install"));
    }


    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        templateResolver.setOrder(1);
        return templateResolver;
    }

}
