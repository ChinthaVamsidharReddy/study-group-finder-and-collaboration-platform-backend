package com.infy.project.Interface;

import com.infy.project.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	Group save(Group group);

    List<Group> findByCourseId(String courseId);
    List<Group> findByPrivacy(String privacy);
    List<Group> findByCreatedBy(Long createdBy);
    boolean existsByName(String name);
    boolean existsByCode(String code);
    

}
