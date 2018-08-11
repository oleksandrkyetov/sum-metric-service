package code.challenge.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

/**
 * Holds metric value together with {@link Instant} when those value was created
 */
@Getter
@ToString
@EqualsAndHashCode
public class MetricValue {
    private final Instant instant;
    private final Long value;

    public MetricValue(final Instant instant, final Long value) {
        this.instant = instant;
        this.value = value;
    }
}