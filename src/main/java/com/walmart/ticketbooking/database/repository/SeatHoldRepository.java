package com.walmart.ticketbooking.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walmart.ticketbooking.database.model.Customer;
import com.walmart.ticketbooking.database.model.SeatHold;

import java.util.Date;
import java.util.List;

@Repository
public interface SeatHoldRepository extends JpaRepository<SeatHold, Integer> {
	List<SeatHold> findByConfirmationCodeIsNullAndHoldTimeBefore(Date date);
	List<SeatHold> findByCustomer(Customer customer);
}
