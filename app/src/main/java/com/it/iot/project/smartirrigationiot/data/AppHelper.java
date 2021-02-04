package com.it.iot.project.smartirrigationiot.data;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.it.iot.project.smartirrigationiot.MyApplication;

public class AppHelper {
    private static final String COLLECTION_NAME = "iotData";
    private static final String COLLECTION_NAME_USER = "owners";

    private static Query mQuery;


// --- COLLECTION REFERENCE ---

    public static CollectionReference geLoanCollection() {
        return new MyApplication().getDb().collection(COLLECTION_NAME);
    }

    public static DatabaseReference getDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    // --- CREATE ---
    public static Task<DataSnapshot> getDaysData(String year, String month, String date,String hour, String minute) {
        System.out.printf("Making request %s%n, %s%n, %s%n, %s%n,%s%n", year, month, date, hour, minute);
        return getDatabaseReference().child("FirebaseIOT").child(year).child(month).child(date).child(hour).child(minute).get();
    }

    public static Task<DataSnapshot> getMonthData(String year, String month) {
        return getDatabaseReference().child(year).child(month).get();

    }

    public static Task<DataSnapshot> getYearData(String year) {
        return getDatabaseReference().child(year).get();

    }

    public static Task<DocumentSnapshot> getReading(String date) {
        return AppHelper.geLoanCollection().document().get();
    }

    // --- GET ---

    public static Task<Void> updateLoan(String username, String uid) {
        return AppHelper.geLoanCollection().document(uid).update("username", username);
    }

    // --- UPDATE ---

    public static Task<Void> deleteUser(String uid) {
        return AppHelper.geLoanCollection().document(uid).delete();
    }


    // --- DELETE ---

    //-- Get many
    public static Query getReadings() {
        return mQuery = geLoanCollection();
    }
}
