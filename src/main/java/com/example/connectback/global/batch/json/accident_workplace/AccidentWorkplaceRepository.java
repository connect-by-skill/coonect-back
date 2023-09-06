package com.example.connectback.global.batch.json.accident_workplace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentWorkplaceRepository extends JpaRepository<AccidentWorkplace, Long> {
}
