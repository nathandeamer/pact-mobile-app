compile:
	./gradlew clean build

pact-consumer-pr:
	./gradlew pactConsumer



pact-can-i-deploy:



pact-provider:
	./gradlew $$GRADLE_PROXY pactProvider

pact-consumer-publish:
	./gradlew $$GRADLE_PROXY pactConsumer
	./gradlew $$GRADLE_PROXY pactConsumerPublish

pact-provider-publish:
	./gradlew $$GRADLE_PROXY pactProviderPublishResult
