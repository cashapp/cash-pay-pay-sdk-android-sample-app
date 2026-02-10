// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.spotless)
}

subprojects {
  apply(plugin = "com.diffplug.spotless")
  extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlin {
      target("src/**/*.kt")
      // ktlint doesn't honour .editorconfig yet: https://github.com/diffplug/spotless/issues/142
      ktlint(libs.versions.ktlint.get()).editorConfigOverride(
        mapOf(
          "insert_final_newline" to "true",
          "end_of_line" to "lf",
          "charset" to "utf-8",
          "indent_size" to "2",
          "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
          "ktlint_standard_filename" to "disabled",
        )
      )
    }
    java {
      target("src/**/*.java")
    }
  }
}
