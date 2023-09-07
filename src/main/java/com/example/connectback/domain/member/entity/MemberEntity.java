package com.example.connectback.domain.member.entity;


import com.example.connectback.domain.jobs.entity.JobAnnouncement;
import com.example.connectback.domain.resume.entity.ResumeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NonNull
    @Column(nullable = false, length = 20)
    private String username;

    @NonNull
    @Column(nullable = false)
    private int age;

    @NonNull
    @Column(nullable = false, unique = true)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String encryptedPwd;

    @NonNull
    @Column(nullable = false,length = 200)
    private String addressInfo;

    @NonNull
    @Column(nullable = false)
    private String addressDetails;

    @NonNull
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Disability disabilityType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "member_wishlist",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "job_announcement_id")
    )
    private Set<JobAnnouncement> wishlist = new HashSet<>();

    @OneToOne(mappedBy = "member")
    private ResumeEntity resume;

    @Builder
    public MemberEntity(@NonNull String username, @NonNull int age, @NonNull String email, @NonNull String encryptedPwd, @NonNull String addressInfo, @NonNull String addressDetails, @NonNull Disability disabilityType) {
        this.username = username;
        this.age = age;
        this.email = email;
        this.encryptedPwd = encryptedPwd;
        this.addressInfo = addressInfo;
        this.addressDetails = addressDetails;
        this.disabilityType = disabilityType;
    }

    public void addToWishlist(JobAnnouncement jobAnnouncement) {
        wishlist.add(jobAnnouncement);
        jobAnnouncement.getMembers().add(this);
    }

    public void removeFromWishlist(JobAnnouncement jobAnnouncement) {
        wishlist.remove(jobAnnouncement);
        jobAnnouncement.getMembers().remove(this);
    }

    public void writeResume(ResumeEntity resume){
        resume.setMember(this);
    }
}
