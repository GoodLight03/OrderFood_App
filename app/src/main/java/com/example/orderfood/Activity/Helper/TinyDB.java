
package com.example.orderfood.Activity.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;


import com.example.orderfood.Activity.Domain.FoodDomain;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class TinyDB {

    private SharedPreferences preferences;
    private String DEFAULT_APP_IMAGEDATA_DIRECTORY;
    private String lastImagePath = "";

    public TinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }


    /**
     * Giải mã Bitmap từ 'đường dẫn' và trả về
     * Đường dẫn hình ảnh đường dẫn @param
     * @ quay lại Bitmap từ 'đường dẫn'
     */
    public Bitmap getImage(String path) {
        Bitmap bitmapFromPath = null;
        try {
            bitmapFromPath = BitmapFactory.decodeFile(path);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return bitmapFromPath;
    }


    /**
     * Trả về đường dẫn chuỗi của hình ảnh được lưu cuối cùng
     * @return chuỗi đường dẫn của hình ảnh được lưu cuối cùng
     */
    public String getSavedImagePath() {
        return lastImagePath;
    }


    /**
     * Lưu 'theBitmap' vào thư mục 'theFolder' với tên 'theImageName'
     * @param theFolder dir đường dẫn thư mục bạn muốn lưu nó vào ví dụ: "DropBox / WorkImages"
     * @param theImageName tên bạn muốn gán cho tệp hình ảnh, ví dụ: "MeAtLunch.png"
     * @param theBitmap hình ảnh bạn muốn lưu dưới dạng Bitmap
     * @return trả về đường dẫn đầy đủ (địa chỉ hệ thống tệp) của hình ảnh đã lưu
     */
    public String putImage(String theFolder, String theImageName, Bitmap theBitmap) {
        if (theFolder == null || theImageName == null || theBitmap == null)
            return null;

        this.DEFAULT_APP_IMAGEDATA_DIRECTORY = theFolder;
        String mFullPath = setupFullPath(theImageName);

        if (!mFullPath.equals("")) {
            lastImagePath = mFullPath;
            saveBitmap(mFullPath, theBitmap);
        }

        return mFullPath;
    }


    /**
     * Lưu 'theBitmap' thành 'fullPath'
     * @param fullPath đường dẫn đầy đủ của tệp hình ảnh, ví dụ: "Hình ảnh / MeAtLunch.png"
     * @param theBitmap hình ảnh bạn muốn lưu dưới dạng Bitmap
     * @return true nếu hình ảnh đã được lưu, false nếu không
     */
    public boolean putImageWithFullPath(String fullPath, Bitmap theBitmap) {
        return !(fullPath == null || theBitmap == null) && saveBitmap(fullPath, theBitmap);
    }

    /**
     * Tạo đường dẫn cho hình ảnh với tên  'imageName' trong DEFAULT_APP.. directory
     * @param imageName tên của hình ảnh
     * @ quay lại đường dẫn đầy đủ của hình ảnh. Nếu không tạo được thư mục, hãy trả về chuỗi trống
     */
    private String setupFullPath(String imageName) {
        File mFolder = new File(Environment.getExternalStorageDirectory(), DEFAULT_APP_IMAGEDATA_DIRECTORY);

        if (isExternalStorageReadable() && isExternalStorageWritable() && !mFolder.exists()) {
            if (!mFolder.mkdirs()) {
                Log.e("ERROR", "Failed to setup folder");
                return "";
            }
        }

        return mFolder.getPath() + '/' + imageName;
    }

    /**
     * Lưu Bitmap dưới dạng tệp PNG tại đường dẫn 'fullPath'
     * @param fullPath đường dẫn của tệp hình ảnh
     * @param bitmap hình ảnh dưới dạng Bitmap
     * @return true nếu nó được lưu thành công, false nếu không
     */
    private boolean saveBitmap(String fullPath, Bitmap bitmap) {
        if (fullPath == null || bitmap == null)
            return false;

        boolean fileCreated = false;
        boolean bitmapCompressed = false;
        boolean streamClosed = false;

        File imageFile = new File(fullPath);

        if (imageFile.exists())
            if (!imageFile.delete())
                return false;

        try {
            fileCreated = imageFile.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageFile);
            bitmapCompressed = bitmap.compress(CompressFormat.PNG, 100, out);

        } catch (Exception e) {
            e.printStackTrace();
            bitmapCompressed = false;

        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                    streamClosed = true;

                } catch (IOException e) {
                    e.printStackTrace();
                    streamClosed = false;
                }
            }
        }

        return (fileCreated && bitmapCompressed && streamClosed);
    }

    // Getters

    /**
     * Nhận giá trị int từ SharedPreferences tại 'key'. Nếu không tìm thấy khóa, hãy trả về 0
     * Khóa @param Khóa SharedPreferences
     * @return int value tại 'key' hoặc 0 nếu không tìm thấy key
     */
    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    /**
     * Nhận ArrayList of Integers được phân tích cú pháp từ SharedPreferences tại 'key'
     * Khóa @param Khóa SharedPreferences
     * @return ArrayList of Integers
     */
    public ArrayList<Integer> getListInt(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (String item : arrayToList)
            newList.add(Integer.parseInt(item));

        return newList;
    }

    /**
     * Nhận giá trị từ SharedPreferences tại 'key'. Nếu không tìm thấy khóa, hãy trả về 0
     * Khóa @param Khóa SharedPreferences
     * @ trả lại giá trị dài tại 'key' hoặc 0 nếu không tìm thấy key
     */
    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    /**
     * Nhận giá trị từ SharedPreferences tại 'key'. Nếu không tìm thấy khóa, hãy trả về 0
     * Khóa @param Khóa SharedPreferences
     * @ trả lại giá trị dài tại 'key' hoặc 0 nếu không tìm thấy key
     */
    public float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    /**
     * Nhận giá trị  từ SharedPreferences tại 'key'. Nếu trường hợp ngoại lệ được ném ra, trả về 0
     * Khóa @param Khóa SharedPreferences
     * @ trả lại giá trị tại 'key' hoặc 0 nếu trường hợp ngoại lệ được ném ra
     */
    public double getDouble(String key) {
        String number = getString(key);

        try {
            return Double.parseDouble(number);

        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Nhận ArrayList of Double được phân tích cú pháp từ SharedPreferences tại 'key'
     * Khóa @param Khóa SharedPreferences
     * @return ArrayList of Double
     */
    public ArrayList<Double> getListDouble(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Double> newList = new ArrayList<Double>();

        for (String item : arrayToList)
            newList.add(Double.parseDouble(item));

        return newList;
    }

    /**
     * Nhận ArrayList of Integers được phân tích cú pháp từ SharedPreferences tại 'key'
     * Khóa @param Khóa SharedPreferences
     * @return ArrayList of Longs
     */
    public ArrayList<Long> getListLong(String key) {
        String[] myList = TextUtils.split(preferences.getString(key, ""), "‚‗‚");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Long> newList = new ArrayList<Long>();

        for (String item : arrayToList)
            newList.add(Long.parseLong(item));

        return newList;
    }

    /**
     * Nhận giá trị Chuỗi từ SharedPreferences tại 'key'. Nếu không tìm thấy khóa, hãy trả lại ""
     * Khóa @param Khóa SharedPreferences
     * Giá trị chuỗi @return tại 'key' hoặc "" (Chuỗi trống) nếu không tìm thấy khóa
     */
    public String getString(String key) {
        return preferences.getString(key, "");
    }

    /**
     * Nhận ArrayList of String được phân tích cú pháp từ SharedPreferences tại 'key'
     * Khóa @param Khóa SharedPreferences
     * @return ArrayList of String
     */
    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    /**
     * Nhận giá trị boolean từ SharedPreferences tại 'key'. Nếu không tìm thấy khóa, hãy trả về false
     * Khóa @param Khóa SharedPreferences
     * @return giá trị boolean tại 'key' hoặc false nếu không tìm thấy key
     */
    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    /**
     * Nhận ArrayList of Boolean được phân tích cú pháp từ SharedPreferences tại 'key'
     * Khóa @param Khóa SharedPreferences
     * @return ArrayList of Boolean
     */
    public ArrayList<Boolean> getListBoolean(String key) {
        ArrayList<String> myList = getListString(key);
        ArrayList<Boolean> newList = new ArrayList<Boolean>();

        for (String item : myList) {
            if (item.equals("true")) {
                newList.add(true);
            } else {
                newList.add(false);
            }
        }
        return newList;
    }


    public ArrayList<FoodDomain> getListObject(String key){
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key);
        ArrayList<FoodDomain> playerList =  new ArrayList<FoodDomain>();

        for(String jObjString : objStrings){
            FoodDomain player  = gson.fromJson(jObjString,  FoodDomain.class);
            playerList.add(player);
        }
        return playerList;
    }



    public <T> T getObject(String key, Class<T> classOfT){

        String json = getString(key);
        Object value = new Gson().fromJson(json, classOfT);
        if (value == null)
            throw new NullPointerException();
        return (T)value;
    }


    // Put methods

    /**
     * Đặt giá trị int vào SharedPreferences bằng 'key' và lưu
     * Khóa @param Khóa SharedPreferences
     * Giá trị @param int giá trị được thêm vào
     */
    public void putInt(String key, int value) {
        checkForNullKey(key);
        preferences.edit().putInt(key, value).apply();
    }

    /**
     * Đặt ArrayList of Integer vào SharedPreferences bằng 'key' và lưu
     * Khóa @param Khóa SharedPreferences
     * @param intList ArrayList of Integer sẽ được thêm vào
     */
    public void putListInt(String key, ArrayList<Integer> intList) {
        checkForNullKey(key);
        Integer[] myIntList = intList.toArray(new Integer[intList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myIntList)).apply();
    }

    /**
     * Đặt giá trị vào SharedPreferences bằng 'key' và lưu
     * Khóa @param Khóa SharedPreferences
     * Giá trị @param giá trị sẽ được thêm vào
     */
    public void putLong(String key, long value) {
        checkForNullKey(key);
        preferences.edit().putLong(key, value).apply();
    }

    /**
     * Đặt ArrayList  vào SharedPreferences bằng 'key' và lưu
     * Khóa @param Khóa SharedPreferences
     * @param longList ArrayList of Long sẽ được thêm vào
     */
    public void putListLong(String key, ArrayList<Long> longList) {
        checkForNullKey(key);
        Long[] myLongList = longList.toArray(new Long[longList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myLongList)).apply();
    }

    /**
     * Put float value into SharedPreferences with 'key' and save
     * @param key SharedPreferences key
     * @param value float value to be added
     */
    public void putFloat(String key, float value) {
        checkForNullKey(key);
        preferences.edit().putFloat(key, value).apply();
    }

    /**
     * Put double value into SharedPreferences with 'key' and save
     * @param key SharedPreferences key
     * @param value double value to be added
     */
    public void putDouble(String key, double value) {
        checkForNullKey(key);
        putString(key, String.valueOf(value));
    }

    /**
     * Put ArrayList of Double into SharedPreferences with 'key' and save
     * @param key SharedPreferences key
     * @param doubleList ArrayList of Double to be added
     */
    public void putListDouble(String key, ArrayList<Double> doubleList) {
        checkForNullKey(key);
        Double[] myDoubleList = doubleList.toArray(new Double[doubleList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myDoubleList)).apply();
    }

    /**
     * Put String value into SharedPreferences with 'key' and save
     * @param key SharedPreferences key
     * @param value String value to be added
     */
    public void putString(String key, String value) {
        checkForNullKey(key); checkForNullValue(value);
        preferences.edit().putString(key, value).apply();
    }

    /**
     * Put ArrayList of String into SharedPreferences with 'key' and save
     * @param key SharedPreferences key
     * @param stringList ArrayList of String to be added
     */
    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    /**
     * Put boolean value into SharedPreferences with 'key' and save
     * @param key SharedPreferences key
     * @param value boolean value to be added
     */
    public void putBoolean(String key, boolean value) {
        checkForNullKey(key);
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * Put ArrayList of Boolean into SharedPreferences with 'key' and save
     * @param key SharedPreferences key
     * @param boolList ArrayList of Boolean to be added
     */
    public void putListBoolean(String key, ArrayList<Boolean> boolList) {
        checkForNullKey(key);
        ArrayList<String> newList = new ArrayList<String>();

        for (Boolean item : boolList) {
            if (item) {
                newList.add("true");
            } else {
                newList.add("false");
            }
        }

        putListString(key, newList);
    }

    /**
     * Put ObJect any type into SharedPrefrences with 'key' and save
     * @param key SharedPreferences key
     * @param obj is the Object you want to put
     */
    public void putObject(String key, Object obj){
    	checkForNullKey(key);
    	Gson gson = new Gson();
    	putString(key, gson.toJson(obj));
    }

    public void putListObject(String key, ArrayList<FoodDomain> playerList){
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for(FoodDomain player: playerList){
            objStrings.add(gson.toJson(player));
        }
        putListString(key, objStrings);
    }

    /**
     * Xóa mục SharedPreferences bằng 'key'
     * Khóa @param Khóa SharedPreferences
     */
    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    /**
     * Xóa tệp hình ảnh tại 'đường dẫn'
     * @param path  đường dẫn của tệp hình ảnh
     * @return true nếu xóa thành công, ngược lại là false
     */
    public boolean deleteImage(String path) {
        return new File(path).delete();
    }


    /**
     * Clear SharedPreferences (remove everything)
     */
    public void clear() {
        preferences.edit().clear().apply();
    }

    /**
     * Lấy tất cả các giá trị từ SharedPreferences. Không sửa đổi trả lại bộ sưu tập theo phương thức
     * @ quay lại Bản đồ đại diện cho danh sách các cặp khóa / giá trị từ SharedPreferences
     */
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }


    /**
     * Register SharedPreferences change listener
     * @param listener listener object of OnSharedPreferenceChangeListener
     */
    public void registerOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener) {

        preferences.registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Unregister SharedPreferences change listener
     * @param listener listener object of OnSharedPreferenceChangeListener to be unregistered
     */
    public void unregisterOnSharedPreferenceChangeListener(
            SharedPreferences.OnSharedPreferenceChangeListener listener) {

        preferences.unregisterOnSharedPreferenceChangeListener(listener);
    }


    /**
     * Kiểm tra xem bộ nhớ ngoài có thể ghi được hay không
     * @return true nếu có thể ghi, false nếu không
     */
    public static boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * Kiểm tra xem bộ nhớ ngoài có thể đọc được hay không
     * @return true nếu có thể đọc được, false nếu không
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
    /**
     * null key sẽ làm hỏng tệp pref được chia sẻ và khiến chúng không thể đọc được, đây là một biện pháp phòng ngừa
     * @param key gõ phím pref để kiểm tra
     */
    private void checkForNullKey(String key){
        if (key == null){
            throw new NullPointerException();
        }
    }
    /**
     * null keys would corrupt the shared pref file and make them unreadable this is a preventive measure
     * @param value the pref value to check
     */
    private void checkForNullValue(String value){
        if (value == null){
            throw new NullPointerException();
        }
    }
}