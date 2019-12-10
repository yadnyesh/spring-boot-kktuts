package io.yadnyesh.TKBooking.model;

import javax.persistence.*;

@Entity
@Table(name="ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ticket_id")
    private Integer ticketId;
}


