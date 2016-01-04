package lu.engine.core.definition;

import lu.cortex.endpoints.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.engine.core.CoreConfiguration;
import lu.engine.core.model.ProcessStepDefinition;
import lu.engine.core.repository.ProcessStepDefinitionRepository;
import lu.engine.core.repository.ProcessStepRepository;
import lu.wf.model.StepDefinition;
import lu.wf.model.WorkflowDefinition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfiguration.class)
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class WorkflowDefinitionTestCase {

    @Autowired
    ObjectMapper jsonMapper;

    @Autowired
    ProcessStepDefinitionRepository processStepDefinitionRepository;

    @Autowired
    ProcessStepRepository processStepRepository;

    @Test
    public void createWorkflowDefinition() throws Exception {

        final WorkflowDefinition definition = buildSubscriptionProcess();
        System.out.println(jsonMapper.writer().withDefaultPrettyPrinter().writeValueAsString(definition));
        final ProcessStepDefinition step = buildProcessDefinition(definition);
        System.out.println(step);
        processStepDefinitionRepository.save(step);
    }

    @Test
    public void findProcessByName() {
        final List<ProcessStepDefinition> definitions = processStepDefinitionRepository.findByProcess("policy", "subscription");

        definitions.stream().forEach(System.out::println);
    }

    @Test
    public void findNextElement() {
        /*
        MATCH (current:ProcessStepDefinition)<-[:CONFIG]-(next) WHERE current.id = {qId} RETURN next
        // Actors who acted in a Matrix movie:
        MATCH (movie:Movie)<-[:ACTS_IN]-(actor)
                WHERE movie.title =~ 'Matrix.*'
        RETURN actor.name, actor.birthplace

        // User-Ratings:
        MATCH (user:User {login:'micha'})-[r:RATED]->(movie)
                WHERE r.stars > 3
        RETURN movie.title, r.stars, r.comment

        // Mutual Friend recommendations:
        MATCH (user:User {login:'micha'})-[:FRIEND]-(friend)-[r:RATED]->(movie)
                WHERE r.stars > 3
        RETURN friend.name, movie.title, r.stars, r.comment
        */
        final ProcessStepDefinition next = processStepDefinitionRepository.findNextProcessStep(58l);
        System.out.println("next>>" + next);
    }

    @Ignore
    @Test
    public void delete() {
        processStepDefinitionRepository.findAll().forEach(p -> processStepDefinitionRepository.delete(p));
    }


    protected ProcessStepDefinition buildProcessDefinition(final WorkflowDefinition definition) {
        final String domain = EndpointPath.getDomain(definition.getPath());
        final String process = EndpointPath.getProcess(definition.getPath());
        final List<ProcessStepDefinition> steps = definition.getSteps().stream().map(s -> {return buildDefinition(domain, process, definition.getVersion(), s.getName());}).collect(Collectors.toList());
        return chainStepsDefinition(steps);
    }

    protected ProcessStepDefinition chainStepsDefinition(final List<ProcessStepDefinition> steps) {
        ProcessStepDefinition current = null;
        Collections.reverse(steps);
        for (ProcessStepDefinition s : steps) {
            if(current != null) {
                s.getSteps().add(current);
            }
            current = s;
        };
        return current;
    }

    protected ProcessStepDefinition buildDefinition(final String domain, final String process, final String version, final String name) {
        ProcessStepDefinition pStep = new ProcessStepDefinition();
        pStep.setDomain(domain);
        pStep.setProcessName(process);
        pStep.setVersion(version);
        pStep.setStepName(name);
        pStep.setSteps(new HashSet<>());
        return pStep;
    };


    protected WorkflowDefinition buildSubscriptionProcess() {
        final WorkflowDefinition subscription = new WorkflowDefinition();
        subscription.setPath("policy:subscription");
        subscription.setVersion("0.1");
        subscription.getSteps().add(buildStep("actors", "parent-child", "actorsDelegator"));
        subscription.getSteps().add(buildStep("covers", "parent-child", "coversDelegator"));
        subscription.getSteps().add(buildStep("pricing", "parent-child", "pricingDelegator"));
        subscription.getSteps().add(buildStep("investment", "parent-child", "investmentDelegator"));
        subscription.getSteps().add(buildStep("document", "parent-child", "documentDelegator"));
        return subscription;
    }

    protected StepDefinition buildStep(final String name, final String type, final String delegator) {
        final StepDefinition step = new StepDefinition();
        step.setName(name);
        step.setType(type);
        step.setDelegatorAliasing(delegator);
        return step;
    }
}
