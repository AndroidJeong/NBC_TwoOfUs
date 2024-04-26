<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/AndroidJeong/NBC_TwoOfUs/tree/master">
    <img src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/436ec8fb-999b-4b3f-92fd-af3ecc8023be" alt="Logo" width="400" height="400">
  </a>

<h3 align="center">Two of Us</h3>

  <p align="center">
    연락처를 관리하는 안드로이드 App입니다.
    <br />
      <a href="https://picayune-whimsey-cb6.notion.site/S-A-45646d1f43d04c0f8c27908712d67d7a"><strong>S.A »</strong></a>
    <br />
    <a href="https://youtu.be/hlVwDCYl1Lw">Video Demo</a>
  </p>
</div>

<!-- ABOUT THE PROJECT -->

## 기능 소개
### 메인화면

|연락처 권한|레이아웃 매니저 전환|연락처 추가|
|:-----:|:-----:|:-----:|
|<img width="200" src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/8f1e0d23-ba60-40d6-9eed-ffdb170b9597">|<img width="200" src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/aeaa2242-1919-47d2-b246-e5203412d4a6"> |<img width="200" src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/23d39a70-df38-4c8c-a5c7-e6c8a704f00e"> |<img width="200" src=""> |

|내 정보|화면 회전 시 정보 유지|
|:-----:|:-----:|
|<img width="200" src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/4b582d39-2d9e-420b-8efc-c60f5adebe15">|<img width="200" src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/50de4018-4d09-4b53-8ec7-26afbd6d9ef9"> |

### 상세화면
|편집|삭제|전화, 문자, 이메일|
|:-----:|:-----:|:-----:|
|<img width="200" src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/c3db8df0-a41c-4837-8c9a-ef1168037018">|<img width="200" src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/883098de-e6c0-482b-909c-9bfa2fd146c8"> |<img width="200" src="https://github.com/AndroidJeong/team2_contactapp/assets/48354989/bf02a07c-2f58-4eec-80c8-b46f9a27c736"> |

## 프로젝트 와이어프레임

![image](https://github.com/AndroidJeong/team2_contactapp/assets/128209823/5edbb3ab-fb95-4d6a-bda8-6ac41fbf621b)

## 프로젝트 Screen Flow

![image](https://github.com/AndroidJeong/team2_contactapp/assets/128209823/9aa37cb6-6988-4545-8f0a-34062cac54da)


## 사용 기술 소개

- [ ]  TabLayout, ViewPager2
- [ ]  ViewModel
- [ ]  RecyclerView, ItemViewType
- [ ]  Fragment & DialogFragment
- [ ]  ContentResolver
- [ ]  Parcelize
- [ ] CollapsingToolbarLayout


## 트러블 슈팅

#### [기능] Detail에서 list로 왔을 때 좋아요 아이콘 처리 안됨
- 원인 : DetailFragment에서 데이터에 수정을 가하고 onDestroy되었을 때 ListFragment에서 알아차리고 뷰를 다시 그리지 못 하고 있음
- 해결 과정 : 데이터의 변화를 adapter에 알려줘야 하는데..
  1안) bundle에 데이터를 담아서 Detail -> List 로 보내주자!
  2안) Observer 역할을 하는 interface를 만들어서 데이터를 공유하도록 하자!
  3안) LiveData를 사용하자!
  결국 ObservingManader object 안에 LiveData를 정의해두고 데이터의 변화를 관리할 수 있도록 개선하였으나 정보은닉이 보장되지 않는 방법이므로 ViewModel과 LiveData를 사용하여 해결

#### [기능] DetailFragment에서 화면 회전 시 ListFragment로 이동하는 건
- 원인 : FragmentContainerView를 배치 하지 않고 FragmentManager를  이용하여 화면 회전 후 복원되는 과정에서 문제가 발생되는 것으로 추정됨
- 해결 과정 : 문제가 어떤 조건에서 발생하는지 확인(좋아요 누르고 회전, 누르지 않고 회전 등) parentFragmentManager를 이용하여  beginTransaction() 하는 부분을 디버깅하여  어떤 view와 연결되어 있는지 확인 >FragmentContainerView와 관련 있는 오류임을 확인함. ViewPager를 Activity에서 Fragment로 옮기고 Activity안에 FragmentContatinerView를 배치하여 사용하도록 개선

