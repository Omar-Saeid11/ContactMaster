package com.example.mvvmtask.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvmtask.data.model.Contact;

import java.util.List;


@Dao
public interface ContactDao {
    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

    @Query("SELECT * FROM contacts")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT * FROM contacts WHERE id = :contactId")
    LiveData<Contact> getContactById(int contactId);
}
