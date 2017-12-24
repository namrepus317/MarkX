package com.example.christopher.markx;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 11/20/2017.
 */

public class AssignmentRVAdapter extends RecyclerView.Adapter<AssignmentRVAdapter.AssignmentViewHolder> {

    Context context;
    List<Assignment> assignments;


    public AssignmentRVAdapter(Context context, List<Assignment> assignments){
        this.context = context;
        this.assignments = assignments;
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        FrameLayout fl;
        TextView name;
        TextView dueDate;
        ImageView moreIcon;

        AssignmentViewHolder(View itemView){
            super(itemView);
            fl = (FrameLayout)itemView.findViewById(R.id.fl);
            name = (TextView)itemView.findViewById(R.id.assignmentTitle);
            dueDate = (TextView)itemView.findViewById(R.id.assignmentDueDate);
            moreIcon = (ImageView)itemView.findViewById(R.id.assignmentMoreIcon);

            moreIcon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            PopupMenu popupMenu = new PopupMenu(itemView.getContext(), v);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.inflate(R.menu.edit_assignment_menu);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.editAssignmentItem:
                    Intent i = new Intent(context.getApplicationContext(), EditAssignmentInfoActivity.class);
                    i.putExtra("AssignmentData", (Parcelable)assignments.get(getAdapterPosition()));
                    i.putExtra("AssignmentPos", getAdapterPosition());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(i);
                    return true;
                case R.id.deleteAssignmentItem:
                    assignments.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), assignments.size());

                    return true;
                default:
                    return false;
            }
        }

    }


    @Override
    public int getItemCount(){
         return assignments.size();
    }

    @Override
    public AssignmentRVAdapter.AssignmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignments_list_layout, viewGroup, false);
        AssignmentViewHolder avh = new AssignmentViewHolder(v);
        return avh;
    }

    @Override
    public void onBindViewHolder(AssignmentRVAdapter.AssignmentViewHolder assignmentViewHolder, int i){
        assignmentViewHolder.name.setText(assignments.get(i).name);
        assignmentViewHolder.dueDate.setText(assignments.get(i).dueDate);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }
}
