package com.karrotmvp.ourapt.v1.preopen;

import com.karrotmvp.ourapt.v1.preopen.entity.PreopenVotingForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreopenRepository extends JpaRepository<PreopenVotingForm, String>{

}
