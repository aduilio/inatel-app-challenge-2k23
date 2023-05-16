package com.aduilio.appchallenge.netcap.controller;

import com.aduilio.appchallenge.netcap.entity.Settings;
import com.aduilio.appchallenge.netcap.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Receives the requests to provide the settings values.
 */
@Controller
@RequestMapping("settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    public String consumption(Model model) {
        model.addAttribute("settings", settingsService.getSettings());

        return "settings";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute("settings") Settings settings) {
        settingsService.saveSettings(settings);

        return new ModelAndView("redirect:/");
    }
}
