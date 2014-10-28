package course.labs.notificationslab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

public class DownloaderTask extends AsyncTask<String, Void, String[]> {

	private static final int SIM_NETWORK_DELAY = 1000;
	private static final String TAG = "Lab-Notifications";
	private final int MY_NOTIFICATION_ID = 11151990;
	private String mFeeds[] = new String[3];
	private MainActivity mParentActivity;
	private Context mApplicationContext;

	// Raw feed file IDs used in this offline version of the app
	public static final int txtFeeds[] = { R.raw.tswift, R.raw.rblack,
			R.raw.lgaga };

	// Constructor
	public DownloaderTask(MainActivity parentActivity) {
		super();

		mParentActivity = parentActivity;
		mApplicationContext = parentActivity.getApplicationContext();

	}

	@Override
	protected String[] doInBackground(String... urlParameters) {
		Log.i(TAG, "Entered doInBackground()");

		return download(urlParameters);

	}

	// Simulate downloading tweets from the network
	private String[] download(String urlParameters[]) {

		boolean downloadCompleted = false;

		try {

			for (int idx = 0; idx < urlParameters.length; idx++) {

				InputStream inputStream;
				BufferedReader in;

				try {
					// Pretend the tweets take a long time to load
					Thread.sleep(SIM_NETWORK_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				inputStream = mApplicationContext.getResources()
						.openRawResource(txtFeeds[idx]);
				in = new BufferedReader(new InputStreamReader(inputStream));

				String readLine;
				StringBuffer buf = new StringBuffer();

				while ((readLine = in.readLine()) != null) {
					buf.append(readLine);
				}

				mFeeds[idx] = buf.toString();

				if (null != in) {
					in.close();
				}
			}

			downloadCompleted = true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.i(TAG, "Tweet Download Completed:" + downloadCompleted);

		notify(downloadCompleted);

		return mFeeds;

	}

	// Call back to the MainActivity to update the feed display

	@Override
	protected void onPostExecute(String[] result) {
		super.onPostExecute(result);

		if (mParentActivity != null) {
			mParentActivity.setRefreshed(result);
		}

	}

	// If necessary, notifies the user that the tweet downloads are complete.
	// Sends an ordered broadcast back to the BroadcastReceiver in MainActivity
	// to determine whether the notification is necessary.

	private void notify(final boolean success) {
		Log.i(TAG, "Entered notify()");

		final Intent restartMainActivtyIntent = new Intent(mApplicationContext,
				MainActivity.class);
		restartMainActivtyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (success) {

			// Save tweets to a file
			saveTweetsToFile();

		}

		// Sends an ordered broadcast to determine whether MainActivity is
		// active and in the foreground. Creates a new BroadcastReceiver
		// to receive a result indicating the state of MainActivity

		// The Action for this broadcast Intent is
		// MainActivity.DATA_REFRESHED_ACTION
		// The result MainActivity.IS_ALIVE, indicates that MainActivity 
		// is active and in the foreground.

		mApplicationContext.sendOrderedBroadcast(new Intent(
				MainActivity.DATA_REFRESHED_ACTION), null,
				new BroadcastReceiver() {

					final String failMsg = "Download has failed. Please retry Later.";
					final String successMsg = "Download completed successfully.";

					@Override
					public void onReceive(Context context, Intent intent) {

						Log.i(TAG,
								"Entered result receiver's onReceive() method");

						// TODO: Check whether the result code is not MainActivity.IS_ALIVE

						if (getResultCode() != MainActivity.IS_ALIVE) {

							// TODO: If so, create a PendingIntent using the
							// restartMainActivityIntent and set its flags
							// to FLAG_UPDATE_CURRENT
							PendingIntent pendingIntent = PendingIntent.getActivity(mApplicationContext, 0, restartMainActivtyIntent, PendingIntent.FLAG_UPDATE_CURRENT);


							
							

							// Uses R.layout.custom_notification for the
							// layout of the notification View. The xml
							// file is in res/layout/custom_notification.xml

							RemoteViews mContentView = new RemoteViews(
									mApplicationContext.getPackageName(),
									R.layout.custom_notification);

							// TODO: Set the notification View's text to
							// reflect whether the download completed
							// successfully
							mContentView.setTextViewText(R.id.text, success ? successMsg : failMsg);


							

							// TODO: Use the Notification.Builder class to
							// create the Notification. You will have to set
							// several pieces of information. You can use
							// android.R.drawable.stat_sys_warning
							// for the small icon. You should also
							// setAutoCancel(true).
							Notification notification = new Notification.Builder(mApplicationContext)
					         .setContentTitle("Download completed successfully.")
					         .setSmallIcon(android.R.drawable.stat_sys_warning)
					         .setAutoCancel(true)
					         .setContentIntent(pendingIntent)
					         .build();


							
							
							
							
							// TODO: Send the notification
							NotificationManager mNotificationManager = (NotificationManager) mApplicationContext.getSystemService(Context.NOTIFICATION_SERVICE);
							mNotificationManager.notify(MY_NOTIFICATION_ID, notification);
							
							

							Log.i(TAG, "Notification Area Notification sent");
						}
					}
				}, null, 0, null, null);
	}

	// Saves the tweets to a file
	private void saveTweetsToFile() {
		PrintWriter writer = null;
		try {
			FileOutputStream fos = mApplicationContext.openFileOutput(
					MainActivity.TWEET_FILENAME, Context.MODE_PRIVATE);
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					fos)));

			for (String s : mFeeds) {
				writer.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				writer.close();
			}
		}
	}

}
