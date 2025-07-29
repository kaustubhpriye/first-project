package com.capsulehq.service

import com.capsulehq.db.AstrologyProfiles
import com.capsulehq.AstrologyProfile
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object ProfileService {
    fun createProfile(profile: AstrologyProfile): AstrologyProfile {
        val id = profile.id.ifBlank { UUID.randomUUID().toString() }
        transaction {
            AstrologyProfiles.insert {
                it[AstrologyProfiles.id] = id
                it[name] = profile.name
                it[gender] = profile.gender
                it[dateOfBirth] = profile.dateOfBirth
                it[timeOfBirth] = profile.timeOfBirth
                it[locationOfBirth] = profile.locationOfBirth
                it[chartData] = profile.chartData
            }
        }
        return profile.copy(id = id)
    }

    fun getProfile(id: String): AstrologyProfile? = transaction {
        AstrologyProfiles.select { AstrologyProfiles.id eq id }
            .mapNotNull {
                AstrologyProfile(
                    id = it[AstrologyProfiles.id],
                    name = it[AstrologyProfiles.name],
                    gender = it[AstrologyProfiles.gender],
                    dateOfBirth = it[AstrologyProfiles.dateOfBirth],
                    timeOfBirth = it[AstrologyProfiles.timeOfBirth],
                    locationOfBirth = it[AstrologyProfiles.locationOfBirth],
                    chartData = it[AstrologyProfiles.chartData]
                )
            }
            .singleOrNull()
    }

    fun getAllProfiles(): List<AstrologyProfile> = transaction {
        AstrologyProfiles.selectAll().map {
            AstrologyProfile(
                id = it[AstrologyProfiles.id],
                name = it[AstrologyProfiles.name],
                gender = it[AstrologyProfiles.gender],
                dateOfBirth = it[AstrologyProfiles.dateOfBirth],
                timeOfBirth = it[AstrologyProfiles.timeOfBirth],
                locationOfBirth = it[AstrologyProfiles.locationOfBirth],
                chartData = it[AstrologyProfiles.chartData]
            )
        }
    }
} 