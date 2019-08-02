package ru.you11.skbkonturtestproject.other

object Consts {

    object Network {
        const val baseUrl = "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/"
        const val timeDiffForUpdateInMillis = 60000L
        const val timeOutLength = 15L
        val filenames = listOf("generated-01", "generated-02", "generated-03")
    }

    object Prefs {
        const val contactsPrefs = "prefs"
        const val contactsPrefsLastUpdate = "update"
    }
}