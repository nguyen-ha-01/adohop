package com.apolom.aodoshop.fragments.profile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apolom.aodoshop.models.UserData;
import com.apolom.aodoshop.repo.DbCloud;
import com.apolom.aodoshop.repo.SharedPreferencesManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewModel extends ViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DbCloud db= new  DbCloud("");
    private SharedPreferencesManager sharedPreferencesManager ;
    public MutableLiveData<UserData> userData = new MutableLiveData<>();
    ProfileViewModel(Context ctx){
        sharedPreferencesManager = new SharedPreferencesManager(ctx);
    }
    public void logout() {
        sharedPreferencesManager.clearUID();
        auth.signOut();
    }
    public  void  getUserName(){
        try{
            FirebaseFirestore.getInstance().collection(DbCloud.user).document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    UserData u = UserData.fromMap(auth.getCurrentUser().getUid(),task.getResult().getData());
                    userData.setValue(u);
                }
            });

        }
        catch (Exception e){     userData.setValue(null);}
    }
    // TODO: Implement the ViewModel
}