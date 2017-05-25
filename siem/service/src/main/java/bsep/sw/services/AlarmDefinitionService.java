package bsep.sw.services;

import bsep.sw.domain.AlarmDefinition;
import bsep.sw.repositories.AlarmDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AlarmDefinitionService {

    private final AlarmDefinitionRepository repository;

    @Autowired
    public AlarmDefinitionService(final AlarmDefinitionRepository repository) {
        this.repository = repository;
    }

    public AlarmDefinition save(AlarmDefinition alarmDefinition) {
        return repository.save(alarmDefinition);
    }

    @Transactional(readOnly = true)
    public Page<AlarmDefinition> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public AlarmDefinition findOne(Long id) {
        return repository.findOne(id);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}