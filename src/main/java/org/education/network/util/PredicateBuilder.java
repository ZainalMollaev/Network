package org.education.network.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;

@Getter
public class PredicateBuilder {

    private CriteriaBuilder cb;
    private Predicate predicate;

    public PredicateBuilder(CriteriaBuilder cb) {
        this.cb = cb;
        this.predicate = cb.conjunction();
    }

    public PredicateBuilder like(String likePattern, Expression... expression){

        if(likePattern != null) {
            predicate = cb.and(
                    predicate,
                    cb.like(
                            cb.concat(cb.concat(expression[0], " "), expression[1]),
                            likePattern
                    )
            );
        }

        return this;
    }

    public PredicateBuilder in(String[] in, Expression expression){

        if(in != null) {
            predicate = cb.and(
                    predicate,
                    expression.in(in)
            );
        }

        return this;
    }

}

