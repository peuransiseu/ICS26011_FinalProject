package com.example.montesmp5


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "UserDatabase"

        //Table for Recipes
        private val TABLE_USER = "UserTable"
        private val KEY_EMAIL = "email"
        private val KEY_FNAME = "fname"
        private val KEY_LNAME = "lname"
        private val KEY_USER = "user"
        private val KEY_PASS = "pass"

        //Table for Recipe
        private val TABLE_RECIPE = "RecipeTable"
        private val KEY_RID = "rId"
        private val KEY_RNAME = "rname"
        private val KEY_ISLAND = "island"
        private val KEY_SERVING_SIZE = "servingsize"
        private val KEY_CALORIES = "calories"
        private val KEY_TIME = "time"
        private val KEY_INSTRUCTIONS = "instructions"

        //Table for Ingredients
        private val TABLE_INGREDIENTS = "IngredientTable"
        private val KEY_IID = "iId"
        private val KEY_INAME = "iname"

        //Table for Measurements
        private val TABLE_MEASUREMENTS = "MeasurementTable"
        private val KEY_MID = "mId"
        private val KEY_MNAME = "mname"


    }



    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_FNAME + " TEXT NOT NULL,"
                + KEY_LNAME + " TEXT NOT NULL,"
                + KEY_USER  + " TEXT NOT NULL,"
                + KEY_PASS + " TEXT NOT NULL)")

        val CREATE_RECIPE_TABLE = ("CREATE TABLE " + TABLE_RECIPE + "("
                + KEY_RID + " INTEGER PRIMARY KEY NOT NULL,"
                + KEY_RNAME + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_ISLAND + " TEXT NOT NULL,"
                + KEY_SERVING_SIZE + " TEXT NOT NULL,"
                + KEY_CALORIES  + " REAL NOT NULL,"
                + KEY_TIME  + " TEXT NOT NULL,"
                + KEY_INSTRUCTIONS + " TEXT NOT NULL)")

        val CREATE_INGREDIENT_TABLE = ("CREATE TABLE " + TABLE_INGREDIENTS + "("
                + KEY_IID + "INTEGER PRIMARY KEY NOT NULL,"
                + KEY_INAME + "TEXT NOT NULL,"
                + "recipeID INTEGER, FOREIGN KEY(recipeID) REFERENCES "
                + TABLE_RECIPE + "(" + KEY_RID + ")")

        val CREATE_MEASUREMENT_TABLE = ("CREATE TABLE " + TABLE_MEASUREMENTS + "("
                + KEY_MID + "INTEGEr PRIMARY KEY NOT NULL,"
                + KEY_MNAME + "TEXT NOT NULL,"
                + "ingredientID INTEGER, FOREIGN KEY(ingredientID) REFERENCES "
                + TABLE_INGREDIENTS + "(" + KEY_IID + ")")


        db?.execSQL(CREATE_USER_TABLE)
        db?.execSQL(CREATE_RECIPE_TABLE)
        db?.execSQL(CREATE_INGREDIENT_TABLE)
        db?.execSQL(CREATE_MEASUREMENT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_MEASUREMENTS)
        onCreate(db)
    }

    fun addUser(emp: EmpModelClass): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_EMAIL, emp.userEmail)
        contentValues.put(KEY_FNAME, emp.userFirstName)
        contentValues.put(KEY_LNAME, emp.userLastName)
        contentValues.put(KEY_USER, emp.userUser)
        contentValues.put(KEY_PASS, emp.userPass)

        val success = db.insert(TABLE_USER,null,contentValues)

        db.close()

        return success
    }


   @SuppressLint("Range")
    fun getUser():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT * FROM $TABLE_USER"
        val db = this.readableDatabase
        var cursor:Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery,null)
        }
        catch(e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var uEmail: String
        var uFirstName: String
        var uLastName: String
        var uUser: String
        var uPass: String

        if(cursor.moveToFirst()){
            do{//assign values from first row
                uEmail = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                uFirstName = cursor.getString(cursor.getColumnIndex(KEY_FNAME))
                uLastName = cursor.getString(cursor.getColumnIndex(KEY_LNAME))
                uUser = cursor.getString(cursor.getColumnIndex(KEY_USER))
                uPass = cursor.getString(cursor.getColumnIndex(KEY_PASS))
                val emp = EmpModelClass(userEmail = uEmail, userFirstName = uFirstName, userLastName = uLastName, userUser = uUser, userPass = uPass)
                empList.add(emp)
            } while (cursor.moveToNext())
        }

       db.close()

        return empList
    }



    @SuppressLint("Range")
    fun getRecipes():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT * FROM $TABLE_USER"
        val db = this.readableDatabase
        var cursor:Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery,null)
        }
        catch(e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var uEmail: String
        var uFirstName: String
        var uLastName: String
        var uUser: String
        var uPass: String

        if(cursor.moveToFirst()){
            do{//assign values from first row
                uEmail = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                uFirstName = cursor.getString(cursor.getColumnIndex(KEY_FNAME))
                uLastName = cursor.getString(cursor.getColumnIndex(KEY_LNAME))
                uUser = cursor.getString(cursor.getColumnIndex(KEY_USER))
                uPass = cursor.getString(cursor.getColumnIndex(KEY_PASS))
                val emp = EmpModelClass(userEmail = uEmail, userFirstName = uFirstName, userLastName = uLastName, userUser = uUser, userPass = uPass)
                empList.add(emp)
            } while (cursor.moveToNext())
        }

        db.close()

        return empList
    }

    fun loadRecipes(emp: EmpModelClass): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_EMAIL, emp.userEmail)
        contentValues.put(KEY_FNAME, emp.userFirstName)
        contentValues.put(KEY_LNAME, emp.userLastName)
        contentValues.put(KEY_USER, emp.userUser)
        contentValues.put(KEY_PASS, emp.userPass)

        val success = db.insert(TABLE_USER,null,contentValues)

        db.close()

        return success
    }


    /*fun updateEmployee(emp: EmpModelClass):Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_EMAIL, emp.userEmail)
        contentValues.put(KEY_FNAME, emp.userFirstName)
        contentValues.put(KEY_LNAME, emp.userLastName)
        contentValues.put(KEY_USER, emp.userUser)
        contentValues.put(KEY_PASS, emp.userPass)
        var success = db.update(TABLE_CONTACTS, contentValues, "email="+emp.userEmail, null)
        db.close()
        return success
    }

    fun deleteEmployee(emp: EmpModelClass): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_EMAIL, emp.userEmail)
        val success = db.delete(TABLE_CONTACTS, "email="+emp.userEmail, null)
        db.close()
        return success
    }*/

}