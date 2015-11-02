package itesm.mx.mamicare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.List;

/**
 * Created by SergioJesúsCorderoBa on 11/2/2015.
 */
public class DBOperations {

    public String TAG = "";

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public static final String TABLE_PRODUCT = "products";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";

    public void addProduct(Product product){
        db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, product.getName());
            values.put(COLUMN_QUANTITY, product.getQuantity());

            db.insert(TABLE_PRODUCT, null, values);
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to add product to database");
        }
    }

    public boolean deleteProduct(String productName){
        boolean result = false;

        db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCT +
                " WHERE " + COLUMN_NAME + " = \"" + productName + "\"";

        Cursor cursor = db.rawQuery(query, null);
        try
        {
            if (cursor.moveToFirst()) {
                int id = Integer.parseInt(cursor.getString(0));
                db.delete( TABLE_PRODUCT, COLUMN_ID + " = ?",
                        new String[] {String.valueOf(id) });
                cursor.close();
                result = true;
            }
        } catch (SQLiteException e) {
            //if db cannot be opened
            Log.d(TAG, "Error while trying to delete a product");
        }
        db.close();
        return result;
    }

    public Product findProduct(String productName){
        String query = "SELECT * FROM " + TABLE_PRODUCT +
                " WHERE " + COLUMN_NAME + " = \"" + productName + "\"";

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Product product = null;
        try {
            if (cursor.moveToFirst()) {
                product = new Product ( Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)));
            }
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get product from database.");
        }
        db.close();
        return product;
    }

    public List<Product> getAllProducts(){
        String query = "SELECT * FROM " + TABLE_PRODUCT;

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        List<Product> products = null;
        try {
            do {
                //fetch product from db
                Product product = new Product ( Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)));
                //adding to list
                products.add(product);
            } while (cursor.moveToNext());
        } catch (SQLiteException e) {
            Log.d(TAG, "Error while trying to get all products");
        }
        db.close();
        return products;

    }

    public int getProductsCount() {
        String query = "SELECT * FROM " + TABLE_PRODUCT;

        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        return cursor.getCount();
    }

    public ProductOperations(Context context) {
        dbHelper = ProductHelper.getInstance(context);
    }
}