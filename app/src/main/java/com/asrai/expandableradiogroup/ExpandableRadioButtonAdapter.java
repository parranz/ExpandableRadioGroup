package com.asrai.expandableradiogroup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.Model.ParentWrapper;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patri on 3/07/16.
 */
public class ExpandableRadioButtonAdapter extends ExpandableRecyclerAdapter<ExpandableRadioButtonAdapter.RadioViewHolder, ExpandableRadioButtonAdapter.DescriptionViewHolder> {

    private LayoutInflater mInflator;
    private int mRadioCheckedId = -1;
    private Map<Integer, RadioViewHolder> mParentViewHoldersByParentPosition;
    private Map<Element, RadioViewHolder> mParentViewHoldersByElement;

    public ExpandableRadioButtonAdapter(Context context, @NonNull List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mInflator = LayoutInflater.from(context);
        mParentViewHoldersByParentPosition = new HashMap<>();
        mParentViewHoldersByElement = new HashMap<>();
    }

    @Override
    public RadioViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View radioParentView = mInflator.inflate(R.layout.item_filter_radiobutton, parentViewGroup, false);
        return new RadioViewHolder(radioParentView);
    }

    @Override
    public DescriptionViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View descriptionView = mInflator.inflate(R.layout.item_filter_radiobutton_description, childViewGroup, false);
        return new DescriptionViewHolder(descriptionView);
    }

    @Override
    public void onBindParentViewHolder(RadioViewHolder radioViewHolder, final int position, final ParentListItem parentListItem) {
        Element element = (Element) parentListItem;
        radioViewHolder.bind(element);

        //Needed because it prevails over the onParentExpandCollapseListener of the ExpandableRecyclerView
        radioViewHolder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radio = (RadioButton) v;
                if (radio.isChecked()) {
                    collapseAllParents();
                    expandParent(parentListItem);
                }
            }
        });
        mParentViewHoldersByElement.put(element, radioViewHolder);
        mParentViewHoldersByParentPosition.put(position, radioViewHolder);
    }

    @Override
    public void onBindChildViewHolder(DescriptionViewHolder DescriptionViewHolder, int position, Object childListItem) {
        String ingredient = (String) childListItem;
        DescriptionViewHolder.bind(ingredient);
    }

    public class RadioViewHolder extends ParentViewHolder {

        private TextView mNameTextView;
        private RadioButton mRadioButton;
        private View mDividerView;
        private ImageView mIcon;

        public RadioViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.elementName);
            mRadioButton = (RadioButton) itemView.findViewById(R.id.elementRadio);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mDividerView = itemView.findViewById(R.id.parentDivider);
        }

        public void bind(Element element) {
            mNameTextView.setText(element.getName());
            mIcon.setImageResource(element.getIconId());
        }

    }

    public class DescriptionViewHolder extends ChildViewHolder {

        private TextView mDescriptionTextView;

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.description);
        }

        public void bind(String description) {
            mDescriptionTextView.setText(description);
        }
    }

    /**
     * Implementation of {@link com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder.ParentListItemExpandCollapseListener#onParentListItemExpanded(int)}.
     * <p>
     * Called when a {@link ParentListItem} is triggered to expand.
     *
     * @param position The index of the item in the list being expanded
     */
    @Override
    public void onParentListItemExpanded(int position) {

        Object listItem = getListItem(position);
        if (listItem instanceof ParentWrapper) {
            //Collapse and uncheck
            collapseAllParents();

            //Expand and check the selected element
            int parentIndex = mItemList.indexOf(listItem);

            //Since we've collapsed the views, the position is no longer the one we received, but
            //the actual parent's position
            expandParent(parentIndex);
        }
    }

    @Override
    public void onParentListItemCollapsed(int position) {
        //Disables onCollapse
    }

    @Override
    public void collapseParent(int parentIndex) {
        mParentViewHoldersByParentPosition.get(parentIndex).mDividerView.setVisibility(View.VISIBLE);
        super.collapseParent(parentIndex);
    }

    @Override
    public void collapseParent(ParentListItem parentListItem) {
        mParentViewHoldersByElement.get(parentListItem).mDividerView.setVisibility(View.VISIBLE);
        mParentViewHoldersByElement.get(parentListItem).mRadioButton.setChecked(false);
        super.collapseParent(parentListItem);
    }

    @Override
    public void expandParent(int parentIndex) {
        mParentViewHoldersByParentPosition.get(parentIndex).mRadioButton.setChecked(true);
        mRadioCheckedId = mParentViewHoldersByParentPosition.get(parentIndex).mRadioButton.getId();
        ParentListItem parentListItem = getParentItemList().get(parentIndex);
        if (!parentListItem.getChildItemList().isEmpty()) {
            mParentViewHoldersByParentPosition.get(parentIndex).mDividerView.setVisibility(View.GONE);
            super.expandParent(parentIndex);
        }
    }

    @Override
    public void expandParent(ParentListItem parentListItem) {
        mParentViewHoldersByElement.get(parentListItem).mRadioButton.setChecked(true);
        mRadioCheckedId = mParentViewHoldersByElement.get(parentListItem).mRadioButton.getId();
        if (!parentListItem.getChildItemList().isEmpty()) {
            mParentViewHoldersByElement.get(parentListItem).mDividerView.setVisibility(View.GONE);
            super.expandParent(parentListItem);
        }
    }

    public int getCheckedRadioId() {
        return mRadioCheckedId;
    }
}
