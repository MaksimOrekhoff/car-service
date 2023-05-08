package ru.rikmasters.owner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rikmasters.car.CarClient;
import ru.rikmasters.exception.NotFoundException;
import ru.rikmasters.owner.dto.OwnerCreateDTO;
import ru.rikmasters.owner.dto.OwnerDTO;
import ru.rikmasters.utils.MyPageRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    private final CarClient client;

    @Override
    public OwnerCreateDTO create(OwnerCreateDTO ownerCreateDTO) {
        Owner owner = ownerRepository.save(ownerMapper.toOwnerCreate(ownerCreateDTO));
        log.debug("Добавлен втовладелец {}", owner);
        return ownerMapper.toOwnerCreateDTO(owner);
    }

    @Override
    public void setNewOwner(Long ownerId, Long carId) {
        validOwnerCar(carId);
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой автовладелец не существует."));
        owner.setCar(carId);
        ownerRepository.save(owner);
        log.debug("Автовладелец {} зарегистировал автомобиль {}", owner, carId);
    }

    private void validOwnerCar(Long carId) {
        List<Owner> owners = ownerRepository.findAll().stream().filter(owner -> {
            if (owner.getCar() != null) {
                return owner.getCar().equals(carId);
            }
            return false;
        }).toList();
        if (!owners.isEmpty()) {
            Owner oldOwner = owners.get(0);
            oldOwner.setCar(null);
            ownerRepository.save(oldOwner);
        }
    }

    @Override
    public OwnerDTO getOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой автовладелец не существует."));
        log.debug("Получен автовладелец {} ", owner);
        return ownerMapper.toOwnerDTO(owner);
    }

    @Override
    public void removeOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой автовладелец не существует."));
        if (owner.getCar() != null) {
            client.removeOwnerCar(owner.getCar());
        }
        ownerRepository.deleteById(ownerId);
        log.debug("Удален автовладелец {} ", owner);
    }

    @Override
    public OwnerDTO patchOwner(OwnerDTO ownerDTO, Long ownerId) {
        Owner oldOwner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой автовладелец не существует."));
        if (ownerDTO.getCar() != null) {
            validOwnerCar(ownerDTO.getCar());
        }
        Owner owner = ownerMapper.toPatchOwner(ownerDTO, oldOwner);
        Owner newOwner = ownerRepository.save(owner);
        if (ownerDTO.getCar() != null) {
            client.changeCar(ownerId, ownerDTO.getCar());
        }
        log.debug("Изменены данные автовладелеца {} ", newOwner);
        return ownerMapper.toOwnerDTO(newOwner);
    }

    @Override
    public List<OwnerDTO> getAllOwnersAndSort(Integer from, Integer size) {
        MyPageRequest myPageRequest = new MyPageRequest(from, size, Sort.by("passport"));
        Page<Owner> owners = ownerRepository.findAll(myPageRequest);
        log.debug("Получены все автовладельцы: {} и отсортированы по паспорту", owners);
        return owners.stream()
                .map(ownerMapper::toOwnerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OwnerDTO> searchOwners(String text, Integer from, Integer size) {
        MyPageRequest myPageRequest = new MyPageRequest(from, size, Sort.by("passport"));
        List<Owner> owners = ownerRepository.searchOwners(text, myPageRequest);
        log.debug("Получены все автовладельцы: {} и отсортированы по паспорту", owners);
        return owners.stream()
                .map(ownerMapper::toOwnerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAuto(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой автовладелец не существует."));
        owner.setCar(null);
        ownerRepository.save(owner);
        log.debug("Автовладелецу: {} больше не принадлежит автомобиль", owner);
    }

    @Override
    public void setOwner(Long ownerId, Long carId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Такой автовладелец не существует."));
        validOwnerCar(carId);
        owner.setCar(carId);
        ownerRepository.save(owner);
        log.debug("Автовладелецу: {} теперь принадлежит автомобиль {}", owner, carId);
    }


}
