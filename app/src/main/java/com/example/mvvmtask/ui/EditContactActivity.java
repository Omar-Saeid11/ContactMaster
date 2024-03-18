package com.example.mvvmtask.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmtask.R;
import com.example.mvvmtask.data.model.Contact;

public class EditContactActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    private Button saveButton;
    private ContactViewModel contactViewModel;
    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        saveButton = findViewById(R.id.saveButton);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        if (getIntent().hasExtra("contactId")) {
            contactId = getIntent().getIntExtra("contactId", -1);
            contactViewModel.getContactById(contactId).observe(this, new Observer<Contact>() {
                @Override
                public void onChanged(Contact contact) {
                    if (contact != null) {
                        nameEditText.setText(contact.getName());
                        phoneEditText.setText(contact.getPhoneNumber());
                    }
                }
            });
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    Contact updatedContact = new Contact(name, phone);
                    updatedContact.setId(contactId);
                    contactViewModel.update(updatedContact);
                    finish();
                } else {
                    Toast.makeText(EditContactActivity.this, "Please enter name and phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
