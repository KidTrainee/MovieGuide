package com.esoxjem.movieguide.common;

import com.esoxjem.movieguide.common.assertions.RecyclerViewItemCountAssertion;

public class EspRecyclerView {
    public static RecyclerViewItemCountAssertion matchCount(int count) {
        return new RecyclerViewItemCountAssertion(count);
    }
}
