package ru.pis.lab3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pis.lab3.entity.Course;

import java.util.List;

public interface CourseRepo  extends JpaRepository<Course, Long> {
    @Query ("SELECT course FROM Course course")
    List<Course> getAll();
}
