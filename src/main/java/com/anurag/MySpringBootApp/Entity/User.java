package com.anurag.MySpringBootApp.Entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {
@Id
private ObjectId userId;
@Indexed(unique = true)
@NonNull
private String userName;
private String email;
private List<String> roles= new ArrayList<>();
@NonNull
private String password;
@DBRef
private List<JournalEntity> journalEntries=new ArrayList<JournalEntity>();
}
