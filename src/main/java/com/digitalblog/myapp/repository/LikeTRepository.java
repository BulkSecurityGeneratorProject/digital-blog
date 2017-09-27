package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.LikeT;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LikeT entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeTRepository extends JpaRepository<LikeT, Long> {

}
