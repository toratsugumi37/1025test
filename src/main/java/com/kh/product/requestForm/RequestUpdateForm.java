package com.kh.product.requestForm;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestUpdateForm {
    @NotBlank
    @Size(min=1,max=10)
    private String pname;

    @NotNull
    @Positive
    @Max(value=1000)
    private Long quantity;

    @NotNull
    @Positive
    @Min(100)
    @Max(1000000)
    private Long price;
}
