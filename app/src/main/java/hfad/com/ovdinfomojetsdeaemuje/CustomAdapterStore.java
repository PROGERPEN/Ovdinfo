package hfad.com.ovdinfomojetsdeaemuje;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static hfad.com.ovdinfomojetsdeaemuje.R.id.parent;


class CustomAdapterStore extends RecyclerView.Adapter<CustomAdapterStore.ViewHolder> {

private Context context;
private List<MyDataTexts> my_data2;

public static class ViewHolder extends RecyclerView.ViewHolder{

    public TextView title;
    public ImageView imageView;

    public ViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.card_title);
        imageView = (ImageView) itemView.findViewById(R.id.card_image);

    }
}

    @Override
    public CustomAdapterStore.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.title.setText(my_data2.get(position).getTitle());

        Glide.with(context).load(my_data2.get(position).getImage()).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment;

                MainActivity mainActivity = (MainActivity)context;


                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivityStore.class);
                intent.putExtra(DetailActivityStore.EXTRA_POSITION, position);
                intent.putExtra(DetailActivityStore.EXTRA_TITLE, my_data2.get(position).getTitle());
                intent.putExtra(DetailActivityStore.EXTRA_TEXT, my_data2.get(position).getText());
                intent.putExtra(DetailActivityStore.EXTRA_IMAGE, my_data2.get(position).getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_data2.size();
    }

    public CustomAdapterStore(Context context, List<MyDataTexts> my_data) {
        this.context = context;
        this.my_data2 = my_data;
    }
}
