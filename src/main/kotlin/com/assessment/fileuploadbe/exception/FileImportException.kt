package com.assessment.fileuploadbe.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class FileImportException(msg: String) : RuntimeException(msg)