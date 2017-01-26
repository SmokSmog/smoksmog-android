package com.antyzero.smoksmog.changelog

import com.antyzero.smoksmog.changelog.model.ChangeType.FIX
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.File
import java.util.*


class ChangelogReaderTest {

    val localePolish = Locale("pl", "PL")

    @Test
    fun readChangelog() {
        val changelogReader = createChangelog(localePolish, "/changelog_simple.json")
        val changelog = changelogReader.changelog

        assertThat(changelog).isNotNull()

        println(changelog)

        with(changelog.versions) {
            assertThat(this).hasSize(2)
            assertThat(this[0].changes[0].type).isEqualTo(FIX)
            assertThat(this[0].changes[0].translation["pl"]).isNotNull()
        }
    }

    private fun createChangelog(locale: Locale, resourcePath: String) = ChangelogReader(
            locale, File(ChangelogReaderTest::class.java.getResource(resourcePath).toURI())
    )
}