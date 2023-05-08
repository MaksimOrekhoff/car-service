package ru.rikmasters.owner;

import ru.rikmasters.owner.dto.OwnerCreateDTO;
import ru.rikmasters.owner.dto.OwnerDTO;

import java.util.List;

public interface OwnerService {
    OwnerCreateDTO create(OwnerCreateDTO owner);

    void setNewOwner(Long ownerId, Long carId);

    OwnerDTO getOwner(Long ownerId);

    void removeOwner(Long ownerId);

    OwnerDTO patchOwner(OwnerDTO ownerDTO, Long ownerId);

    List<OwnerDTO> getAllOwnersAndSort(Integer from, Integer size);

    List<OwnerDTO> searchOwners(String text, Integer from, Integer size);

    void deleteAuto(Long ownerId);

    void setOwner(Long ownerId, Long carId);
}
