package com.healthbox.hms_backend.modules.charges;

import com.healthbox.hms_backend.security.principal.CurrentUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargeItemService {
    private final ChargeItemRepository repo;
    private final CurrentUser currentUser;

    public ChargeItemService(ChargeItemRepository repo, CurrentUser currentUser) {
        this.repo = repo;
        this.currentUser = currentUser;
    }

    public ChargeItem create(ChargeItem item) {
        item.setHospitalId(currentUser.get().getHospitalId());
        return repo.save(item);
    }

    public List<ChargeItem> getActive() {
        return repo.findByHospitalIdAndActiveTrue(currentUser.get().getHospitalId());
    }

    public List<ChargeItem> getAll() {
        return repo.findByHospitalId(currentUser.get().getHospitalId());
    }

    public void deactivate(Long id) {
        ChargeItem item = repo.findById(id).orElseThrow(() -> new RuntimeException("Charge item not found"));
        if (!item.getHospitalId().equals(currentUser.get().getHospitalId())) {
            throw new AccessDeniedException("Cross-tenant access denied");
        }
        item.setActive(false);
        repo.save(item);
    }
}
