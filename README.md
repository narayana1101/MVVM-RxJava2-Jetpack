# MVVM-RxJava2-Jetpack
A sample application which fetches open pull requests from Github using MVVM, functional reactive programming and Google Jetpack components.

# Brief Overview

Fetches the list of open pull requests for the user's public repository specified. Uses the following:
- Model-View-ViewModel architecture with Data Binding
- RxJava2 + Retrofit/OkHTTP for managing network calls.
- RxJava2 + Room for Cache Synchronization/Persistence.
- RxJava2 for generic functional reactive programming needs.
- Dagger2 for Dependency Injection.
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
