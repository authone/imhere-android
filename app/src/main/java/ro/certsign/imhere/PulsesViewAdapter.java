package ro.certsign.imhere;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PulsesViewAdapter extends RecyclerView.Adapter<PulsesViewAdapter.MyViewHolder> {

    private List<Pulse> pulsesAfter8;
    private List<Pulse> pulsesLasth;
    private List<Pulse> dataSet;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, date, room;
        public ImageView ismoke;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            room = (TextView) view.findViewById(R.id.room);
            date = (TextView) view.findViewById(R.id.date);
            ismoke = (ImageView) view.findViewById(R.id.ismoke);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PulsesViewAdapter() {
        dataSet = new ArrayList<Pulse>(0);
    }

    public void setPulsesAfter8(List<Pulse> pulses ) {
        this.pulsesAfter8 = pulses;
        updateDataSet();
    }
    public void setPulsesLasth(List<Pulse> pulses ) {
        this.pulsesLasth = pulses;
        updateDataSet();
    }

    private void updateDataSet() {
        dataSet.clear();
        dataSet.add( Pulse.getInstanceEmpty().setName("---- After Eight ----"));
        if(pulsesAfter8 != null) { dataSet.addAll(pulsesAfter8); }
        dataSet.add( Pulse.getInstanceEmpty().setName("---- Last Hour ----"));
        if(pulsesLasth != null)  { dataSet.addAll(pulsesLasth); }
        notifyDataSetChanged();
    }
    // Create new views (invoked by the layout manager)
    @Override
    public PulsesViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_item, parent, false);
        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Pulse p = this.dataSet.get(position);

        holder.name.setText( (p.getName()!="")?p.getName() : p.getId() );
        holder.room.setText(p.getRoom());
        holder.date.setText(p.getDate());
        if(p.smoker == 0) { holder.ismoke.setVisibility(View.INVISIBLE); }
        else { holder.ismoke.setVisibility(View.VISIBLE); }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}