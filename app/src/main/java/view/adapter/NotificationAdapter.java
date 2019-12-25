package view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.martinarroyo.wgutermtracker.NoteDetailActivity;
import com.martinarroyo.wgutermtracker.R;

import java.util.List;

import logic.entity.AssessmentNotification;
import view.DeleteDialogFragment;

public class NotificationAdapter extends RecyclerView.Adapter {
    private Context context;

    // Inner class to display each item in the Recycler view - requires its own layout
    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNotificationTitleView;
        private final TextView mNotificationDateView;
        private final Button mDeleteNoteButton;

        private NotificationViewHolder(View itemView) {
            super(itemView);
            ViewGroup viewGroup = (ViewGroup) itemView;
            mNotificationTitleView = itemView.findViewById(R.id.notification_title);
            mNotificationDateView = itemView.findViewById(R.id.notification_date);
            mDeleteNoteButton = itemView.findViewById(R.id.delete_notification_button);
        }
    }
    private final LayoutInflater mInflater;
    private List<AssessmentNotification> mNotifications; // Cached copy of Notes
    // Constructor for the Adapter -
    public NotificationAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    // Override methods for the Adapter class

    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.notification_adapter_layout, parent, false);
        return new NotificationAdapter.NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        NotificationAdapter.NotificationViewHolder holder1 = (NotificationAdapter.NotificationViewHolder) holder;

        if (mNotifications != null) {
            // Get an instance of the current item
            final AssessmentNotification current = mNotifications.get(position);
            final int currentPos = position;
            //Set up the data for each of the views
            holder1.mNotificationTitleView.setText(current.getTitle());
            // Set up button actions
            // View Course details
            // Set on click listener for adapter - VIEW/MODIFY Assessment
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(((NoteDetailActivity)context),current.toString(),Toast.LENGTH_SHORT).show();
                    /*
                    Intent intent = new Intent(((NoteDetailActivity)context), AddEditCourseNote.class);
                    intent.putExtra(AddEditCourseNote.MOD_NOTE,current);
                    ((NoteDetailActivity)context).startActivityForResult(intent, NoteDetailActivity.NOTE_MOD_CODE);

                     */
                }
            });


            //DELETE Notification
            holder1.mDeleteNoteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteDialogFragment delete = new DeleteDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(DeleteDialogFragment.ITEM_POS,currentPos);
                    delete.setArguments(bundle);
                    delete.show(((NoteDetailActivity)context).getSupportFragmentManager(),"Delete Notification?");
                }

            });

        } else {
            // Covers the case of data not being ready yet.
            holder1.mNotificationTitleView.setText("No Note");
        }
    }
    // set the data for our list
    public void setNotifications(List<AssessmentNotification> notifications){
        mNotifications = notifications;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNotifications != null)
            return mNotifications.size();
        else return 0;
    }


}
