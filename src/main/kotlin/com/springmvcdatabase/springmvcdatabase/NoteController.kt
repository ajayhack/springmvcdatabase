package com.springmvcdatabase.springmvcdatabase

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.FileOutputStream


@RestController
class NoteController {
    @Autowired
    var noteRepository: NoteRepository? = null

    @Autowired
    var demoRepository: DemoDataInterface? = null

    var token : String? = null

    @RequestMapping("/")
    fun home(): String {
        return "Welcome to Home !!!!!!"
    }

    // Add new Note in Table with Image POST API.
    @PostMapping("/createNote")
    @ResponseBody
    fun addNewNote(@RequestParam noteData: String, @RequestParam noteTime: String,
                   @RequestParam notificationToken: String
                   /*,@RequestParam("noteImage") file: MultipartFile*/): ResponseEntity<Any> {
        when {
            noteData.isEmpty() -> {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Note Data is required"
                return ResponseEntity(map, HttpStatus.PARTIAL_CONTENT)
            }

            noteTime.isEmpty() -> {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Note Date is required"
                return ResponseEntity(map, HttpStatus.PARTIAL_CONTENT)
            }

            /*notificationToken.isEmpty() -> {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Notification ID required"
                return ResponseEntity(map, HttpStatus.PARTIAL_CONTENT)
            }*/

            /*file.isEmpty -> {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Image file is required"
                return ResponseEntity(map, HttpStatus.PARTIAL_CONTENT)
            }*/

            else -> {
                //val imagePath = "src/images/" //path
                val n = Note()
                n.noteId = 0
                n.noteData = noteData
                n.noteTime = noteTime
                //n.imagePath = imagePath + file.name
                n.appToken = notificationToken
                noteRepository?.save(n)
               /* try {
                    val output = FileOutputStream(imagePath + file.name + ".jpeg")
                    output.write(file.bytes)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    val map = HashMap<String, String>()
                    map["responseCode"] = "200"
                    map["responseMessage"] = ex.printStackTrace().toString()
                    return ResponseEntity(map, HttpStatus.NOT_FOUND)
                }*/

                token = notificationToken
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Note Saved Successfully"
                return ResponseEntity(map, HttpStatus.OK)
            }
        }
    }

    // Add new Note in Table GET API for ReactJs WebApp.
    @CrossOrigin
    @GetMapping("/createNoteReact")
    @ResponseBody
    fun addNewNoteReact(@RequestParam noteData: String, @RequestParam noteTime: String,
                   @RequestParam notificationToken: String
            /*,@RequestParam("noteImage") file: MultipartFile*/): ResponseEntity<Any> {
        when {
            noteData.isEmpty() -> {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Note Data is required"
                return ResponseEntity(map, HttpStatus.PARTIAL_CONTENT)
            }

            noteTime.isEmpty() -> {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Note Date is required"
                return ResponseEntity(map, HttpStatus.PARTIAL_CONTENT)
            }

            /*notificationToken.isEmpty() -> {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Notification ID required"
                return ResponseEntity(map, HttpStatus.PARTIAL_CONTENT)
            }*/

            /*file.isEmpty -> {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Image file is required"
                return ResponseEntity(map, HttpStatus.PARTIAL_CONTENT)
            }*/

            else -> {
                //val imagePath = "src/images/" //path
                val n = Note()
                n.noteId = 0
                n.noteData = noteData
                n.noteTime = noteTime
                //n.imagePath = imagePath + file.name
                n.appToken = notificationToken
                noteRepository?.save(n)
                /* try {
                     val output = FileOutputStream(imagePath + file.name + ".jpeg")
                     output.write(file.bytes)
                 } catch (ex: Exception) {
                     ex.printStackTrace()
                     val map = HashMap<String, String>()
                     map["responseCode"] = "200"
                     map["responseMessage"] = ex.printStackTrace().toString()
                     return ResponseEntity(map, HttpStatus.NOT_FOUND)
                 }*/

                token = notificationToken
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Note Saved Successfully"
                return ResponseEntity(map, HttpStatus.OK)
            }
        }
    }

    // Get All Notes
    @GetMapping("/readNotes")
    @ResponseBody
    fun allNotes(): List<Note?> {
        return noteRepository!!.findAll()
    }

    // Get All Notes
    @CrossOrigin
    @GetMapping("/readNotesReact")
    @ResponseBody
    fun allNotesReact(): List<Note?> {
        return noteRepository!!.findAll()
    }

    // Get All Dummy Data
    @GetMapping("/readListData")
    @ResponseBody
    fun allListData(@RequestParam pageCount : Int , @RequestParam limit : Int ): ResponseEntity<Any> {
        val sortedByName: Pageable = PageRequest.of(pageCount, limit, Sort.by("id").ascending())
        val page : Page<Demo> = demoRepository!!.findAll(sortedByName)
        return if (page.totalPages > pageCount) {
             ResponseEntity(demoRepository!!.findAll(sortedByName), HttpStatus.OK)
        }else{
             ResponseEntity("No More Data Available!!!", HttpStatus.OK)
        }
    }

    //Get AllListData Search Data:-
    @GetMapping("/searchList")
    @ResponseBody
    fun searchData(@RequestParam name : String) : List<Demo>{
        return demoRepository!!.searchListData(name)
    }

