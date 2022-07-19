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

import com.fisi.sgapcbackend.dto.WithdrawalDTO;
import com.fisi.sgapcbackend.entities.Customer;
import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.entities.Withdrawal;
import com.fisi.sgapcbackend.exceptions.ResourceNotFoundException;
import com.fisi.sgapcbackend.repositories.ICustomerRepository;
import com.fisi.sgapcbackend.repositories.IUserRepository;
import com.fisi.sgapcbackend.repositories.IWithdrawalRepository;
import com.fisi.sgapcbackend.response.WithdrawalResponse;
import com.fisi.sgapcbackend.services.IWithdrawalService;

@Service
public class WithdrawalServiceImpl implements IWithdrawalService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IWithdrawalRepository repo;
	
	@Autowired
	private ICustomerRepository customerRepository;
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	@Transactional
	public WithdrawalDTO save(WithdrawalDTO withdrawalDTO) {
		Withdrawal withdrawal = mapToEntity(withdrawalDTO);
		Withdrawal newWithdrawal = repo.save(withdrawal);
		WithdrawalDTO withdrawalResponse = mapToDTO(newWithdrawal);
		return withdrawalResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public WithdrawalDTO findById(Long id) {
		Withdrawal withdrawal = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Salida de Productos", "id", id));
		return mapToDTO(withdrawal);
	}

	@Override
	public void deleteById(Long id) {
		Withdrawal withdrawal = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Salida de Productos", "id", id));
		
		if(withdrawal.getCustomer() == null && withdrawal.getUser() == null) {
			repo.delete(withdrawal);
		}
		
		if(withdrawal.getCustomer()==null) {
			User user = userRepository.findById(withdrawal.getCustomer().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
			repo.delete(withdrawal);
			userRepository.save(user);
		}
		
		if(withdrawal.getUser()==null) {
			Customer customer = customerRepository.findById(withdrawal.getUser().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
			repo.delete(withdrawal);
			customerRepository.save(customer);
		}
		
		if(withdrawal.getCustomer()!=null && withdrawal.getUser()!=null) {
			User user = userRepository.findById(withdrawal.getCustomer().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
			Customer customer = customerRepository.findById(withdrawal.getUser().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
			repo.delete(withdrawal);
			customerRepository.save(customer);
			userRepository.save(user);
		}
	}

	private WithdrawalDTO mapToDTO(Withdrawal withdrawal) {
		WithdrawalDTO withdrawalDTO = modelMapper.map(withdrawal, WithdrawalDTO.class);
		return withdrawalDTO;
	}

	// Convierte de DTO a Entidad
	private Withdrawal mapToEntity(WithdrawalDTO withdrawalDTO) {
		Withdrawal withdrawal = modelMapper.map(withdrawalDTO, Withdrawal.class);
		return withdrawal;
	}

	@Override
	@Transactional
	public WithdrawalDTO update(WithdrawalDTO t, Long id) {
		Withdrawal withdrawal = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Salida de Productos", "id", id));

		withdrawal.setCustomer(t.getCustomer());
		withdrawal.setDate_withdrawal(t.getDate_withdrawal());
		withdrawal.setIgv(t.getIgv());
		withdrawal.setTotal(t.getTotal());
		withdrawal.setUser(t.getUser());
		withdrawal.setWithdrawalDetails(t.getWithdrawalDetails());
		Withdrawal updateWithdrawal = repo.save(withdrawal);
		return mapToDTO(updateWithdrawal);
	}

	@Override
    @Transactional(readOnly=true)
	public WithdrawalResponse getAll(int numberOfPage, int sizeOfPage, String sortPeer, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortPeer).ascending()
				: Sort.by(sortPeer).descending();
		Pageable pageable = PageRequest.of(numberOfPage, sizeOfPage, sort);

		Page<Withdrawal> withdrawals = repo.findAll(pageable);

		List<Withdrawal> listOfWithdrawals = withdrawals.getContent();
		List<WithdrawalDTO> content = listOfWithdrawals.stream().map(withdrawal -> mapToDTO(withdrawal)).collect(Collectors.toList());
		
		WithdrawalResponse withdrawalResponse = new WithdrawalResponse();
		withdrawalResponse.setContent(content);
		withdrawalResponse.setNumberOfPage(withdrawals.getNumber());
		withdrawalResponse.setSizeOfPage(withdrawals.getSize());
		withdrawalResponse.setTotalElements(withdrawals.getTotalElements());
		withdrawalResponse.setTotalPages(withdrawals.getTotalPages());
		withdrawalResponse.setFin(withdrawals.isLast());
		
		return withdrawalResponse;
	}

}
