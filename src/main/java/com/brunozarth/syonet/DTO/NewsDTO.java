package com.brunozarth.syonet.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDTO {
    @NotBlank(message = "Title cannot be empty")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotNull(message = "Description is required")
    @NotBlank(message = "Description is required")
    private String description;

    private String link;
}
