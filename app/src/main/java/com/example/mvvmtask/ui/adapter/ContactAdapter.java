package com.example.mvvmtask.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmtask.R;
import com.example.mvvmtask.data.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> contacts;
    private Context context;
    private OnItemClickListener listener;

    public ContactAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.contacts = new ArrayList<>(); // Initialize the contacts list
    }

    public interface OnItemClickListener {
        void onDeleteClick(Contact contact);
        void onPhoneClick(Contact contact);
        void onEditClick(Contact contact);
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.nameTextView.setText(contact.getName());
        holder.phoneTextView.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView phoneTextView;
        private ImageView deleteImageView;
        private ImageView editImageView;
        private ImageView phoneImageView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
            editImageView = itemView.findViewById(R.id.editImageView);
            phoneImageView = itemView.findViewById(R.id.phoneImageView);

            phoneImageView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onPhoneClick(contacts.get(position));
                    }
                }
            });

            deleteImageView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(contacts.get(position));
                    }
                }
            });

            editImageView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onEditClick(contacts.get(position));
                    }
                }
            });
        }
    }
}
