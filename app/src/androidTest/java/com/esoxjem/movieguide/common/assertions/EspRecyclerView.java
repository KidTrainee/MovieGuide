package com.esoxjem.movieguide.common.assertions;

public class EspRecyclerView {
    public static RecyclerViewItemCountAssertion matchCount(int count) {
        return new RecyclerViewItemCountAssertion(count);
    }
}
