package com.springmvcdatabase.springmvcdatabase

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "noteTable")
class Note {
    @Id
    @GeneratedValue
    var noteId: Long? = null
    @NotBlank
    var noteData: String? = null
    @NotBlank
    var noteTime: String? = null
    var imagePath: String? = null
    @NotBlank
    var appToken: String? = null
}

@Entity
@Table(name = "DemoList")
class Demo {
    @Id
    @GeneratedValue
    var id: Int? = null
    @NotBlank
    var name: String? = null
    @NotBlank
    var desctiption: String? = null
    @NotBlank
    var image_url: String? = null
}

/*
@Entity
@Table(name = "LoanDetails")
class Loan {
    @Id
    @GeneratedValue
    var id: Int? = null
    @NotBlank
    var applicantName: String? = null
    @NotBlank
    var applicantMobile: String? = null
    @NotBlank
    var applicantEmail: String? = null
    @NotBlank
    var applicantGender: ApplicantGender? = null
    @NotBlank
    var applicantHouseType: ApplicantHouseType? = null
}

class ApplicantGender{
    @Id
    @GeneratedValue
    var id: Int? = null
    @NotBlank
    var genderType: String? = null
}

class ApplicantHouseType{
    @Id
    @GeneratedValue
    var id: Int? = null
    @NotBlank
    var houseType: String? = null
}*/
