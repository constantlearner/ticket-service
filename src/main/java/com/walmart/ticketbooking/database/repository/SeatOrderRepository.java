package com.walmart.ticketbooking.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walmart.ticketbooking.database.model.SeatHold;
import com.walmart.ticketbooking.database.model.SeatOrder;
import com.walmart.ticketbooking.database.model.Venue;

import java.util.List;

@Repository
public interface SeatOrderRepository extends JpaRepository<SeatOrder, Long> {
	List<SeatOrder> findBySeatHold(SeatHold seathold);
	List<SeatOrder> findByVenue(Venue venue);
}
