package com.digitalblog.myapp.repository;

import com.digitalblog.myapp.domain.LikeT;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LikeT entity.
 */
@SuppressWarnings("unused")
public interface LikeTRepository extends JpaRepository<LikeT,Long> {

}
