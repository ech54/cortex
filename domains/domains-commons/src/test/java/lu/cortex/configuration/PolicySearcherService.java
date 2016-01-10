package lu.cortex.configuration;

import lu.cortex.annotation.ProcessName;
import lu.cortex.annotation.Reference;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Emilien Charton on 10/01/16.
 */
@Component
@ProcessName(name="policy_searcher")
public class PolicySearcherService {

    @Reference
    public List<String> getPolicyName(String criteria) {
        return Arrays.asList("A123456", "B123456", "C123456");
    }

    @Reference
    public List<String> getPolicyName(String criteria, String otherCriteria) {
        return Arrays.asList("D123456", "E123456");
    }

}
