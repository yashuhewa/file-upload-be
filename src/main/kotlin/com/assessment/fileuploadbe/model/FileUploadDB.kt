package com.assessment.fileuploadbe.model

import com.opencsv.bean.CsvBindByName
import com.opencsv.bean.CsvDate
import java.util.Date
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "files")
data class FileUploadDB(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @CsvBindByName(column = "InvoiceNo")
    var invoiceNo: String? = null,
    @CsvBindByName(column = "StockCode")
    var stockCode: String? = null,
    @CsvBindByName(column = "Description")
    var description: String? = null,
    @CsvBindByName(column = "Quantity")
    var quantity: Int? = null,
    @CsvBindByName(column = "InvoiceDate")
    @CsvDate("MM/dd/yyyy HH:mm")
    var invoiceDate: Date? = null,
    @CsvBindByName(column = "UnitPrice")
    var unitPrice: Double? = null,
    @CsvBindByName(column = "CustomerID")
    var customer: String? = null,
    @CsvBindByName(column = "Country")
    var country: String? = null

)
