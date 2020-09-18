package co.wishroll.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import co.wishroll.WishRollApplication;

import static co.wishroll.utilities.FileUtils.getDocumentCacheDir;

public class ThumbnailHandler {
    private static final String TAG = "ThumbnailHandler";// Set to true to enable logging



    private static final String CHILD_DIR = "images";
    private static final String TEMP_FILE_NAME = "img";
    private static final String FILE_EXTENSION = ".jpeg";

    private static final int COMPRESS_QUALITY = 100;

    /**
     * Save image to the App cache
     * @param bitmap to save to the cache
     * @param name file name in the cache.
     * If name is null file will be named by default {@link #TEMP_FILE_NAME}
     * @return file dir when file was saved
     */
    public static File saveImgToCache(Bitmap bitmap, @Nullable String name) {
        File cachePath = null;
        String fileName = TEMP_FILE_NAME;
        if (!TextUtils.isEmpty(name)) {
            fileName = name;
        }
        try {
            cachePath = new File(getDocumentCacheDir(WishRollApplication.getContext()), CHILD_DIR);
            cachePath.mkdirs();

            FileOutputStream stream = new FileOutputStream(cachePath + "/" + fileName + FILE_EXTENSION);
            bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, stream);
            stream.close();
        } catch (IOException e) {
            Log.e(TAG, "saveImgToCache error: " + bitmap, e);
        }

        Log.d(TAG, "saveImgToCache: HOPEFULLY THIS WORKS THIS IS THE PATH OF THE SAVED IMAGE " + cachePath);
        return cachePath;
    }

    /**
     * Save an image to the App cache dir and return it {@link Uri}
     * @param bitmap to save to the cache
     */

    public static Uri saveToCacheAndGetUri(Bitmap bitmap) {
        return saveToCacheAndGetUri(bitmap, null);
    }

    /**
     * Save an image to the App cache dir and return it {@link Uri}
     * @param bitmap to save to the cache
     * @param name file name in the cache.
     * If name is null file will be named by default {@link #TEMP_FILE_NAME}
     */
    public static Uri saveToCacheAndGetUri(Bitmap bitmap, @Nullable String name) {
        File file = saveImgToCache(bitmap, name);
        return getImageUri(file, name);
    }

    /**
     * Get a file {@link Uri}
     * @param name of the file
     * @return file Uri in the App cache or null if file wasn't found
     */
    @Nullable
    public Uri getUriByFileName(String name) {
        Context context = WishRollApplication.getContext();
        String fileName;
        if (!TextUtils.isEmpty(name)) {
            fileName = name;
        } else {
            return null;
        }

        File imagePath = new File(context.getCacheDir(), CHILD_DIR);
        File newFile = new File(imagePath, fileName + FILE_EXTENSION);
        Log.d(TAG, "getUriByFileName: NEW FILE PATH??? " + newFile.getAbsolutePath());

        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", newFile);
    }


    // Get an image Uri by name without extension from a file dir
    private static Uri getImageUri(File fileDir, @Nullable String name) {
        Context context = WishRollApplication.getContext();
        String fileName = TEMP_FILE_NAME;
        if (!TextUtils.isEmpty(name)) {
            fileName = name;
        }
        File newFile = new File(fileDir, fileName + FILE_EXTENSION);
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", newFile);
    }

    /**
     * Get Uri type by {@link Uri}
     */
    public String getContentType(Uri uri) {
        return WishRollApplication.getContext().getContentResolver().getType(uri);
    }

    public static boolean clearCache(){
        return true;
    }


    public static void clearApplicationData(){

        File cache = getDocumentCacheDir(WishRollApplication.getContext()); //WishRollApplication.getContext().getCacheDir() // getDocumentCacheDir(WishRollApplication.getContext() switched! might get cache/documents instead of /cache/
        Log.d(TAG, "clearApplicationData: cache path just to make sure " + cache.getAbsolutePath());
        File appDir = new File(cache.getParent());  //from get parent to getname we are trying to target the documents file
        if (appDir.exists()) { //exists()
            String[] children = appDir.list();
            assert children != null;

            for (String s : children) {
                if (!s.equals("lib") && !s.equals("shared_prefs") && !s.equals("code_cache") && !s.equals("files") && !s.equals("wishrollCache") && !s.equals("image_manager_disk_cache") ) {  //!s.equals("documents"
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        assert dir != null;
        return dir.delete();
    }

}
