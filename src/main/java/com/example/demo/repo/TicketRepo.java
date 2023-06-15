package com.example.demo.repo;

import com.example.demo.entity.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepo extends CrudRepository<Ticket, Long> {

}
