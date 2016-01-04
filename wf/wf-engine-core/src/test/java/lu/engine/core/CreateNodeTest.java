
package lu.engine.core;

import java.util.Collection;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import lu.engine.core.model.ProcessStep;
import lu.engine.core.repository.ProcessStepRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreConfiguration.class)
//@TransactionConfiguration()
@TransactionConfiguration(defaultRollback = false)
@Transactional
public class CreateNodeTest {

    @Autowired
    ProcessStepRepository processStepRepository;

    @Autowired
    Session session;

    @Test
    public void createPolicyWorkflow() {

    }

    protected ProcessStep createActivity(String domain, String processName, String activityName, String status) {
        final ProcessStep step = new ProcessStep();
        step.setStepName(activityName);
        return step;
    }

    /*
    @Test
    public void sample() {

        final Activity init = new Activity();
        //init.setId(1l);
        init.setDomain("policy");
        init.setProcessName("souscription");
        init.setStepName("start");
        init.setStatus("CREATED");
        actvitityRepository.save(init);
        Activity fromDb = actvitityRepository.findOne(init.getId());
        System.out.println("from db id : " + fromDb.getId());
        System.out.println("from db domaine: " + fromDb.getDomain());
        //session.s
    } */

    @Test
    public void read() {
        Collection<ProcessStep> res = session.loadAll(ProcessStep.class);
        res.forEach(r -> System.out.println(">>> " + r.getId()));
    }

    @Ignore
    @Test
    public void deleteActivity() {
        processStepRepository.findAll().forEach(a -> processStepRepository.delete(a));
    }

}
