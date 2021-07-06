package com.example.graphql.repositories

interface HeaderRepository {

    /**
     * токен авторизации
     */
    fun getTocken(): String
}