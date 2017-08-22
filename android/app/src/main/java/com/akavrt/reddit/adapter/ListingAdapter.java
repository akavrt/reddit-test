package com.akavrt.reddit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akavrt.reddit.models.Link;
import com.akavrt.reddit.test.R;
import com.akavrt.reddit.utils.NavigationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class ListingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final AdapterItemRenderer itemRenderer;
    private final LayoutInflater inflater;
    private final List<Link> data;
    private NavigationListener listener;


    public ListingAdapter(Context context, AdapterItemRenderer itemRenderer) {
        this.itemRenderer = itemRenderer;
        this.inflater = LayoutInflater.from(context);

        data = new ArrayList<>();
    }

    public void setNavigationListener(NavigationListener listener) {
        this.listener = listener;
    }

    public void addLinks(List<Link> links) {
        int index = data.size();

        data.addAll(links);
        notifyItemRangeInserted(index, links.size());
    }

    public void clear() {
        int size = data.size();

        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.layout_adapter_item, parent, false);
        return new LinkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        Link link = data.get(position);
        AdapterItem item = itemRenderer.convert(link);
        ((LinkViewHolder) viewHolder).bind(item);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                if (position != NO_POSITION) {
                    Link link = data.get(position);
                    handleItemClick(link);
                }
            }
        });
    }

    private void handleItemClick(Link link) {
        if (listener == null) {
            return;
        }

        if (link.hasDefaultImage()) {
            listener.openImageUrl(link.getDefaultImage().getSource().getUrl());
        } else {
            listener.openRelativeUrl(link.getRelativeUrl());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private static class LinkViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subtitle;
        private final TextView author;
        private final TextView score;
        private final ImageView preview;

        LinkViewHolder(View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.title);
            this.subtitle = itemView.findViewById(R.id.subtitle);
            this.author = itemView.findViewById(R.id.author);
            this.score = itemView.findViewById(R.id.score);
            this.preview = itemView.findViewById(R.id.preview);
        }

        void bind(AdapterItem item) {
            title.setText(item.title);
            subtitle.setText(item.subtitle);
            author.setText(item.author);
            score.setText(item.score);

            preview.setImageResource(android.R.color.transparent);

            String previewUrl = item.imagePreviewUrl;
            if (!TextUtils.isEmpty(previewUrl)) {
                preview.setVisibility(View.VISIBLE);
                RequestOptions options = new RequestOptions();
                options.centerCrop();

                Glide.with(itemView.getContext())
                        .load(previewUrl)
                        .apply(options)
                        .into(preview);

            } else {
                preview.setVisibility(View.GONE);
            }
        }
    }
}
