package com.it.iot.project.smartirrigationiot;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(2, 4,
            60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private static MyApplication mInstance;
//    private RequestQueue mRequestQueue;
    private FirebaseFirestore db;
//    private PeriodicWorkRequest workRequest;
    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        DatabaseReference scoresRef = FirebaseDatabase.getInstance().getReference("FirebaseIOT");
        scoresRef.keepSynced(true);
        FirebaseMessaging.getInstance().subscribeToTopic("global");
        db = FirebaseFirestore.getInstance();
        setupCacheSize();

    }



    // [END delete_collection]

    public FirebaseFirestore getDb() {
        return FirebaseFirestore.getInstance();
    }

    public static void setQuicksand_Regular(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand/static/Quicksand-Regular.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }

    public static void setQuicksand_Regular(Context context, TextView textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand/static/Quicksand-Regular.ttf");
        textViews.setTypeface(typeface);
    }
    public static void setBodoni_Moda(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Bodoni_Moda/BodoniModa_6pt-Regular.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }

    public static void setBodoni_Moda(Context context, TextView textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Bodoni_Moda/BodoniModa_7pt-Regular.ttf");
        textViews.setTypeface(typeface);
    }

    void deleteAll() {
        deleteCollection("cities");
        deleteCollection("users");
    }

    private void deleteCollection(final String path) {
        deleteCollection(db.collection(path), 50, EXECUTOR);
    }

    public void setup() {
        // [START get_firestore_instance]
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }

    public void setupCacheSize() {
        // [START fs_setup_cache]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build();
        db.setFirestoreSettings(settings);
        // [END fs_setup_cache]
    }

    /**
     * Delete all documents in a collection. Uses an Executor to perform work on a background
     * thread. This does *not* automatically discover and delete subcollections.
     */
    private Task<Void> deleteCollection(final CollectionReference collection,
                                        final int batchSize,
                                        Executor executor) {

        // Perform the delete operation on the provided Executor, which allows us to use
        // simpler synchronous logic without blocking the main thread.
        return Tasks.call(executor, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                // Get the first batch of documents in the collection
                Query query = collection.orderBy(FieldPath.documentId()).limit(batchSize);

                // Get a list of deleted documents
                List<DocumentSnapshot> deleted = deleteQueryBatch(query);

                // While the deleted documents in the last batch indicate that there
                // may still be more documents in the collection, page down to the
                // next batch and delete again
                while (deleted.size() >= batchSize) {
                    // Move the query cursor to start after the last doc in the batch
                    DocumentSnapshot last = deleted.get(deleted.size() - 1);
                    query = collection.orderBy(FieldPath.documentId())
                            .startAfter(last.getId())
                            .limit(batchSize);

                    deleted = deleteQueryBatch(query);
                }

                return null;
            }
        });

    }

    /**
     * Delete all results from a query in a single WriteBatch. Must be run on a worker thread
     * to avoid blocking/crashing the main thread.
     */
    @WorkerThread
    private List<DocumentSnapshot> deleteQueryBatch(final Query query) throws Exception {
        QuerySnapshot querySnapshot = Tasks.await(query.get());

        WriteBatch batch = query.getFirestore().batch();
        for (QueryDocumentSnapshot snapshot : querySnapshot) {
            batch.delete(snapshot.getReference());
        }
        Tasks.await(batch.commit());

        return querySnapshot.getDocuments();
    }

    public void toggleOffline() {
        // [START disable_network]
        db.disableNetwork()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Do offline things
                        // ...
                    }
                });
        // [END disable_network]

        // [START enable_network]
        db.enableNetwork()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Do online things
                        // ...
                    }
                });
        // [END enable_network]
    }

    public void offlineListen(FirebaseFirestore db) {
        // [START offline_listen]
        db.collection("cities").whereEqualTo("state", "CA")
                .addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }

                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                Log.d(TAG, "New city:" + change.getDocument().getData());
                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }

                    }
                });
        // [END offline_listen]
    }
}
