package com.ztkmkoo.trending.github;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Ztkmkoo on 2019-09-16
 */
@Getter @Setter
public class SearchResponse {

    private Long totalCount;
    private Boolean incompleteResults;
    private List<SearchRepository> repositoryList;

    @Override
    public String toString() {

        return String.format("{%n totalCount: %d%n incompleteResults: %s%n repositoryList: %n[%s%n]%n}",
                totalCount, String.valueOf(incompleteResults), listToString(repositoryList));
    }

    private static String listToString(final List list) {
        final StringBuilder sb = new StringBuilder();

        for (final Object o : list) {

            if (sb.length() > 0) {
                sb.append(",");
            }

            if (o == null) {
                sb.append("null");
            } else {
                sb.append(o.toString());
            }
        }

        return sb.toString();
    }
}
