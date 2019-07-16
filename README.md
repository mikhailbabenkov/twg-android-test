# TWG Android Test
Changes:

1. Introduce AndroidX
2. Convert classes to Kotlin
3. Extract layers (services, datasources, repo, usecases, viewmodels)
4. Refactor adapter, some activities, viewholders.
5. Introduce DI
6. Introduce Unit testing with JUnit5 (as proof of concept)
7. Coroutines
8. MVVM
9. Livedata

Out of scope:

1. Didnt touch POJOs and didnt extract proper models for each layer
3. Didnt touch UI/UX
4. The app is not handling all edge cases (like proper error handling , havent checked Barcode activity assuming it s working)
5. Didnt refactor SearchActivity , BarCodeActivity
6. No local storage apart from storing userId
7. Comments in the code

Further refactoring can be performed as there is no 100% perfect code . The scope is big and i was trying to cover a lot of things . If you have any questions Or if you want me to add smth or explain smth Please dont hesitate.

