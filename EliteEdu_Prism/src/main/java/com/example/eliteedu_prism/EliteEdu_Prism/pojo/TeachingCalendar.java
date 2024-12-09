package com.example.eliteedu_prism.EliteEdu_Prism.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeachingCalendar {

    private int calendarId;
    private int courseId;

    private String eventDate;

    private String eventDescription;

}
