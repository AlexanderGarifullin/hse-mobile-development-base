package org.hse.base;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface HseDao {
    @Query("SELECT * FROM `group`")
    LiveData<List<GroupEntity>> getAllGroups();

    @Insert
    void insertGroup(List<GroupEntity> data);

    @Delete
    void delete(GroupEntity data);

    @Query("SELECT * FROM 'teacher'")
    LiveData<List<TeacherEntity>> getAllTeacher();

    @Insert
    void insertTeacher(List<TeacherEntity> data);

    @Delete
    void delete(TeacherEntity data);

    @Query("SELECT * FROM time_table")
    LiveData<List<TimeTableEntity>> getAllTimeTable();

    @Transaction
    @Query("SELECT * FROM time_table")
    LiveData<List<TimeTableWithTeacherEntity>> getTimeTableTeacher();

    @Insert
    void insertTimeTable(List<TimeTableEntity> data);
}
