package hfad.com.ovdinfomojetsdeaemuje;

import android.app.Fragment;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


class CustomAdapterREALM extends RecyclerView.Adapter<CustomAdapterREALM.ViewHolder> {

private Context context;
private List<MyDataRealm> my_data;


public static class ViewHolder extends RecyclerView.ViewHolder{

    public TextView title;

    public ViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.text_view);

    }
}

    @Override
    public CustomAdapterREALM.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.title.setText(my_data.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment;

                MainActivity mainActivity = (MainActivity)context;
                Context context = v.getContext();

                Intent intent = new Intent(context, DetailActivity.class);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(intent);

                intent.putExtra(DetailActivity.EXTRA_POSITION, position);
                intent.putExtra(DetailActivity.EXTRA_TITLE, my_data.get(position).getTitle());
                intent.putExtra(DetailActivity.EXTRA_TEXT, my_data.get(position).getText());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public CustomAdapterREALM(Context context, List<MyDataRealm> my_data) {
        this.context = context;
        this.my_data = my_data;
    }
}
