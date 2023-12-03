package com.example.controllers;

import com.example.entities.CustomerEntity;
import com.example.models.CustomerDto;
import com.example.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public String home(@RequestParam(required = false, defaultValue = "0") final Integer page,
                       @RequestParam(required = false, defaultValue = "5") final Integer size,
                       final Model model) {

        final PageRequest pageRequest = PageRequest.of(page, size)
                .withSort(Sort.Direction.ASC, "createAt", "name");
        final Page<CustomerDto> customers = service.findAll(pageRequest);
        model.addAttribute("customers", customers);
        return "home";
    }

    @GetMapping("/form/create")
    public String createForm(final Model model) {

        model.addAttribute("customer", new CustomerDto());
        return "create";
    }

    @GetMapping("/form/update/{id}")
    public String updateForm(@PathVariable final Long id,
                             final Model model) {

        final CustomerEntity customer = service.getById(id);
        model.addAttribute("customer", customer);
        return "update";
    }

    @GetMapping("/form/upload")
    public String updateForm() {
        return "upload";
    }

    @PutMapping
    public String create(@ModelAttribute final CustomerDto customer) {

        service.create(customer);
        return "redirect:/customer";
    }

    @PostMapping
    public String update(@ModelAttribute final CustomerDto customer,
                         final Model model) {

        final CustomerDto updated = service.update(customer);
        model.addAttribute("customer", updated);
        return "redirect:/customer/form/update/" + customer.getId();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable final Long id) {

        service.delete(id);
        return "redirect:/customer";
    }

    @PostMapping("/upload")
    public String upload(final MultipartFile file) throws IOException {

        service.upload(file.getInputStream());
        return "redirect:/customer";
    }
}
