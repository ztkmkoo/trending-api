package com.ztkmkoo.trending.github;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ztkmkoo on 2019-09-16
 */
@Getter @Setter
public class SearchRepository {

    private Long id;
    private String name;
    private String description;

    @Override
    public String toString() {
        return String.format("%n{%n\tid: %d%n\tname: %s%n\tdescription: %s%n}",
                id, name, description);
    }


}
