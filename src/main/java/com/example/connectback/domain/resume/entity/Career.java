package com.example.connectback.domain.resume.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "careers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "career_id")
    private Long id;
    private int period;
    @Enumerated(value = EnumType.STRING)
    private JobCategory category;

    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private ResumeEntity resume;

    @Builder
    public Career(int period, JobCategory category) {
        this.period = period;
        this.category = category;
    }

    public void setResume(ResumeEntity resume){
        this.resume = resume;
    }
}
