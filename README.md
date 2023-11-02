# Wonder
<p align="center"><img src="https://res.cloudinary.com/startup-grind/image/upload/c_fill,dpr_2,f_auto,g_center,q_auto:good/v1/gcs/platform-data-dsc/contentbuilder/GDG-Bevy-ChapterThumbnail.png" height="200px" width="200px"></p>

Project to participate in 2023 google solution challenge

## Top 10 finalist 
<img width="411" alt="badge" src="https://github.com/KUGODS-Wonder/Wonder-Backend/assets/83508073/665b99f3-b4d1-4703-91ee-9c34fd917895">

![스크린샷 2023-11-02 오후 5 30 27](https://github.com/KUGODS-Wonder/Wonder-API-Server/assets/83508073/f786b5de-1e1a-4d59-a98c-7aaa73f81c1f)


We made it!!

# Member
Chanho Park                     | Keo Kim    | Boyoung Kim | SeoKyung Baek |
|------------------------|------------|-------------|---------------|
| - Lead <br/> - Backend | - Frontend | - Frontend  | - AI          |

### Team Notion Link
[Team Notion](https://grateful-ermine-27f.notion.site/12-Wonder-6379a19d73f94ab29ba1b3424992b110)

# UN-SDGs that our solution solving for
## Goal 3. Good Health and Well-Being
<img src="https://user-images.githubusercontent.com/83508073/228183331-9a51e851-0ae2-474e-8511-6ae086b67a1d.png">


# About our solution
The lack of physical activity among modern people has been a serious problem in many nations. The wonder app tries to fix this problem with our unique approach to walking exercises.

We added some gamification feature to help users enjoy walking in their daily lives. The user is encouraged to walk on daily basis, through various motivations and game-like mechanics we provide.

In addition, we connect local volunteering organizations with users so that they can participate in various volunteer activities that involves some 'walking' in the progress. For example, there are volunteer activities to take a walk with dogs at a dog shelter or lunch box delivery services for the elderly living alone. This way, walking is not just a daily experience, but also a way to help others and contribute to society.

# App Demo

<figure class="third">

![myprofile](https://user-images.githubusercontent.com/83508073/228186679-5a72397b-2b11-4fcc-a433-f4f09133d66c.gif)
![map](https://user-images.githubusercontent.com/83508073/228186705-e6b85ba2-8c21-4ecf-a0da-2df0906322e5.gif)
![voluntary_work](https://user-images.githubusercontent.com/83508073/228186724-2b547cff-32a2-4dd2-bb73-bb65661ea250.gif)
<figure>

# About Implementation
## Backend
### 1. Tech Stack
- Java 11
- Spring, Spring boot
- Spring Web MVC, Spring Security
- Spring Data JPA, QueryDsl
- MySQL
- Docker, Docker-compose
- GCP

### 2. Architecture
![wonder server architecture](https://user-images.githubusercontent.com/83508073/223980536-cc1bd254-3910-43e4-a545-abeb4459b5b5.png)
- I deploy my Spring server application with Docker and Docker Compose.
- First, I create a Dockerfile to build an image of my application.
- Then, I build an image of my application and push to the DockerHub.
- I also create a docker-compose.yml file with information about my spring application from the hub and Nginx and certbot.
  [related issue](https://github.com/KUGODS-Wonder/Wonder-Backend/issues/8)
- Nginx was used to implement the reverse proxy, and certbot was used to use the https protocol.
- Finally, I can start my app with Docker compose by running a command like "docker-compose up". This starts containers for my app.


### 3. Api Docs
[Gitbook](https://cksgh1735.gitbook.io/wonder/)

### 4. ERD
![image](https://user-images.githubusercontent.com/83508073/228537441-d65cff0d-369f-4986-acd2-ecf30f97fce2.png)

