package com.example.allinone.base;

import java.util.List;

/**
 * Created by JongLim on 6/16/2019.
 */
public class StickyHeaderGroupItem<CHILD extends Object> {
    private String title;
    private boolean expanded;
    private List<CHILD> children;

    public StickyHeaderGroupItem(String title, boolean expanded, List<CHILD> children) {
        this.title = title;
        this.expanded = expanded;
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public List<CHILD> getChildren() {
        return children;
    }

    public int childrenSize() {
        return children.size();
    }
}
