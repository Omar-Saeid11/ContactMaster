package com.example.mvvmtask.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmtask.R;
import com.example.mvvmtask.data.model.Contact;
import com.example.mvvmtask.ui.adapter.ContactAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ContactViewModel contactViewModel;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new ContactAdapter(this, new ContactAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(Contact contact) {
                contactViewModel.delete(contact);
            }

            @Override
            public void onPhoneClick(Contact contact) {
                String phoneNumber = contact.getPhoneNumber();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }
            }

            @Override
            public void onEditClick(Contact contact) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                intent.putExtra("contactId", contact.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, contacts -> adapter.setContacts(contacts));

        FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> showAddContactDialog());
    }

    private void showAddContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Contact");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_contact, null);
        builder.setView(view);

        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText phoneEditText = view.findViewById(R.id.phoneEditText);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String name = nameEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();

            if (!name.isEmpty() && !phone.isEmpty()) {
                Contact contact = new Contact(name, phone);
                contactViewModel.insert(contact);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

