package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * POJO класс (модель) сущности Entity
 * @version 1.0
 * @autor Игорь Герасименко
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    @Builder.Default
    private Addition addition = Addition
            .builder()
            .additionalInfo("Дополнительные сведения")
            .additionalNumber(123).build();
    @Builder.Default
    private List<Integer> importantNumbers = Arrays.asList(42, 87, 15);
    @Builder.Default
    private String title = "Заголовок сущности";
    @Builder.Default
    private boolean verified = true;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Addition {
        @Builder.Default
        private String additionalInfo = "Дополнительные сведения";
        @Builder.Default
        private int additionalNumber = 123;
    }
}
