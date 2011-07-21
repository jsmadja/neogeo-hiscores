package com.anzymus.neogeo.hiscores.controller;

import com.anzymus.neogeo.hiscores.domain.Scores;
import com.anzymus.neogeo.hiscores.service.ScoreService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TimelineBean {

    @EJB ScoreService scoreService;

    public String getTimeline() {
        Scores scores = scoreService.findAll();
        
        return scores.toString();
    }
    
}