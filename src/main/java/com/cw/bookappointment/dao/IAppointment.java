package com.cw.bookappointment.dao;

import org.springframework.stereotype.Repository;

import com.cw.bookappointment.entity.Appointment;

@Repository
public interface IAppointment extends IGenericDao<Appointment, Integer> {
}
