package com.bk.bestfind.preferences;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bk.bestfind.models.Product;
import com.bk.bestfind.utils.FormatUtil;
import com.example.mrt.breakfast.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrt on 24/06/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Product> data = new ArrayList<>();
    private Context context;

    // Interface click item
    private static OnItemClickListener click_listener;

    public interface OnItemClickListener{
        void onItemClick(View itemView, int position);
    }

    // Set listener
    public void setOnItemClickListener(OnItemClickListener listener){
        click_listener = listener;
    }


    // constructor
    public ProductsAdapter(Context context){
        this.context = context;
        this.data = new ArrayList<>();
    }


    private static class ProductVH extends RecyclerView.ViewHolder{
        //Member variable of itemView
        ImageView product_image;
        TextView product_name, product_price, product_shop_name, product_shop_address;

        ProductVH(final View itemView) {
            super(itemView);
            product_image = (ImageView)itemView.findViewById(R.id.product_image);
            product_name = (TextView)itemView.findViewById(R.id.product_name);
            product_price = (TextView)itemView.findViewById(R.id.product_price);
            product_shop_name = (TextView)itemView.findViewById(R.id.product_shop_name);
            product_shop_address = (TextView)itemView.findViewById(R.id.product_shop_address);

            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click_listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                            click_listener.onItemClick(itemView, position);
                    }
                }
            });
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_product, parent, false);
        return new ProductVH(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // Get the order model based on position
        Product product = data.get(position);
        ProductVH vh = (ProductVH)holder;

        // Set item views
        vh.product_name.setText(product.getName());
        vh.product_price.setText(FormatUtil.formatCurrency(Double.valueOf(product.getPrice()), "VND"));
        vh.product_shop_name.setText(product.getShop().getName());
        vh.product_shop_address.setText(product.getShop().getAddress());

        Picasso.with(context).load(product.getImage_link()).fit().centerCrop().
                into(vh.product_image);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}

