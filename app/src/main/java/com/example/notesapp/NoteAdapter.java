package com.example.notesapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemClickListener listener;
    private static Context context;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority() == newItem.getPriority() &&
                    Arrays.equals(oldItem.getImage(), newItem.getImage());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
        holder.textViewPriority.setText(String.valueOf(currentNote.getPriority()));
        holder.circleImageView.setImageBitmap(convertByteArrayToBitmap(currentNote.getImage()));
    }

    public static Bitmap convertByteArrayToBitmap(byte[] image) {
        if (image != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(image , 0, image.length);
            return bitmap;
        } else {
            Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_add_a_photo_black_48dp);
            return icon;
        }
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewDescription, textViewPriority;
        private CircleImageView circleImageView;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            circleImageView = itemView.findViewById(R.id.note_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
