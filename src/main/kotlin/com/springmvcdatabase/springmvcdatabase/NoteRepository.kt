package com.springmvcdatabase.springmvcdatabase

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface NoteRepository : JpaRepository<Note?, Long?>{

    //Update Notes Transaction:-
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Note n SET n.noteData = :noteData WHERE n.noteId = :noteId")
    fun updateNotes(@Param("noteData") noteData: String?, @Param("noteId") noteId: Long?): Int

    //Delete Note By ID Transaction:-
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("DELETE from Note n where n.noteId = :noteId")
    fun deleteNoteById(@Param("noteId") noteId: Long?): Int

    //Getting Notes App FCM Token Transaction:-
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("select n.appToken from Note n")
    fun getToken():String

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Select * from note_table n WHERE n.note_data like :note_data%" , nativeQuery = true)
    fun searchNote(@Param("note_data") note_data: String?): List<Note>

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Select * from note_table n WHERE n.note_time between :startDate and :endDate" , nativeQuery = true)
    fun filterNote(@Param("startDate") startDate : String? , @Param("endDate") endDate : String?): List<Note>
}

@Repository
interface DemoDataInterface : PagingAndSortingRepository<Demo , Long>{
    //Search List Data Transaction:-
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("Select * from DemoList d WHERE d.name like :name%" , nativeQuery = true)
    fun searchListData(@Param("name") name: String?): List<Demo>
}
