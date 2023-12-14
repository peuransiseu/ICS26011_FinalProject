package com.example.montesmp5


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
<<<<<<< Updated upstream
import java.io.BufferedReader
import java.io.FileReader
=======
import java.io.InputStream
>>>>>>> Stashed changes

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
        private val KEY_IMEASURE = "imeasure"

    }



    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY NOT NULL,"
                + KEY_FNAME + " TEXT NOT NULL,"
                + KEY_LNAME + " TEXT NOT NULL,"
                + KEY_USER  + " TEXT NOT NULL,"
                + KEY_PASS + " TEXT NOT NULL)")

        val CREATE_RECIPE_TABLE = ("CREATE TABLE " + TABLE_RECIPE + "("
                + KEY_RID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_RNAME + " TEXT NOT NULL,"
                + KEY_ISLAND + " TEXT NOT NULL,"
                + KEY_SERVING_SIZE + " TEXT NOT NULL,"
                + KEY_CALORIES  + " REAL NOT NULL,"
                + KEY_TIME  + " TEXT NOT NULL,"
                + KEY_INSTRUCTIONS + " TEXT NOT NULL);")

        val CREATE_INGREDIENT_TABLE = ("CREATE TABLE " + TABLE_INGREDIENTS + "("
                + KEY_IID + " INTEGER,"
                + KEY_INAME + " TEXT NOT NULL,"
                + KEY_IMEASURE + " TEXT NOT NULL,"
                + " FOREIGN KEY ($KEY_IID) REFERENCES $TABLE_RECIPE($KEY_RID));")


        db?.execSQL(CREATE_USER_TABLE)
        db?.execSQL(CREATE_RECIPE_TABLE)
        db?.execSQL(CREATE_INGREDIENT_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS)
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

