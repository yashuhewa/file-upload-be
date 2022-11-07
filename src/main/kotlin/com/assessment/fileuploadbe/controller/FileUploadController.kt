package com.assessment.fileuploadbe.controller

import com.assessment.fileuploadbe.model.FileUploadDB
import com.assessment.fileuploadbe.service.FileUploadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/csv")
class FileUploadController( private val csvService: FileUploadService
) {

    @CrossOrigin
    @PostMapping("/upload")
    fun uploadCsvFile(@RequestParam("file") file: MultipartFile): ResponseEntity<List<FileUploadDB>> {
        val importedEntries = csvService.uploadFile(file)

        return ResponseEntity.ok(importedEntries)
    }

    @CrossOrigin
    @GetMapping("/data")
    fun getData(
        @RequestParam("s",required = false, defaultValue = "") s: String,
        @RequestParam("page", defaultValue = "1") page: Int,
        @RequestParam("size", defaultValue = "9") size : Int
    ): ResponseEntity<MutableMap<String, Any>> {
        var searchPaginationResult = csvService.searchPagination(s, page, size)

        return ResponseEntity.ok(searchPaginationResult)
    }
}