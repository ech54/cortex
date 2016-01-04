package lu.wf.admin.controller;

import lu.cortex.spi.ResultEndpoint;
import lu.cortex.spi.ResultStatus;
import lu.wf.admin.AdminApplication;
import lu.wf.admin.model.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static lu.wf.admin.AdminApplicationTestUtils.buildActivity;
import static lu.wf.admin.AdminApplicationTestUtils.buildRequest;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AdminApplication.class)
@WebIntegrationTest({"server.port=0"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActivityControllerTest {

    @Value("${local.server.port}")
    int port;

    RestTemplate template = new TestRestTemplate();

    @Test
    public void testPostSuccessful() throws Exception {
        template.delete("http://localhost:"+ port + "activity/delete/0");
        HttpEntity request = buildRequest(buildActivity("Activity"));

        ResultEndpoint resultEndpoint = template.postForObject("http://localhost:"+port+"/activity/create", request, ResultEndpoint.class);

        assertEquals(ResultStatus.SUCCESS, resultEndpoint.getStatus());
    }

    @Test
    public void testPost() throws Exception {
        template.delete("http://localhost:"+ port + "activity/delete/0");
        HttpEntity request = buildRequest(buildActivity("activity"));

        ResultEndpoint resultEndpoint = template.postForObject("http://localhost:"+port+"/activity/create", request, ResultEndpoint.class);
        assertEquals(ResultStatus.SUCCESS, resultEndpoint.getStatus());

        resultEndpoint = template.postForObject("http://localhost:"+port+"/activity/create", request, ResultEndpoint.class);
        assertEquals(ResultStatus.FAILURE, resultEndpoint.getStatus());
    }

    @Test
    public void testDelete() throws Exception {
        template.delete("http://localhost:"+port+"/activity/delete/0");
    }
}