package com.bernardo.dsclient.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardo.dsclient.dto.ClientDTO;
import com.bernardo.dsclient.entities.Client;
import com.bernardo.dsclient.repositories.ClientRepository;
import com.bernardo.dsclient.services.exceptions.DatabaseException;
import com.bernardo.dsclient.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
		List<Client> list = repository.findAll();

		return list.stream().map(x -> new ClientDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ClientDTO(entity);

	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClientDTO(entity);

	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			if (dto.getName() != null) {
				entity.setName(dto.getName());
			}
			if (dto.getCpf() != null) {
				entity.setCpf(dto.getCpf());
			}
			if (dto.getIncome() != null) {
				entity.setIncome(dto.getIncome());
			}
			if (dto.getBirthDate() != null) {
				entity.setBirthDate(dto.getBirthDate());
			}
			if (dto.getChildren() != null) {
				entity.setChildren(dto.getChildren());
			}

			entity = repository.save(entity);
			return new ClientDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

}
