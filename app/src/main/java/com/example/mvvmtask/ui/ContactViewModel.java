package com.example.mvvmtask.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mvvmtask.data.ContactRepo;
import com.example.mvvmtask.data.model.Contact;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepo contactRepository;
    private LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        contactRepository = new ContactRepo(application);
        allContacts = contactRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insert(Contact contact) {
        contactRepository.insert(contact);
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    public void update(Contact contact) {
        contactRepository.update(contact);
    }

    public LiveData<Contact> getContactById(int contactId) {
        return contactRepository.getContactById(contactId);
    }
}

