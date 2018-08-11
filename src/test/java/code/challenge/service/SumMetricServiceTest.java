package code.challenge.service;

import code.challenge.provider.InstantProvider;
import code.challenge.provider.Provider;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SumMetricServiceTest.TestConfiguration.class)
@TestPropertySource(locations = "classpath:application.properties")
public class SumMetricServiceTest {

    @Autowired
    private Provider<Instant> instantProvider;

    @Autowired
    private MetricService metricService;

    @Test
    public void test() {
        final Instant now = Instant.now();

        // Lets assume that first value we put now
        Mockito.when(instantProvider.now()).thenReturn(now);
        // Metric should be a currently inserted value
        Assertions.assertThat(metricService.put("key-0001", 10L)).isEqualTo(10L);
        Assertions.assertThat(metricService.calculate("key-0001")).isEqualTo(10L);
        // Value for not updated key is still 0
        Assertions.assertThat(metricService.calculate("key-0002")).isEqualTo(0L);

        // Lets assume that next value we put in 30 minutes + 1 second from now
        Mockito.when(instantProvider.now()).thenReturn(now.plusSeconds(1800 + 1));
        // Metric should be a sum of previously inserted value and newly inserted value
        Assertions.assertThat(metricService.put("key-0001", 20L)).isEqualTo(30L);
        Assertions.assertThat(metricService.calculate("key-0001")).isEqualTo(30L);
        // Value for not updated key is still 0
        Assertions.assertThat(metricService.calculate("key-0002")).isEqualTo(0L);

        // Lets assume that next value we put in 60 minutes + 1 second from now
        Mockito.when(instantProvider.now()).thenReturn(now.plusSeconds(3600 + 1));
        // Metric should be a sum of previously inserted value and newly inserted value
        // Very first inserted values should be removed
        Assertions.assertThat(metricService.put("key-0001", 30L)).isEqualTo(50L);
        Assertions.assertThat(metricService.calculate("key-0001")).isEqualTo(50L);

        // Value for not updated key is still 0
        Assertions.assertThat(metricService.calculate("key-0002")).isEqualTo(0L);
    }

    @Configuration
    public static class TestConfiguration {

        @Bean
        public MetricService metricService() {
            return new SumMetricService();
        }

        @Bean
        public Provider<Instant> instantProvider() {
            return Mockito.mock(InstantProvider.class);
        }
    }

}
