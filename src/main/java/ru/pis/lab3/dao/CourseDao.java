package ru.pis.lab3.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.pis.lab3.entity.Course;
import ru.pis.lab3.repo.CourseRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Repository
@Transactional
@RequiredArgsConstructor
public class CourseDao {
    @PersistenceContext
    private final EntityManager entityManager;
    private final CourseRepo courseRepo;

    public void addCourse(Course course) {
        if (isNull(course))
            throw new IllegalArgumentException("Empty course!");

        entityManager.persist(course);
    }

    public List<Course> getAllCourses() {
        return courseRepo.getAll();
    }
}
