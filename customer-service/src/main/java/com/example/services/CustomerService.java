package com.example.services;

import com.example.entities.CustomerEntity;
import com.example.models.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.InputStream;

public interface CustomerService {
    CustomerDto create(CustomerDto dto);

    Page<CustomerDto> findAll(Pageable pageable);

    CustomerDto update(CustomerDto dto);

    void delete(Long id);

    CustomerEntity getById(Long id);

    void upload(InputStream csvStream);
}
