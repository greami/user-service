package org.user.service.adapter.repository;


import org.user.service.adapter.repository.entity.RuoloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRuoloRepository extends JpaRepository<RuoloEntity, Integer>
{
}
