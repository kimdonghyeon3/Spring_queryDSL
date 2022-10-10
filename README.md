<div id="top"></div>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->


<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">Query Dsl 연습</h3>

  <p align="center">
    Query DSL 연습 레퍼지토리
    <br />
  </p>
</div>

<hr/>

<!-- ABOUT THE PROJECT -->

[//]: # (## About The Project)

[//]: # (![img]&#40;./demo.png&#41;)



[//]: # (<p align="right">&#40;<a href="#top">back to top</a>&#41;</p>)

## Query DSL

JPA를 학습해 보면, 복잡한 쿼리의 경우 로직을 구현하지 못하던가, 또 다른 2중 쿼리를 작성해야한다.

또한, 메서드명으로 판단하기에 가독성이 떨어지기도 한다. 이러한 문제를 해결하기 위해 Query DSL을 활용해 해결해볼 수 있는데 이러한 경우를 미리 연습해보자.

 JPQL과 비슷하게 동작하지만, 가장 큰 차이점은 오류를 잡아내는 시점이다. 쿼리를 자바 코드르 작성하기에
문법오류를 컴파일 시점에서 잡아낼 수 있다. 앞서 설명한 것 처럼 JPA로 구현하기 어렵거나 불가능한 쿼리를 작성하기 위해
만들어진 기술이다.

 기본적으로 Custom 인터페이스를 정의해서 JPA와 비슷하게 구성한다. 이때 Custom 인터페이스를
구현하는 Class를 만들어 정확한 Query를 작성해 주면 된다. 기본적인 예시 코드는 아래를 참고하면 된다.

```aidl
public interface SiteUserRepository extends JpaRepository<SiteUser, Long>, SiteUserRepositoryCustom{
}
```
```aidl
@RequiredArgsConstructor
public class SiteUserRepositoryImpl implements SiteUserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public SiteUser getQslUser(Long id) {
        return jpaQueryFactory
                .select(siteUser)
                .from(siteUser)
                .where(siteUser.id.eq(id))
                .fetchOne();
    }
}
```
```aidl
public interface SiteUserRepositoryCustom {
    SiteUser getQslUser(Long id);
}
```

가장 위에 코드는 기본적인 JPA Repository이며, 앞서 설명한 Custom 인터페이스는 3번째, Custom 인터페이스를 구현한 Class는 2번째 코드가 된다.
해당 코드는 아래 쿼리처럼 동작하게 된다.

```aidl
SELECT *
FROM site_user
WHERE id = parameter
```
(여기서 parameter은 인자로 들어온 Long id를 의미한다.)

여러 쿼리문을 작성해보면서, Query DSL을 학습해보자

[//]: # (<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB"> <img src="https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=JSON&logoColor=61DAFB">)

[//]: # (<p align="right">&#40;<a href="#top">back to top</a>&#41;</p>)

<!-- GETTING STARTED -->
## Getting Started



### 설치 방법

1. Clone the repo
   ```sh
   git clone https://github.com/kimdonghyeon3/Spring_queryDSL
   ```

2. Gradle update
   ```sh
   Gradle -> Tasks -> other -> compileQuertdsl (double click)
   ```
3. Run Start
   ```sh
   start
      ```
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->

[//]: # (## Usage)

[//]: # ()
[//]: # (좌, 우, 랜덤 버튼을 통해 명언 보기)

[//]: # ()
[//]: # ()
[//]: # (<p align="right">&#40;<a href="#top">back to top</a>&#41;</p>)

<!-- DEMO EXAMPLES -->

[//]: # (## Demo)

[//]: # (Demo Link : https://wisesayingofthetoday.netlify.app)

[//]: # ()
[//]: # (![img]&#40;./demo1.png&#41;)

[//]: # (![img]&#40;./demo2.png&#41;)


[//]: # (<p align="right">&#40;<a href="#top">back to top</a>&#41;</p>)

<!-- CONTACT -->
## Contact

Email - kimdonghyeon98@gmail.com

Project Link: [https://github.com/kimdonghyeon3/Spring_queryDSL](https://github.com/kimdonghyeon3/Spring_queryDSL)

<p align="right">(<a href="#top">back to top</a>)</p>


MIT License
Copyright (c) 2021 Othneil Drew

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo_name.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo_name/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo_name.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo_name/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo_name.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo_name/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo_name.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo_name/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo_name.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo_name/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/linkedin_username
[product-screenshot]: images/screenshot.png
