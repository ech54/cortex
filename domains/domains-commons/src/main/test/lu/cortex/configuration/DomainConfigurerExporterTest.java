/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.cortex.configuration;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <class_description>
 */
@ContextConfiguration(classes = {DomainConfigurerExporterTest.TestingConf.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class DomainConfigurerExporterTest {

    @Configuration()
    public class TestingConf {

    }


}
