package com.asche.wetalk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.asche.wetalk.R;
import com.asche.wetalk.bean.HomeItem;
import com.asche.wetalk.bean.ItemBean;
import com.asche.wetalk.bean.TopicPageBean;
import com.asche.wetalk.helper.GlideImageLoader;
import com.asche.wetalk.other.MyScrollView;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.asche.wetalk.MyApplication.getContext;

public class TopicPageRVAdapter extends RecyclerView.Adapter {
    public static final int TYPE_BANNER = 0;
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_CHIP = 2;
    public static final int TYPE_TOPIC_LIST = 3;

    private List<TopicPageBean> list;
    private Context context;

    public TopicPageRVAdapter(List<TopicPageBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_BANNER) {
            View view = inflater.inflate(R.layout.item_discover_topic_banner, parent, false);
            return new BannerHolder(view);
        } else if (viewType == TYPE_TEXT) {
            View view = inflater.inflate(R.layout.item_discover_topic_text, parent, false);
            return new TextHolder(view);
        } else if (viewType == TYPE_CHIP) {
            View view = inflater.inflate(R.layout.item_discover_topic_chip, parent, false);
            return new ChipHolder(view);
        } else if (viewType == TYPE_TOPIC_LIST) {
            View view = inflater.inflate(R.layout.item_discover_topic_topiclist, parent, false);
            return new TopicListHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TopicPageBean bean = list.get(position);
        int type = bean.getType();
        if (type == TYPE_BANNER) {
            List<Integer> imgsList = bean.getImgList();
            List<String> titleList = bean.getTitleList();

            BannerHolder bannerHolder = (BannerHolder)holder;
            bannerHolder.banner.setImageLoader(new GlideImageLoader());
            bannerHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            bannerHolder.banner.setImages(imgsList);
            bannerHolder.banner.setBannerTitles(titleList);
            bannerHolder.banner.setIndicatorGravity(BannerConfig.RIGHT);
            bannerHolder.banner.start();

            bannerHolder.banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(getContext(), "You clicked " + position, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (type == TYPE_TEXT) {
            TextHolder textHolder = (TextHolder)holder;

            textHolder.textTitle.setText(bean.getTextTitle());
            textHolder.textMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "More!", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (type == TYPE_CHIP) {
            ChipHolder chipHolder = (ChipHolder)holder;

            List<String> tagList = bean.getTitleList();
            TopicChipRVAdapter topicChipRVAdapter = new TopicChipRVAdapter(tagList);

            chipHolder.recyclerViewChips.addItemDecoration(new SpacingItemDecoration(MyScrollView.dip2px(getContext(), 4), MyScrollView.dip2px(getContext(), 6)));
            chipHolder.recyclerViewChips.setAdapter(topicChipRVAdapter);


        } else if (type == TYPE_TOPIC_LIST) {
            TopicListHolder topicListHolder = (TopicListHolder)holder;

            List<HomeItem> itemBeanList = bean.getItemBeanList();
            HomeSuggestRVAdapter adapter = new HomeSuggestRVAdapter(itemBeanList);
            adapter.setEnableRadiusAndEle(true);
            topicListHolder.recyclerTopic.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        TopicPageBean bean = list.get(position);
        int type = bean.getType();
        if (type == TYPE_BANNER)
            return TYPE_BANNER;
        else if (type == TYPE_TEXT)
            return TYPE_TEXT;
        else if (type == TYPE_CHIP)
            return TYPE_CHIP;
        else if (type == TYPE_TOPIC_LIST)
            return TYPE_TOPIC_LIST;
        return super.getItemViewType(position);
    }

    public class BannerHolder extends RecyclerView.ViewHolder {
        private Banner banner;

        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner_discover_topic);
        }
    }

    public class TextHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textMore;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_item_discover_topic_text_title);
            textMore = itemView.findViewById(R.id.text_item_discover_topic_text_more);
        }
    }

    public class ChipHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerViewChips;

        public ChipHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewChips = itemView.findViewById(R.id.recycler_discover_topic_chips);

            ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(context)
                    .setScrollingEnabled(false)
                    .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                    .withLastRow(true)
                    .build();

            recyclerViewChips.setLayoutManager(chipsLayoutManager);
        }
    }

    public class TopicListHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerTopic;

        public TopicListHolder(@NonNull View itemView) {
            super(itemView);
            recyclerTopic = itemView.findViewById(R.id.recycler_discover_topic_topiclist);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
            recyclerTopic.setLayoutManager(layoutManager);
        }
    }
}
