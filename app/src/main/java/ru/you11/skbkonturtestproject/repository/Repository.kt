package ru.you11.skbkonturtestproject.repository

import ru.you11.skbkonturtestproject.api.ApiMethods
import ru.you11.skbkonturtestproject.api.ApiService
import ru.you11.skbkonturtestproject.api.RetrofitFactory
import ru.you11.skbkonturtestproject.api.models.ApiPerson
import ru.you11.skbkonturtestproject.models.Person

class Repository {

    private val apiService = ApiService(RetrofitFactory().create().create(ApiMethods::class.java))

    fun getPersons(): List<Person> {
        val persons = ArrayList<Person>()
        persons.addAll(ApiPerson.convertToPersonList(apiService.getPersonsData("generated-01").data))
        persons.addAll(ApiPerson.convertToPersonList(apiService.getPersonsData("generated-02").data))
        persons.addAll(ApiPerson.convertToPersonList(apiService.getPersonsData("generated-03").data))
        return persons
    }
}