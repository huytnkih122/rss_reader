package com.example.rssreader.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.rssreader.data.RssItem;
import com.example.rssreader.data.RssParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Repository {

    private static final Repository INSTANCE = new Repository();

    // Private constructor to avoid client applications to use constructor


    public static Repository getInstance() {
        return INSTANCE;
    }

    private final MutableLiveData<FirebaseUser> userMutableLiveData;
    private final FirebaseAuth firebaseAuth;
    private MutableLiveData<List<RssItem>> listRssItemMutableLiveData;
    public AsyncResponse delegate = null;
    public Repository() {
        firebaseAuth = FirebaseAuth.getInstance();
        this.userMutableLiveData = new MutableLiveData<>();
        this.listRssItemMutableLiveData = new MutableLiveData<List<RssItem>>();
    }

    public MutableLiveData<List<RssItem>> getListRssItemMutableLiveData() {
        return listRssItemMutableLiveData;
    }
    private void updateUser() {
        userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
    }

    public void register(String email, String password) {
        this.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
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
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUser();
                        } else {
                            Log.e("MYTAG", "Registration Failed" + task.getException().getMessage()); }
                    }
                });
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        this.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUser();
                        } else {
                            Log.e("MYTAG", "Registration Failed" + task.getException().getMessage());  }
                    }
                });
    }

    public void signOut() {
        this.firebaseAuth.signOut();
        updateUser();
    }


    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void fetchRSSData(String RSS_FEED_LINK) throws MalformedURLException {
        URL url = new URL(RSS_FEED_LINK);

        MutableLiveData<List<RssItem>> data = new MutableLiveData<List<RssItem>>();
        FetchFeedTask task =  new FetchFeedTask(data);
        task.execute(url);
        task.delegate = new AsyncResponse() {
            @Override
            public void processFinish(List<RssItem> output) {
                listRssItemMutableLiveData.postValue(output);
            }
        };

    }
    public interface AsyncResponse {
        void processFinish(List<RssItem> output);
    }

    private class FetchFeedTask extends AsyncTask<URL, Void, List<RssItem>> {
        private MutableLiveData<List<RssItem>> dataObj;
        private InputStream stream = null;
        public AsyncResponse delegate = null;
        public FetchFeedTask(MutableLiveData<List<RssItem>> data) {
            this.dataObj = data;
        }

        @Override
        protected List<RssItem> doInBackground(URL... urls) {
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
            List<RssItem> rssItems = null;
            if (responseCode == 200) {
                try {
                    stream = connect.getInputStream();
                    RssParser parser = new RssParser();
                    rssItems = parser.parse(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return rssItems;
        }

        @Override
        protected void onPostExecute(List<RssItem> rssItems) {
            super.onPostExecute(rssItems);
            if (rssItems != null && !rssItems.isEmpty()) {
                dataObj.setValue(rssItems);
                delegate.processFinish(rssItems);
            }
        }


    }
}




