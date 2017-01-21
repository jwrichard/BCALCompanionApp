package ca.justinrichard.bcal;

import android.util.Log;
import android.webkit.CookieManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Justin on 1/17/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    final String TAG = "FBInstanceIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // Take action with the recieved token
        saveRegistrationToken(refreshedToken);
    }

    private void saveRegistrationToken(String token){
        // Save value to local storage, and trigger main activity to update its cookie to include it
        // Set cookie
        CookieManager.getInstance().setCookie("https://bcal.ca/", "firebaseToken="+token);
    }
}
