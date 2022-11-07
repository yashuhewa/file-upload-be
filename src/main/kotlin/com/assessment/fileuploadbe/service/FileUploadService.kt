package com.assessment.fileuploadbe.service

import com.assessment.fileuploadbe.exception.BadRequestException
import com.assessment.fileuploadbe.exception.FileImportException
import com.assessment.fileuploadbe.model.FileUploadDB
import com.assessment.fileuploadbe.repository.FileUploadRepository
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.CsvToBeanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale


@Service
class FileUploadService (private val fileUploadRepository: FileUploadRepository){

    fun uploadFile(file: MultipartFile): List<FileUploadDB> {
        //check if empty
        checkFileEmpty(file)
        var fileReader : BufferedReader? = null;

        try {
            fileReader = BufferedReader(InputStreamReader(file.inputStream))
            val csvToBean = createCSVToBean(fileReader)
            return fileUploadRepository.saveAll(csvToBean)
        } catch (ex: Exception) {
            throw FileImportException("Error During csv import")
        } finally {
            closeFileReader(fileReader)
        }
    }

    fun searchPagination( search: String, page: Int, size: Int) :MutableMap<String, Any> {


        var data: List<FileUploadDB>

        val paging: Pageable = PageRequest.of(page, size)

        var pageData: Page<FileUploadDB>
        if(search.isEmpty()) {
            pageData = fileUploadRepository.findAll(paging)
        } else {
            pageData = fileUploadRepository.findBySearch(search, paging)
        }

        data = pageData.content

        val total = this.fileUploadRepository.countSearch(search.uppercase(Locale.getDefault()))

        val response: MutableMap<String, Any> = HashMap()
        response["data"] = data
        response["currentPage"] = pageData.number
        response["totalItems"] = pageData.totalElements
        response["totalPages"] = pageData.totalPages
        return response;

    }

    private fun createCSVToBean(fileReader: BufferedReader?): CsvToBean<FileUploadDB> =
            CsvToBeanBuilder<FileUploadDB>(fileReader)
                    .withType(FileUploadDB::class.java)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()

    private fun closeFileReader(fileReader: BufferedReader?) {
        try {
            fileReader!!.close()
        } catch (ex: IOException) {
            throw IOException("Error during csv import")
        }
    }

    private fun checkFileEmpty(file: MultipartFile) {
        if (file.isEmpty)
            throw BadRequestException("Empty file")
    }
}