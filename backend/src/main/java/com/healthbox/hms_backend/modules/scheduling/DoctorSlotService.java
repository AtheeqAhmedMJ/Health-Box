package com.healthbox.hms_backend.modules.scheduling;

import com.healthbox.hms_backend.modules.auth.Role;
import com.healthbox.hms_backend.security.principal.AppUserPrincipal;
import com.healthbox.hms_backend.security.principal.CurrentUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorSlotService {

    private final DoctorSlotRepository repo;
    private final CurrentUser currentUser;

    public DoctorSlotService(DoctorSlotRepository repo, CurrentUser currentUser) {
        this.repo = repo;
        this.currentUser = currentUser;
    }

    public DoctorSlot create(DoctorSlot s) {
        AppUserPrincipal me = currentUser.get();
        if (me.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only the doctor manages their own schedule");
        }
        s.setHospitalId(me.getHospitalId());
        s.setDoctorPhno(me.getPhno());
        return repo.save(s);
    }

    public List<DoctorSlot> getForDoctor(String doctorPhno) {
        return repo.findByHospitalIdAndDoctorPhno(currentUser.get().getHospitalId(), doctorPhno);
    }

    public List<DoctorSlot> getAll() {
        return repo.findByHospitalId(currentUser.get().getHospitalId());
    }

    public void delete(Long id) {
        DoctorSlot s = repo.findById(id).orElseThrow(() -> new RuntimeException("Slot not found"));
        AppUserPrincipal me = currentUser.get();
        if (!s.getHospitalId().equals(me.getHospitalId())) throw new AccessDeniedException("Cross-tenant access denied");
        if (me.getRole() == Role.ADMIN && !me.getPhno().equals(s.getDoctorPhno())) throw new AccessDeniedException("Not your slot");
        repo.deleteById(id);
    }
}
