package com.skylightapp.Classes;

import android.content.Context;
import android.widget.Toast;

import com.skylightapp.Database.DBHelper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


public class DatabaseHelper {
    public static final String SAVINGS_KEY = "DatabaseHelper.SAVINGS_KEY";
    ArrayList<Birthday> birthdays;

    DBHelper dbHelper;
    private static DatabaseHelper instance;

    public static final String POSTS_DB_KEY = "posts";
    public static final String PROFILES_DB_KEY = "profiles";
    public static final String PACKAGE_DB_KEY = "package_key";
    public static final String LOAN_DB_KEY = "loan_key";
    public static final String DATA_DB_KEY = "data_key";
    public static final String DSTV_DB_KEY = "dstv_key";
    public static final String STARTTIMES_DB_KEY = "startTimes_key";
    public static final String GOTV_DB_KEY = "gotv_key";
    public static final String ACCT_DB_KEY = "acct_Key";
    public static final String PLAN_DB_KEY = "plan_Key";
    public static final String POST_COMMENTS_DB_KEY = "post-comments";
    public static final String POST_LIKES_DB_KEY = "post-likes";
    public static final String FOLLOW_DB_KEY = "follow";
    public static final String FOLLOWINGS_DB_KEY = "followings";
    public static final String FOLLOWINGS_POSTS_DB_KEY = "followingPostsIds";
    public static final String FOLLOWERS_DB_KEY = "followers";
    public static final String IMAGES_STORAGE_KEY = "images";

    private Context context;

    /*private FirebaseDatabase database;
    FirebaseStorage storage;
    private final Map<ValueEventListener, DatabaseReference> activeListeners = new HashMap<>();

    public void createOrUpdateProfile(Profile profile, OnProfileCreatedListener onProfileCreatedListener) {

    }

    public ValueEventListener getProfile(String id, OnObjectChangedListenerSimple<Profile> listener) {
        return null;
    }

    public void getProfileSingleValue(String id, OnObjectChangedListener<Profile> listener) {

    }

    public interface BirthdaysLoadedListener {
        void onBirthdaysReturned(ArrayList<Birthday> birthdays);
        void onCancelled(String errorMessage);
    }
    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

    private DatabaseHelper(Context context) {
        this.context = context;
    }

    public void init() {
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        storage = FirebaseStorage.getInstance();

//        Sets the maximum time to retry upload operations if a failure occurs.
        storage.setMaxUploadRetryTimeMillis(Utils.Database.MAX_UPLOAD_RETRY_MILLIS);
    }

    public StorageReference getStorageReference() {

        return storage.getReferenceFromUrl(context.getResources().getString(R.string.storage_link));
    }

    public DatabaseReference getDatabaseReference() {
        return database.getReference();
    }

    public void closeListener(ValueEventListener listener) {
        if (activeListeners.containsKey(listener)) {
            DatabaseReference reference = activeListeners.get(listener);
            if (reference != null) {
                reference.removeEventListener(listener);
            }
            activeListeners.remove(listener);
            LogUtil.logDebug(TAG, "closeListener(), listener was removed: " + listener);
        } else {
            LogUtil.logDebug(TAG, "closeListener(), listener not found :" + listener);
        }
    }

    public void closeAllActiveListeners() {
        for (ValueEventListener listener : activeListeners.keySet()) {
            DatabaseReference reference = activeListeners.get(listener);
            if (reference != null) {
                reference.removeEventListener(listener);
            }
        }

        activeListeners.clear();
    }

    public void addActiveListener(ValueEventListener listener, DatabaseReference reference) {
        activeListeners.put(listener, reference);
    }

    public Task<Void> removeImage(String imageTitle) {
        StorageReference desertRef = getStorageReference().child(IMAGES_STORAGE_KEY + "/" + imageTitle);
        return desertRef.delete();
    }

    public UploadTask uploadImage(Uri uri, String imageTitle) {
        StorageReference riversRef = getStorageReference().child(IMAGES_STORAGE_KEY + "/" + imageTitle);
        // Create file metadata including the content type
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setCacheControl("max-age=7776000, Expires=7776000, public, must-revalidate")
                .build();

        return riversRef.putFile(uri, metadata);
    }*/


