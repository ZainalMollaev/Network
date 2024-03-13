package org.education.network.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeFilter {

    private String likePattern;
    private Pageable pageable;
    private Principal principal;
    private String[] languages;
    private String[] locations;
    private String[] companies;
    private String[] educations;
    private String[] interests;

    public SubscribeFilter setPag(Pageable pageable) {
        this.setPageable(pageable);
        return this;
    }

    public SubscribeFilter setPrin(Principal principal) {
        this.setPrincipal(principal);
        return this;
    }

}
