package com.poc.managerService.service;

import java.util.List;

import com.poc.managerService.model.GetManagerResponse;
import com.poc.managerService.model.Skill;

public interface ManagerService {
	
	List<GetManagerResponse> getSkills(int managerId) throws Exception;
	List<Skill> updateSkills(List<Integer> skillIds, String status, String approverComments) throws Exception;

}
