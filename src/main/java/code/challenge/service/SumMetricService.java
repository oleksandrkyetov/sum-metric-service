package code.challenge.service;

import code.challenge.model.MetricHolder;
import code.challenge.model.MetricValue;
import code.challenge.provider.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SumMetricService implements MetricService {

    final ConcurrentHashMap<String, MetricHolder> metrics = new ConcurrentHashMap<>();

    @Autowired
    private Provider<Instant> instantProvider;

    @Value("${window.length.seconds}")
    private long windowLengthSeconds;

    @Override
    public Long put(final String key, final Long value) {
        // Calculate time slot which needs to be considered starting from now and back to configurable moment in time
        final Instant to = instantProvider.now();
        final Instant from = to.minusSeconds(windowLengthSeconds);

        // There might be no metric holder for such a key in the map, create if not exists
        final MetricHolder metricHolder = metrics.computeIfAbsent(key, k -> new MetricHolder());

        // Add new value to the tail of the deque and recalculate metric based on added value
        metricHolder.getValues().offer(new MetricValue(to, value));
        metricHolder.getMetric().updateAndGet(sum -> sum + value);

        return removeOutdated(metricHolder, from, to).getMetric().get();
    }

    @Override
    public Long calculate(final String key) {
        // Calculate time slot which needs to be considered starting from now and back to configurable moment in time
        final Instant to = instantProvider.now();
        final Instant from = to.minusSeconds(windowLengthSeconds);

        // There might be no metric holder for such a key in the map, create if not exists
        final MetricHolder metricHolder = metrics.computeIfAbsent(key, k -> new MetricHolder());

        return removeOutdated(metricHolder, from, to).getMetric().get();
    }
    
    /**
     * Removes outdated values from the deque and at the same time recalculates metric
     *
     * @param metricHolder {@link MetricHolder}
     * @return {@link MetricHolder} with removed outdated values and recalculated metric
     */
    private MetricHolder removeOutdated(final MetricHolder metricHolder, final Instant from, final Instant to) {
        while (Optional.ofNullable(metricHolder.getValues().peek()).map(MetricValue::getInstant).orElse(to).isBefore(from)) {
            metricHolder.getMetric().updateAndGet(sum -> sum - metricHolder.getValues().poll().getValue());
        }

        return metricHolder;
    }
}
