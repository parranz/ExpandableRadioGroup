package com.asrai.expandableradiogroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patri on 3/07/16.
 */
public class Element implements ParentListItem {

    private String name;
    private int iconId;

    private List<String> mDescription = new ArrayList<>();

    public Element(int iconId, String name) {
        this.name = name;
        this.iconId = iconId;
    }

    public Element(int iconId, String name, String description) {
        mDescription.add(description);
        this.name = name;
        this.iconId = iconId;
    }

    @Override
    public List getChildItemList() {
        return mDescription;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getName() {
        return name;
    }

    public int getIconId() {
        return iconId;
    }
}
