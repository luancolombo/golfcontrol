package com.golf.control.services.impl;

import com.golf.control.dtos.ClientDTO;
import com.golf.control.exception.DataBaseException;
import com.golf.control.exception.GolfException;
import com.golf.control.mapper.ClientMapper;
import com.golf.control.models.Client;
import com.golf.control.repositories.ClientRepository;
import com.golf.control.services.IClientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ClientService implements IClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    public ClientService(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    @Override
    public List<ClientDTO> findByIdList(List<Long> ids) {
        List<Client> clientList = new ArrayList<>();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            clientList = repository.findAll();
        } else {
            clientList = repository.findAllById(ids);
        }
        if (!clientList.isEmpty()) {
            clientDTOList = mapper.clientToClientDTOList(clientList);
        }
        List<Client> finalLists = clientList;
        List<Long> nonExistentIds = ids.stream()
                .filter(id -> finalLists.stream().noneMatch(p -> {
                    return p.getId().equals(id);
                }))
                .collect(Collectors.toList());
        if (!nonExistentIds.isEmpty()) {
            throw new GolfException("Non-existent IDs found during search: " + nonExistentIds);
        }
        return clientDTOList;
    }
    @Override
    @Transactional
    public List<ClientDTO> update(List<ClientDTO> clientDTOs) {
        List<ClientDTO> updateClients = findByIdList(clientDTOs.stream()
                .map(ClientDTO::getId)
                .collect(Collectors.toList()));
        List<Client> saveEntities = new ArrayList<>();
        for (ClientDTO clientDTO : clientDTOs) {
            boolean idExists = updateClients.stream()
                    .anyMatch(p -> p.getId().equals(clientDTO.getId()));
            if (idExists) {
                Client client = mapper.clientDTOToClient(clientDTO);
                saveEntities.add(repository.save(client));
            }
        }
        return mapper.clientToClientDTOList(saveEntities);
    }
    @Override
    @Transactional
    public List<ClientDTO> saveClient(List<ClientDTO> clientDTOsList) {
        List<Client> clientsList = mapper.clientDTOToClientList(clientDTOsList);
        repository.saveAllAndFlush(clientsList);
        return mapper.clientToClientDTOList(clientsList);
    }
    @Override
    @Transactional
    public void deleteClient(List<Long> ids) {
        try {
            for (Long id : ids) {
                repository.deleteById(id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new GolfException(ids);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }
}