<<<<<<< Updated upstream
    fun loadRecipes(): Long{
=======
    fun loadRecipes(emp: EmpModelClass): Long{
        fun readCsv(inputStream: InputStream): Boolean{
            val reader = inputStream.bufferedReader()
            val header = reader.readLine()
            return reader.lineSequence()
                .filter { it.isNotBlank() }
                .map {
                    val (year, rating, title) = it.split(',', ignoreCase = false, limit = 3)
                    Movie(Year.of(year.trim().toInt()), rating.trim().toInt(), title.trim().removeSurrounding("\""))
                }.toList()
        }
        val movies = readCsv(/*Open a stream to CSV file*/)

>>>>>>> Stashed changes
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Pork Sisig")
        contentValues.put(KEY_ISLAND, "Luzon")
        contentValues.put(KEY_SERVING_SIZE, "6 persons")
        contentValues.put(KEY_CALORIES, 933)
        contentValues.put(KEY_TIME, "1 hour, 42 minutes")
        contentValues.put(KEY_INSTRUCTIONS, "Pour the water in a pan and bring to a boil Add salt and pepper.\n" +
                "Put-in the pig’s ears and pork belly then simmer for 40 minutes to 1 hour (or until tender).\n" +
                "Remove the boiled ingredients from the pot then drain excess water\n" +
                "Grill the boiled pig ears and pork belly until done\n" +
                "Chop the pig ears and pork belly into fine pieces\n" +
                "In a wide pan, melt the butter or margarine. Add the onions. Cook until onions are soft.\n" +
                "Put-in the ginger and cook for 2 minutes\n" +
                "Add the chicken liver. Crush the chicken liver while cooking it in the pan.\n" +
                "Add the chopped pig ears and pork belly. Cook for 10 to 12 minutes\n" +
                "Put-in the soy sauce, garlic powder, and chili. Mix well\n" +
                "Add salt and pepper to taste\n" +
                "Put-in the mayonnaise and mix with the other ingredients\n" +
                "Transfer to a serving plate. Top with chopped green onions and raw egg.\n" +
                "Serve hot. Share and Enjoy (add the lemon or calamansi before eating)\n"
                )

         var success = db.insert(TABLE_RECIPE,null,contentValues)

        contentValues = ContentValues()
        var ingredients = listOf("Pig ears", "Pork Belly", "Onion", "Soy Sauce", "Ground Black Pepper", "Ginger", "Chiliflakes", "Garlic Powder", "Calamansi","Butter", "Chicken Liver","Water","Mayonnaise",
                "Salt", "Egg")
        var measurements = listOf("1 lb.", "1 1/2 lb.", "3 tbsp.", "1/4 tsp.", "1 knob", "3 tbsp.", "1/2 tsp.", "3-5 pcs.", "1/2 cup",
            "1/4 lb.", "6 cups", "3 tbsp.", "1/2 tsp.","1")

        (ingredients.zip(measurements)).forEach() {
                (ingredients,measurements) ->
            contentValues.put(KEY_IID, 1)
            contentValues.put(KEY_INAME, ingredients)
            contentValues.put(KEY_IMEASURE, measurements)
            success  = db.insert(TABLE_INGREDIENTS,null,contentValues)
        }

        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Kare Kare")
        contentValues.put(KEY_ISLAND, "Luzon")
        contentValues.put(KEY_SERVING_SIZE, "6 persons")
        contentValues.put(KEY_CALORIES, 934)
        contentValues.put(KEY_TIME, "2 hours, 40 minutes")
        contentValues.put(KEY_INSTRUCTIONS, "In a large pot, bring the water to a boil\n" +
                "Put in the oxtail followed by the onions and simmer for 2.5 to 3 hrs or until tender (35 minutes if using a pressure cooker)\n" +
                "Once the meat is tender, add the ground peanuts, peanut butter, and coloring (water from the annatto seed mixture) and simmer for 5 to 7 minutes\n" +
                "Add the toasted ground rice and simmer for 5 minutes\n" +
                "On a separate pan, saute the garlic then add the banana flower, eggplant, and string beans and cook for 5 minutes\n" +
                "Transfer the cooked vegetables to the large pot (where the rest of the ingredients are)\n" +
                "Add salt and pepper to taste\n" +
                "Serve hot with shrimp paste. Enjoy!"
        )

        success = db.insert(TABLE_RECIPE,null,contentValues)

        contentValues = ContentValues()
        ingredients = listOf("Oxtail", "Banana FLower Bud", "Bok Choy or Pechay", "String Beans", "Eggplants", "Ground Peanuts", "Peanute Butter", "Shrimp Paste", "Water","Annatto Seeds", "Toasted Ground Rice","Garlic","Onion",
            "Salt", "Pepper")
        measurements = listOf("3 lb.", "1 pc.", "1 bundle", "4 pcs.", "1 cup", "1/2 cup ", "1/2 cup", "1 Liter", "1/2 cup",
            "1/2 cup", "1 tbsp.", "1 pc.", "1/2 tsp.","1 tbsp")

        (ingredients.zip(measurements)).forEach() {
                (ingredients,measurements) ->
            contentValues.put(KEY_IID, 2)
            contentValues.put(KEY_INAME, ingredients)
            contentValues.put(KEY_IMEASURE, measurements)
            success  = db.insert(TABLE_INGREDIENTS,null,contentValues)
        }

        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Bulalo")
        contentValues.put(KEY_ISLAND, "Luzon")
        contentValues.put(KEY_SERVING_SIZE, "4 persons")
        contentValues.put(KEY_CALORIES, 231)
        contentValues.put(KEY_TIME, "2 hours")
        contentValues.put(KEY_INSTRUCTIONS, "In a big cooking pot, pour in water and bring to a boil\n" +
                "Put-in the beef shank followed by the onion and whole pepper corn then simmer for 1.5 hours (30 mins if using a pressure cooker) or until meat is tender.\n" +
                "Add the corn and simmer for another 10 minutes\n" +
                "Add the fish sauce,cabbage, pechay, and green onion (onion leeks)\n" +
                "Serve hot. Share and Enjoy!"
        )

        success = db.insert(TABLE_RECIPE,null,contentValues)

        contentValues = ContentValues()
        ingredients = listOf("Beef Shank", "Cabbage", "Bok Choy", "Corn Cob", "Whole Peppercorn", "Green Onion", "Onion", "Water","Fish Sauce")
        measurements = listOf("2 lb.", "1/2 pc.", "1 bundle", "2 pcs.", "2 tbsp.", "1/2 cup ", "1 pc.", "34 ounces", "To taste (optional)")

        (ingredients.zip(measurements)).forEach() {
                (ingredients,measurements) ->
            contentValues.put(KEY_IID, 3)
            contentValues.put(KEY_INAME, ingredients)
            contentValues.put(KEY_IMEASURE, measurements)
            success  = db.insert(TABLE_INGREDIENTS,null,contentValues)
        }

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