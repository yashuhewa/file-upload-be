package com.assessment.fileuploadbe.dto

data class FileUploadPaginationResponse(
    val data: List<Any>,
    val total: Int,
    val page: Int,
    val last_page: Int
)
