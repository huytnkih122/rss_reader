package com.example.rssreader.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.data.RssInfo;
import com.example.rssreader.data.RssParser;
import com.example.rssreader.ui.history.HistoryItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

public class Repository {

    private static final Repository INSTANCE = new Repository();
    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final FirebaseAuth firebaseAuth;
    private MutableLiveData<RssInfo> rssInfoMutableLiveData;
    private final DatabaseReference database;
    public AsyncResponse delegate = null;
    private MutableLiveData<List<HistoryItem>> historyItemsMutableLiveData;

    public Repository() {
        firebaseAuth = FirebaseAuth.getInstance();
        this.userMutableLiveData = new MutableLiveData<>();
        updateUser();
        this.rssInfoMutableLiveData = new MutableLiveData<RssInfo>();
        database = FirebaseDatabase.getInstance("https://rssreader-d5d85-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        historyItemsMutableLiveData = new MutableLiveData<>();
    }

    public static Repository getInstance() {
        return INSTANCE;
    }

    public MutableLiveData<List<HistoryItem>> getHistoryItemsMutableLiveData() {
        return historyItemsMutableLiveData;
    }
    public void resetRssInfo(){
        this.rssInfoMutableLiveData = new MutableLiveData<RssInfo>();
    }
    public MutableLiveData<RssInfo> getRssInfoMutableLiveData() {
        return rssInfoMutableLiveData;
    }

    private void updateUser() {
        userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
    }

    public void register(String email, String password) {
        this.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUser();
                        } else {
                            Log.e("MYTAG", "Registration Failed" + task.getException().getMessage());
                        }
                    }
                });
    }

    public void login(String email, String password) {
        this.firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUser();
                        } else {
                            Log.e("MYTAG", "Registration Failed" + task.getException().getMessage());
                        }
                    }
                });
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        this.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUser();
                        } else {
                            Log.e("MYTAG", "Registration Failed" + task.getException().getMessage());
                        }
                    }
                });
    }

    public void signOut() {
        this.firebaseAuth.signOut();
        this.rssInfoMutableLiveData = new MutableLiveData<RssInfo>();
        historyItemsMutableLiveData = new MutableLiveData<>();
        updateUser();
    }


    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void fetchRSSData(String RSS_FEED_LINK) throws MalformedURLException {
        URL url = new URL(RSS_FEED_LINK);

        FetchFeedTask task = new FetchFeedTask();
        task.execute(url);
        task.delegate = new AsyncResponse() {
            @Override
            public void processFinish(RssInfo output) {
                rssInfoMutableLiveData.postValue(output);
            }
        };

    }

    public void addHistoryItem(HistoryItem item) {
        if (firebaseAuth.getCurrentUser() != null) {
            database.child("data")
                    .child(this.firebaseAuth.getCurrentUser().getUid())
                    .child(String.valueOf(UUID.nameUUIDFromBytes(item.getUrl().getBytes()).getMostSignificantBits()))
                    .setValue(item, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                            if (error != null) {
                                System.out.println("Data could not be saved " + error.getMessage());
                            } else {
                                System.out.println("Data saved successfully.");
                            }
                        }
                    });
        }
    }

    public void loadData() {
        if (this.firebaseAuth.getCurrentUser() != null) {
            ArrayList<HistoryItem> list = new ArrayList<>();
            Query query = database.child("data").child(this.firebaseAuth.getCurrentUser().getUid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        list.add(dataSnapshot.getValue(HistoryItem.class));
                        Log.i("MYTAG", dataSnapshot.getValue(HistoryItem.class).getUrl());
                    }
                    historyItemsMutableLiveData.postValue(list);

                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }


    }

    public interface AsyncResponse {
        void processFinish(RssInfo output);
    }

    private class FetchFeedTask extends AsyncTask<URL, Void, RssInfo> {
        public AsyncResponse delegate = null;
        private InputStream stream = null;

        @Override
        protected RssInfo doInBackground(URL... urls) {
            int responseCode = 0;
            HttpsURLConnection connect = null;
            try {
                connect = (HttpsURLConnection) urls[0].openConnection();
                connect.setReadTimeout(8000);
                connect.setConnectTimeout(8000);
                connect.setRequestMethod("GET");
                connect.connect();
                responseCode = connect.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RssInfo rssInfo = null;
            if (responseCode == 200) {
                try {
                    stream = connect.getInputStream();
                    RssParser parser = new RssParser();
                    rssInfo = parser.parse(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return rssInfo;
        }

        @Override
        protected void onPostExecute(RssInfo rssInfo) {
            super.onPostExecute(rssInfo);
            if (rssInfo != null && !rssInfo.getRssItems().isEmpty()) {
                delegate.processFinish(rssInfo);
            }
        }
    }
}




