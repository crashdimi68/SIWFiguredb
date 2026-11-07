package it.uniroma3.siw.figuredb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.figuredb.model.Editore;
import it.uniroma3.siw.figuredb.service.EditoreService;
import it.uniroma3.siw.figuredb.service.RisorsaNonTrovataException;
import jakarta.validation.Valid;

@Controller
public class EditoreController {

    @Autowired
    private EditoreService editoreService;

    /** Scheda pubblica di un editore, con i fumetti che pubblica. */
    @GetMapping("/editori/{id}")
    public String show(@PathVariable Long id, Model model) {
        Editore editore = editoreService.findByIdWithFumetti(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("l'editore", id));
        model.addAttribute("editore", editore);
        return "showEditore";
    }

    @GetMapping("/admin/editori")
    public String list(Model model) {
        model.addAttribute("editori", editoreService.findAll());
        return "listEditori";
    }

    @GetMapping("/admin/editori/new")
    public String createForm(Model model) {
        model.addAttribute("editore", new Editore());
        return "formEditore";
    }

    @PostMapping("/admin/editori")
    public String newEditore(@Valid @ModelAttribute("editore") Editore editore,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formEditore";
        editoreService.save(editore);
        return "redirect:/admin/editori";
    }

    @GetMapping("/admin/editori/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Editore editore = editoreService.findById(id).orElse(null);
        if (editore == null)
            return "redirect:/admin/editori";
        model.addAttribute("editore", editore);
        return "formEditore";
    }

    @PostMapping("/admin/editori/{id}/edit")
    public String editEditore(@PathVariable Long id,
            @Valid @ModelAttribute("editore") Editore editore,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "formEditore";
        editore.setId(id);
        editoreService.save(editore);
        return "redirect:/editori/" + id;
    }

    @PostMapping("/admin/editori/{id}/delete")
    public String deleteEditore(@PathVariable Long id) {
        editoreService.delete(id);
        return "redirect:/admin/editori";
    }
}
