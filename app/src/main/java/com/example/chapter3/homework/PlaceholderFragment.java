package com.example.chapter3.homework;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.chapter3.homework.masterdetail.Item;
import com.example.chapter3.homework.masterdetail.ItemsListFragment;

import java.util.ArrayList;
import java.util.Objects;

public class PlaceholderFragment extends Fragment {
    private LottieAnimationView animationView;
    private ListView lvItems;
    private ItemsListFragment.OnItemSelectedListener listener;
    private ArrayAdapter<Item> adapterItems;

    public interface OnItemSelectedListener {
        public void onItemSelected(Item i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create arraylist from item fixtures
        ArrayList<Item> items = Item.getItems();
        adapterItems = new ArrayAdapter<Item>(Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_activated_1, items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view = inflater.inflate(R.layout.fragment_placeholder, container,
                false);
        animationView = view.findViewById(R.id.animation_view);
        lvItems = (ListView) view.findViewById(R.id.lvItems);
        lvItems.setVisibility(View.INVISIBLE);
        lvItems.setAdapter(adapterItems);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position,
                                    long rowId) {
                // Retrieve item based on position
                Item i = adapterItems.getItem(position);
                // Fire selected event for item

            }
        });
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                //animationView.pauseAnimation();

                lvItems.setAlpha(0f);
                lvItems.setVisibility(View.VISIBLE);
                lvItems.animate()
                        .alpha(1f)
                        .setDuration(1000)
                        .setListener(null);
                animationView.animate()
                        .alpha(0f)
                        .setDuration(1000)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animationView.setVisibility(View.GONE);
                            }
                        });

            }
        }, 5000);
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        lvItems.setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

}
