package com.fisi.sgapcbackend.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fisi.sgapcbackend.dto.CustomerDTO;
import com.fisi.sgapcbackend.entities.Customer;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.ICustomerRepository;
import com.fisi.sgapcbackend.response.CustomerResponse;
import com.fisi.sgapcbackend.services.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ICustomerRepository repo;

	@Override
	@Transactional(readOnly = true)
	public CustomerDTO findById(Long id) {
		Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
		return mapToDTO(customer);
	}

	@Override
	@Transactional
	public CustomerDTO save(CustomerDTO customerDTO) {
		Customer customer = mapToEntity(customerDTO);
		Customer newCustomer = repo.save(customer);
		CustomerDTO customerResponse = mapToDTO(newCustomer);
		return customerResponse;
	}

	@Override
	public void deleteById(Long id) {
		Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
		repo.delete(customer);
	}

	// Convierte entidad a DTO
	private CustomerDTO mapToDTO(Customer customer) {
		CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
		return customerDTO;
	}

	// Convierte de DTO a Entidad
	private Customer mapToEntity(CustomerDTO customerDTO) {
		Customer customer = modelMapper.map(customerDTO, Customer.class);
		return customer;
	}

	@Override
	public CustomerDTO update(CustomerDTO t, Long id) {
		Customer customer = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));

		customer.setFirstname(t.getFirstname());
		customer.setLastname(t.getLastname());
		customer.setAddress(t.getAddress());
		customer.setDni(t.getDni());
		customer.setEmail(t.getEmail());
		customer.setRuc(t.getRuc());
		customer.setTelephone(t.getTelephone());
		customer.setUpdateAt(t.getUpdateAt());
		customer.setCreateAt(t.getCreateAt());
		Customer updateCustomer = repo.save(customer);
		return mapToDTO(updateCustomer);
	}

	@Override
	public CustomerResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<Customer> customers = repo.findAll(pageable);

		List<Customer> listOfCustomers = customers.getContent();
		List<CustomerDTO> content = listOfCustomers.stream().map(category -> mapToDTO(category)).collect(Collectors.toList());
		
		CustomerResponse customerResponse = new CustomerResponse();
		customerResponse.setContent(content);
		customerResponse.setNumberOfPage(customers.getNumber());
		customerResponse.setSizeOfPage(customers.getSize());
		customerResponse.setTotalElements(customers.getTotalElements());
		customerResponse.setTotalPages(customers.getTotalPages());
		customerResponse.setFin(customers.isLast());
		
		return customerResponse;
	}

	@Override
	public List<CustomerDTO> findCustomerByFullname(String firstname, String lastname) {
		List<Customer> customers = repo.findByFirstnameAndLastnameContainingIgnoreCase(firstname, lastname);
		return customers.stream().map(customer -> mapToDTO(customer)).collect(Collectors.toList());
	}

	@Override
	public List<CustomerDTO> findCustomerByDni(String term) {
		List<Customer> customers = repo.findByDniContainingIgnoreCase(term);
		return customers.stream().map(customer -> mapToDTO(customer)).collect(Collectors.toList());
	}

}
