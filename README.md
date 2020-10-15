# Profanity Filter
A simple android library for filtering obscene speech.
## Setup
### Gradle
Download the latest version via Gradle.
Make sure you have `jcenter` repository.
```
repositories {
	jcenter()    
}
```
Add implementation to your `build.gradle` in app level.
```
dependencies {
    ...
    implementation 'com.sosorevgm.profanityfilter:profanityfilter:1.0.0'
}
```
#### If you are using Java in your project, do not forget to add kotlin stdLib
```
dependencies {
    ...
    implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk7:$latest_version'
}
```
### Maven
Download the latest version via Maven.
```
<dependency>
  <groupId>com.sosorevgm.profanityfilter</groupId>
  <artifactId>profanityfilter</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
## Quickstart
Create a ProfaintyFilter instance that requires context.
```
val filter = ProfanityFilter(this)
```
By default, the filter contains about 400 english curses. Function `getFilteredString()` returns a new string with no forbidden content.
```
val filteredString = filter.getFilteredString("All swearwords will look like: f*** this s***")
```
But you can use your own list of words you don't want to see. Add a txt file to assets folder or raw directory of resources. You can also set the list in code.
```
filter.setNewProfanityList(R.raw.raw_profanity_list)
```
```
filter.setNewProfanityList("assets_profanity_list")
```
```
filter.setNewProfanityList(listOf("These", "words", "will", "be", "filtered"))
```
You can also expand the existing list by adding one or more words.
```
filter.addSwearwords("Swearword")
filter.addSwearwords(listOf("Expand", "your", "filter"))
```
