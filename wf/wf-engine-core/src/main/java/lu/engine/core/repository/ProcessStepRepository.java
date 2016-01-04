package lu.engine.core.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import lu.engine.core.model.ProcessStep;

@Repository
public interface ProcessStepRepository extends GraphRepository<ProcessStep> {

    @Query("MATCH (d:ProcessStep) WHERE d.domain={qDomain} AND d.processName={qProcess} AND d.processId={qProcessId} RETURN d")
    ProcessStep findUniqueStep(@Param("qDomain") final String domain,
            @Param("qProcess") final String process,
            @Param("qProcessId") final String processId);


    @Query("MATCH (i:ProcessStep)-[:PARENT_CHILD]->(iPlusUn:ProcessStep) "
            + "WHERE id(i)={qId} RETURN iPlusUn")
    ProcessStep nextStep(@Param("qId")final Long qId);
}
