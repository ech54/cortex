package lu.wf.admin.controller;


import lu.cortex.spi.ResultEndpoint;
import lu.cortex.spi.ResultStatus;
import lu.wf.admin.AdminApplication;
import lu.wf.admin.model.Activity;
import lu.wf.admin.model.SimpleWorkflow;
import lu.wf.admin.repository.ActivityRepository;
import lu.wf.admin.repository.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static lu.wf.admin.AdminApplicationTestUtils.buildActivity;
import static lu.wf.admin.AdminApplicationTestUtils.buildRequest;
import static lu.wf.admin.AdminApplicationTestUtils.buildWorkflow;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AdminApplication.class)
@WebIntegrationTest({"server.port=0"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WorkflowControllerTest {

    @Value("${local.server.port}")
    int port;

    RestTemplate template = new TestRestTemplate();

    @Test
    public void testPostSuccessful() throws Exception {
        template.delete("http://localhost:"+ port + "activity/delete/0");
        template.delete("http://localhost:"+ port + "workflow/delete/0");

        ResultEndpoint result = template.postForObject("http://localhost:" + port + "/activity/create", buildRequest(buildActivity("ActivityA")), ResultEndpoint.class);
        assertEquals(ResultStatus.SUCCESS, result.getStatus());
        result = template.postForObject("http://localhost:" + port + "/activity/create", buildRequest(buildActivity("ActivityB")), ResultEndpoint.class);
        assertEquals(ResultStatus.SUCCESS, result.getStatus());
        result = template.postForObject("http://localhost:" + port + "/activity/create", buildRequest(buildActivity("ActivityC")), ResultEndpoint.class);
        assertEquals(ResultStatus.SUCCESS, result.getStatus());
        SimpleWorkflow simpleWorkflow = buildWorkflow("MonWrkflow","ActivityC","ActivityA","ActivityB","ActivityC");
        result = template.postForObject("http://localhost:" + port + "/workflow/create", buildRequest(simpleWorkflow), ResultEndpoint.class);
        assertEquals(ResultStatus.SUCCESS, result.getStatus());

    }

    @Test
    public void testPost() throws Exception {
        HttpEntity request = buildRequest(buildWorkflow("no"));

        ResultEndpoint resultEndpoint = template.postForObject("http://localhost:" + port + "/workflow/create", request, ResultEndpoint.class);
        assertEquals(ResultStatus.FAILURE, resultEndpoint.getStatus());

        request = buildRequest(buildWorkflow("no","way"));
        resultEndpoint = template.postForObject("http://localhost:" + port + "/workflow/create", request, ResultEndpoint.class);
        assertEquals(ResultStatus.FAILURE, resultEndpoint.getStatus());
    }

    //@Test
    public void testDelete() throws Exception {
        template.delete("http://localhost:" + port + "/workflow/delete/0");
    }

}
