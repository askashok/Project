package commontestware.com.delieveryordermgmt.database;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import commontestware.com.delieveryordermgmt.model.Model;
import commontestware.com.delieveryordermgmt.model.ModelDineIn;
import commontestware.com.delieveryordermgmt.model.ModelEmployee;
import commontestware.com.delieveryordermgmt.model.ModelTakeAway;
import commontestware.com.delieveryordermgmt.model.Modelone;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "employee.db";
    public static final int DATABASE_VERSION = 1;


    public static final String TABLE_NAME = "employeelogin";
    public static final String NAME = "name";
    private static final String ADMIN_ID = "admin_id";
    public static final String EMAIL_ADDRESS = "mail";
    public static final String PASSWORD = "password";
    public static final String CONFIRM_PASSWORD = "confirmpassword";
    public static final String PROFILE = "profile";
    private static final String LOGCAT = null;
    private static final String PROFILEPATH = "ProfileImage";

    public static final String MY_TABLE = "createcategory";
    public static final String FOOD_NAME = "foodname";
    public static final String TYPE_OF_FOOD = "foodtype";
    public static final String RATE = "rate";
    public static final String IMAGE_PATH = "image_path";
    public static final String EMAIL_ID = "emailid";

    public static final String DINE_IN = "createdinein";
    public static final String CATEGORY = "category";
    public static final String FOOD_ITEM = "fooditem";
    public static final String QUANTITY = "quantity";
    public static final String DINE_RATE = "rate";
    public static final String TOTAL_AMT = "totalamount";
    public static final String TAX = "tax";
    public static final String GROSS_AMT = "grossamount";
    public static final String DINE_ID = "dineid";


    public static final String TAKE_AWAY = "createtakeaway";
    public static final String FOOD_LIST = "foodlist";
    public static final String FOOD_QUANTITY = "foodquantity";
    public static final String FOOD_RATE = "foodrate";
    public static final String TOTAL_AMOUNT = "totalamount";
    public static final String DELIVERY = "delivery";
    public static final String ASSIGN = "assign";
    public static final String MOBILE = "mobile";
    public static final String TAKEAWAY_ID = "takeawayid";


    public static final String EMP_TABLE = "employeetable";
    public static final String EMP_NAME = "empname";
    public static final String EMP_MOBILE = "empmobile";
    public static final String EMP_ID = "empid";
    public static final String IMAGEPATH = "imagepath";
    public static final String EMPLOYEE_ID = "employeeid";


    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + ADMIN_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + EMAIL_ADDRESS + " TEXT,"
            + PASSWORD + " TEXT," + CONFIRM_PASSWORD + " TEXT," + PROFILE + " TEXT," + PROFILEPATH + " text)";

    String TABLE_CREATE = "CREATE TABLE " + MY_TABLE +
            "(" + EMAIL_ID + " TEXT," + FOOD_NAME + " TEXT," + TYPE_OF_FOOD + " TEXT," + RATE + " TEXT," + IMAGE_PATH + " TEXT)";

    String DINE_IN_TABLE = "CREATE TABLE " + DINE_IN + "("
            + DINE_ID + " INTEGER PRIMARY KEY," + CATEGORY + " TEXT," + FOOD_ITEM + " TEXT,"
            + QUANTITY + " TEXT," + DINE_RATE + " TEXT," + TOTAL_AMT + " TEXT)";

    String TAKE_AWAY_TABLE = "CREATE TABLE " + TAKE_AWAY + "("
            + TAKEAWAY_ID + " INTEGER PRIMARY KEY," + FOOD_LIST + " TEXT," + FOOD_QUANTITY + " TEXT,"
            + FOOD_RATE + " TEXT," + TOTAL_AMOUNT + " TEXT," + DELIVERY + " TEXT," + ASSIGN + " TEXT," + MOBILE + " TEXT)";

    String EMPLOYEES_TABLE = "CREATE TABLE " + EMP_TABLE +
            "(" + EMP_NAME + " TEXT," + EMPLOYEE_ID + " INTEGER PRIMARY KEY," + EMP_MOBILE + " TEXT," + EMP_ID + " TEXT," + IMAGEPATH + " TEXT)";

    Activity act;

    public DbHelper(Activity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.act = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(TABLE_CREATE);
        db.execSQL(DINE_IN_TABLE);
        db.execSQL(TAKE_AWAY_TABLE);
        db.execSQL(EMPLOYEES_TABLE);
        Log.d(LOGCAT, "table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DINE_IN);
        db.execSQL("DROP TABLE IF EXISTS " + TAKE_AWAY);
        db.execSQL("DROP TABLE IF EXISTS " + EMP_TABLE);
        onCreate(db);

    }

    public void insert(Model model) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, model.getName());
        values.put(EMAIL_ADDRESS, model.getMail());
        values.put(PASSWORD, model.getPassword());
        values.put(CONFIRM_PASSWORD, model.getConfirmpassword());
        values.put(PROFILEPATH, model.getProfile());
        db.insert(TABLE_NAME, null, values);
        Log.d(LOGCAT, "values inserted");
        db.close();
    }

    public String getUserName(String email) {


        String selectNameQuery = "SELECT name FROM " + TABLE_NAME + " where " + EMAIL_ADDRESS + "='" + email + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(selectNameQuery, null);
        String userName = "";


        if (cur.moveToFirst()) {

            do {
                userName = cur.getString(cur.getColumnIndex(NAME));
            }
            while (cur.moveToNext());

        }


        db.close();
        return userName;

    }


    public void employee(ModelEmployee modelEmployee) {
        Log.e("", "Values " + modelEmployee.getEmpid());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMP_NAME, modelEmployee.getEmpname());
        values.put(EMP_MOBILE, modelEmployee.getEmpmobile());
        values.put(EMP_ID, modelEmployee.getEmpid());
        values.put(IMAGEPATH, modelEmployee.getImagepath());
        db.insert(EMP_TABLE, null, values);
        Log.d(LOGCAT, "values inserted");
        db.close();
    }

    public List<ModelEmployee> employeedetails() {
        List<ModelEmployee> empList = new ArrayList<ModelEmployee>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + EMP_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ModelEmployee contact = new ModelEmployee();


                contact.setEmpname(cursor.getString(cursor.getColumnIndex(EMP_NAME)));
                contact.setEmpmobile(cursor.getString(cursor.getColumnIndex(EMP_MOBILE)));
                contact.setEmpid(cursor.getString(cursor.getColumnIndex(EMP_ID)));
                contact.setImagepath(cursor.getString(cursor.getColumnIndex(IMAGEPATH)));
                // Adding contact to list
                empList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return empList;
    }


    /* public boolean login(String email) {
         String selectQuery = " SELECT * from " + TABLE_NAME + " where " + EMAIL_ADDRESS + "='" + email + "'";

         SQLiteDatabase db = this.getWritableDatabase();
         Cursor cur = db.rawQuery(selectQuery, null);

         int getC = cur.getCount();
         boolean status = false;

         if (getC > 0) {
             status = true;

         } else {

             status = false;
         }


         db.close();
         return status;
     }*/
    public boolean login(String user, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select * from " + TABLE_NAME + " where " + EMAIL_ADDRESS + "='" + user + "'" +
                " AND " + PASSWORD + "='" + pass + "'";
        Log.d("selectQuery", selectQuery);

        Cursor cur = db.rawQuery(selectQuery, null);

        int getC = cur.getCount();
        Log.d("getC", String.valueOf(getC));
        boolean status = false;

        if (getC > 0) {
            status = true;

        } else {

            status = false;
        }

        db.close();
        return status;
    }

    public String getid(String id, EditText editpass) {
        String selectQuery = " SELECT " + EMAIL_ADDRESS + " from " + TABLE_NAME
                + " where " + EMAIL_ADDRESS + "='" + id + "'" + " AND " + PASSWORD + "='" + editpass + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(selectQuery, null);
        String userId = "";


        if (cur.moveToFirst()) {

            do {
                userId = cur.getString(cur.getColumnIndex(EMAIL_ADDRESS));
            }
            while (cur.moveToNext());

        }


        db.close();
        return userId;
    }

    public boolean register(String email) {
        String selectQuery = "SELECT * from " + TABLE_NAME + " where " + EMAIL_ADDRESS + "='" + email + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery(selectQuery, null);

        int getC = cur.getCount();

        boolean status = false;

        if (getC > 0) {
            status = true;

        } else {

            status = false;
        }


        db.close();
        return status;
    }

    public List<Model> getAllContacts() {
        List<Model> contactList = new ArrayList<Model>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Model contact = new Model();


                contact.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                contact.setMail(cursor.getString(cursor.getColumnIndex(EMAIL_ADDRESS)));
                contact.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                contact.setConfirmpassword(cursor.getString(cursor.getColumnIndex(CONFIRM_PASSWORD)));
                contact.setProfile(cursor.getString(cursor.getColumnIndex(PROFILE)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return contactList;
    }

    public void valuesinsert(Modelone modelone) {
        SQLiteDatabase dbase = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EMAIL_ID, modelone.getEmail());
        cv.put(FOOD_NAME, modelone.getName());
        cv.put(TYPE_OF_FOOD, modelone.getFoodtype());
        cv.put(RATE, modelone.getRate());
        cv.put(IMAGE_PATH, modelone.getImage_path());
        dbase.insert(MY_TABLE, null, cv);
        Log.w(LOGCAT, "Table Inserted");
        dbase.close();

    }

    public int updateContact(Modelone modelone, String foodname) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMAIL_ID, modelone.getEmail());
        values.put(FOOD_NAME, modelone.getName());
        values.put(TYPE_OF_FOOD, modelone.getFoodtype());
        values.put(RATE, modelone.getRate());
        values.put(IMAGE_PATH, modelone.getImage_path());

        // updating row
        return db.update(MY_TABLE, values, TYPE_OF_FOOD + " = ?",
                new String[]{String.valueOf(foodname)});
    }

    public int updateContactemp(ModelEmployee modelEmployee, String fdname) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EMP_ID, modelEmployee.getEmpid());
        values.put(EMP_MOBILE, modelEmployee.getEmpmobile());
        values.put(EMP_NAME, modelEmployee.getEmpname());
        values.put(IMAGEPATH, modelEmployee.getImagepath());

        // updating row
        return db.update(EMP_TABLE, values, EMP_ID + " = ?",
                new String[]{String.valueOf(modelEmployee.getEmpid())});
    }

    public boolean deleteContact(String modelone) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EMP_TABLE, EMP_ID + " = ?",
                new String[]{String.valueOf(modelone)});
        db.close();

        return true;
    }

    public boolean deleteContactdb(String modelemployee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MY_TABLE, TYPE_OF_FOOD + " = ?",
                new String[]{String.valueOf(modelemployee)});
        db.close();

        return true;
    }


    public ArrayList<Modelone> getCategoryDetails() {
        //String str = "SELECT * FROM " + MY_TABLE + " where " + EMAIL_ID + "='" + emailId + "'";

        String str = "SELECT * FROM " + MY_TABLE;
        ArrayList<Modelone> food = new ArrayList<Modelone>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(str, null);
        System.out.println("getCategoryDetails  " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Modelone fooditem = new Modelone();
                fooditem.setName(cursor.getString(cursor.getColumnIndex(FOOD_NAME)));
                fooditem.setFoodtype(cursor.getString(cursor.getColumnIndex(TYPE_OF_FOOD)));
                fooditem.setRate(cursor.getString(cursor.getColumnIndex(RATE)));
                fooditem.setImage_path(cursor.getString(cursor.getColumnIndex(IMAGE_PATH)));
                fooditem.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_ID)));
                food.add(fooditem);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return food;
    }

    public ArrayList<Modelone> getspinner() {
        ArrayList<Modelone> food = new ArrayList<Modelone>();
        String selectQuery = "SELECT  * FROM " + MY_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Modelone fooditem = new Modelone();
                fooditem.setName(cursor.getString(cursor.getColumnIndex(FOOD_NAME)));
                fooditem.setFoodtype(cursor.getString(cursor.getColumnIndex(TYPE_OF_FOOD)));
                fooditem.setRate(cursor.getString(cursor.getColumnIndex(RATE)));
                fooditem.setImage_path(cursor.getString(cursor.getColumnIndex(IMAGE_PATH)));
                fooditem.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL_ID)));
                food.add(fooditem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return food;
    }

    public ArrayList<ModelTakeAway> getspinners() {
        ArrayList<ModelTakeAway> food = new ArrayList<ModelTakeAway>();
        String selectQuery = "SELECT  * FROM " + TAKE_AWAY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ModelTakeAway foodlist = new ModelTakeAway();
                foodlist.setFoodlist(cursor.getString(cursor.getColumnIndex(FOOD_LIST)));
                foodlist.setFoodquantity(cursor.getString(cursor.getColumnIndex(FOOD_QUANTITY)));
                foodlist.setFoodrate(cursor.getString(cursor.getColumnIndex(FOOD_RATE)));
                foodlist.setTotalamount(cursor.getString(cursor.getColumnIndex(TOTAL_AMOUNT)));
                //foodlist.setDelivery(cursor.getString(cursor.getColumnIndex(DELIVERY)));
                foodlist.setAssign(cursor.getString(cursor.getColumnIndex(ASSIGN)));
                foodlist.setMobile(cursor.getString(cursor.getColumnIndex(MOBILE)));
                food.add(foodlist);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return food;
    }

    public ArrayList<ModelEmployee> getemployee() {
        ArrayList<ModelEmployee> emp = new ArrayList<ModelEmployee>();
        String selectQuery = "SELECT  * FROM " + EMP_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ModelEmployee employees = new ModelEmployee();
                employees.setEmpname(cursor.getString(cursor.getColumnIndex(EMP_NAME)));
                employees.setEmpmobile(cursor.getString(cursor.getColumnIndex(EMP_MOBILE)));
                employees.setEmpid(cursor.getString(cursor.getColumnIndex(EMP_ID)));
                employees.setImagepath(cursor.getString(cursor.getColumnIndex(IMAGEPATH)));

                emp.add(employees);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return emp;
    }

    public String getPassword(String mail_ID) {
        Log.d(LOGCAT, mail_ID);
        String password = null;
        String selectQuery = "SELECT  password FROM " + TABLE_NAME
                + " where " + EMAIL_ADDRESS + "='" + mail_ID + "'";
        Log.d("select Query", "select Query" + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = null;

        cur = db.rawQuery(selectQuery, null);
        if (cur.moveToFirst()) {
            do {
                password = cur.getString(cur.getColumnIndex(PASSWORD));
            } while (cur.moveToNext());
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }
        db.close();
        return password;
    }

    public Model getName(String mail_ID) {
        Log.d(LOGCAT, mail_ID);
        Model model = new Model();

        String selectQuery = "SELECT name,profile FROM " + TABLE_NAME + " WHERE "
                + EMAIL_ADDRESS + "='" + mail_ID + "'";
        Log.d("select Query", "select Query" + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = null;

        cur = db.rawQuery(selectQuery, null);
        if (cur.moveToFirst()) {
            do {
                model.setName(cur.getString(cur.getColumnIndex(NAME)));
                model.setProfile(cur.getString(cur.getColumnIndex(PROFILE)));

            } while (cur.moveToNext());
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }
        db.close();
        return model;
    }

    public void dinein(ModelDineIn modelDineIn) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY, modelDineIn.getCategory());
        values.put(FOOD_ITEM, modelDineIn.getFooditem());
        values.put(QUANTITY, modelDineIn.getQuantity());
        //values.put(DINE_RATE, modelDineIn.getRate());
        values.put(TOTAL_AMT, modelDineIn.getTotalamount());
        //values.put(TAX, modelDineIn.getTax());
        //values.put(GROSS_AMT, modelDineIn.getGrossamount());
        db.insert(DINE_IN, null, values);
        Log.d(LOGCAT, "values inserted");
        db.close();
    }

    public void dineInList(ModelDineIn modelDineIn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY, modelDineIn.getCategory());
        values.put(FOOD_ITEM, modelDineIn.getFooditem());
        values.put(QUANTITY, modelDineIn.getQuantity());
       // values.put(DINE_RATE, modelDineIn.getRate());
        values.put(TOTAL_AMT, modelDineIn.getTotalamount());
        //values.put(TAX, modelDineIn.getTax());
       // values.put(GROSS_AMT, modelDineIn.getGrossamount());
        db.insert(DINE_IN, null, values);
        Log.d(LOGCAT, "values inserted");
        db.close();
    }

    public ArrayList<ModelDineIn> getAllLabels() {
        String str = "SELECT  * FROM " + DINE_IN;
        ArrayList<ModelDineIn> dine = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(str, null);

        System.out.println("Count " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                ModelDineIn dinein = new ModelDineIn();
                dinein.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));
                dinein.setFooditem(cursor.getString(cursor.getColumnIndex(FOOD_ITEM)));
                dinein.setQuantity(cursor.getString(cursor.getColumnIndex(QUANTITY)));
                //dinein.setRate(cursor.getString(cursor.getColumnIndex(DINE_RATE)));
                dinein.setTotalamount(cursor.getString(cursor.getColumnIndex(TOTAL_AMT)));
                //dinein.setTax(cursor.getString(cursor.getColumnIndex(TAX)));
               // dinein.setGrossamount(cursor.getString(cursor.getColumnIndex(GROSS_AMT)));
                dine.add(dinein);
            } while (cursor.moveToNext());
            // closing connection
            cursor.close();
            db.close();
        }
        return dine;
    }

    public void takeaway(ModelTakeAway modelTakeAway) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FOOD_LIST, modelTakeAway.getFoodlist());
        values.put(FOOD_QUANTITY, modelTakeAway.getFoodquantity());
        values.put(FOOD_RATE, modelTakeAway.getFoodrate());
        values.put(TOTAL_AMOUNT, modelTakeAway.getTotalamount());
        //values.put(DELIVERY, modelTakeAway.getDelivery());
        values.put(ASSIGN, modelTakeAway.getAssign());
        values.put(MOBILE, modelTakeAway.getMobile());
        db.insert(TAKE_AWAY, null, values);
        Log.d(LOGCAT, "values inserted");
        db.close();
    }

    public void saveImagepath(String imagepath, String uName) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PROFILEPATH, imagepath);

        // updating row
        db.update(TABLE_NAME, values, EMAIL_ADDRESS + " = ?", new String[]{uName});
    }

    public ArrayList<String> getProfileData(String uName) {

        ArrayList<String> profileData = new ArrayList<>();
        System.out.println("getProfileImage " + uName);
        String str = "SELECT  * FROM " + TABLE_NAME + " where " + EMAIL_ADDRESS + "='" + uName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(str, null);
        String imagePath;
        String name;

        System.out.println("Count " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                profileData = new ArrayList<>();
                imagePath = cursor.getString(cursor.getColumnIndex(PROFILEPATH));
                name = cursor.getString(cursor.getColumnIndex(NAME));
                profileData.add(name);
                profileData.add(imagePath);

            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return profileData;
    }


}
