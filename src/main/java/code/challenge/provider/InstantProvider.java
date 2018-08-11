package code.challenge.provider;

import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Supplies instance of {@link Instant}
 * Should be used instead of static methods in order to make possible to mock instance creation in the unit tests
 */
@Service
public class InstantProvider implements Provider<Instant> {

    @Override
    public Instant now() {
        return Instant.now();
    }

    @Override
    public Instant get() {
        return now();
    }

}
