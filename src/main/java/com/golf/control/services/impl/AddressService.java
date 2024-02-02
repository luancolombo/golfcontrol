package com.golf.control.services.impl;

import com.golf.control.dtos.AddressDTO;
import com.golf.control.exception.DataBaseException;
import com.golf.control.exception.GolfException;
import com.golf.control.mapper.AddressMapper;
import com.golf.control.models.Address;
import com.golf.control.repositories.AddressRepository;
import com.golf.control.services.IAddressService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService implements IAddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper;

    public AddressService(AddressRepository repository, AddressMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AddressDTO> findByIdList(List<Long> ids) {
        List<Address> addressList = new ArrayList<>();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            addressList = repository.findAll();
        } else {
            addressList = repository.findAllById(ids);
        }
        if (!addressList.isEmpty()) {
            addressDTOList = mapper.addressToAddressDTOList(addressList);
        }
        List<Address> finalLists = addressList;
        List<Long> nonExistentIds = ids.stream()
                .filter(id -> finalLists.stream().noneMatch(p -> {
                    return p.getId().equals(id);
                }))
                .collect(Collectors.toList());
        if (!nonExistentIds.isEmpty()) {
            throw new GolfException("Non-existent IDs found during search: " + nonExistentIds);
        }
        return addressDTOList;

    }
    @Override
    @Transactional
    public List<AddressDTO> update(List<AddressDTO> addressDTOS) {
        List<AddressDTO> updateAddress = findByIdList(addressDTOS.stream()
                .map(AddressDTO::getId)
                .collect(Collectors.toList()));
        List<Address> saveEntities = new ArrayList<>();
        for (AddressDTO addressDTO : addressDTOS) {
            boolean idExists = updateAddress.stream()
                    .anyMatch(p -> p.getId().equals(addressDTO.getId()));
            if (idExists) {
                Address address = mapper.addressDTOToAddress(addressDTO);
                saveEntities.add(repository.save(address));
            }
        }
        return mapper.addressToAddressDTOList(saveEntities);
    }
    @Override
    @Transactional
    public List<AddressDTO> saveClient(List<AddressDTO> addressDTOList) {
        List<Address> addressList = mapper.addressDTOToAddressList(addressDTOList);
        repository.saveAllAndFlush(addressList);
        return mapper.addressToAddressDTOList(addressList);
    }
    @Override
    @Transactional
    public void deleteAddress(List<Long> ids) {
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
