package com.bernardo.dsclient.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bernardo.dsclient.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
