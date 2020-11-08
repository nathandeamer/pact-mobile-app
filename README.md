Steps:
1. make compile (Runs all code and unit tests)
2. make pact-consumer (Runs and publishes pacts)
3. make ENV=dev pact-can-i-deploy (Checks if the contract is met by a provider)



4. ./gradlew clean pactProvider
5.  ./gradlew -Dpact.verifier.publishResults=true -Dpact.provider.version=1.2 -Dpact.provider.tag=dev clean pactProvider 