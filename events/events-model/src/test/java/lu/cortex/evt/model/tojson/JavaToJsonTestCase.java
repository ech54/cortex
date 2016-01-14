/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.cortex.evt.model.tojson;

import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.evt.model.EventType;

public class JavaToJsonTestCase {

    @Test
    public void toJson()throws JsonProcessingException{
        //JsonToJavaConverter converter = new JsonToJavaConverter();
        ObjectMapper mapper = new ObjectMapper();
        final Event event = EventBuilder
                .from(new EndpointDefault("test", "test:junit"))
                .to("mock", "mock:listener")
                .withType(EventType.INFO)
                .withPayload("'hello':'world'")
                .build();
        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(event);
        System.out.println(jsonInString);
    }

}
