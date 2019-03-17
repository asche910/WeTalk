package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.MessageMoneyRVAdapter;
import com.asche.wetalk.bean.MessageMoneyBean;
import com.asche.wetalk.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentMessagingMoney extends Fragment {

    private RecyclerView recyclerView;
    private MessageMoneyRVAdapter messageMoneyRVAdapter;
    public static List<MessageMoneyBean> messagingMoneyList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging_money, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getView().findViewById(R.id.recycler_messaging_money);

        if (messagingMoneyList.isEmpty()) {
            messagingMoneyList.add(new MessageMoneyBean("asche", "dst","你发起了付费咨询", "2019-03-15 10:15:15", false));
            messagingMoneyList.add(new MessageMoneyBean("asche", "dst","你发起了付费咨询", "2019-03-17 18:22:50", false));
            messagingMoneyList.add(new MessageMoneyBean("asche", "dst","你发起了付费咨询", "2019-03-16 10:12:14", false));
            messagingMoneyList.add(new MessageMoneyBean("asche", "dst","你发起了付费咨询", TimeUtils.getCurrentTime(), false));
        }

        messageMoneyRVAdapter = new MessageMoneyRVAdapter(messagingMoneyList, true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(messageMoneyRVAdapter);

    }
}
