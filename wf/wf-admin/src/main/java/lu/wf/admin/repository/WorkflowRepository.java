package lu.wf.admin.repository;

import lu.wf.admin.model.Activity;
import lu.wf.admin.model.Workflow;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowRepository extends GraphRepository<Workflow> {

    @Query("MATCH (w:Workflow) WHERE w.name={qName} RETURN w")
    List<Workflow> findByName(@Param("qName")final String name);

    @Query("MATCH (w:Workflow)-[wa:IS_COMPOSED_OF]->(a) WHERE w.name={qName} RETURN a.processName ORDER BY wa.order")
    List<String> findActivities(@Param("qName") final String name);
}
