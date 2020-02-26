
<img src="https://github.com/Nexters/Yetda_Android/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png?raw=true" width="20%">

# Yetda (옛다)

[![Platform](https://img.shields.io/badge/platform-Android-green.svg) ]()
[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![Language](https://img.shields.io/github/languages/top/Nexters/Yetda_Android)]()
[![GooglePlay](https://img.shields.io/badge/google_play-Download-red?logo=google-play&logoColor=white)](https://play.google.com/store/apps/details?id=com.nexters.yetda.android)


> 매번 친구들 생일선물 챙기기, 선물고르기 너무 어렵지 않나요?

> 옛다와 함께 선물고민 날려버려요!

## :baby: Code Convention

- 변수명은 camelCase로 작성한다.
- 시작 중괄호는 "{" 따로 한줄을 차지하지 않는다.
- 축약어는 단어처럼 취급할 것.
- 로깅(Log)은 간결하게.

## :fire: Firebase products

별도의 서버 구축 없이 서비스를 제공하기 위해서 파이어베이스의 제품들을 사용하였다.

- [Cloud Firestore]([https://firebase.google.com/products/firestore?hl=ko](https://firebase.google.com/products/firestore?hl=ko))

-- Firestore에 세팅된 최신의 데이터를 앱 DB에 저장한다.

- [Cloud Storage](https://firebase.google.com/products/storage?hl=ko)

-- 앱 DB에 저장된 이미지 URL을 통해 Storage의 이미지를 호출한다.

- [Crashlytics](https://firebase.google.com/products/crashlytics?hl=ko)

-- 앱 이슈가 생기면 바로 대응할 수 있도록 Crashlytics를 세팅한다.

- [Google 애널리틱스](https://firebase.google.com/products/analytics?hl=ko)

-- 활성 사용자가 어느 정도되는지 파악한다.


## :facepunch: Local Database : Realm

Firebase에서 받은 선물 및 질문 리스트를 Realm에 저장하여 관리한다.

- Firebase에서 데이터를 받았을 때, Data Create and Insert가 있고 이후에는 오직 조회만 한다.
- 파이어베이스의 데이터를 갱신하고 수기로 `Updates` 컬렉션을 수정하게 되는데, `Updates`가 이전 값과 다를 경우에 데이터를 갱신하게 된다.
- Firestore 내부의 `DocumentSnapshot`으로 데이터 스냅샷을 Object화 시킨 다음 Realm DB에 저장한다.
- `deleteRealmIfMigrationNeeded` 사용으로 별도의 마이그레이션을 하지 않는다. 필요에 따라 마이그레이션이 되는 형태로 수정할 수도 있다.
- in 메소드로 원하는 데이터를 찾는다. (아래 코드 참조)
- not 메소드로 해당 값을 포함하지 않는 결과값만 가지고 온다. (아래 코드 참조)

```bash
mRealm.where<Present>()
            .not()
            .beginGroup()
            .`in`("tags.tag", tagList)
            .endGroup()
            .between("price", _startPrice, _endPrice).or().equalTo("price",0L)
            .findAll()
```


## :pouting_cat: Github branching
- 개인 Branch에서 작업을 한다.
- 개인 Branch에서 Dev 브랜치로 PR을 올린다.
- 릴리즈 전에 Dev 브랜치에서 Master 브랜치로 PR을 올린다.


## :open_file_folder: Open Source

- `bumptech/glide`

이미지 로딩 라이브러리

- `airbnb/lottie-android`

네이티브 앱에서 high-quality 애니메이션(Json 파일)을 추가.


## :whale: Todo

- 선물 및 질문 데이터를 추가한다.
- 가격대 선택이 좀 더 유연하게 선택되도록 한다.


## :baby_chick: Demo
<p float="left">
    <img src="https://lh3.googleusercontent.com/iYHEwh2_Q6nIKS67eItV4AwIokeJDNe0ojtpWGqKpRyhaRlmCSmBcnkFNCmXbTkajKA=w2560-h1330-rw" width=200 />
    <img src="https://lh3.googleusercontent.com/xl0sqT6Jz1p9Gq9slw4VXRr-akf4v74b_k3QkZUMZPvYV37-e5LqTZcOjofof4Xyl48=w2560-h1330-rw" width=200 />
    <img src="https://lh3.googleusercontent.com/JqUUXWSgU0bhSBpOObERLvfUGE3eBnInmYvDMY3S2aAatyeFKLOifWnBLgZ0KLGbmA=w2560-h1330-rw" width=200 />
    <img src="https://lh3.googleusercontent.com/AdN5fkguQMSc4M6iVkAFONsuxZhOQaKE7TDzuhF56FgDLORAnBv8160W7vva4a6kFBg=w2560-h1330-rw" width=200 />
    <img src="https://lh3.googleusercontent.com/ruDvvtKehqGB_4PX7QBsUY2RLDe_v6g5FL-_XmC6SUGjKUQqa08Uy-DtsNi8wYuuXU4=w2560-h1330-rw" width=200 />
</p>
