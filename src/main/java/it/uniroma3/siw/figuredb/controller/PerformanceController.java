package it.uniroma3.siw.figuredb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.figuredb.dto.FetchScenarioRisultato;
import it.uniroma3.siw.figuredb.service.FetchBenchmarkService;

@Controller
public class PerformanceController {

    @Autowired
    private FetchBenchmarkService fetchBenchmarkService;

    @GetMapping("/admin/performance")
    public String performance(Model model) {
        List<FetchScenarioRisultato> risultati = fetchBenchmarkService.eseguiConfronto();
        long maxTempoMs = risultati.stream().mapToLong(FetchScenarioRisultato::getTempoMs).max().orElse(1);

        model.addAttribute("risultati", risultati);
        model.addAttribute("numeroFigure", risultati.isEmpty() ? 0 : risultati.get(0).getNumeroFigure());
        model.addAttribute("numeroAziende", risultati.isEmpty() ? 0 : risultati.get(0).getNumeroAziende());
        model.addAttribute("maxTempoMs", Math.max(1, maxTempoMs));
        return "performance";
    }
}
