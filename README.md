# Trip Plan Project [진행 중]
- 여행 플랜 관리 웹
- 여행 일정을 생성하고, 입력한 목적지를 기반으로 적절한 여행 동선 추천

💡 
1. Frontend by KIM: React
2. Backend by OH: Spring boot
3. Database: MySQL
4. API: Naver MAP API
5. IDE: IntelliJ, VSCode

### 상세 기능 설명

1. **회원 관련 기능**
    1. 회원 가입 및 탈퇴
        1. 이름, 아이디, 비밀번호
        2. 아이디 중복 확인
        3. 이메일 인증 - 중복 가입 방지
    2. 로그인 및 로그아웃
        1. 로그인 시 세션 유지
        2. 로그아웃 시 세션 삭제
    3. 회원 정보 수정
        1. 아이디, 이름 수정 불가
        2. 이메일, 비밀번호 수정 가능
        3. 이메일 변경 시 인증 필수
        4. 변경 전 비밀번호 입력으로 본인 인증
    4. (SNS 회원가입 및 로그인)
  
2. **여행 일정 관련 기능**
    1. 여행 일정 생성
        1. 여행 이름, 여행 날짜, 여행 목적지, 1일차~n일차 별 가고 싶은 목적지 추가
        2. 입력한 목적지 기반으로 적절한 여행 동선 추천
            ⇒ 거리 적은 순으로 추천
    2. 여행 일정 수정
        1. 여행 일정 생성 시 작성한 모든 것들 수정 가능
        2. 입력한 목적지 기반으로 적절한 여행 동선 추천
        3. 여행 일정 공유할 멤버 추가 및 삭제
            ⇒ 여행 대표자가 멤버의 권한을 부여할 수 있음(전체 권한, 읽기만 가능)     
    3. 여행 일정 관리
        1. 여행 일정 삭제 
        ⇒ 관리자가 일정 삭제 시 일정에 포함된 멤버의 일정에서도 연쇄 삭제가 되어야 함
    4. 각 여행지에 대한 후기 작성
        1. 이용 인증 후 후기 작성 (영수증 인증 혹은 이용 사진)
        

ERD(미완성): https://www.erdcloud.com/d/oDeFHEKHeYieQ2Mxa
![image](https://github.com/user-attachments/assets/7c36503a-204e-4eda-9325-c563ba54736f)
API 명세서(미완성)
https://drive.google.com/file/d/1zxGzOyzVSTWIOZZwXxDZJ8FlJ89dGd_f/view?usp=drive_link

