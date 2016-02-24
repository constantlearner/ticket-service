package com.walmart.ticketbooking.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walmart.ticketbooking.database.model.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {

}
