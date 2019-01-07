# MVVM-RxJava2-Jetpack
A bare-bones application which fetches open pull requests from Github using MVVM, functional reactive programming and Android jetpack components.

# Brief Overview
Fetches the list of open pull requests for the user's public repository specified. Uses the following:
- MVVM with Data Binding
- RxJava2 + Retrofit/OkHTTP for managing network calls.
- RxJava2 + Room for Caching/Persistence.
- Dagger for Dependency Injection.
- ViewModel + LiveData for Lifecycle aware view modifications.
- Picasso for image loading.

# Features
- Architecture/Design
  - Model-View-ViewModel
  - Dependency Injection
  - Functional Reactive Programming
  
- Jetpack Components 
  - ViewModel
  - LiveData
  - Room
- Third Party Libraries
  - RxJava
  - Dagger
  - Retrofit/OkHTTP
  - Picasso
