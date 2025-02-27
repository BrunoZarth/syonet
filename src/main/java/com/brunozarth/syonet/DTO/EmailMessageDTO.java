package com.brunozarth.syonet.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageDTO {
    private String to;
    private String subject;
    private String body;
}
