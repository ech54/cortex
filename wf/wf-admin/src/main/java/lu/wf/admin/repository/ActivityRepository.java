package lu.wf.admin.repository;

import lu.wf.admin.model.Activity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends GraphRepository<Activity> {

    @Query("MATCH (d:Activity) WHERE d.processName={qProcessName} RETURN d ORDER BY d.version LIMIT 1")
    Activity findLastVersionByName(@Param("qProcessName")final String processName);


    @Query("MATCH (d:Activity) WHERE d.processName={qProcessName} AND d.version={qVersion} RETURN d")
    Activity findActivityByNameAndVersion(@Param("qProcessName")final String processName, @Param("qVersion")final Integer version);
}
