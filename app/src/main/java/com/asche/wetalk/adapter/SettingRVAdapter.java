package com.asche.wetalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.activity.ClockInActivity;
import com.asche.wetalk.activity.LoginActivity;
import com.asche.wetalk.activity.PasswordActivity;
import com.asche.wetalk.activity.SettingActivity;
import com.asche.wetalk.bean.SettingItemBean;
import com.suke.widget.SwitchButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.shuyu.gsyvideoplayer.GSYVideoBaseManager.TAG;

public class SettingRVAdapter extends RecyclerView.Adapter {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_ITEM = 1;

    private List<SettingItemBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public SettingRVAdapter(List<SettingItemBean> list) {
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if (viewType == TYPE_TITLE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_setting_title, parent, false);
            return new TitleHolder(view);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_setting_item, parent, false);
            return new ItemHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        SettingItemBean bean = list.get(position);
        if (holder instanceof TitleHolder) {
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.textTitle.setText(bean.getTitle());
        } else if (holder instanceof ItemHolder) {
            final ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.textTitle.setText(bean.getTitle());
            itemHolder.textDesc.setText(bean.getDescription());
            if (bean.isHasSwitch()) {
                itemHolder.switchButton.setVisibility(View.VISIBLE);
                if (position == 4){
                    if (SettingActivity.THEME_CURRENT == SettingActivity.THEME_DARK){
                        itemHolder.switchButton.setChecked(true);
                    }else {
                        itemHolder.switchButton.setChecked(false);
                    }
                }
            } else {
                itemHolder.switchButton.setVisibility(View.GONE);
            }

            itemHolder.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: -------------> " + position );
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        // 修改密码
                        nextActivity(PasswordActivity.class);
                        break;
                    case 2:
                        // 注销登录
                        nextActivity(LoginActivity.class);
                        Toast.makeText(context, "注销成功！", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        break;
                    case 4:
                        // 夜间模式
                        // onItemClickListener.onItemClick(position);
                        ((ItemHolder)(holder)).switchButton.performClick();
                    case 5:
                        // 免打扰
                    case 6:
                        // 省流模式
                        ItemHolder itemHolder = (ItemHolder) holder;
                        if (itemHolder.switchButton.isChecked()) {
                            itemHolder.switchButton.setChecked(false);
                        } else {
                            itemHolder.switchButton.setChecked(true);
                        }
                        break;
                    case 7:
                        // 清除缓存
                        break;
                    case 8:
                        break;
                    case 9:
                        // 反馈
                        break;
                    case 10:
                        // 检查更新
                        Toast.makeText(context, "当前已是最新版本！", Toast.LENGTH_SHORT).show();
                        break;
                    case 11:
                        // 关于
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    private class TitleHolder extends RecyclerView.ViewHolder {
        private TextView textTitle;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_item_setting_title);
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, textDesc;
        private SwitchButton switchButton;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_setting_item_title);
            textDesc = itemView.findViewById(R.id.text_setting_item_description);
            switchButton = itemView.findViewById(R.id.switch_setting);
        }
    }

    private void nextActivity(Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }
}
