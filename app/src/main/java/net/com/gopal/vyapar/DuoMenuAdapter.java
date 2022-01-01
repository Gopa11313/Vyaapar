package net.com.gopal.vyapar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import net.com.gopal.vyapar.duonavigation.views.DuoOptionView;

import java.util.ArrayList;

/**
 * Created by PSD on 13-04-17.
 */

public class DuoMenuAdapter extends BaseAdapter {
    private ArrayList<String> mOptions = new ArrayList<>();
    private ArrayList<DuoOptionView> mOptionViews = new ArrayList<>();
    private int[] drawable = {R.drawable.ic_home_new,
            R.drawable.ic_invite_friend_new, R.drawable.ic_my_card_new,
            R.drawable.ic_recharge_pin_new, R.drawable.ic_my_code_new,
            R.drawable.ic_history_icon_new, R.drawable.ic_setting_new,
            R.drawable.ic_notification_icon_new, R.drawable.ic_faq_new,
            R.drawable.ic_contact_new, R.drawable.ic_kyc_new_filled,
            R.drawable.ic_redeem_new_filled,
            R.drawable.ic_about_new,
            /*R.drawable.round*//*,R.drawable.ic_action_lock*/};


    public DuoMenuAdapter(ArrayList<String> options) {
        mOptions = options;
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return mOptions.get(position);
    }

    void setViewSelected(int position, boolean selected) {
        // Looping through the options in the menu
        // Selecting the chosen option
        for (int i = 0; i < mOptionViews.size(); i++) {
            if (i == position) {
//                mOptionViews.get(i).setSelected(selected);
            } else {
//                mOptionViews.get(i).setSelected(!selected);
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String option = mOptions.get(position);

        // Using the DuoOptionView to easily recreate the demo
        final DuoOptionView optionView;
        if (convertView == null) {
            optionView = new DuoOptionView(parent.getContext());
        } else {
            optionView = (DuoOptionView) convertView;
        }
        // Using the DuoOptionView's default selectors
        optionView.bind(option, parent.getContext().getResources().getDrawable(drawable[position]), parent.getContext().getResources().getDrawable(drawable[position]));
        if (option.equals("0")) {
        }
        // Adding the views to an array list to handle view selection
        mOptionViews.add(optionView);
        return optionView;
    }
}
