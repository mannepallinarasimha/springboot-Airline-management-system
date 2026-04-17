package com.nara.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRequest {

    @NotBlank(message = "city name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "cityCode name is required")
    @Size(max = 10)
    private String cityCode;

    @NotBlank(message = "countryCode name is required")
    @Size(max = 5)
    private String countryCode;

    @NotBlank(message = "countryCode name is required")
    @Size(max = 10)
    private String countryName;

}
