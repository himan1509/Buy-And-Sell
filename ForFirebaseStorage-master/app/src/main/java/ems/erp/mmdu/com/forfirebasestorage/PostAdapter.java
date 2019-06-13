package ems.erp.mmdu.com.forfirebasestorage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ProductViewHolder> {


        //this context we will use to inflate the layout
        private Context mCtx;

        //we are storing all the products in a list
        private List<Post> productList;

        //getting the context and product list with constructor
        public PostAdapter(Context mCtx, List<Post> productList) {
            this.mCtx = mCtx;
            this.productList = productList;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            //inflating and returning our view holder
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view;
            view = inflater.inflate(R.layout.layout_product2, null);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductViewHolder holder, int position) {
            //getting the product of the specified position
            Post post = productList.get(position);

            //binding the data with the viewholder views
            holder.itemName.setText(post.getTitle());
            holder.itemCategory.setText(post.getCategories());
            holder.itemPrice.setText("Rs "+String.valueOf(post.getPrice()));
            Picasso
                    .with(holder.itemImage.getContext())
                    .load(post.getImageLink())
                    .into(holder.itemImage);
            holder.itemBids.setText("No of Bids "+Integer.valueOf(post.getBids().size()).toString());
            holder.added_by.setText("added by "+ (post.getAddedBy().length() > 10 ? post.getAddedBy().substring(0,10) : post.getAddedBy()));
        }


        @Override
        public int getItemCount() {
            return productList.size();
        }


        class ProductViewHolder extends RecyclerView.ViewHolder {

            TextView itemName, itemCategory, itemPrice,itemBids,added_by;
            ImageView itemImage;

            public ProductViewHolder(View itemView) {
                super(itemView);

                itemName = itemView.findViewById(R.id.item_name);
                itemCategory = itemView.findViewById(R.id.item_category);
                itemPrice = itemView.findViewById(R.id.item_price);
                itemImage = itemView.findViewById(R.id.item_image);
                itemBids = itemView.findViewById(R.id.item_bids);
                added_by = itemView.findViewById(R.id.added_by);
            }
        }
}
