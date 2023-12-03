package com.example.services.impl;

import com.example.entities.CustomerEntity;
import com.example.models.CustomerDto;
import com.example.repos.CustomerRepository;
import com.example.services.CustomerMapper;
import com.example.services.CustomerService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper mapper;
    private final CustomerRepository repository;

    @Override
    public CustomerDto create(final CustomerDto dto) {

        final CustomerEntity entity = mapper.createNew(dto);
        final CustomerEntity saved = repository.save(entity);
        return mapper.mapToDto(saved);
    }

    @Override
    public Page<CustomerDto> findAll(Pageable pageable) {

        return repository.findAll(pageable)
                .map(mapper::mapToDto);
    }

    @Override
    @Transactional
    public CustomerDto update(final CustomerDto dto) {

        final CustomerEntity customer = getById(dto.getId());
        mapper.updateEntity(customer, dto);
        final CustomerEntity saved = repository.save(customer);
        return mapper.mapToDto(saved);
    }

    @Override
    public void delete(final Long id) {
        repository.deleteById(id);
    }

    @Override
    public CustomerEntity getById(final Long id) {

        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("customer with id {} not found", id);
                    final String message = String.format("customer with id %s not found", id);
                    return new EntityNotFoundException(message);
                });
    }

    @Override
    @Transactional
    public void upload(final InputStream csvStream) {

        final CsvToBean<CustomerDto> bean = new CsvToBeanBuilder<CustomerDto>(new InputStreamReader(csvStream))
                .withType(CustomerDto.class)
                .build();
        bean.forEach(customerDto -> {
            final CustomerEntity toSave = mapper.createNew(customerDto);
            repository.save(toSave);
        });
    }

}
