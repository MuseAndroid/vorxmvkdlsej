package kr.co.genexon.factFinder.Fingerprint;

import android.content.Context;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/**
 * Created by topgu on 2018-04-24.
 */

public class Util {

    public static boolean isfingerprintAuthAvailable(Context context)
    {
        FingerprintManagerCompat mFingerprintManager;
        mFingerprintManager = FingerprintManagerCompat.from(context);
        if (mFingerprintManager.isHardwareDetected() && mFingerprintManager.hasEnrolledFingerprints()) {
            return true;
        } else {
            return false;
        }
    }

    public static FingerprintManagerCompat getFingerprintManagerCompat (Context context) {
        FingerprintManagerCompat mFingerprintManager;
        mFingerprintManager = FingerprintManagerCompat.from(context);
        return mFingerprintManager;
    }
}
