package com.cw.bookappointment.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cw.bookappointment.entity.Appointment;
import com.cw.bookappointment.entity.PageModel;

@Repository
public interface IAppointmentDao extends IGenericDao<Appointment, Integer> {
	public List<Appointment> listByPage(PageModel pageModel);
	public List<Appointment> selectByPage(Map<Object,Object> params);
}