    public static void saveBirthdayChange(Birthday birthday, Update state) {
        if (PrefManager.isUsingFirebase(AppController.getInstance())) {
            //saveFirebaseChangeAndSetAlarms(birthday, state);
        } else {
            try {
                saveJSONChange(birthday, state);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AppController.getInstance(), "ERROR saving data, try signing in!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public interface MigrateUsersCallback {
        void onSuccess(int migratedCount);
        void onFailure(String message);
    }

    /*public void migrateJsonToFirebase(Context context, FirebaseUser user, MigrateUsersCallback callback) {

        if (Utils.isStringEmpty(user.getUid())) {
            Log.i("Migration", "User null or empty");
            return;
        }

        ArrayList<Birthday> jsonbirthdays = new ArrayList();
        try {
            jsonbirthdays = loadJSONData(context);
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFailure(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure(e.getMessage());
        }

        if (jsonbirthdays.size() > 0) {
            Log.i("Migration", "Found JSON birthdays!");

            // Get Firebase Database reference
            DatabaseReference databaseReference;
            for (Birthday jsonBirthday : jsonbirthdays) {

                if (jsonBirthday.getUID() == null || jsonBirthday.getUID().isEmpty()) {
                    jsonBirthday.setUID(UUID.randomUUID().toString());
                }

                // Update database ref
                databaseReference = AppController.getInstance().getDatabaseReference().child(user.getUid()).child(AppConstants.TABLE_REPORTS)
                        .child(String.valueOf(jsonBirthday.getUID()));

                // Convert to Firebase birthday
                FirebaseImportantDay firebaseImportantDay = FirebaseImportantDay.convertToFirebaseBirthday(jsonBirthday);

                // Save to Firebase
                databaseReference.setValue(firebaseImportantDay);
            }

            databaseReference = AppController.getInstance().getDatabaseReference().child(user.getUid());
            databaseReference.child("lastUpdated").setValue(ServerValue.TIMESTAMP);
            databaseReference.child("email").setValue(user.getEmail()); // todo refactor

            PrefManager.setHasMigratedjsonData(context, true);
            callback.onSuccess(jsonbirthdays.size());

        } else {
            callback.onFailure(context.getString(R.string.error_no_json_birthdays));
        }
    }*/

    private ArrayList<Birthday> loadJSONData(Context context) throws Exception {
        ArrayList<Birthday> birthdays = new ArrayList<>();
        // Load birthdays
        BufferedReader reader = null;
        try {
            // Open and read the file into a StringBuilder

            InputStream in = context.openFileInput(AppConstants.FILENAME);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();

            // Build the array of birthdays from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                birthdays.add(new Birthday(array.getJSONObject(i)));
            }
        } finally {
            if (reader != null) reader.close();
        }
        return birthdays;
    }

    private static void saveJSONChange(Birthday birthday, Update state) throws IOException {
        ArrayList<Birthday> birthdays = new ArrayList<>();
        Context context = AppController.getInstance();

        // Load birthdays
        BufferedReader reader = null;
        try {
            // Open and read the file into a StringBuilder
            InputStream in = context.openFileInput(AppConstants.FILENAME);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();

            for (int i = 0; i < array.length(); i++) {
                birthdays.add(new Birthday(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // Ignore this one; it happens when starting fresh
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) reader.close();
        }

        // Perform action
        switch (state) {
            case CREATE:
                birthdays.add(birthday);
                break;
            case UPDATE:
                for (Birthday b : birthdays) {
                    if (b.getBirthdayID()==(birthday.getBirthdayID())) {
                        birthdays.remove(b);
                        birthdays.add(birthday);
                        break;
                    }
                }
                break;
            case DELETE:
                for (int i = 0; i < 5; i++) {
                    if (birthdays.get(i).getBirthdayID()==(birthday.getBirthdayID())) {
                        birthdays.remove(i);
                        break;
                    }
                }
                break;
        }

        // Save JSON data
        try {
            // Build an array in JSON
            JSONArray array = new JSONArray();
            for (Birthday b : birthdays)
                array.put(b.toJSON());

            // Write the file to disk
            Writer writer = null;
            try {

                OutputStream out = context.openFileOutput(AppConstants.FILENAME,
                        Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(out);
                writer.write(array.toString());

            } finally {
                if (writer != null)
                    writer.close();
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        EventBus.getDefault().post(new BirthdaysLoadedEvent(birthdays));

        AlarmsHelper.setAllNotificationAlarms(AppController.getInstance());
    }

    public enum Update {
        CREATE,
        UPDATE,
        DELETE,
    }
}
