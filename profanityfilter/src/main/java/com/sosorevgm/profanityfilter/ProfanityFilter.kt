package com.sosorevgm.profanityfilter

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

class ProfanityFilter(
    private val context: Context
) {

    companion object {
        const val DEFAULT_PATH = "default_profanity_list"
        private val profanityWords = HashSet<String>()
    }

    init {
        loadWordsFromAssetsFile(DEFAULT_PATH)
    }

    fun setNewProfanityList(assetsFileName: String) {
        loadWordsFromAssetsFile(assetsFileName)
    }

    fun setNewProfanityList(rawResources: Int) {
        loadWordsFromRawResources(rawResources)
    }

    fun setNewProfanityList(words: List<String>) {
        profanityWords.clear()
        profanityWords.addAll(words)
    }

    fun addSwearwords(swearword: String) {
        profanityWords.add(swearword)
    }

    fun addSwearwords(swearwords: List<String>) {
        profanityWords.addAll(swearwords)
    }

    private fun loadWordsFromRawResources(rawResources: Int) {
        if (profanityWords.isNotEmpty()) {
            profanityWords.clear()
        }

        val bufferedReader =
            BufferedReader(InputStreamReader(context.resources.openRawResource(rawResources)))
        while (bufferedReader.ready()) {
            profanityWords.add(bufferedReader.readLine())
        }
        bufferedReader.close()
    }

    private fun loadWordsFromAssetsFile(assetsFileName: String) {
        if (profanityWords.isNotEmpty()) {
            profanityWords.clear()
        }

        val bufferedReader =
            BufferedReader(InputStreamReader(context.assets.open(assetsFileName)))
        while (bufferedReader.ready()) {
            profanityWords.add(bufferedReader.readLine())
        }
        bufferedReader.close()
    }

    fun checkProfanityContent(text: String): Boolean {
        val contentCharArray = text.toCharArray()
        val word = StringBuilder()

        for (i in contentCharArray.indices) {
            if (contentCharArray[i].isLetter() && i != contentCharArray.size - 1) {
                word.append(contentCharArray[i])
            } else if (contentCharArray[i].isLetter() && i == contentCharArray.size - 1) {
                word.append(contentCharArray[i])
                for (j in profanityWords.indices) {
                    if (word.toString().toLowerCase() == profanityWords.elementAt(j).toLowerCase()) {
                        return true
                    }
                }
                word.clear()
            } else {
                for (j in profanityWords.indices) {
                    if (word.toString().toLowerCase() == profanityWords.elementAt(j).toLowerCase()) {
                        return true
                    }
                }
                word.clear()
            }
        }
        return false
    }

    fun getFilteredString(text: String) =
        if (checkProfanityContent(text)) {
            applyDefaultFilter(text)
        } else {
            text
        }

    private fun applyDefaultFilter(text: String): String {
        val filteredText = StringBuilder(text)
        val textCharArray = text.toCharArray()
        val word = StringBuilder()

        for (i in textCharArray.indices) {
            if (textCharArray[i].isLetter() && i != textCharArray.size - 1) {
                word.append(textCharArray[i])
            } else if (textCharArray[i].isLetter() && i == textCharArray.size - 1) {
                word.append(textCharArray[i])
                for (j in profanityWords.indices) {
                    if (word.toString().toLowerCase() == profanityWords.elementAt(j).toLowerCase() && word.length != 1) {
                        val substitute = StringBuilder()
                        for (k in word.indices - 1) {
                            substitute.append("*")
                        }
                        filteredText.replace(i - word.length + 2, i + 1, substitute.toString())
                    }
                }
                word.clear()
            } else {
                for (j in profanityWords.indices) {
                    if (word.toString().toLowerCase() == profanityWords.elementAt(j).toLowerCase() && word.length != 1) {
                        val substitute = StringBuilder()
                        for (k in word.indices - 1) {
                            substitute.append("*")
                        }
                        filteredText.replace(i - word.length + 1, i, substitute.toString())
                    }
                }
                word.clear()
            }
        }
        return filteredText.toString()
    }
}