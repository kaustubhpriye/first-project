package com.capsulehq.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Database

object AstrologyProfiles : Table() {
    val id = varchar("id", 36)
    val name = varchar("name", 100)
    val gender = varchar("gender", 20)
    val dateOfBirth = varchar("date_of_birth", 20)
    val timeOfBirth = varchar("time_of_birth", 10)
    val locationOfBirth = varchar("location_of_birth", 100)
    val chartData = text("chart_data")
    override val primaryKey = PrimaryKey(id)
}

fun initDatabase() {
//    val dbUrl = System.getenv("DB_URL") ?: "jdbc:postgresql://localhost:5432/astrodb"
//    val dbUser = System.getenv("DB_USER") ?: "postgres"
//    val dbPass = System.getenv("DB_PASS") ?: "postgres"
//    Database.connect(dbUrl, driver = "org.postgresql.Driver", user = dbUser, password = dbPass)
//    transaction {
//        SchemaUtils.create(AstrologyProfiles)
//    }
} 