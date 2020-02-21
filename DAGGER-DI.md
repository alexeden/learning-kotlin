# Android DI with Dagger

@Inject — base annotation whereby the “dependency is requested”
@Module — classes which methods “provide dependencies”
@Provide — methods inside @Module, which “tell Dagger how we want to build and present a dependency“
@Component — bridge between @Inject and @Module
