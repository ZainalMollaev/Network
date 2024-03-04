package org.education.network.dto.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class SubscriptionFilterDto {

    private String[] location;
    private String[] language;
    private String[] company;
    private String[] education;
    private String[] interests;
    private Pageable pageable;


}