    //Get Search Note Data:-
    @GetMapping("/searchNote")
    @ResponseBody
    fun noteSearchList(@RequestParam noteData : String) : List<Note?>{
        return noteRepository!!.searchNote(noteData)
    }

    //Get Search Note Data React:-
    @CrossOrigin
    @GetMapping("/searchNoteReact")
    @ResponseBody
    fun noteSearchListReact(@RequestParam noteData : String) : List<Note?>{
        return noteRepository!!.searchNote(noteData)
    }

    //Get Sort Note Data:-
    @PostMapping("/sortNote")
    @ResponseBody
    fun noteSorting(@RequestParam sortType : Int) : List<Note?>{
        return if(sortType == 1) {
            noteRepository!!.findAll(Sort.by("noteData").ascending())
        }else {
            noteRepository!!.findAll(Sort.by("noteData").descending())
        }
    }

    //Get Sort Note Data React:-
    @CrossOrigin
    @GetMapping("/sortNoteReact")
    @ResponseBody
    fun noteSortingReact(@RequestParam sortType : Int) : List<Note?>{
        return if(sortType == 1) {
            noteRepository!!.findAll(Sort.by("noteData").ascending())
        }else {
            noteRepository!!.findAll(Sort.by("noteData").descending())
        }
    }

    //Get Filter Note Data:-
    @PostMapping("/filterNote")
    @ResponseBody
    fun noteFiltering(@RequestParam startDate : String , @RequestParam endDate : String) : List<Note?>{
            return noteRepository!!.filterNote(startDate , endDate)
    }

    //Get Filter Note Data React:-
    @CrossOrigin
    @GetMapping("/filterNoteReact")
    @ResponseBody
    fun noteFilteringReact(@RequestParam startDate : String , @RequestParam endDate : String) : List<Note?>{
        return noteRepository!!.filterNote(startDate , endDate)
    }

    // Get All Updated Notes
    @PostMapping("/updateNote")
    @ResponseBody
    fun updateNote(@RequestParam noteId: Int , @RequestParam noteData : String) : ResponseEntity<Any> {
            if(noteId < 1){
                return ResponseEntity("Note ID is required.", HttpStatus.OK)
            }
            if(noteData.isEmpty()){
                return ResponseEntity("Note Data is required.", HttpStatus.OK)
            }
            else {
                val updateStatus = noteRepository!!.updateNotes(noteData , noteId.toLong())
                return if(updateStatus > 0)
                    ResponseEntity(noteRepository!!.findAll(), HttpStatus.OK)
                else
                    ResponseEntity("Update Failed.", HttpStatus.OK)
            }
    }

    // Get All Updated Notes React
    @CrossOrigin
    @GetMapping("/updateNoteReact")
    @ResponseBody
    fun updateNoteReact(@RequestParam noteId: Int , @RequestParam noteData : String) : ResponseEntity<Any> {
            if(noteId < 1){
                return ResponseEntity("Note ID is required.", HttpStatus.OK)
            }
            if(noteData.isEmpty()){
                return ResponseEntity("Note Data is required.", HttpStatus.OK)
            }
            else {
                val updateStatus = noteRepository!!.updateNotes(noteData , noteId.toLong())
                return if(updateStatus > 0)
                    ResponseEntity(noteRepository!!.findAll(), HttpStatus.OK)
                else
                    ResponseEntity("Update Failed.", HttpStatus.OK)
            }
    }

    // Delete Note By ID
    @PostMapping("/deleteNote")
    @ResponseBody
    fun deleteNote(@RequestParam noteId: Int): ResponseEntity<Any> {
         if (noteId < 1) {
             return ResponseEntity("Note ID is required.", HttpStatus.OK)
        } else {
            val deleteStatus = noteRepository!!.deleteNoteById(noteId.toLong())
             return if(deleteStatus > 0) {
                 val map = HashMap<String, String>()
                 map["responseCode"] = "200"
                 map["responseMessage"] = "Note Deleted Successfully"
                 map["noteId"] = noteId.toString()
                 ResponseEntity(map, HttpStatus.OK)
             }else{
                 val map = HashMap<String, String>()
                 map["responseCode"] = "200"
                 map["responseMessage"] = "Note Deletion Failed"
                 map["noteId"] = noteId.toString()
                 ResponseEntity(map, HttpStatus.OK)
             }
        }
    }

    // Delete Note By ID
    @CrossOrigin
    @GetMapping("/deleteNoteReact")
    @ResponseBody
    fun deleteNoteReact(@RequestParam noteId: Int): ResponseEntity<Any> {
        if (noteId < 1) {
            return ResponseEntity("Note ID is required.", HttpStatus.OK)
        } else {
            val deleteStatus = noteRepository!!.deleteNoteById(noteId.toLong())
            return if(deleteStatus > 0) {
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Note Deleted Successfully"
                map["noteId"] = noteId.toString()
                ResponseEntity(map, HttpStatus.OK)
            }else{
                val map = HashMap<String, String>()
                map["responseCode"] = "200"
                map["responseMessage"] = "Note Deletion Failed"
                map["noteId"] = noteId.toString()
                ResponseEntity(map, HttpStatus.OK)
            }
        }
    }

  /*  @Scheduled(fixedDelay = 60*60*30 , initialDelay = 60*60*5)
    @Throws(Exception::class)
    fun pushNotification() = AndroidPushNotificationsService.sendPushNotification(noteRepository!!.getToken(), "Notes App", "This is your Daily Note Reminder")*/
}