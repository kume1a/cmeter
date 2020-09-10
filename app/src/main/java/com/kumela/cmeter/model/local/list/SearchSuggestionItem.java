package com.kumela.cmeter.model.local.list;

import androidx.annotation.NonNull;

import com.kumela.cmeter.R;

import java.util.Objects;

/**
 * Created by Toko on 01,August,2020
 **/

public class SearchSuggestionItem {
    public static final int INDICATOR_SEARCH = 0;
    public static final int INDICATOR_RECENT = 1;

    private final String suggestionName;
    private final int drawableId;

    public SearchSuggestionItem(String suggestionName, int indicatorId) {
        this.suggestionName = suggestionName;

        switch (indicatorId) {
            case INDICATOR_SEARCH:
                drawableId = R.drawable.ic_search;
                break;
            case INDICATOR_RECENT:
                drawableId = R.drawable.ic_recent;
                break;
            default:
                throw new RuntimeException("indicatorId should be one of SearchSuggestionItem.INDICATOR_SEARCH or INDICATOR_SEARCH.INDICATOR_RECENT");
        }
    }

    public String getSuggestionName() {
        return suggestionName;
    }

    public int getDrawableId() {
        return drawableId;
    }

    @NonNull
    @Override
    public String toString() {
        return "SearchSuggestionItem{" +
                "title='" + suggestionName + '\'' +
                ", drawableId=" + drawableId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchSuggestionItem that = (SearchSuggestionItem) o;
        return drawableId == that.drawableId &&
                Objects.equals(suggestionName, that.suggestionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suggestionName, drawableId);
    }
}
