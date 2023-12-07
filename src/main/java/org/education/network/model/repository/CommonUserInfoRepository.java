package org.education.network.model.repository;

import org.education.network.model.CommonUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonUserInfoRepository extends JpaRepository<CommonUserInfo, Long> {
}