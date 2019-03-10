package com.asche.wetalk.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.asche.wetalk.MyApplication;
import com.asche.wetalk.R;
import com.asche.wetalk.adapter.EmoticonRVAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.shuyu.gsyvideoplayer.GSYVideoADManager.TAG;

public class FragmentEmoticon extends Fragment {

    private RecyclerView recyclerView;
    private EmoticonRVAdapter emoticonRVAdapter;
    private GridLayoutManager layoutManager;
    public static List<Bitmap> emoticonList = new ArrayList<>();

    // 表情目标输出框
    private EditText editText;

    static {
        for (int i = 1; i <= 100; i++) {
            InputStream inputStream = null;
            try {
                inputStream = MyApplication.getContext().getResources().getAssets().open("emoji/" + i + ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            emoticonList.add(bitmap);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emoticon, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e(TAG, "onActivityCreated: ------------>" );
        recyclerView = getView().findViewById(R.id.recycler_emoticon);


        emoticonRVAdapter = new EmoticonRVAdapter(emoticonList);
        emoticonRVAdapter.setEditText(editText);
        layoutManager = new GridLayoutManager(getContext(), 7);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(emoticonRVAdapter);

    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}
