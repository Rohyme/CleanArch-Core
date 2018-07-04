# Clean Architecture Core
 Android architecture core using [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) and [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) as presenter architecture 
Clean Architecture is used to achieve  separation of concerns by dividing the software to layers and that's exactly what we are going to do here :
**I have divided Core to 3 Layers**
- Domain layer (Business Rules)
- Data layer
- Presenter Layer (MVVM)

 ## Our Architecture Layers 
### 1. Domain Layer
 Domain layer is the business rules layer. it the baseline of your application  . it's abstracted layers contains     interfaces for all of your business logic which will be implemented in the upper layers such as Data

  ![Domain](https://image.ibb.co/ja7vmd/domain.png "Domain contents")

 + **Entites**
  Package contains dataclasses that describe the basic data structure you will work with .
![entities](https://image.ibb.co/iDvb9y/entities.png "entities package")
     
```kotlin
    data class GuestEntity(@SerializedName("success") val isSuccess: Boolean,
                       @SerializedName("guest_session_id") val guestSessionId: String,
                       @SerializedName("expires_at") val expiredAt: String)
```
+ **Base Interactors**
  It has abstract classes that UseCasesClasses will extend later . Here i have made 3 types 
    (CompletableUseCase , SingleUseCase , FlowableUseCase) 
They all need observeOnSchedular and subscribeOnSchedular in their constractor 
![Base Interactors](https://image.ibb.co/ei269y/Screenshot_from_2018_07_04_14_16_38.png "Base Interactors")

+ **BaseData**
It has interfaces of :
  - BaseCacheI : Interface with functions used in all cache classes
  - BaseRemoteI:Interface with functions used in all cache classes
  - BaseDataStoreFactory : used to get the suitable dataStore(cache , remote) 
  ![Base data package](https://image.ibb.co/g90Uwd/base_Data_Base.png "Base data package")

+ **Business Logic**
Package contains two packages :
    - UseCaseImplementations : it contains all usecases classes which extend base interactors abstract classes
    ![useCases](https://image.ibb.co/bMBcGd/useCases.png "useCases")

    - Business case data logic interfaces
    ![alt text](https://image.ibb.co/n9Qewd/business_Data_Logic.png "Business data package")
               
-- CaseDataStore : interface has all functions in common in caseCacheI and caseRemoteI 

-- CaseCacheI    : interface has functions that only needed in the cache class e.g: savingDataToCache implements BaseCacheI

-- CaseRemoteI   : interface has functions that only needed in the remote class implements  BaseCacheI
                
                
**.....To Be Continued**
