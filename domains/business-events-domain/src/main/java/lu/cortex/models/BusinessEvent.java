package lu.cortex.models;

/**
 * Class model a <code>Business Event</code> in insurance context.
 * During the policy lifecycle, some events are generated depending
 *  of event type. Events are categorized between two main types:
 *  <ul>
 *      <li>Financial : which impacts policy value.</li>
 *      <li>Non financial : purely administrative.</li>
 *  </ul>
 *
 * Created by echarton on 04/01/16.
 */
public interface BusinessEvent {

    /**
     * Accessor in reading on the policy unique identifier.
     * @return The identifier.
     */
    String getPolicyId();

    /**
     * Accessor in reading on the business event type.
     * @return The event type.
     */
    BusinessType getType();

    /**
     * Accessor in reading on the event status.
     * @return The status.
     */
    EventStatus getEventStatus();

    /**
     * Accessor in reading on the calendar of event.
     *  Business temporal is containing all main dates
     *   of business event.
     * @return The business temporal.
     */
    Calendar getCalendar();

    /**
     * Accessor in reading on the business logic which
     *  will be or has been applied to the policy.
     * @return The endorsement.
     */
    Endorsement getEndorsement();
}
