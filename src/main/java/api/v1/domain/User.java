package api.v1.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    /*
    Kakao API
     */
    @Column(name = "kakao_id",nullable = false, unique = true)
    private Long kakaoId;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "profile_img",nullable = false)
    private String profileImg;

    /*
    추가 회원가입 과정
     */
    @Column(name="nickname",nullable = false, unique = true)
    private String nickname;

    @Column(name = "birth_date",nullable = false)
    private LocalDate birthDate;

    /*
    security
     */
    @Column(nullable = false)
    private String role;

    @Builder
    private User(Long kakaoId,
                 String name,
                 String profileImg,
                 String nickname,
                 LocalDate birthDate,
                 String role
    ) {
        this.kakaoId=kakaoId;
        this.name = name;
        this.profileImg = profileImg;
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.role=role;
    }
}
