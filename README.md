# MVVM-RxJava2-Jetpack
A bare-bones application which fetches open pull requests from Github using MVVM, functional reactive programming and Android jetpack components.

# Brief Overview

Fetches the list of open pull requests for the user's public repository specified. Uses the following:
- Model-View-ViewModel architecture with Data Binding
- RxJava2 + Retrofit/OkHTTP for managing network calls.
- RxJava2 + Room for Caching/Persistence.
- Dagger for Dependency Injection.
- Reactive Streams to convert RxJava2 streams to LiveData observables.
- ViewModel + LiveData for Lifecycle aware view modifications.
- Picasso for image loading.

# Libraries

- Jetpack 
  - ViewModel
  - LiveData
  - Room
  - ReactiveStreams
  
- Third Party Libraries
  - RxJava2
  - Dagger2
  - Retrofit/OkHTTP
  - Picasso
