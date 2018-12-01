package com.asche.wetalk.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.asche.wetalk.R;
import com.asche.wetalk.adapter.CommentRVAdapter;
import com.asche.wetalk.bean.CommentItemBean;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FullSheetDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior behavior;

    private ImageView imgClose;

    private RecyclerView recyclerComment;
    private LinearLayoutManager layoutManagerComment;
    private CommentRVAdapter commentRVAdapter;
    public static List<CommentItemBean> commentNormalList = new ArrayList<>();

    private Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.fragment_comment, null);
        dialog.setContentView(view);
        this.dialog = dialog;
        behavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    private Dialog getDialogView(){
        return dialog;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        init();
    }

    private void init(){
        recyclerComment = getDialogView().findViewById(R.id.recycler_dialog_comment);
        imgClose = getDialogView().findViewById(R.id.img_fragment_comment_close);

        commentRVAdapter = new CommentRVAdapter(commentNormalList);
        layoutManagerComment = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerComment.setLayoutManager(layoutManagerComment);
        recyclerComment.setAdapter(commentRVAdapter);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
