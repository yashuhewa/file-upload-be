package com.assessment.fileuploadbe.repository

import com.assessment.fileuploadbe.model.FileUploadDB
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface FileUploadRepository : JpaRepository<FileUploadDB, Long> {

    @Query("select p from FileUploadDB p where p.customer like UPPER(CONCAT('%', ?1,'%')) or p.description like UPPER(CONCAT('%', ?1,'%')) or p.invoiceNo like UPPER(CONCAT('%', ?1,'%'))")
    fun findBySearch(@Param("search") search: String, pageable: Pageable): Page<FileUploadDB>

    @Query("select COUNT(p) from FileUploadDB p where p.customer like %?1% or p.description like %?1% or p.invoiceNo like %?1%", countQuery = "*")
    fun countSearch(s: String): Int


}