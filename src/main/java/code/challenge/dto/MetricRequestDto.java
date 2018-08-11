package code.challenge.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class MetricRequestDto {

    @NotNull
    private Long value;
}
