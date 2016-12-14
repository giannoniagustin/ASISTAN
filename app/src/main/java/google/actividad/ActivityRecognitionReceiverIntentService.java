package google.actividad;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

//import transporte.appbase.Archivos.Log;


public class ActivityRecognitionReceiverIntentService extends IntentService {

    private static final String ACTION_FOO = "trackerubicacion.serviciotrackerubicacion.action.FOO";
    private static final String ACTION_BAZ = "trackerubicacion.serviciotrackerubicacion.action.BAZ";


    private static final String EXTRA_PARAM1 = "trackerubicacion.serviciotrackerubicacion.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "trackerubicacion.serviciotrackerubicacion.extra.PARAM2";


    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ActivityRecognitionReceiverIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, ActivityRecognitionReceiverIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public ActivityRecognitionReceiverIntentService() {
        super("ActivityRecognitionReceiverIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            if (intent != null) {
                ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);

                DetectedActivity mostProbableActivity = result.getMostProbableActivity();
                int activityType = mostProbableActivity.getType();
                int confidence = mostProbableActivity.getConfidence();

                Intent notifyIntent = new Intent("receive_recognition");
                notifyIntent.setPackage(getPackageName());
                notifyIntent.putExtra("activity_type", activityType);
                notifyIntent.putExtra("confidence", confidence);
                notifyIntent.putExtra("time", result.getTime());
                sendBroadcast(notifyIntent);

            }
        }catch (Exception e){
            //Log.LOGGER.severe(e.toString());
        }


    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
