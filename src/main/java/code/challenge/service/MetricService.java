package code.challenge.service;

public interface MetricService {

    /**
     * Put new value to the metric by key
     *
     * @param key   key
     * @param value value
     * @return current value of the calculate metric
     */
    Long put(final String key, final Long value);

    /**
     * Calculate metric for key
     * Exact metric (sum, average, etc) depends on concrete implementation
     *
     * @param key key
     * @return current value of the calculated metric
     */
    Long calculate(final String key);
}
