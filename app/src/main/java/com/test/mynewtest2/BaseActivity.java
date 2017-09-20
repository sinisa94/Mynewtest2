package com.test.mynewtest2;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.test.mynewtest2.R;
import com.google.firebase.auth.FirebaseAuth;

public class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
    public String getUid() {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static class getCurrentLocation {
        public static double latitude, longitude;
        public double[] getValues(double x, double y) {
            double[] latlong = new double[2];
            latlong[0] = x;
            latlong[1] = y;
            latitude = y;
            longitude = x;
            return latlong;
        }
    }

}