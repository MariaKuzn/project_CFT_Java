package autotests.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true)
public class DuckProperties {
    @JsonProperty
    String color;

    @JsonProperty
    double height;

    @JsonProperty
    String material;

    @JsonProperty
    String sound;

    @JsonProperty
    String wingsState;
}
