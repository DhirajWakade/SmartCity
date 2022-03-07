package com.allinone.smartocity.customer.adaptor;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.allinone.smartocity.R;
import com.allinone.smartocity.customer.utility.ImageUrlUtils;
import com.allinone.smartocity.retrofit.OnitemClick;
import com.facebook.drawee.view.SimpleDraweeView;

public class TopBusinessAdapter  extends RecyclerView.Adapter<TopBusinessAdapter.ViewHolder> {
    private final OnitemClick click;
    private String[] mValues;
    private RecyclerView mRecyclerView;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final SimpleDraweeView mImageView;
        public final LinearLayout mLayoutItem;
        public final ImageView mImageViewWishlist;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
            mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
            mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
        }
    }

    public TopBusinessAdapter(RecyclerView recyclerView, String[] items, OnitemClick clic) {
        mValues = items;
        mRecyclerView = recyclerView;
        this.click = clic;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        if (holder.mImageView.getController() != null) {
            holder.mImageView.getController().onDetach();
        }
        if (holder.mImageView.getTopLevelDrawable() != null) {
            holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
           /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/
        final Uri uri = Uri.parse(mValues[position]);
        holder.mImageView.setImageURI(uri);
            /*holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, mValues[position]);
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mActivity.startActivity(intent);

                }
            });*/

        //Set click action for wishlist
        holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                imageUrlUtils.addWishlistImageUri(mValues[position]);
                holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp);
                notifyDataSetChanged();
                /// Toast.makeText(mActivity, "Item added to wishlist.", Toast.LENGTH_SHORT).show();

            }
        });


        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.itemClick(position, "");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.length;
    }
}
