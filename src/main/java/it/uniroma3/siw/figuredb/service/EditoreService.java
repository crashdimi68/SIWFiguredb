package it.uniroma3.siw.figuredb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.figuredb.model.Editore;
import it.uniroma3.siw.figuredb.repository.EditoreRepository;

@Service
public class EditoreService {

    @Autowired
    private EditoreRepository editoreRepository;

    @Transactional(readOnly = true)
    public Optional<Editore> findById(Long id) {
        return editoreRepository.findById(id);
    }

    /** Dettaglio editore: fumetti pubblicati caricati con la stessa query (EntityGraph). */
    @Transactional(readOnly = true)
    public Optional<Editore> findByIdWithFumetti(Long id) {
        return editoreRepository.findWithFumettiById(id);
    }

    @Transactional(readOnly = true)
    public List<Editore> findAll() {
        return editoreRepository.findAllByOrderByNomeAsc();
    }

    @Transactional
    public void save(Editore editore) {
        editoreRepository.save(editore);
    }

    /** Non si puo' eliminare un editore che ha ancora fumetti pubblicati. */
    @Transactional
    public void delete(Long id) {
        Editore editore = editoreRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("l'editore", id));
        if (!editore.getFumetti().isEmpty()) {
            throw new OperazioneNonValidaException("Impossibile eliminare " + editore.getNome()
                    + ": ha ancora fumetti pubblicati.");
        }
        editoreRepository.delete(editore);
    }
}