#### [기능] Activity Result를 Lifecycle - Resumed 전에 register 완료하지 않았다는 오류발생
- 원인 : Fragment에서 PermissionManager 사용 시  RESUMED 상태에서 register를 호출하여  런타임 exception 발생하는 게 문제
- 해결 과정 : permission 처리하는 부분을 activity에  등록해야할 때 RESUMED 이전 상태에서 등록

#### [기능] DetailFragment에서 뒤로가기 클릭 시 앱 꺼짐
- 원인 : ViewPagerFragment에서 onCreateView에 setViewPager(adapter 설정)에 문제가 있는 것으로 추정됨
- 안드로이드 bug로 fragment를 파괴할 때 adapter에 대한 해제 처리가 정상적으로 되고 있지 않다는 것을 확인함. ViewPager adapter에 대해서는  onDestroyView()에서 adapter를 해제(null)할 수 있도록 수동으로 코드를 작성하여 해결

#### [UI] popup menu가 하단에 뜸
- 원인 : PopupMenu의 anchor를 view로 잡고 있었음
- 해결 과정 : anchor를 메뉴 버튼으로 변경하여 해결

#### [UI] grid layout 으로 변경 시 깨짐
- 원인 : grid layout으로 변경 했을 때 ViewType을 별도의 xml로 지정해주지 않았음
- 해결 과정 별도의 xml을 생성하여 적용

#### [기능] 화면 회전 시 grid layout이 정상적으로 보이지 않음
- 원인 : 화면이 회전하고 fragment가 다시 그려지면서 기본 값(Linear)으로 처리되고 있는 게 문제
- 해결 과정 : onSaveInstanceState, onViewStateRestored callback 함수를 활용하여 직전에 설정된 layout 값을 기억하고 그릴 수 있도록 수정하여 해결

#### [기능] My page 누르면 다른 사람 정보가 출력됨
- 원인 : 연락처를 수정한 후 My page로 이동할 경우, 직전에 사용되던 fragment가 여전히 살아있어 직전 수정한 연락처에 대한 정보를 보여주고 있었음
- 해결 과정 : detail 화면과 home 화면을 구분하도록 변수를 추가하고, home 화면에서 detail fragment로 넘어갈 때 bundle로 데이터를 넘겨 받아 정확한 정보를 출력하도록 수정

#### [기능] 연락처 추가 시 이미지 반영이 안 되고 있음
- 원인 : 연락처 추가 시 이미지를 추가하지 않고 있었음
- 해결 과정 : 연락처 추가 이벤트 발생 시 앨범에서 선택된 이미지가 데이터에 추가되고 화면 상에 보이도록 수정

#### [UI] 연락처 목록의 팝업 메뉴 색상 변경 필요
- 원인 : 연락처 목록의 popupMenu 색상이 Theme에 있는  기본 값으로 처리되고 있는 것
- 해결 과정 : Theme에서 item background 색상을 변경하여 해결

#### [기능] list에서 나를 눌러서 들어갔을 때 좋아요를 누르고 화면 회전하면 좋아요 아이콘 반영이 안됨
- 원인 : fragment가 다시 생성되면서 이전 값을 가져오는 게 문제
- 해결 과정 : 처음에는 회전 하기 전의 상태를 viewModel에 저장해두고 불러와서 사용하는 것으로 했으나, 생명주기에 맞춰 개발하는 것이 더 효과적이라는 판단하에 onSaveInstanceState와 onViewStateRestored 사용하여 해결

#### [기능] 편집했을 때 이메일 변경이 안됨
- 원인 : email을 편집했을 때 UI 상에 변경된 데이터를 보여주지 않고 있음
- 해결 과정 : email 변경 사항 적용해주는 코드 추가하여 해결

#### [기능] 내 정보 편집 후 화면 회전했을 때 나에 대한 좋아요 변경 사항이 화면에 반영 안됨
- 원인 : 여러 화면에서 LiveData를 observe하고 있고, 편집했을 때 LiveData의 observe가 적절하게 이뤄지고 있는 것 같지 않음
- 해결 과정 : Event class를 생성하여 observing이 중복되지 않도록 수정하고, LiveDat를 두 곳에서 동시에 접근하는 경우 처리를 정확히 하기 위해 synchronized를 적용하여 해결
