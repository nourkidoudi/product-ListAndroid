package com.example.logiiiiin.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE products (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                price REAL,
                description TEXT,
                imageUrl INTEGER
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS products")
        onCreate(db)
    }

    // Insert a product into the database
    fun addProduct(product: Product): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", product.name)
            put("price", product.price)
            put("description", product.description)
            put("imageUrl", product.imageUrl)
        }
        return db.insert("products", null, values)  // Returns the row ID of the newly inserted row
    }

    // Get all products from the database
    fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM products", null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getLong(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val price = cursor.getDouble(cursor.getColumnIndex("price"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val imageUrl = cursor.getInt(cursor.getColumnIndex("imageUrl"))

                products.add(Product(id, name, price, description, imageUrl))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return products
    }

    // Update a product
    fun updateProduct(product: Product): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", product.name)
            put("price", product.price)
            put("description", product.description)
            put("imageUrl", product.imageUrl)
        }
        // Update the product where the ID matches
        return db.update("products", values, "id = ?", arrayOf(product.id.toString()))
    }

    // Delete a product
    fun deleteProduct(productId: Long): Int {
        val db = writableDatabase
        // Delete the product where the ID matches
        return db.delete("products", "id = ?", arrayOf(productId.toString()))
    }

    companion object {
        const val DATABASE_NAME = "products.db"
        const val DATABASE_VERSION = 1
    }
}
