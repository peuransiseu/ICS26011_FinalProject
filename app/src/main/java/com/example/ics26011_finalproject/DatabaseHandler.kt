package com.example.montesmp5


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.ics26011_finalproject.RecipeModel
import com.example.ics26011_finalproject.UserRecipeModel
import java.io.BufferedReader
import java.io.FileReader

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
        private val KEY_INGREDIENTS = "ingredients"
        private val KEY_SERVING_SIZE = "servingsize"
        private val KEY_CALORIES = "calories"
        private val KEY_TIME = "time"
        private val KEY_INSTRUCTIONS = "instructions"

        //Table for Ingredients
        private val TABLE_INGREDIENTS = "IngredientTable"
        private val KEY_IID = "iId"
        private val KEY_INAME = "iname"
        private val KEY_IMEASURE = "imeasure"

        private val TABLE_USER_RECIPE = "UserRecipeTable"
        private val KEY_URID = "urId"
        private val KEY_URNAME = "urname"
        private val KEY_URINGREDIENTS = "ingredients"
        private val KEY_URSERVESIZE = "servingsize"
        private val KEY_URCALORIES = "calories"
        private val KEY_URTIME = "time"
        private val KEY_URINSTRUCTIONS = "instructions"

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
                + KEY_INGREDIENTS + " TEXT NOT NULL,"
                + KEY_SERVING_SIZE + " TEXT NOT NULL,"
                + KEY_CALORIES  + " REAL NOT NULL,"
                + KEY_TIME  + " TEXT NOT NULL,"
                + KEY_INSTRUCTIONS + " TEXT NOT NULL);")

        val CREATE_USER_RECIPE_TABLE = ("CREATE TABLE " + TABLE_USER_RECIPE + "("
                + KEY_URID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_URNAME + " TEXT NOT NULL,"
                + KEY_URINGREDIENTS + " TEXT NOT NULL,"
                + KEY_URSERVESIZE + " TEXT NOT NULL,"
                + KEY_URCALORIES  + " TEXT NOT NULL,"
                + KEY_URTIME  + " TEXT NOT NULL,"
                + KEY_URINSTRUCTIONS + " TEXT NOT NULL);")




        db?.execSQL(CREATE_USER_TABLE)
        db?.execSQL(CREATE_RECIPE_TABLE)
        db?.execSQL(CREATE_USER_RECIPE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_USER)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_RECIPE)
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

    //add recipe to user recipe table
    fun addRecipe(urm : UserRecipeModel):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_URNAME, urm.urName)
        contentValues.put(KEY_URINGREDIENTS, urm.urIngredients)
        contentValues.put(KEY_URSERVESIZE, urm.urServing)
        contentValues.put(KEY_URCALORIES, urm.urCalories)
        contentValues.put(KEY_TIME, urm.urTime)
        contentValues.put(KEY_URINSTRUCTIONS, urm.urInstructions)

        val success = db.insert(TABLE_USER_RECIPE,null,contentValues)

        db.close()

        return success
    }

    @SuppressLint("Range")
    fun getClickedRecipe(recipe:String):ArrayList<RecipeModel>{
        val empList:ArrayList<RecipeModel> = ArrayList<RecipeModel>()
        val selectQuery = "SELECT * FROM $TABLE_RECIPE WHERE $KEY_RNAME = \"$recipe\""
        val db = this.readableDatabase
        var cursor:Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery,null)
        }
        catch(e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id:Int
        var name: String
        var island: String
        var ingred: String
        var serve: String
        var cal: Int
        var time: String
        var ins: String


        if(cursor.moveToFirst()){
            do{//assign values from first row
                id = cursor.getInt(cursor.getColumnIndex(KEY_RID))
                name = cursor.getString(cursor.getColumnIndex(KEY_RNAME))
                island = cursor.getString(cursor.getColumnIndex(KEY_ISLAND))
                ingred = cursor.getString(cursor.getColumnIndex(KEY_INGREDIENTS))
                serve = cursor.getString(cursor.getColumnIndex(KEY_SERVING_SIZE))
                cal = cursor.getInt(cursor.getColumnIndex(KEY_CALORIES))
                time = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                ins = cursor.getString(cursor.getColumnIndex(KEY_INSTRUCTIONS))
                val emp = RecipeModel(recId = id,recName = name, recIsland = island, recIngred = ingred, recServeSize = serve, recCalories = cal, recTime = time, recInstructions = ins)
                empList.add(emp)
            } while (cursor.moveToNext())
        }

        db.close()

        return empList
    }

    @SuppressLint("Range")
    fun getClickedUserRecipe(recipe:String):ArrayList<UserRecipeModel>{
        val empList:ArrayList<UserRecipeModel> = ArrayList<UserRecipeModel>()
        val selectQuery = "SELECT * FROM $TABLE_USER_RECIPE WHERE $KEY_URNAME = \"$recipe\""
        val db = this.readableDatabase
        var cursor:Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery,null)
        }
        catch(e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var name: String
        var ingred: String
        var serve: String
        var cal: String
        var time: String
        var ins: String

        if(cursor.moveToFirst()){
            do{//assign values from first row
                name = cursor.getString(cursor.getColumnIndex(KEY_URNAME))
                ingred = cursor.getString(cursor.getColumnIndex(KEY_URINGREDIENTS))
                serve = cursor.getString(cursor.getColumnIndex(KEY_URSERVESIZE))
                cal = cursor.getString(cursor.getColumnIndex(KEY_CALORIES))
                time = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                ins = cursor.getString(cursor.getColumnIndex(KEY_INSTRUCTIONS))
                val emp = UserRecipeModel(urName = name, urIngredients = ingred, urServing = serve, urCalories = cal, urTime = time, urInstructions = ins)
                empList.add(emp)
            } while (cursor.moveToNext())
        }

        db.close()

        return empList
    }

    //get user recipes from user recipe table
    @SuppressLint("Range")
    fun getUserRecipes():List<UserRecipeModel>{
        val empList:ArrayList<UserRecipeModel> = ArrayList<UserRecipeModel>()
        val selectQuery = "SELECT * FROM $TABLE_USER_RECIPE"
        val db = this.readableDatabase
        var cursor:Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery,null)
        }
        catch(e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }


        var name: String
        var ingred: String
        var serve: String
        var cal: String
        var time: String
        var ins: String
        var desc: String

        if(cursor.moveToFirst()){
            do{//assign values from first row
                name = cursor.getString(cursor.getColumnIndex(KEY_URNAME))
                ingred = cursor.getString(cursor.getColumnIndex(KEY_URINGREDIENTS))
                serve = cursor.getString(cursor.getColumnIndex(KEY_URSERVESIZE))
                cal = cursor.getString(cursor.getColumnIndex(KEY_URCALORIES))
                time = cursor.getString(cursor.getColumnIndex(KEY_URTIME))
                ins = cursor.getString(cursor.getColumnIndex(KEY_URINSTRUCTIONS))
                val emp = UserRecipeModel(urName = name, urIngredients = ingred, urServing = serve, urCalories = cal, urTime = time, urInstructions = ins)
                empList.add(emp)
            } while (cursor.moveToNext())
        }

        db.close()

        return empList
    }
    @SuppressLint("Range")
    fun getRecipes(isla:String):List<RecipeModel>{
        val empList:ArrayList<RecipeModel> = ArrayList<RecipeModel>()
        val selectQuery = "SELECT * FROM $TABLE_RECIPE WHERE $KEY_ISLAND = \"$isla\""
        val db = this.readableDatabase
        var cursor:Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery,null)
        }
        catch(e: SQLiteException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var name: String
        var island: String
        var ingred: String
        var serve: String
        var cal: Int
        var time: String
        var ins: String

        if(cursor.moveToFirst()){
            do{//assign values from first row
                id = cursor.getInt(cursor.getColumnIndex(KEY_RID))
                name = cursor.getString(cursor.getColumnIndex(KEY_RNAME))
                island = cursor.getString(cursor.getColumnIndex(KEY_ISLAND))
                ingred = cursor.getString(cursor.getColumnIndex(KEY_INGREDIENTS))
                serve = cursor.getString(cursor.getColumnIndex(KEY_SERVING_SIZE))
                cal = cursor.getInt(cursor.getColumnIndex(KEY_CALORIES))
                time = cursor.getString(cursor.getColumnIndex(KEY_TIME))
                ins = cursor.getString(cursor.getColumnIndex(KEY_INSTRUCTIONS))
                val emp = RecipeModel(recId=id,recName = name, recIsland = island, recIngred = ingred, recServeSize = serve, recCalories = cal, recTime = time, recInstructions = ins)
                empList.add(emp)
            } while (cursor.moveToNext())
        }

        db.close()

        return empList
    }

    fun loadRecipes(): Long{
        val db = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Pork Sisig")
        contentValues.put(KEY_ISLAND, "Luzon")
        contentValues.put(KEY_INGREDIENTS, "1 lb. Pig Ears.\n" +
                "1 1/2 Pork Belly.\n" +
                "3 tbsp. Onion\n" +
                "1/4 tbsp. Soy Sauce\n" +
                "Ground Black Pepper\n" +
                "Ginger\n" +
                "Chili Flakes\n" +
                "Garlic Powder\n" +
                "Chicken Liver\n" +
                "Water\n" +
                "Butter\n" +
                "Mayonnaise\n" +
                "Salt\n" +
                "Egg(Optional).")
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



        //**************************************************************************//


        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Kare Kare")
        contentValues.put(KEY_ISLAND, "Luzon")
        contentValues.put(
            KEY_INGREDIENTS, "1 lb. Pig Ears.\n" +
                "1 1/2 Pork Belly.\n" +
                "3 tbsp. Onion\n" +
                "1/4 tbsp. Soy Sauce\n" +
                "Ground Black Pepper\n" +
                "Ginger\n" +
                "Chili Flakes\n" +
                "Garlic Powder\n" +
                "Chicken Liver\n" +
                "Water\n" +
                "Butter\n" +
                "Mayonnaise\n" +
                "Salt\n" +
                "Egg(Optional).")
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

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Bulalo")
        contentValues.put(KEY_ISLAND, "Luzon")
        contentValues.put(KEY_INGREDIENTS, "2 lb. Beef Shank.\n" +
                "1/2 Cabbage.\n" +
                "1 Bundle Bok Choy\n" +
                "2 Corn Cobs\n" +
                "2 tbsp. Whole Peppercorn\n" +
                "Green Onions\n" +
                "Onion\n" +
                "34 oz. Water\n" +
                "Fish Sauce (Optional)\n")
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

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Lechon Kawali")
        contentValues.put(KEY_ISLAND, "Luzon")
        contentValues.put(KEY_INGREDIENTS, "2 lb. Pork Belly.\n" +
                "2 tbsp. Salt.\n" +
                "2 tbsp. Whole Peppercorn\n" +
                "5 pcs. Dried Bay Leaves\n" +
                "3 cups Cooking Oil\n" +
                "34 oz. Water\n")
        contentValues.put(KEY_SERVING_SIZE, "6 persons")
        contentValues.put(KEY_CALORIES, 893)
        contentValues.put(KEY_TIME, "1 hour")
        contentValues.put(KEY_INSTRUCTIONS, "Pour water on cooking pot. Boil\n" +
                "Add the pork belly and 1 tablespoon of salt. Put some pepper pepper and bay leaves. Boil for 30 minutes or until meat gets tender\n" +
                "Remove the meat from the pot and let it cool down for a few minutes\n" +
                "Spread 1 tablespoon of salt on the pork belly. Make sure to distribute it evenly on all sections\n" +
                "Start to deep fry the boiled meat. Heat oil on a cooking pot.\n" +
                "Put each piece of boiled pork belly into the hot oil with extra caution. Fry until crispy. Turn the meat over to completely fry the opposite side until crispy. Note: be extra careful when frying as oil can splatter. You can use the cover of the pot to cover it while oil splatters, but do not cover completely as steam will be trapped. It will cause more splatters.\n" +
                "Remove the meat from the cooking pot. Arrange on a wire rack to cool down.  Slice according to desired portions\n" +
                "Serve with lechon sauce. Share and Enjoy!"
        )

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//


        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Champorado")
        contentValues.put(KEY_ISLAND, "Luzon")
        contentValues.put(KEY_INGREDIENTS, "5 pcs. Tablea.\n" +
                "1 3/4 cups Glutinous Rice\n" +
                "3/4 cup Granulated Sugar\n" +
                "6-8 cups Water\n" +
                "Condensed Milk (To Taste)\n")
        contentValues.put(KEY_SERVING_SIZE, "4 persons")
        contentValues.put(KEY_CALORIES, 486)
        contentValues.put(KEY_TIME, "30 minutes")
        contentValues.put(KEY_INSTRUCTIONS, "Pour water in a cooking pot. Bring to a boil.\n" +
                "Put-in the tablea and then stir. Let it dissolve in boiling water.\n" +
                "Add-in the rice. Let the water re-boil. Set the heat to low-medium and then stir almost constantly to avoid sticking. The rice should be ready when it absorbs the water (about 15 to 25 minutes).\n" +
                "Add the sugar. Stir until the sugar dissolves.\n" +
                "Transfer the champorado in individual serving bowls. Top with condensed milk.\n" +
                "Serve with tuyo. Share and enjoy."
        )

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Bam-I (Pancit Bisaya)")
        contentValues.put(KEY_ISLAND, "Visayas")
        contentValues.put(
            KEY_INGREDIENTS, "1/2 lb. Pork.\n" +
                    "1/2 lb. Chicken\n" +
                    "4 pcs. Chinese Sausage\n" +
                    "1 lb. Shrimp\n" +
                    "8 oz. Pancit Canton Noodles\n" +
                    "4-6 oz. Sotanghon (Soaked)\n" +
                    "1 1/2-2 cups Chicken Stock\n" +
                    "3 cups cabbage\n" +
                    "3/4 cup Carrots\n" +
                    "1/2 cup dried wood ear\n" +
                    "Onion\n" +
                    "Parsley\n" +
                    "1/2 cup Shrimp Juice\n" +
                    "1/2 cup Soy Sauce\n" +
                    "Chicken Cube\n" +
                    "Garlic\n" +
                    "2-3 tbsp. Cooking Oil\n" +
                    "Salt\n" +
                    "Pepper")
        contentValues.put(KEY_SERVING_SIZE, "6 persons")
        contentValues.put(KEY_CALORIES, 352)
        contentValues.put(KEY_TIME, "50 minutes")
        contentValues.put(KEY_INSTRUCTIONS, "Heat a cooking pot then pour-in cooking oil.\n" +
                "Sauté garlic and onions then add the sliced pork and cook for 3 minutes.\n" +
                "Put-in the Chinese sausage and shredded chicken and cook for 3 to 5 minutes.\n" +
                "Add soy sauce, shrimp juice, salt, ground black pepper, chicken bouillon, and chicken stock then let boil. Simmer for 5 to 8 minutes.\n" +
                "Put the shrimps, cabbage, carrots, and wood ears in then cook for 2 minutes.\n" +
                "Add the soaked noodles then stir. Cook for a minute.\n" +
                "Put-in the flour sticks then stir well. Cook for 3 minutes or until the liquid is gone.\n" +
                "Top with green onions and place calamansi on the side.\n" +
                "Serve hot. Share and enjoy! Mangaon na Ta!!")

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Humba")
        contentValues.put(KEY_ISLAND, "Visayas")
        contentValues.put(KEY_INGREDIENTS, "4 lb. Pork Belly.\n" +
                "Knorr Pork Cube.\n" +
                "3 tbsp. Onion\n" +
                "3 cups Clear Soda (Sprite)\n" +
                "1 cup Water\n" +
                "2 oz. Banana Blossoms\n" +
                "2 tbsp. Salted Black Beans\n" +
                "1/2 cup Soy Sauce\n" +
                "Onion\n" +
                "2 tbsp. Brown Sugar\n" +
                "6 cloves Garlic\n" +
                "1/2 tsp. Crushed Peppercorn\n")
        contentValues.put(KEY_SERVING_SIZE, "10 persons")
        contentValues.put(KEY_CALORIES, 971)
        contentValues.put(KEY_TIME, "2 hours")
        contentValues.put(KEY_INSTRUCTIONS, "Heat a wok or pan. Sear pork belly until it turns brown and enough oil gets extracted from the fat. Remove the pork from the wok. Set aside.\n" +
                "Heat around 3 tablespoons of oil. Sauté garlic and onion until the onion softens.\n" +
                "Add the pork back into the wok and pour-in soy sauce. Sauté for 1 minute.\n" +
                "Add vinegar. Continue sautéing until liquid dries-up.\n" +
                "Pour clear soft drinks into the wok. Cover and let boil.\n" +
                "Add crushed peppercorn. Adjust heat to low. Continue boiling for 35 minutes.\n" +
                "Add banana blossoms and salted black beans. Cover the wok and continue cooking until the pork gets tender. Note: you can add water if needed.\n" +
                "Add brown sugar. Stir. Cook for 1 minute.\n" +
                "Transfer to a serving plate. Serve with warm rice.\n" +
                "Share and enjoy!"
        )

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Batchoy")
        contentValues.put(KEY_ISLAND, "Visayas")
        contentValues.put(KEY_INGREDIENTS, "1 lb. Miki Noodles.\n" +
                "1 lb. Pork.\n" +
                "1 lb. Pig Intestines\n" +
                "1/4 lb. Pig Liver\n" +
                "1 1/2 tsp. Salt\n" +
                "1 1/2 tsp. Ground Black Pepper\n" +
                "2 tsp. Sugar\n" +
                "1 tsp. Bagoong\n" +
                "1 tsp. Onion Powder\n" +
                "1 cup Pork Cracklings\n" +
                "Spring Onion\n" +
                "Toasted Garlic\n" +
                "7 cups Water\n")
        contentValues.put(KEY_SERVING_SIZE, "6 persons")
        contentValues.put(KEY_CALORIES, 612)
        contentValues.put(KEY_TIME, "1 hour, 40 minutes")
        contentValues.put(KEY_INSTRUCTIONS, "Boil water in a cooking pot.\n" +
                "Put-in salt, sugar, onion powder, ground black pepper, and shrimp paste. Cook for a minute.\n" +
                "Add the pork and cook until tender (about 30 to 45 minutes)\n" +
                "Put-in the intestines and liver, and then cook for 6 to 10 minutes.\n" +
                "Remove the pork, liver, and intestine from the broth (caldo). Set aside.\n" +
                "Slice the pork into strips.\n" +
                "Arranged the cooked miki noodles in a single serving bowl.\n" +
                "Place the strips of pork, liver, and intestine on top of the miki noodles.\n" +
                "Pour the broth in the bowl, and then garnish with spring onions and toasted garlic.\n" +
                "Serve hot. Share and enjoy!"
        )

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Kinilaw")
        contentValues.put(KEY_ISLAND, "Visayas")
        contentValues.put(KEY_INGREDIENTS, "2 lb. Tanigue.\n" +
                "2 thumbs Fresh Ginger\n" +
                "8 pcs. Siling Labuyo\n" +
                "15-20 pcs. Calamansi\n" +
                "Red Onion\n" +
                "1/4 tsp. Ground Black Pepper\n" +
                "1/2 tsp. Sugar\n" +
                "Salt (To Taste)\n")
        contentValues.put(KEY_SERVING_SIZE, "6 persons")
        contentValues.put(KEY_CALORIES, 339)
        contentValues.put(KEY_TIME, "3 hours, 1 minute")
        contentValues.put(KEY_INSTRUCTIONS, "Squeeze the juice out of the calamansi over a large bowl. Use a sieve to filter the seeds. Discard the seeds.\n" +
                "In the bowl with calamansi juice, add sugar, ½ teaspoon salt, and ground black pepper. Stir until the sugar and salt are diluted.\n" +
                "Add the ginger, chili, and onion. Stir for a few seconds until all ingredients are well blended.\n" +
                "Arranged the raw fish cubes in a large bowl. Pour the calamansi mixture. Toss until the fish cubes are coated. Let is stay for 10 minutes.\n" +
                "Toss one more time and taste. Add more salt if needed. You can also add more calamansi juice if needed. Toss to blend all the ingredients. Securely cover the bowl and place it inside the refrigerator. Let it chill for at least 3 hours.\n" +
                "Serve. Share and enjoy!"
        )

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//


        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Binignit")
        contentValues.put(KEY_ISLAND, "Visayas")
        contentValues.put(KEY_INGREDIENTS, "2 Sweet Potato.\n" +
                "2 Purple yam\n" +
                "2 Saba Banana \n" +
                "1 cup Muscovado sugar\n" +
                "4 cups Coconunut Milk\n" +
                "1 1/2-2 cups Water\n" +
                "1 1/2-2 cups Ripe Jackfruit\n" +
                "2 Gabi\n" +
                "1 1/4 cup Tapioca Pearls\n" +
                "2 tbsp. Glutinous Rice Flour\n")
        contentValues.put(KEY_SERVING_SIZE, "4 persons")
        contentValues.put(KEY_CALORIES, 847)
        contentValues.put(KEY_TIME, "40 minutes")
        contentValues.put(KEY_INSTRUCTIONS, "Combine coconut milk and water in a cooking pot. Let boil.\n" +
                "Stir the mixture. Add sweet potato, purple yam, saba banana, ripe jackfruit, and eddo (gabi). Cover the pot. Continue to cook in medium heat for 8 to 10 minutes.\n" +
                "Add Muscovado sugar and sago pearls. Stir. Cover and continue to cook for another 8 minutes.\n" +
                "Combine glutinous rice flour (galapong) and 1/4 cup water. Stir until flour dilutes completely. Pour into the cooking pot. Stir. Continue to cook until all the sweet potato, yams, and gabi are soft. Note: you can add more water in the pot if you want your binignit to be soupy, add more sugar as needed.\n" +
                "Transfer to a serving plate. Serve.\n" +
                "Share and enjoy!"
        )

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//



        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Odong")
        contentValues.put(KEY_ISLAND, "Mindanao")
        contentValues.put(KEY_INGREDIENTS, "3 ounces odong noodles. (use udon as an alternative)\n" +
                "3 cans 5.5 oz. spicy sardines in tomato sauce\n" +
                "1 cup sliced bottle gourd upo\n" +
                "1 cup water\n" +
                "3 cloves garlic crushed\n" +
                "1 small yellow onion sliced\n" +
                "2 tablespoons cooking oil\n" +
                "salt and pepper to taste\n")
        contentValues.put(KEY_SERVING_SIZE, "3 persons")
        contentValues.put(KEY_CALORIES, 194)
        contentValues.put(KEY_TIME, "20 minutes")
        contentValues.put(KEY_INSTRUCTIONS, "Heat oil in a cooking pot.\n" +
                "Saute the garlic and onion when the oil becomes hot. Cook until the onion is soft.\n" +
                "Put in sliced bottle gourd. Cook for 1 minute.\n" +
                "Add the sardines including the sauce and pour-in water. Let boil.\n" +
                "Put-in the noodles. Cook for for 5 to 8 minutes or until the noodles are done.\n" +
                "Add salt and pepper to taste. Stir.\n" +
                "Transfer to a serving bowl.\n" +
                "Serve with rice. Share and enjoy!\n")

                success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Balbacua")
        contentValues.put(KEY_ISLAND, "Mindanao")
        contentValues.put(KEY_INGREDIENTS, "5 lbs. cow trotters\n" +
                "1 Knorr Beef Cube\n" +
                "1 bunch lemongrass (white part) cut in 4-inch pieces\n" +
                "1 bell pepper sliced\n" +
                "3 tablespoons salted black beans\n" +
                "1 bunch green onions cut in 2-inch pieces\n" +
                "3 Jalapeno pepper\n" +
                "1 teaspoon ground black pepper\n"+
                "8 ounces tomato sauce\n" +
                "10 cups water\n" +
                "2 teaspoons annatto powder\n" +
                "5 cloves garlic crushed\n" +
                "1 onion chopped\n" +
                "2 knobs ginger crushed\n" +
                "Fish sauce to taste\n" +
                "3 tablespoons cooking oil\n")
        contentValues.put(KEY_SERVING_SIZE, "6 persons")
        contentValues.put(KEY_CALORIES, 101)
        contentValues.put(KEY_TIME, "3 hours")
        contentValues.put(KEY_INSTRUCTIONS, "Heat oil in a pan. Sauté garlic, onion, and ginger until the onion softens. Set aside.\n" +
                "Combine trotters, 10 cups water, ground black pepper, and lemongrass in a cooking pot. Let it boil. Adjust the stove to the lowest heat setting and then simmer for 3 hours or until the meat and fibers on the trotters soften.\n" +
                "Remove the lemongrass. Add the annatto powder, sauteed aromatics (onion, garlic, and ginger), salted black beans, and Knorr Beef Cube. Continue cooking for 30 minutes.\n" +
                "Add jalapeno, bell pepper, and green onions. Cook for 10 minutes.\n")

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Beef Hinalang")
        contentValues.put(KEY_ISLAND, "Mindanao")
        contentValues.put(KEY_INGREDIENTS, "1 1/2 lbs beef thinly sliced\n" +
                "1 piece Knorr Beef Cube\n" +
                "3/4 cup green onion chopped\n" +
                "12 pieces Thai chili pepper chopped\n" +
                "2 tablespoons white vinegar\n" +
                "4 tablespoons soy sauce\n" +
                "1 piece onion chopped\n" +
                "3 thumbs ginger sliced\n"+
                "5 cloves garlic minced\n" +
                "4 cups water\n" +
                "2 teaspoons annatto powder\n" +
                "5 cloves garlic crushed\n" +
                "3 tablespoons cooking oil\n" +
                "Ground black pepper to taste\n")
        contentValues.put(KEY_SERVING_SIZE, "4 persons")
        contentValues.put(KEY_CALORIES, 563)
        contentValues.put(KEY_TIME, "45 mins")
        contentValues.put(KEY_INSTRUCTIONS, "Heat oil in a pot. Saute onion, garlic, and ginger.\n" +
                "Once onion softens, add beef. Saute until medium brown.\n" +
                "Pour vinegar and soy sauce. Let boil.\n" +
                "Add Knorr Beef Cube. Stir and then pour-in water. Let the water boil. Continue to boil in low to medium heat for 30 minutes of until the beef gets tender. Note: add more water if needed.\n" +
                "Add Thai chili pepper and green onions. Season with ground black pepper. Cook for 2 minutes. Serve hot. Share and enjoy!\n")

        success = db.insert(TABLE_RECIPE,null,contentValues)



        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Sinuglaw")
        contentValues.put(KEY_ISLAND, "Mindanao")
        contentValues.put(KEY_INGREDIENTS, "1 lb Inihaw na liempo grilled pork belly, chopped\n" +
                "1 lb fresh tuna meat cubed\n" +
                "2 cups cucumber seeded and thinly sliced\n" +
                "1 1/4 cup vinegar cane, white, or coconut vinegar\n" +
                "1 medium sized red onion sliced\n" +
                "2 tablespoons ginger julienned\n" +
                "4 pieces finger chilies sliced\n" +
                "1 piece lemon\n"+
                "4 to 6 pieces Thai or Bird’s eye chili chopped\n" +
                "1 teaspoon salt\n")
        contentValues.put(KEY_SERVING_SIZE, "4 persons")
        contentValues.put(KEY_CALORIES, 1460)
        contentValues.put(KEY_TIME, "30 mins")
        contentValues.put(KEY_INSTRUCTIONS, "Place the tuna meat in a bowl then pour-in 1/2 cup of vinegar. Soak for 8 minutes.\n" +
                "Using a spoon of fork, press the tuna meat lightly.\n" +
                "Pour vinegar and soy sauce. Let boil.\n" +
                "Drain the vinegar then combine cucumber, onion, ginger, finger chili, Thai or Bird’s eye chili, and salt. Mix well.\n" +
                "Squeeze the lemon until all the juices are extracted then pour-in the remaining 3/4 cup vinegar. Mix well and soak for 10 minutes.\n"+
                "Put-in the Grilled pork belly and mix thoroughly. Let the mixture stand for at least 1 hour (you may place this inside the refrigerator).\n"+
        "Transfer to a serving plate then serve.\n" +
        "Share and enjoy!\n")

        success = db.insert(TABLE_RECIPE,null,contentValues)


        //**************************************************************************//

        contentValues = ContentValues()
        contentValues.put(KEY_RNAME, "Binangkal ")
        contentValues.put(KEY_ISLAND, "Mindanao")
        contentValues.put(KEY_INGREDIENTS, "2 cups all-purpose flour\n" +
                "1/4 Cup cornstarch\n" +
                "3/4 Cup white sugar\n" +
                "1/4 teaspoon Salt\n" +
                "1 teaspoon baking powder\n" +
                "2 tablespoons salted butter melted\n" +
                "1 large egg\n" +
                "1/2 Cup evaporated milk\n"+
                "1/2 Cup sesame seeds\n" +
                "1/4 teaspoon vanilla Optional\n")
        contentValues.put(KEY_SERVING_SIZE, "15 persons")
        contentValues.put(KEY_CALORIES, 1950)
        contentValues.put(KEY_TIME, "18 mins")
        contentValues.put(KEY_INSTRUCTIONS, "Combine all dry ingredients in a bowl: flour, cornstarch, sugar, salt, and baking powder. Mix well.\n" +
                "In a separate bowl mix together milk, melted butter, and egg. Mix well.\n" +
                "Slowly add the dry ingredients into the wet mixture. Mix well to form a dough.\n" +
                "Prepare oil in a pan for deep frying. Make sure the oil is hot before putting the dough.\n" +
                "Deep fry until golden brown.\n")

        success = db.insert(TABLE_RECIPE,null,contentValues)


        //**************************************************************************//

        db.close()

       return success
    }



}