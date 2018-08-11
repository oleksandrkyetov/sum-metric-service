package code.challenge.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Holds {@link MetricValue}'s together with a metric which needs to be calculated against that value
 */
@Getter
@ToString
@EqualsAndHashCode
public class MetricHolder {
    private final AtomicLong metric;
    private final ConcurrentLinkedDeque<MetricValue> values;

    public MetricHolder() {
        metric = new AtomicLong(0L);
        values = new ConcurrentLinkedDeque<>();
    }

}
