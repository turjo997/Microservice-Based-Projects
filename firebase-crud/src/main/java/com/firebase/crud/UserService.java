package com.firebase.crud;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    public String addUser(Users users) throws ExecutionException, InterruptedException {

        Firestore dbStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbStore
                .collection("users").document(users.getDocumentId()).set(users);

       return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Users getUser(String documentId) throws ExecutionException, InterruptedException {

        Firestore dbStore = FirestoreClient.getFirestore();

        DocumentReference documentReference = dbStore.collection("users").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        DocumentSnapshot document = future.get();
        Users users;

        if(document.exists()){
            users = document.toObject(Users.class);
            return users;
        }

       return null;
    }

    public String updateUser(Users users) throws ExecutionException, InterruptedException {
        Firestore dbStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbStore
                .collection("users").document(users.getDocumentId()).set(users);

        return collectionsApiFuture.get().getUpdateTime().toString();

    }

    public String deleteUser(String documentId) {
        Firestore dbStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult =  dbStore.collection("users").document(documentId).delete();
        return "Successfully deleted " +documentId;
    }
}
