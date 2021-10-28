package com.karrotmvp.ourapt.admin;

import com.karrotmvp.ourapt.v1.article.question.QuestionService;
import com.karrotmvp.ourapt.v1.article.question.dto.QuestionDto;
import com.karrotmvp.ourapt.v1.common.Static;
import com.karrotmvp.ourapt.v1.preopen.PreopenRepository;
import com.karrotmvp.ourapt.v1.preopen.entity.PreopenForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PreopenRepository preopenRepository;


    @GetMapping("")
    public String renderIndex() {
        return "index";
    }

    @GetMapping("/questions")
    public String renderQuestionsPage(
            Model model,
            @RequestParam(name = "perPage") int perPage,
            @RequestParam(name = "pageNum") int pageNum
    ) {
        if (pageNum < 1) {
            throw new RuntimeException("pageNum must larger than 0");
        }
        List<QuestionDto> questions = new ArrayList<>(this.questionService.getQuestionsWithOffsetPaging(perPage, pageNum - 1));
        long countOfAll = this.questionService.getCountOfAllQuestions();
        long countOfTextNotEmpty = this.questionService.getCountOfNotEmptyQuestions();
        model.addAttribute("questions", questions);
        model.addAttribute("perPage", perPage);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("isLastPage", questions.size() < perPage);
        model.addAttribute("countOfAll", countOfAll);
        model.addAttribute("countOfTextNotEmpty", countOfTextNotEmpty);
        return "questions";
    }

    @GetMapping("/preopen")
    public String renderPreopenIndex(Model model) {

        // Count of preopenData in each region
        Map<String, Integer> preopenCountMap = new HashMap<String, Integer>();
        Static.regionDict.forEach((key, value) -> preopenCountMap.put(key, 0));
        List<PreopenForm> preopenData = this.preopenRepository.findAll();
        preopenData.forEach((pd) -> {
            if (preopenCountMap.containsKey(pd.getRegionId())) {
                preopenCountMap.replace(pd.getRegionId(), preopenCountMap.get(pd.getRegionId()) + 1);
            }
        });


        // Count of preopenData
        model.addAttribute("countOfAll", preopenData.size());

        // Service area
        // apartmentEntry = [0]: regionId, [1]: regionName, [2]: countOfPreopenData
        List<List<String>> apartmentEntries = Static.regionDict.entrySet()
                .stream()
                .map(entry -> new ArrayList<>(Arrays.asList(entry.getKey(), entry.getValue())))
                .peek(entryAsList -> entryAsList.add(preopenCountMap.get(entryAsList.get(0)) + ""))
                .sorted(Comparator.comparing(entryA -> entryA.get(1)))
                .collect(Collectors.toList());
        model.addAttribute("apartments", apartmentEntries);
        return "preopen";
    }
}
