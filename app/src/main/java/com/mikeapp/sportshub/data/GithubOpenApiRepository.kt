package com.mikeapp.sportshub.data

import android.util.Log
import com.mikeapp.sportshub.data.NetworkModule.githubApiService
import com.mikeapp.sportshub.data.github.model.GitHubFileRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GithubOpenApiRepository {
    fun test() {
        CoroutineScope(Dispatchers.IO).launch {
//            readContentInSingleCall("todoroot/test1.txt")
//            getExistFileMeta()
            createNewFile(
                "todoroot/test2.txt",
                "121212good test string input good new changes!!!",
                "test commit hahaha commit#2",
            )
        }
    }

    private suspend fun getFileMeta() {
        val response = githubApiService.getFileMetadata("todoroot/test1.txt")
        Log.d("bbbb", "response.code: ${response.code()}")

        if (response.isSuccessful && response.body() != null) {
            Log.d("bbbb", "response.body: ${response.body()}")
        }
    }

    private suspend fun getNonExistFileMeta() {
        val response = githubApiService.getFileMetadata("todoroot/test2.txt")
        Log.d("bbbb", "response.code: ${response.code()}")

        if (response.isSuccessful && response.body() != null) {
            Log.d("bbbb", "response.body: ${response.body()}")
        }
    }

    suspend fun readFileContent(path: String): String? {
        val response = githubApiService.getFileContent(path)
        val body = response.body()

        if (response.isSuccessful && body != null) {
            // Decode the Base64 content of the file
            val decodedContent = String(
                android.util.Base64.decode(
                    body.content,
                    android.util.Base64.URL_SAFE
                )
            )
            Log.d("bbbb", "decodedContent: $decodedContent")
            return decodedContent
        }
        return null
    }

    suspend fun createNewFile(
        path: String,
        fileContent: String,
        commitMessage: String
    ) {
        // Encode the file content in Base64
        val encodedContent = android.util.Base64.encodeToString(
            fileContent.toByteArray(Charsets.UTF_8),
            android.util.Base64.DEFAULT
        )

        // Create the request body with the encoded content
        val createFileRequest = GitHubFileRequest(
            message = commitMessage,
            content = encodedContent,
            sha = null // make sha null
        )

        val response = githubApiService.createFile(path, createFileRequest)

        if (response.isSuccessful) {
            // File created successfully
            val fileContent = response.body()
            Log.d("bbbb", "File created successfully. body: $fileContent")
        } else {
            // Handle errors
            Log.e("bbbb", "Error creating file: ${response.code()}")

            val errorBody = response.errorBody()?.string()
            Log.e("bbbb", "Error creating file: ${response.code()}, $errorBody")
        }
    }

    suspend fun createOrForceUpdateFile(
        path: String,
        fileContent: String,
        commitMessage: String
    ) {
        val fileMetadataResponse = githubApiService.getFileMetadata(path)
        if (fileMetadataResponse.isSuccessful || fileMetadataResponse.code() == 404) {
            val currentSha = if (fileMetadataResponse.isSuccessful) {
                val fileMetadata = fileMetadataResponse.body()

                // Ensure we have the current SHA
                fileMetadata?.sha ?: throw IllegalStateException("File SHA not found")
            } else null

            // Encode the file content in Base64
            val encodedContent = android.util.Base64.encodeToString(
                fileContent.toByteArray(Charsets.UTF_8),
                android.util.Base64.DEFAULT
            )

            // Create the request body with the encoded content
            val createFileRequest = GitHubFileRequest(
                message = commitMessage,
                content = encodedContent,
                sha = currentSha
            )

            // Make the API call to create the file
            val response = githubApiService.updateFile(path, createFileRequest)

            if (response.isSuccessful) {
                // File created successfully
                val fileContent = response.body()
            } else {
                // Handle errors
                Log.e("bbbb", "Error creating file: ${response.code()}")

                val errorBody = response.errorBody()?.string()
                Log.e("bbbb", "Error creating file: ${response.code()}, $errorBody")
            }
        }
    }
}