package ru.you11.skbkonturtestproject.other

object Consts {

    object Network {
        const val timeOutLength = 15L

        val filenames = listOf("generated-01", "generated-02", "generated-03")
    }

    object Database {
        const val savedElementsCount = 200
    }

    object Prefs {
        const val appPrefs = "prefs"
        const val appPrefsLastUpdate = "update"
    }
}