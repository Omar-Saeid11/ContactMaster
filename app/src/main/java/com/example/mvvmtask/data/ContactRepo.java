package com.example.mvvmtask.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mvvmtask.data.model.Contact;
import com.example.mvvmtask.db.ContactDao;
import com.example.mvvmtask.db.ContactDatabase;

import java.util.List;

public class ContactRepo {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    public ContactRepo(Application application) {
        ContactDatabase database = ContactDatabase.getInstance(application);
        contactDao = database.contactDao();
        allContacts = contactDao.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public void insert(Contact contact) {
        new InsertContactAsyncTask(contactDao).execute(contact);
    }

    public void update(Contact contact) {
        new UpdateContactAsyncTask(contactDao).execute(contact);
    }

    public void delete(Contact contact) {
        new DeleteContactAsyncTask(contactDao).execute(contact);
    }

    public LiveData<Contact> getContactById(int contactId) {
        return contactDao.getContactById(contactId);
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;

        private InsertContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;

        private UpdateContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;

        private DeleteContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.delete(contacts[0]);
            return null;
        }
    }
}

