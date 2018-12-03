package com.asche.wetalk.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Asche
 * @data 2018-12-2 11:21:40
 */
public abstract class BaseDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior behavior;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = savedInstanceState.getInt("layoutId");
        View view = inflater.inflate(layoutId, container, false);
        return view;
    }

/*    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int layoutId = savedInstanceState.getInt("layoutId");
        View view = View.inflate(getContext(), layoutId, null);

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setContentView(view);
        this.dialog = dialog;

        behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        return dialog;
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        behavior = BottomSheetBehavior.from((View) getView().getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public BottomSheetBehavior getBehavior() {
        return behavior;
    }
}
