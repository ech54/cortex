package lu.engine.core.repository;

import java.util.List;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.engine.core.model.ProcessStep;
import lu.engine.core.model.ProcessStepDefinition;

@Repository
public interface ProcessStepDefinitionRepository extends GraphRepository<ProcessStepDefinition> {

    @Query("MATCH (d:ProcessStepDefinition) WHERE d.domain={qDomain} AND d.processName={qProcess} AND d.version={qVersion} RETURN d")
    List<ProcessStepDefinition> findLastVersion(@Param("qDomain")final String domain,
            @Param("qProcess")final String process, @Param("qVersion")final String version);

    @Query("MATCH (d:ProcessStepDefinition) WHERE d.domain={qDomain} AND d.processName={qProcess} RETURN d")
    List<ProcessStepDefinition> findByProcess(@Param("qDomain")final String domain, @Param("qProcess")final String process);

    @Query("MATCH (current:ProcessStepDefinition)-[:CONFIG]->(next:ProcessStepDefinition) WHERE id(current)={qId} RETURN next")
    ProcessStepDefinition findNextProcessStep(@Param("qId")final Long id);
}
